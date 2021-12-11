package com.hodor.dao;

import com.hodor.pojo.ActivityPerson;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Author limingli006
 * @Date 2021/10/17
 */
@Mapper
public interface ActivityPersonDao {

    Integer insertActivityPerson(ActivityPerson activityPerson);

    ActivityPerson getByAidAndUid(@Param("aId") Long aId, @Param("uId") Long uId);
}
