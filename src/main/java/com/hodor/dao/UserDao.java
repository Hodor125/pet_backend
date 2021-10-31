package com.hodor.dao;

import com.hodor.constants.JsonResult;
import com.hodor.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author limingli006
 * @Date 2021/10/17
 */
@Mapper
public interface UserDao {

    User getUserByUserNameAndPassWord(Long id, String passWord);

    List<User> getUserListByQueryLimit(@Param("query") String query,
                                       @Param("power") Long power,
                           @Param("start") Long pageno,
                           @Param("size") Long pagesize);

    List<User> getUserListByQuery(@Param("query") String query,
                                  @Param("power") Long power);

    User getUserListById(@Param("id") Long id);

    Integer addUser(User user);

    Integer updateUser(User user);

    Integer deleteById(Long id);
}
