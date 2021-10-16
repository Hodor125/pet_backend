package com.hodor.web;

import com.hodor.constants.JsonResult;
import com.hodor.pojo.User;
import com.hodor.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author limingli006
 * @Date 2021/10/17
 */
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/user/login")
    public JsonResult<User> login(@RequestParam Long id, @RequestParam String password) {
        JsonResult<User> userByUserNameAndPassWord = userService.getUserByUserNameAndPassWord(id, password);
        return userByUserNameAndPassWord;
    }
}
