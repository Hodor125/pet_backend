package com.hodor.web;

import com.hodor.constants.JsonResult;
import com.hodor.dto.UserAddDTO;
import com.hodor.pojo.User;
import com.hodor.service.UserService;
import com.hodor.vo.user.UserAddVO;
import com.hodor.vo.user.UserListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author limingli006
 * @Date 2021/10/17
 */
@RestController
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/user/login")
    public JsonResult<User> login(@RequestParam Long id, @RequestParam String password) {
        JsonResult<User> userByUserNameAndPassWord = userService.getUserByUserNameAndPassWord(id, password);
        return userByUserNameAndPassWord;
    }

    /**
     * 获取用户列表
     * @param query
     * @param pagenum
     * @param pagesize
     * @return
     */
    @GetMapping("/user/users")
    public JsonResult<List<UserListVO>> getUserListByQuery(@RequestParam(required = false) String query,
                                                           @RequestParam(required = false, defaultValue = "1") Long pagenum,
                                                           @RequestParam(required = false, defaultValue = "10") Long pagesize) {
        JsonResult<List<UserListVO>> userListByQuery = userService.getUserListByQuery(query, pagenum, pagesize);
        return userListByQuery;
    }

    @PostMapping("/user/users")
    public JsonResult<UserAddVO> addUser(@RequestBody UserAddDTO userAddDTO) {
        JsonResult<UserAddVO> userAddVOJsonResult = userService.addUser(userAddDTO);
        return userAddVOJsonResult;
    }
}
