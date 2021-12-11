package com.hodor.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hodor.constants.JsonResult;
import com.hodor.constants.Meta;
import com.hodor.dao.PetDao;
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

import java.text.SimpleDateFormat;
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
    @Autowired
    private PetDao petMapper;


    /**
     * 查询预约列表信息
     * @param query 搜索词
     * @param pageno 页码
     * @param pagesize 页大小
     * @return
     */
    @Override
    public JsonResult<Map<String, Object>> getReserveListByQuery(String query, Integer pageno, Integer pagesize) {
        Map<String, Object> map = new HashMap<>();
        if(pageno < 1) {
            map.put("total", 0);
            map.put("pagenum", pageno);
            map.put("applies", new ArrayList<>());
            return new JsonResult<Map<String, Object>>().setMeta(new Meta("获取失败", 500L)).setData(map);
        }
        PageHelper.startPage(pageno, pagesize);
        List<Reservation> reserveListByQueryLimit = reserveMapper.getReserveListByQueryLimit(query);
        PageInfo pageRes = new PageInfo(reserveListByQueryLimit);
        Meta meta = new Meta("获取成功", 200L);
        map.put("total", pageRes.getTotal());
        map.put("pagenum", pageRes.getPageNum());
        map.put("applies", reserveListByQueryLimit);
        return new JsonResult<Map<String, Object>>().setMeta(meta).setData(map);
    }

    /**
     * 根据ID查询
     * @param id 预约id
     * @return
     */
    @Override
    public JsonResult<Reservation> getReserveListById(Long id) {
        Reservation reserveById = reserveMapper.getReserveById(id);
        if(reserveById == null) {
            throw new PetBackendException("预约不存在");
        }
        Meta meta = new Meta("获取成功", 200L);
        return new JsonResult<Reservation>().setMeta(meta).setData(reserveById);
    }

    /**
     * 添加预约信息
     * @param reservation 预约信息
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public JsonResult<Reservation> addReserve(Reservation reservation) {
        validReserveAdd(reservation);

        Reservation reserveByPid = reserveMapper.getReserveByPid(reservation.getP_id());
        if(reservation.getState() != null && reservation.getState() == 1 && reserveByPid != null)
            throw new PetBackendException("这个宠物已经预约审核通过，预约人：" + reserveByPid.getU_id());
        if(reservation.getAdopt() != null && reservation.getAdopt() == 1)
            throw new PetBackendException("新增的记录不能为线下审核通过");
        reserveMapper.addReserve(reservation);
        return new JsonResult<List<UserListVO>>().setMeta(new Meta("添加成功", 201L))
                .setData(reservation);
    }

    /**
     * 更新预约信息
     * @param id 预约id
     * @param state 线上预约状态
     * @return
     */
    @Override
    public JsonResult<ReserveUpdateVO> updateReserveState(Long id, Integer state) {
        Reservation reserveById = reserveMapper.getReserveById(id);
        if(reserveById == null)
            throw new PetBackendException("预约记录不存在");
        Reservation reserveByPid = reserveMapper.getReserveByPid(reserveById.getP_id());
        if(state != null && state == 1 && reserveByPid != null) {
            throw new PetBackendException("这个宠物已经预约审核通过，预约人：" + reserveByPid.getU_id());
        }
        reserveById.setState(state);
        reserveMapper.updateReserve(reserveById);
        return new JsonResult<ReserveUpdateVO>().setMeta(new Meta("修改成功", 201L))
                .setData(new ReserveUpdateVO().setId(id).setState(state));
    }

    /**
     * 删除预约信息
     * @param id 预约id
     * @return
     */
    @Override
    public JsonResult deleteById(Long id) {
        reserveMapper.deleteById(id);
        return new JsonResult().setMeta(new Meta("删除成功", 204L)).setData(null);
    }

    /**
     * 更新预约信息
     * @param id 预约id
     * @param reservation 预约信息
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public JsonResult<Reservation> updateReserve(Long id, Reservation reservation) {
        if(id == null) {
            throw new PetBackendException("id为空");
        };
        reservation.setId(id);
        reserveMapper.updateReserve(reservation);
        if(Objects.equals(reservation.getAdopt(), 1)) {
            Pet pet = new Pet();
            pet.setId(reservation.getP_id());
            pet.setState(1);
            petMapper.updatePet(pet);
        }
        return new JsonResult<Reservation>().setMeta(new Meta("修改成功", 201L))
                .setData(reservation);
    }

    @Override
    public void cancelReserve(Long id, Integer state) {
        Reservation reserveByPid = reserveMapper.getReserveById(id);
        if(Objects.isNull(reserveByPid)) {
            throw new PetBackendException("审批不存在");
        }

        if(reserveByPid.getState().equals(-1)) {
            //在某一个时间点上加两小时的写法
            Calendar calendar = Calendar.getInstance();
            //此处setTime为Date类型
            calendar.setTime(reserveByPid.getTime());
            //加上两小时
            calendar.add(Calendar.HOUR, -2);//时
            //进行时间格式化
            Date newTime = calendar.getTime();
            Date now = new Date();
            if(!now.before(newTime)) {
                throw new PetBackendException("活动开始前两小时内不能取消");
            }
        }
        Reservation reservation = new Reservation();
        reservation.setId(id);
        reservation.setState(state);
        reserveMapper.updateReserve(reservation); }

    private void validReserveAdd(Reservation reservation) {
        if(reservation.getP_id() == null)
            throw new PetBackendException("宠物id为空");
        if(reservation.getU_id() == null)
            throw new PetBackendException("领养人id为空");
        if(reservation.getTime() == null)
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
