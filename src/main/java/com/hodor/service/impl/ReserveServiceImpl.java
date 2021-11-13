package com.hodor.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hodor.constants.JsonResult;
import com.hodor.constants.Meta;
import com.hodor.dao.ReserveDao;
import com.hodor.dao.UserDao;
import com.hodor.dto.ReservationDTO;
import com.hodor.dto.UserAddDTO;
import com.hodor.exception.PetBackendException;
import com.hodor.pojo.Pet;
import com.hodor.pojo.Reservation;
import com.hodor.pojo.User;
import com.hodor.service.ReserveService;
import com.hodor.service.UserService;
import com.hodor.util.IdCardUtils;
import com.hodor.vo.reserve.ReserveUpdateVO;
import com.hodor.vo.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 预约信息相关
 * @Author limingli006
 * @Date 2021/10/17
 */
@Service
public class ReserveServiceImpl implements ReserveService {

    @Autowired
    private ReserveDao reserveMapper;


    /**
     * 查询预约列表信息
     * @param query
     * @param pageno
     * @param pagesize
     * @return
     */
    @Override
    public JsonResult<Map<String, Object>> getReserveListByQuery(String query, Integer pageno, Integer pagesize) {
        Map<String, Object> map = new HashMap<>();
        if(pageno < 1) {
            map.put("total", 0);
            map.put("pagenum", pageno);
            map.put("applies", new ArrayList<>());
            return new JsonResult<List<UserListVO>>().setMeta(new Meta("获取失败", 400L)).setData(map);
        }
        PageHelper.startPage(pageno, pagesize);
        List<Reservation> reserveListByQueryLimit = reserveMapper.getReserveListByQueryLimit(query);
        PageInfo pageRes = new PageInfo(reserveListByQueryLimit);
        List<ReservationDTO> res = new ArrayList<>();
        reserveListByQueryLimit.stream().forEach(r -> res.add(transReserveDOtoDTO(r)));
        Meta meta = new Meta("获取成功", 200L);
        map.put("total", pageRes.getTotal());
        map.put("pagenum", pageRes.getPageNum());
        map.put("applies", res);
        return new JsonResult<List<UserListVO>>().setMeta(meta).setData(map);
    }

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    @Override
    public JsonResult<ReservationDTO> getReserveListById(Long id) {
        Reservation reserveById = reserveMapper.getReserveById(id);
        if(reserveById == null) {
            throw new PetBackendException("预约不存在");
        }
        Meta meta = new Meta("获取成功", 200L);
        ReservationDTO reservationDTO = transReserveDOtoDTO(reserveById);
        return new JsonResult<ReservationDTO>().setMeta(meta).setData(reservationDTO);
    }

    /**
     * 添加预约信息
     * @param reservationDTO
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public JsonResult<ReservationDTO> addReserve(ReservationDTO reservationDTO) {
        validReserveAdd(reservationDTO);

        Reservation reservation = transReserveDTOtoDO(reservationDTO);
        Reservation reserveByPid = reserveMapper.getReserveByPid(reservation.getP_id());
        if(reservationDTO.getState() != null && reservationDTO.getState() && reserveByPid != null)
            throw new PetBackendException("这个宠物已经预约审核通过");
        if(reservationDTO.getAdopt() != null && reservationDTO.getAdopt())
            throw new PetBackendException("新增的记录不能为线下审核通过");
        reserveMapper.addReserve(reservation);
        ReservationDTO reservationDTO1 = transReserveDOtoDTO(reservation);
        return new JsonResult<List<UserListVO>>().setMeta(new Meta("添加成功", 201L))
                .setData(reservationDTO1);
    }

    /**
     * 更新预约信息
     * @param id
     * @param state
     * @return
     */
    @Override
    public JsonResult<ReserveUpdateVO> updateReserveState(Long id, Boolean state) {
        Reservation reserveById = reserveMapper.getReserveById(id);
        if(reserveById == null)
            throw new PetBackendException("预约记录不存在");
        Reservation reserveByPid = reserveMapper.getReserveByPid(reserveById.getP_id());
        if(state && reserveByPid != null) {
            throw new PetBackendException("这个宠物已经预约审核通过");
        }
        reserveById.setState(state ? 1 : 0);
        reserveMapper.updateReserve(reserveById);
        return new JsonResult<ReserveUpdateVO>().setMeta(new Meta("修改成功", 201L))
                .setData(new ReserveUpdateVO().setId(id).setState(state ? 1 : 0));
    }

    /**
     * 删除预约信息
     * @param id
     * @return
     */
    @Override
    public JsonResult deleteById(Long id) {
        reserveMapper.deleteById(id);
        return new JsonResult().setMeta(new Meta("删除成功", 204L)).setData(null);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public JsonResult<ReservationDTO> updateReserve(Long id, ReservationDTO reservationDTO) {
        if(id == null) {
            throw new PetBackendException("id为空");
        }

        //更新线上预约
        Reservation reserveByPid = reserveMapper.getReserveByPid(reservationDTO.getP_id());
        if(reservationDTO.getState() != null && reservationDTO.getState() && reserveByPid != null)
            throw new PetBackendException("这个宠物已经预约审核通过");
        Reservation reservation = transReserveDTOtoDO(reservationDTO);
        reservation.setId(id);
        reservation.setAdopt(null);
        reserveMapper.updateReserve(reservation);

        //更新线下预约
        Reservation reserveByPid2 = reserveMapper.getReserveByPid(reservationDTO.getP_id());
        Reservation reserveByPidAndAdopt = reserveMapper.getReserveByPidAndAdopt(reservationDTO.getP_id());
        if(reservationDTO.getAdopt() != null && reservationDTO.getAdopt()) {
            if(reserveByPidAndAdopt != null)
                throw new PetBackendException("这个宠物已经线下审核通过");
            if(reserveByPid2 == null)
                throw new PetBackendException("这个宠物线上审核未通过");
        }
        reservation = transReserveDTOtoDO(reservationDTO);
        reservation.setId(id);
        reservation.setState(null);
        reserveMapper.updateReserve(reservation);

        Reservation reserveById = reserveMapper.getReserveById(id);
        if(reserveById == null) {
            throw new PetBackendException("预约不存在");
        }
        ReservationDTO reservationDTO1 = transReserveDOtoDTO(reserveById);
        return new JsonResult<UserUpdateStateVO>().setMeta(new Meta("修改成功", 201L))
                .setData(reservationDTO1);
    }

    private void validReserveAdd(ReservationDTO reservationDTO) {
        if(reservationDTO.getP_id() == null)
            throw new PetBackendException("宠物id为空");
        if(reservationDTO.getU_id() == null)
            throw new PetBackendException("领养人id为空");
        if(reservationDTO.getTime() == null)
            throw new PetBackendException("预约时间为空");
    }

    private Reservation transReserveDTOtoDO(ReservationDTO reservationDTO) {
        Reservation reservation = new Reservation();
        reservation.setP_id(reservationDTO.getP_id());
        reservation.setU_id(reservationDTO.getU_id());
        reservation.setTime(reservationDTO.getTime());
        reservation.setState(reservationDTO.getState() == null ? 2 : (reservationDTO.getState() ? 1 : 0));
        reservation.setAdopt(reservationDTO.getAdopt() == null ? 2 : (reservationDTO.getAdopt() ? 1 : 0));
        return reservation;
    }

    private ReservationDTO transReserveDOtoDTO(Reservation reservation) {
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setId(reservation.getId());
        reservationDTO.setP_id(reservation.getP_id());
        reservationDTO.setU_id(reservation.getU_id());
        if(reservation.getState() == 0){
            reservationDTO.setState(false);
        } else if (reservation.getState() == 1) {
            reservationDTO.setState(true);
        } else {
            reservationDTO.setState(null);
        }

        if(reservation.getAdopt() == 0){
            reservationDTO.setAdopt(false);
        } else if (reservation.getAdopt() == 1) {
            reservationDTO.setAdopt(true);
        } else {
            reservationDTO.setAdopt(null);
        }
        reservationDTO.setTime(reservation.getTime());
        return reservationDTO;
    }
}
