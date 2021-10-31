package com.hodor.web;

import com.hodor.constants.JsonResult;
import com.hodor.constants.Meta;
import com.hodor.dto.UserAddDTO;
import com.hodor.pojo.User;
import com.hodor.service.UserService;
import com.hodor.vo.user.*;
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
    public JsonResult<UserLoginVO> login(@RequestParam Long id, @RequestParam String password) {
        try {
            JsonResult<UserLoginVO> userByUserNameAndPassWord = userService.getUserByUserNameAndPassWord(id, password);
            return userByUserNameAndPassWord;
        } catch (Exception e) {
            return new JsonResult<UserUpdateStateVO>().setMeta(new Meta("登陆失败:" + e.getMessage(), 500L))
                    .setData(null);
        }
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
        try {
            JsonResult<Map<String, Object>> userListByQuery = userService.getUserListByQuery(query, power, pagenum, pagesize);
            return userListByQuery;
        } catch (Exception e) {
            return new JsonResult<UserUpdateStateVO>().setMeta(new Meta("获取失败:" + e.getMessage(), 500L))
                    .setData(null);
        }
    }

    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    @GetMapping("/user/users/{id}")
    public JsonResult<UserListVO> getUserListByQuery(@PathVariable Long id) {
        try {
            JsonResult<UserListVO> userListById = userService.getUserListById(id);
            return userListById;
        } catch (Exception e) {
            return new JsonResult<UserUpdateStateVO>().setMeta(new Meta("获取失败:" + e.getMessage(), 500L))
                    .setData(null);
        }
    }

    /**
     * 添加用户
     * @param userAddDTO
     * @return
     */
    @PostMapping("/user/users")
    public JsonResult<UserAddVO> addUser(@RequestBody UserAddDTO userAddDTO) {
        try {
            JsonResult<UserAddVO> userAddVOJsonResult = userService.addUser(userAddDTO);
            return userAddVOJsonResult;
        } catch (Exception e) {
            return new JsonResult<UserUpdateStateVO>().setMeta(new Meta("添加失败:" + e.getMessage(), 500L))
                    .setData(null);
        }
    }

    @PutMapping("/user/users/{uId}/state/{state}")
    public JsonResult updateUser(@PathVariable Long uId, @PathVariable Boolean state) {
        try {
            JsonResult<UserUpdateStateVO> userUpdateStateVOJsonResult = userService.updateUserState(uId, state);
            return userUpdateStateVOJsonResult;
        } catch (Exception e) {
            return new JsonResult<UserUpdateStateVO>().setMeta(new Meta("修改失败:" + e.getMessage(), 500L)).setData(new UserUpdateStateVO());
        }
    }

    /**
     * 根据id删除用户
     * @param id
     * @return
     */
    @DeleteMapping("/user/users/{id}")
    public JsonResult deleteById(@PathVariable Long id) {
        try {
            JsonResult jsonResult = userService.deleteById(id);
            return jsonResult;
        } catch (Exception e) {
            return new JsonResult<UserUpdateStateVO>().setMeta(new Meta("删除失败:" + e.getMessage(), 500L))
                    .setData(null);
        }
    }

    /**
     * 更新用户
     * @param id
     * @param userUpdateDTO
     * @return
     */
    @PutMapping("/user/users/{id}")
    public JsonResult<UserUpdateVO> updateUserById(@PathVariable Long id, @RequestBody UserAddDTO userUpdateDTO) {
        try {
            JsonResult<UserUpdateVO> userUpdateVOJsonResult = userService.updateUser(id, userUpdateDTO);
            return userUpdateVOJsonResult;
        } catch (Exception e) {
            return new JsonResult<UserUpdateStateVO>().setMeta(new Meta("修改失败:" + e.getMessage(), 500L))
                    .setData(null);
        }
    }
}
