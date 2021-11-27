package com.hodor.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hodor.constants.JsonResult;
import com.hodor.constants.Meta;
import com.hodor.dao.ActivityDao;
import com.hodor.dao.ActivityPersonDao;
import com.hodor.dao.UserDao;
import com.hodor.exception.PetBackendException;
import com.hodor.pojo.Activity;
import com.hodor.pojo.ActivityPerson;
import com.hodor.pojo.User;
import com.hodor.service.ActivityService;
import com.hodor.vo.activity.ActivityUpdateVO;
import com.hodor.vo.activity.ActivityVO;
import com.hodor.vo.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 活动信息相关
 * @Author limingli006
 * @Date 2021/10/17
 */
@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityDao activityMapper;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ActivityPersonDao activityPersonDao;


    @Override
    public JsonResult<Map<String, Object>> getActivityListByQuery(String query, Integer pageno, Integer pagesize) {
        Map<String, Object> map = new HashMap<>();
        if(pageno < 1) {
            map.put("total", 0);
            map.put("pagenum", pageno);
            map.put("activitys", new ArrayList<>());
            return new JsonResult<List<UserListVO>>().setMeta(new Meta("获取失败", 500L)).setData(map);
        }
        PageHelper.startPage(pageno, pagesize);
        List<Activity> activityListByQueryLimit = activityMapper.getActivityListByQueryLimit(query);
        PageInfo pageRes = new PageInfo(activityListByQueryLimit);
        activityListByQueryLimit.forEach(a -> a.setSigned(a.getPerson().size()));
        Meta meta = new Meta("获取成功", 200L);
        map.put("total", pageRes.getTotal());
        map.put("pagenum", pageRes.getPageNum());
        map.put("activities", activityListByQueryLimit);
        return new JsonResult<List<UserListVO>>().setMeta(meta).setData(map);
    }

    @Override
    public JsonResult<Activity> getActivityById(Long id) {
        Activity activityById = activityMapper.getActivityById(id);
        Date now = new Date();
        if(now.after(activityById.getEndtime())) {
            activityById.setState(0);
            Activity activity = new Activity();
            activity.setId(id);
            activity.setState(0);
            activityMapper.updateActivity(activity);
        }
        if(activityById == null) {
            throw new PetBackendException("活动不存在");
        }
        Meta meta = new Meta("获取成功", 200L);
        activityById.setSigned(activityById.getPerson().size());

        return new JsonResult<List<UserListVO>>().setMeta(meta).setData(activityById);
    }

    @Override
    public JsonResult<ActivityVO> addActivity(Activity activity) {
        validActivityAdd(activity);
        activityMapper.addActivity(activity);
        return new JsonResult<Activity>().setMeta(new Meta("添加成功", 201L))
                .setData(new ActivityVO(activity.getId(), activity.getContent(), activity.getImg()));
    }

    @Override
    public JsonResult deleteById(Long id) {
        activityMapper.deleteById(id);
        return new JsonResult().setMeta(new Meta("删除成功", 204L)).setData(null);
    }

    @Override
    public JsonResult<ActivityUpdateVO> updateActivity(Long id, Activity activity) {
        if(id == null) {
            throw new PetBackendException("id为空");
        }
        activity.setId(id);
        activityMapper.updateActivity(activity);
        Activity activityById = activityMapper.getActivityById(id);
        if(activityById == null) {
            throw new PetBackendException("活动不存在");
        }
        return new JsonResult<ActivityUpdateVO>().setMeta(new Meta("修改成功", 201L))
                .setData(new ActivityUpdateVO(activity.getId(), activity.getState()));
    }

    @Override
    public JsonResult<ActivityPerson> signActivity(Long id, Long u_id) {
        Activity activityById = activityMapper.getActivityById(id);
        if(Objects.isNull(activityById)) {
            throw new PetBackendException("活动不存在");
        }
        User userListById = userDao.getUserListById(u_id);
        if(Objects.isNull(userListById)) {
            throw new PetBackendException("用户不存在");
        }
        ActivityPerson activityPerson = new ActivityPerson();
        activityPerson.setActId(id);
        activityPerson.setUId(u_id);
        activityPerson.setUserName(userListById.getName());
        activityPersonDao.insertActivityPerson(activityPerson);
        return new JsonResult<ActivityPerson>().setMeta(new Meta("报名成功", 201L))
                .setData(activityPerson);
    }

    private void validActivityAdd(Activity activity) {

        if(activity.getTitle() == null || "".equals(activity.getTitle())) {
            activity.setTitle("");
        }
        if(activity.getImg() == null || "".equals(activity.getImg())) {
            activity.setImg("");
        }
        if(activity.getStarttime() == null) {
            throw new PetBackendException("活动开始时间为空");
        }
        if(activity.getEndtime() == null) {
            throw  new PetBackendException("活动结束时间为空");
        }
        if(activity.getContent() == null || "".equals(activity.getContent())) {
            activity.setContent("");
        }
        if(activity.getNum() == null) {
            activity.setContent("");
        }
        if(activity.getState() == null) {
            activity.setState(1);
        }
    }
}
