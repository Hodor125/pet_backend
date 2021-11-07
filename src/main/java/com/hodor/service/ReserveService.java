package com.hodor.service;

import com.hodor.constants.JsonResult;
import com.hodor.dto.ReservationDTO;
import com.hodor.dto.UserAddDTO;
import com.hodor.vo.reserve.ReserveUpdateVO;
import com.hodor.vo.user.*;

import java.util.Map;

public interface ReserveService {

    JsonResult<Map<String, Object>> getReserveListByQuery(String query, Long pageno, Long pagesize);

    JsonResult<ReservationDTO> getReserveListById(Long id);

    JsonResult<ReservationDTO> addReserve(ReservationDTO reservationDTO);

    JsonResult<ReserveUpdateVO> updateReserveState(Long id, Boolean state);

    JsonResult deleteById(Long id);

    JsonResult<ReservationDTO> updateReserve(Long id, ReservationDTO reservationDTO);
}
