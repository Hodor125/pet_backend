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
        map.put("applies", reserveListByQueryLimit);
        return new JsonResult<List<UserListVO>>().setMeta(meta).setData(map);
    }

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

    @Override
    public JsonResult<ReservationDTO> addReserve(ReservationDTO reservationDTO) {
        validReserveAdd(reservationDTO);
        Reservation reservation = transReserveDTOtoDO(reservationDTO);
        Reservation reserveByPid = reserveMapper.getReserveByPid(reservation.getP_id());
        if(reservationDTO.getState() != null && reservationDTO.getState() && reserveByPid != null)
            throw new PetBackendException("这个宠物已经被领养");
        reserveMapper.addReserve(reservation);
        ReservationDTO reservationDTO1 = transReserveDOtoDTO(reservation);
        return new JsonResult<List<UserListVO>>().setMeta(new Meta("添加成功", 200L))
                .setData(reservationDTO1);
    }

    @Override
    public JsonResult<ReserveUpdateVO> updateReserveState(Long id, Boolean state) {
        Reservation reserveById = reserveMapper.getReserveById(id);
        if(reserveById == null)
            throw new PetBackendException("预约记录不存在");
        Reservation reserveByPid = reserveMapper.getReserveByPid(reserveById.getP_id());
        if(state && reserveByPid != null) {
            throw new PetBackendException("这个宠物已经被领养");
        }
        reserveById.setState(state ? 1 : 0);
        reserveMapper.updateReserve(reserveById);
        return new JsonResult<ReserveUpdateVO>().setMeta(new Meta("修改成功", 200L))
                .setData(new ReserveUpdateVO().setId(id).setState(state ? 1 : 0));
    }

    @Override
    public JsonResult deleteById(Long id) {
        reserveMapper.deleteById(id);
        return new JsonResult().setMeta(new Meta("删除成功", 200L)).setData(null);
    }

    @Override
    public JsonResult<ReservationDTO> updateReserve(Long id, ReservationDTO reservationDTO) {
        if(id == null) {
            throw new PetBackendException("id为空");
        }
        Reservation reserveByPid = reserveMapper.getReserveByPid(reservationDTO.getP_id());
        if(reserveByPid != null)
            throw new PetBackendException("这个宠物已经被领养");
        Reservation reservation = transReserveDTOtoDO(reservationDTO);
        reservation.setId(id);
        reserveMapper.updateReserve(reservation);
        Reservation reserveById = reserveMapper.getReserveById(id);
        if(reserveById == null) {
            throw new PetBackendException("用户不存在");
        }
        ReservationDTO reservationDTO1 = transReserveDOtoDTO(reserveById);
        return new JsonResult<UserUpdateStateVO>().setMeta(new Meta("修改成功", 200L))
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
        reservationDTO.setTime(reservation.getTime());
        return reservationDTO;
    }
}
