package com.hodor.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hodor.constants.JsonResult;
import com.hodor.constants.Meta;
import com.hodor.dao.UserDao;
import com.hodor.dto.UserAddDTO;
import com.hodor.exception.PetBackendException;
import com.hodor.pojo.User;
import com.hodor.service.UserService;
import com.hodor.util.IdCardUtils;
import com.hodor.vo.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
    public JsonResult<Map<String, Object>> getUserListByQuery(String query, Long power, Integer pageno, Integer pagesize) {
        Map<String, Object> map = new HashMap<>();
        if(pageno < 1) {
            map.put("total", 0);
            map.put("pagenum", pageno);
            map.put("users", new ArrayList<>());
            return new JsonResult<List<UserListVO>>().setMeta(new Meta("获取失败", 500L)).setData(map);
        }
        PageHelper.startPage(pageno, pagesize);
        List<User> userListByQueryLimit = userMapper.getUserListByQueryLimit(query, power);
        PageInfo pageRes = new PageInfo(userListByQueryLimit);
        Meta meta = new Meta("获取成功", 200L);
        List<UserListVO> userListVOS = transUserToUserListVO(userListByQueryLimit);
        map.put("total", pageRes.getTotal());
        map.put("pagenum", pageRes.getPageNum());
        map.put("users", userListVOS);
        return new JsonResult<List<UserListVO>>().setMeta(meta).setData(map);
    }



    @Override
    public JsonResult<UserListVO> getUserListById(Long id) {
        User userListById = userMapper.getUserListById(id);
        if(userListById == null) {
            throw new PetBackendException("用户不存在");
        }
        Meta meta = new Meta("获取成功", 200L);
        List<UserListVO> userListVOS = transUserToUserListVO(Arrays.asList(userListById));
        return new JsonResult<List<UserListVO>>().setMeta(meta).setData(userListVOS.get(0));
    }

    @Override
    public JsonResult<UserAddVO> addUser(UserAddDTO userAddDTO) {
        validUserAdd(userAddDTO);
        User user = transUserAddToUser(userAddDTO);
        Integer id = userMapper.addUser(user);
        return new JsonResult<List<UserListVO>>().setMeta(new Meta("添加成功", 201L))
                .setData(new UserAddVO(user.getId(), user.getNickName(), user.getAge(), user.getSex()));
    }

    @Override
    public JsonResult<UserUpdateStateVO> updateUserState(Long id, Boolean state) {
        User user = new User();
        user.setId(id);
        user.setState(state ? 1 : 0);
        userMapper.updateUser(user);
        return new JsonResult<UserUpdateStateVO>().setMeta(new Meta("修改成功", 201L))
                .setData(new UserUpdateStateVO().setId(id).setState(state ? 1 : 0));
    }

    @Override
    public JsonResult deleteById(Long id) {
        Integer integer = userMapper.deleteById(id);
        return new JsonResult().setMeta(new Meta("删除成功", 204L)).setData(null);
    }

    @Override
    public JsonResult<UserUpdateVO> updateUser(Long id, UserAddDTO userAddDTO) {
        if(id == null) {
            throw new PetBackendException("id为空");
        }
        User user = transUserUpdateToUser(userAddDTO);
        user.setId(id);
        userMapper.updateUser(user);
        User userListById = userMapper.getUserListById(id);
        if(userListById == null) {
            throw new PetBackendException("用户不存在");
        }
        return new JsonResult<UserUpdateStateVO>().setMeta(new Meta("修改成功", 200L))
                .setData(new UserUpdateVO(id, userListById.getNickName()));
    }

    @Override
    public JsonResult<UserRegisterVO> register(String nick_name, String password) {
        User user = new User();
        user.setNickName(nick_name);
        user.setPassword(password);
        Integer res = userMapper.register(user);

        return new JsonResult<UserRegisterVO>().setMeta(new Meta("注册成功", 201L))
                .setData(new UserRegisterVO(user.getId(), 0L));
    }

    private List<UserListVO> transUserToUserListVO(List<User> users) {
        List<UserListVO> userListVOS = new ArrayList<>();
        for (User user : users) {
            UserListVO userListVO = new UserListVO();
            userListVO.setId(user.getId());
            userListVO.setNick_name(user.getNickName());
            userListVO.setName(user.getName());
            userListVO.setP_id(user.getPId());
            userListVO.setP_img(user.getPImage());
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
        user.setPId(userAddDTO.getP_id());
        user.setPImage(userAddDTO.getP_img());
        user.setAddress(userAddDTO.getAddress());
        //设置年龄和性别
        Integer age = null;
        String gender = null;
        try {
            age = IdCardUtils.getAge(user.getPId());
            gender = IdCardUtils.getGender(user.getPId());
        } catch (Exception e) {
            throw new PetBackendException("身份证信息错误");
        }
        user.setAge(age);
        user.setSex(gender.equals("1") ? "男" : "女");
        user.setName(userAddDTO.getName());
        user.setState(0);
        return user;
    }

    private User transUserUpdateToUser(UserAddDTO userAddDTO) {

        User user = new User();
        if(userAddDTO.getNick_name() != null && !"".equals(userAddDTO.getNick_name())) {
            user.setNickName(userAddDTO.getNick_name());
        }
        if(userAddDTO.getPassword() != null && !"".equals(userAddDTO.getPassword())) {
            user.setPassword(userAddDTO.getPassword());
        }
        if(userAddDTO.getTel() != null && !"".equals(userAddDTO.getTel())) {
            user.setTel(userAddDTO.getTel());
        }
        if(userAddDTO.getP_id() != null && !"".equals(userAddDTO.getP_id())) {
            user.setPId(userAddDTO.getP_id());
            //设置年龄和性别
            Integer age = null;
            String gender = null;
            try {
                age = IdCardUtils.getAge(user.getPId());
                gender = IdCardUtils.getGender(user.getPId());
            } catch (Exception e) {
                throw new PetBackendException("身份证信息错误");
            }
            user.setAge(age);
            user.setSex(gender.equals("1") ? "男" : "女");
        }
        if(userAddDTO.getP_img() != null && !"".equals(userAddDTO.getP_img())) {
            user.setPImage(userAddDTO.getP_img());
        }
        if(userAddDTO.getAddress() != null && !"".equals(userAddDTO.getAddress())) {
            user.setAddress(userAddDTO.getAddress());
        }
        if(userAddDTO.getName() != null && !"".equals(userAddDTO.getName())) {
            user.setName(userAddDTO.getName());
        }
        return user;
    }

    private void validUserAdd(UserAddDTO userAddDTO) {

        if(userAddDTO.getPassword() == null || userAddDTO.getPassword().equals("")) {
            throw new PetBackendException("密码为空");
        }
        if(userAddDTO.getTel() == null || userAddDTO.getTel().equals("")) {
            throw new PetBackendException("手机号为空");
        }
        if(userAddDTO.getP_id() == null || userAddDTO.getP_id().equals("")) {
            throw new PetBackendException("身份证为空");
        }
        if(userAddDTO.getAddress() == null || userAddDTO.getAddress().equals("")) {
            throw new PetBackendException("地址为空");
        }
        if(userAddDTO.getName() == null || userAddDTO.getName().equals("")) {
            throw new PetBackendException("姓名为空");
        }
        if(userAddDTO.getNick_name() == null || userAddDTO.getNick_name().equals("")) {
            throw new PetBackendException("昵称为空");
        }
        if(userAddDTO.getAddress() == null || userAddDTO.getAddress().equals("")) {
            throw new PetBackendException("地址为空");
        }
    }
}
