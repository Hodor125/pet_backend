package com.hodor.dao;

import com.hodor.pojo.Activity;
import com.hodor.pojo.Reservation;
import com.hodor.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author limingli006
 * @Date 2021/10/17
 */
@Mapper
public interface ReserveDao {

    List<Reservation> getReserveListByQueryLimit(@Param("query") String query,
                                                 @Param("start") Long pageno,
                                                 @Param("size") Long pagesize);

    List<Reservation> getReserveListByQuery(@Param("query") String query);

    Reservation getReserveById(@Param("id") Long id);

    Reservation getReserveByPid(@Param("pId") Long pId);

    Integer addReserve(Reservation reservation);

    Integer updateReserve(Reservation reservation);

    Integer deleteById(Long id);
}
