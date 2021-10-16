package com.hodor.service;

import com.hodor.constants.JsonResult;
import com.hodor.pojo.User;

public interface UserService {

    /**
     * 账密查询用户
     * @param id
     * @param passWord
     * @return
     */
    JsonResult<User> getUserByUserNameAndPassWord(Long id, String passWord);
}
