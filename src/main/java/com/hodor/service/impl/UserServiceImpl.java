package com.hodor.service.impl;

import com.hodor.constants.JsonResult;
import com.hodor.constants.Meta;
import com.hodor.dao.UserDao;
import com.hodor.dto.UserAddDTO;
import com.hodor.exception.PetBackendException;
import com.hodor.pojo.User;
import com.hodor.service.UserService;
import com.hodor.util.IdCardUtils;
import com.hodor.vo.user.UserAddVO;
import com.hodor.vo.user.UserListVO;
import com.hodor.vo.user.UserLoginVO;
import com.hodor.vo.user.UserUpdateStateVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public JsonResult<UserLoginVO> getUserByUserNameAndPassWord(Long id, String passWord) {
        User user = userMapper.getUserByUserNameAndPassWord(id, passWord);
        if(user != null) {
            Meta meta = new Meta("登陆成功", 200L);
            UserLoginVO userLoginVO = new UserLoginVO(user.getId(), user.getPower(), user.getName(), "");
            return new JsonResult<User>().setMeta(meta).setData(userLoginVO);
        }
        return null;
    }

    /**
     * 查询用户列表
     * @param query
     * @param pageno
     * @param pagesize
     * @return
     */
    @Override
    public JsonResult<Map<String, Object>> getUserListByQuery(String query, Long power, Long pageno, Long pagesize) {
        Map<String, Object> map = new HashMap<>();
        if(pageno < 1) {
            map.put("total", 0);
            map.put("pagenum", pageno);
            map.put("users", new ArrayList<>());
            return new JsonResult<List<UserListVO>>().setMeta(new Meta("获取失败", 400L)).setData(map);
        }
        List<User> userListByQueryLimit = userMapper.getUserListByQueryLimit(query, power, (pageno - 1) * pagesize, pagesize);
        List<User> userListByQuery = userMapper.getUserListByQuery(query, power);
        Meta meta = new Meta("获取成功", 200L);
        List<UserListVO> userListVOS = transUserToUserListVO(userListByQueryLimit);
        map.put("total", userListByQuery.size());
        map.put("pagenum", pageno);
        map.put("users", userListVOS);
        return new JsonResult<List<UserListVO>>().setMeta(meta).setData(map);
    }

    @Override
    public JsonResult<UserAddVO> addUser(UserAddDTO userAddDTO) {
        String s = validUserAdd(userAddDTO);
        User user = transUserAddToUser(userAddDTO);
        if("".equals(s)) {
            new JsonResult<List<UserListVO>>().setMeta(new Meta("添加失败", 400L))
                    .setData(new UserAddVO());
        }
        Integer id = userMapper.addUser(user);
        return new JsonResult<List<UserListVO>>().setMeta(new Meta("添加成功", 200L))
                .setData(new UserAddVO(user.getId(), user.getNickName(), user.getAge(), user.getSex()));
    }

    @Override
    public JsonResult<UserUpdateStateVO> updateUserState(Long id, Integer state) {
        User user = new User();
        user.setId(id);
        user.setState(state);
        userMapper.updateUser(user);
        return new JsonResult<UserUpdateStateVO>().setMeta(new Meta("修改成功", 200L))
                .setData(new UserUpdateStateVO().setId(id).setState(state));
    }

    private List<UserListVO> transUserToUserListVO(List<User> users) {
        List<UserListVO> userListVOS = new ArrayList<>();
        for (User user : users) {
            UserListVO userListVO = new UserListVO();
            userListVO.setId(user.getId());
            userListVO.setNick_name(user.getNickName());
            userListVO.setName(user.getName());
            userListVO.setP_id(user.getpId());
            userListVO.setP_image(user.getpImage());
            userListVO.setTel(user.getTel());
            userListVO.setAddress(user.getAddress());
            userListVO.setAge(user.getAge());
            userListVO.setSex(user.getSex());
            userListVOS.add(userListVO);
            userListVO.setState(user.getState() == 0 ? false : true);
        }
        return userListVOS;
    }

    private User transUserAddToUser(UserAddDTO userAddDTO) {

        User user = new User();
        user.setNickName(userAddDTO.getNick_name());
        user.setPassword(userAddDTO.getPassword());
        user.setPower(0L);
        user.setEmail("");
        user.setTel(userAddDTO.getTel());
        user.setpId(userAddDTO.getP_id());
        user.setpImage(userAddDTO.getP_image());
        user.setAddress(userAddDTO.getAddress());
        //设置年龄和性别
        Integer age = null;
        String gender = null;
        try {
            age = IdCardUtils.getAge(user.getpId());
            gender = IdCardUtils.getGender(user.getpId());
        } catch (Exception e) {
            throw new PetBackendException("身份证信息错误");
        }
        user.setAge(age);
        user.setSex(gender.equals("1") ? "男" : "女");
        user.setName(userAddDTO.getName());
        user.setpImage("");
        user.setState(0);
        return user;
    }

    private String validUserAdd(UserAddDTO userAddDTO) {

        if(userAddDTO.getPassword() == null || userAddDTO.getPassword().equals("")) {
            return "密码为空";
        }
        if(userAddDTO.getTel() == null || userAddDTO.getTel().equals("")) {
            return "手机号为空";
        }
        if(userAddDTO.getP_id() == null || userAddDTO.getP_id().equals("")) {
            return "身份证为空";
        }
        if(userAddDTO.getAddress() == null || userAddDTO.getAddress().equals("")) {
            return "地址为空";
        }
        if(userAddDTO.getName() == null || userAddDTO.getName().equals("")) {
            return "姓名为空";
        }
        if(userAddDTO.getNick_name() == null || userAddDTO.getNick_name().equals("")) {
            return "昵称为空";
        }
        if(userAddDTO.getAddress() == null || userAddDTO.getAddress().equals("")) {
            return "地址为空";
        }
        return "";
    }
}
