package com.hodor.dao;

import com.hodor.pojo.ActivityPerson;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author limingli006
 * @Date 2021/10/17
 */
@Mapper
public interface ActivityPersonDao {

    Integer insertActivityPerson(ActivityPerson activityPerson);
}
