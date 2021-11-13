package com.hodor.service;

import com.hodor.constants.JsonResult;
import com.hodor.dto.ReservationDTO;
import com.hodor.dto.UserAddDTO;
import com.hodor.pojo.Reservation;
import com.hodor.vo.reserve.ReserveUpdateVO;
import com.hodor.vo.user.*;

import java.util.Map;

public interface ReserveService {

    JsonResult<Map<String, Object>> getReserveListByQuery(String query, Integer pageno, Integer pagesize);

    JsonResult<Reservation> getReserveListById(Long id);

    JsonResult<Reservation> addReserve(Reservation reservation);

    JsonResult<ReserveUpdateVO> updateReserveState(Long id, Integer state);

    JsonResult deleteById(Long id);

    JsonResult<Reservation> updateReserve(Long id, Reservation reservation);
}
