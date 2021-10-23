package com.hodor.service;

import com.hodor.constants.JsonResult;
import com.hodor.dto.UserAddDTO;
import com.hodor.pojo.User;
import com.hodor.vo.user.UserAddVO;
import com.hodor.vo.user.UserListVO;

import java.util.List;

public interface UserService {

    /**
     * 账密查询用户
     * @param id
     * @param passWord
     * @return
     */
    JsonResult<User> getUserByUserNameAndPassWord(Long id, String passWord);

    JsonResult<List<UserListVO>> getUserListByQuery(String query, Long pageno, Long pagesize);

    JsonResult<UserAddVO> addUser(UserAddDTO userAddDTO);
}
