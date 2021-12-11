package com.hodor.web;

import com.hodor.constants.JsonResult;
import com.hodor.constants.Meta;
import com.hodor.dto.ReservationDTO;
import com.hodor.dto.ReserveUpdateDTO;
import com.hodor.dto.UserAddDTO;
import com.hodor.pojo.Reservation;
import com.hodor.service.ReserveService;
import com.hodor.service.UserService;
import com.hodor.vo.reserve.ReserveUpdateVO;
import com.hodor.vo.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author limingli006
 * @Date 2021/10/17
 */
@RestController
@CrossOrigin
public class ReserveController {
    @Autowired
    private ReserveService reserveService;

    /**
     * 添加预约信息
     * @param reservation
     * @return
     */
    @PostMapping("/user/applies")
    public JsonResult<Reservation> addReserve(@RequestBody Reservation reservation) {
        try {
            JsonResult<Reservation> reservationDTOJsonResult = reserveService.addReserve(reservation);
            return reservationDTOJsonResult;
        } catch (Exception e) {
            return new JsonResult<ReservationDTO>().setMeta(new Meta("添加失败:" + e.getMessage(), 500L))
                    .setData(null);
        }
    }

    /**
     * 根据id查询预约信息
     * @param id
     * @return
     */
    @GetMapping("/user/applies/{id}")
    public JsonResult<Reservation> getReserveByQuery(@PathVariable Long id) {
        try {
            JsonResult<Reservation> reserveListById = reserveService.getReserveListById(id);
            return reserveListById;
        } catch (Exception e) {
            return new JsonResult<Reservation>().setMeta(new Meta("获取失败:" + e.getMessage(), 500L))
                    .setData(null);
        }
    }

    /**
     * 更新预约信息
     * @param id
     * @param reservation
     * @return
     */
    @PutMapping("/user/applies/{id}")
    public JsonResult<Reservation> updateReserveById(@PathVariable Long id, @RequestBody Reservation reservation) {
        try {
            JsonResult<Reservation> reservationDTOJsonResult = reserveService.updateReserve(id, reservation);
            return reservationDTOJsonResult;
        } catch (Exception e) {
            return new JsonResult<ReservationDTO>().setMeta(new Meta("修改失败:" + e.getMessage(), 500L))
                    .setData(null);
        }
    }

    /**
     * 获取预约列表
     * @param query
     * @param pagenum
     * @param pagesize
     * @return
     */
    @GetMapping("/user/applies")
    public JsonResult<Map<String, Object>> getUserListByQuery(@RequestParam(required = false) String query,
                                                              @RequestParam(required = false, defaultValue = "1") Integer pagenum,
                                                              @RequestParam(required = false, defaultValue = "10") Integer pagesize) {
        try {
            JsonResult<Map<String, Object>> reserveListByQuery = reserveService.getReserveListByQuery(query, pagenum, pagesize);
            return reserveListByQuery;
        } catch (Exception e) {
            return new JsonResult<Map<String, Object>>().setMeta(new Meta("获取失败:" + e.getMessage(), 500L))
                    .setData(null);
        }
    }

    /**
     * 根据id删除预约信息
     * @param id
     * @return
     */
    @DeleteMapping("/user/applies/{id}")
    public JsonResult deleteById(@PathVariable Long id) {
        try {
            JsonResult jsonResult = reserveService.deleteById(id);
            return jsonResult;
        } catch (Exception e) {
            return new JsonResult<UserUpdateStateVO>().setMeta(new Meta("删除失败:" + e.getMessage(), 500L))
                    .setData(null);
        }
    }

    @PutMapping("/user/applies/{id}/state/{state}")
    public JsonResult updateUser(@PathVariable Long id, @PathVariable Integer state) {
        try {
            JsonResult<ReserveUpdateVO> reserveUpdateVOJsonResult = reserveService.updateReserveState(id, state);
            return reserveUpdateVOJsonResult;
        } catch (Exception e) {
            return new JsonResult<ReserveUpdateVO>().setMeta(new Meta("修改失败:" + e.getMessage(), 500L)).setData(new ReserveUpdateVO());
        }
    }

    @PostMapping("/user/applies/cancel")
    public JsonResult<ReserveUpdateDTO> cancelReserve(@RequestParam Long id, @RequestParam Integer state) {
        try {
            reserveService.cancelReserve(id, state);
            return new JsonResult<ReserveUpdateDTO>().setMeta(new Meta("取消成功", 201L))
                    .setData(new ReserveUpdateDTO(id, state));
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult<ReserveUpdateDTO>().setMeta(new Meta("取消失败" + e.getMessage(), 500L))
                    .setData(null);
        }
    }
}
