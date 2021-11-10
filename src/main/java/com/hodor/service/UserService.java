package com.hodor.service;

import com.hodor.constants.JsonResult;
import com.hodor.dto.UserAddDTO;
import com.hodor.vo.user.*;

import java.util.Map;

public interface UserService {

    /**
     * 账密查询用户
     * @param id
     * @param passWord
     * @return
     */
    JsonResult<UserLoginVO> getUserByUserNameAndPassWord(Long id, String passWord);

    JsonResult<Map<String, Object>> getUserListByQuery(String query, Long power, Integer pageno, Integer pagesize);

    JsonResult<UserListVO> getUserListById(Long id);

    JsonResult<UserAddVO> addUser(UserAddDTO userAddDTO);

    JsonResult<UserUpdateStateVO> updateUserState(Long id,  Boolean state);

    JsonResult deleteById(Long id);

    JsonResult<UserUpdateVO> updateUser(Long id, UserAddDTO userAddDTO);
}
