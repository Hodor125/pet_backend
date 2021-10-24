package com.hodor.service.impl;

import com.hodor.constants.JsonResult;
import com.hodor.constants.Meta;
import com.hodor.dao.UserDao;
import com.hodor.dto.UserAddDTO;
import com.hodor.pojo.User;
import com.hodor.service.UserService;
import com.hodor.vo.user.UserAddVO;
import com.hodor.vo.user.UserListVO;
import com.hodor.vo.user.UserLoginVO;
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
    public JsonResult<User> getUserByUserNameAndPassWord(Long id, String passWord) {
        User user = userMapper.getUserByUserNameAndPassWord(id, passWord);
        if(user != null) {
            Meta meta = new Meta("登陆成功", 200L);
            UserLoginVO userLoginVO = new UserLoginVO(user.getId(), user.getPower(), user.getNickName(), "");
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
    public JsonResult<List<UserListVO>> getUserListByQuery(String query, Long pageno, Long pagesize) {
        Map<String, Object> map = new HashMap<>();
        if(pageno < 1) {
            map.put("total", 0);
            map.put("pagenum", pageno);
            map.put("users", new ArrayList<>());
            return new JsonResult<List<UserListVO>>().setMeta(new Meta("获取失败", 400L)).setData(map);
        }
        List<User> userListByQueryLimit = userMapper.getUserListByQueryLimit(query, (pageno - 1) * pagesize, pagesize);
        List<User> userListByQuery = userMapper.getUserListByQuery(query);
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
                .setData(new UserAddVO(user.getId(), user.getName()));
    }

    private List<UserListVO> transUserToUserListVO(List<User> users) {
        List<UserListVO> userListVOS = new ArrayList<>();
        for (User user : users) {
            UserListVO userListVO = new UserListVO();
            userListVO.setId(user.getId());
            userListVO.setNick_name(user.getNickName());
//            userListVO.setPsw(user.getPassword());
            userListVO.setName(user.getName());
            userListVO.setP_id(user.getpId());
            userListVO.setP_image(user.getpImage());
            userListVO.setTel(user.getTel());
            userListVO.setAddress(user.getAddress());
            userListVO.setAge(user.getAge());
            userListVO.setSex(user.getSex());
            userListVOS.add(userListVO);
            userListVO.setState(user.getState());
        }
        return userListVOS;
    }

    private User transUserAddToUser(UserAddDTO userAddDTO) {

        User user = new User();
        user.setNickName(userAddDTO.getNickname());
        user.setPassword(userAddDTO.getPassword());
        user.setPower(0L);
        user.setEmail(userAddDTO.getEmail());
        user.setEmail(userAddDTO.getEmail());
        user.setTel(userAddDTO.getTel());
        user.setpId(userAddDTO.getpId());
        user.setpImage("");
        user.setAddress(userAddDTO.getAddress());
        user.setAge(userAddDTO.getAge());
        user.setSex(userAddDTO.getSex());
        user.setName(userAddDTO.getUsername());
        user.setState(0);
        return user;
    }

    private String validUserAdd(UserAddDTO userAddDTO) {

        if(userAddDTO.getPassword() == null || userAddDTO.getPassword().equals("")) {
            return "密码为空";
        }
        if(userAddDTO.getEmail() == null || userAddDTO.getEmail().equals("")) {
            return "邮箱为空";
        }
        if(userAddDTO.getTel() == null || userAddDTO.getTel().equals("")) {
            return "手机号为空";
        }
        if(userAddDTO.getpId() == null || userAddDTO.getpId().equals("")) {
            return "身份证为空";
        }
        if(userAddDTO.getAddress() == null || userAddDTO.getAddress().equals("")) {
            return "地址为空";
        }
        if(userAddDTO.getAge() == null) {
            return "年龄为空";
        }
        if(userAddDTO.getSex() == null || userAddDTO.getSex().equals("")) {
            return "性别为空";
        }
        if(userAddDTO.getUsername() == null || userAddDTO.getUsername().equals("")) {
            return "姓名为空";
        }
        if(userAddDTO.getNickname() == null || userAddDTO.getNickname().equals("")) {
            return "姓名为空";
        }
        return "";
    }
}
