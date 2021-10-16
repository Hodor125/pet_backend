package com.hodor.dao;

import com.hodor.pojo.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author limingli006
 * @Date 2021/10/17
 */
@Mapper
public interface UserDao {

    User getUserByUserNameAndPassWord(Long id, String passWord);
}
