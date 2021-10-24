package com.hodor.dao;

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
                           @Param("start") Long pageno,
                           @Param("size") Long pagesize);

    List<User> getUserListByQuery(@Param("query") String query);

    Integer addUser(User user);
}
