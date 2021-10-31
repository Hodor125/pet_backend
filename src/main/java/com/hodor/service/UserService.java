package com.hodor.service;

import com.hodor.constants.JsonResult;
import com.hodor.dto.UserAddDTO;
import com.hodor.pojo.User;
import com.hodor.vo.user.*;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

public interface UserService {

    /**
     * 账密查询用户
     * @param id
     * @param passWord
     * @return
     */
    JsonResult<UserLoginVO> getUserByUserNameAndPassWord(Long id, String passWord);

    JsonResult<Map<String, Object>> getUserListByQuery(String query, Long power, Long pageno, Long pagesize);

    JsonResult<UserListVO> getUserListById(Long id);

    JsonResult<UserAddVO> addUser(UserAddDTO userAddDTO);

    JsonResult<UserUpdateStateVO> updateUserState(Long id,  Boolean state);

    JsonResult deleteById(Long id);

    JsonResult<UserUpdateVO> updateUser(Long id, UserAddDTO userAddDTO);
}
