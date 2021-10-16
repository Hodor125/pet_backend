package com.hodor.service.impl;

import com.hodor.constants.JsonResult;
import com.hodor.constants.Meta;
import com.hodor.dao.UserDao;
import com.hodor.pojo.User;
import com.hodor.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户信息相关
 * @Author limingli006
 * @Date 2021/10/17
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userMapper;

    @Override
    public JsonResult<User> getUserByUserNameAndPassWord(Long id, String passWord) {
        User userByUserNameAndPassWord = userMapper.getUserByUserNameAndPassWord(id, passWord);
        if(userByUserNameAndPassWord != null) {
            Meta meta = new Meta("success", 10000L);
            return new JsonResult<User>().setMeta(meta).setData(userByUserNameAndPassWord);
        }
        return null;
    }
}
