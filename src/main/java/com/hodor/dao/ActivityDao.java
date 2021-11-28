package com.hodor.dao;

import com.hodor.pojo.Activity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author limingli006
 * @Date 2021/10/17
 */
@Mapper
public interface ActivityDao {

    List<Activity> getActivityListByQueryLimit(@Param("query") String query);

    List<Activity> getActivityListByQuery(@Param("query") String query);

    Activity getActivityById(@Param("id") Long id);

    List<Activity> getActivityByUserId(@Param("userIds") List<Long> userIds);

    Integer addActivity(Activity activity);

    Integer updateActivity(Activity activity);

    Integer deleteById(Long id);
}
