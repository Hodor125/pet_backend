package com.hodor.web;

import com.hodor.constants.JsonResult;
import com.hodor.constants.Meta;
import com.hodor.dto.UserAddDTO;
import com.hodor.pojo.User;
import com.hodor.service.UserService;
import com.hodor.vo.user.UserAddVO;
import com.hodor.vo.user.UserListVO;
import com.hodor.vo.user.UserUpdateStateVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public JsonResult<Map<String, Object>> getUserListByQuery(@RequestParam(required = false) String query,
                                                              @RequestParam(required = false) Long power,
                                                           @RequestParam(required = false, defaultValue = "1") Long pagenum,
                                                           @RequestParam(required = false, defaultValue = "10") Long pagesize) {
        JsonResult<Map<String, Object>> userListByQuery = userService.getUserListByQuery(query, power, pagenum, pagesize);
        return userListByQuery;
    }

    /**
     * 添加用户
     * @param userAddDTO
     * @return
     */
    @PostMapping("/user/users")
    public JsonResult<UserAddVO> addUser(@RequestBody UserAddDTO userAddDTO) {
        JsonResult<UserAddVO> userAddVOJsonResult = userService.addUser(userAddDTO);
        return userAddVOJsonResult;
    }

    @PutMapping("/user/users/{uId}/state/{state}")
    public JsonResult updateUser(@PathVariable Long uId, @PathVariable Integer state) {
        try {
            JsonResult<UserUpdateStateVO> userUpdateStateVOJsonResult = userService.updateUserState(uId, state);
            return userUpdateStateVOJsonResult;
        } catch (Exception e) {
            return new JsonResult<UserUpdateStateVO>().setMeta(new Meta("修改失败", 400L)).setData(new UserUpdateStateVO());
        }
    }
}
