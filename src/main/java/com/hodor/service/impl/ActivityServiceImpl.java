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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

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


    /**
     * 获取宠物列表
     * @param query 搜索关键词
     * @param pageno 页码
     * @param pagesize 页大小
     * @return
     */
    @Override
    public JsonResult<Map<String, Object>> getActivityListByQuery(String query, String startTime, String endTime, Integer pageno, Integer pagesize) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date start = null;
        Date end = null;
        try {
            if(!Objects.isNull(startTime) && !"".equals(startTime))
                start = simpleDateFormat.parse(startTime);
            if(!Objects.isNull(endTime) && !"".equals(startTime))
                end = simpleDateFormat.parse(endTime);
        } catch (ParseException e) {
            throw new PetBackendException("时间格式错误");
        }

        Map<String, Object> map = new HashMap<>();
        if(pageno < 1) {
            map.put("total", 0);
            map.put("pagenum", pageno);
            map.put("activitys", new ArrayList<>());
            return new JsonResult<List<UserListVO>>().setMeta(new Meta("获取失败", 500L)).setData(map);
        }
        PageHelper.startPage(pageno, pagesize);
        List<Activity> activityListByQueryLimit = activityMapper.getActivityListByQueryLimit(query, startTime, endTime);
        PageInfo pageRes = new PageInfo(activityListByQueryLimit);
        addUser(activityListByQueryLimit);

        //参加人数和活动有效性
        Date now = new Date();
        activityListByQueryLimit.forEach(a -> {
            a.setSigned(a.getPerson().size());
            if(now.before(a.getStarttime())) {
                a.setState(1);
            } else if (now.after(a.getEndtime())) {
                a.setState(0);
            } else {
                a.setState(2);
            }
        });
        Meta meta = new Meta("获取成功", 200L);
        map.put("total", pageRes.getTotal());
        map.put("pagenum", pageRes.getPageNum());
        map.put("activities", activityListByQueryLimit);
        return new JsonResult<List<UserListVO>>().setMeta(meta).setData(map);
    }

    public void addUser(List<Activity> activityList) {
        List<Long> actIds = activityList.stream().map(Activity::getId).collect(Collectors.toList());

        Map<Long, List<ActivityPerson>> map = getActivityUserByActIds(actIds);
        for (Activity activity : activityList) {
            activity.setPerson(map.get(activity.getId()));
        }
    }

    public Map<Long, List<ActivityPerson>> getActivityUserByActIds(List<Long> actIds) {
        Map<Long, List<ActivityPerson>> map = new LinkedHashMap<>();
        actIds.forEach(a -> map.put(a, new ArrayList<>()));

        List<ActivityPerson> personByActIds = activityMapper.getPersonByActIds(actIds);
        if(!Objects.isNull(personByActIds)) {
            for (ActivityPerson personByActId : personByActIds) {
                map.get(personByActId.getActId()).add(personByActId);
            }
        }
        return map;
    }

    /**
     * 根据id查询
     * @param id 宠物id
     * @return
     */
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

        if(now.before(activityById.getStarttime())) {
            activityById.setState(1);
        } else if (now.after(activityById.getEndtime())) {
            activityById.setState(0);
        } else {
            activityById.setState(2);
        }
        Meta meta = new Meta("获取成功", 200L);
        activityById.setSigned(activityById.getPerson().size());

        return new JsonResult<List<UserListVO>>().setMeta(meta).setData(activityById);
    }

    /**
     * 添加活动
     * @param activity
     * @return
     */
    @Override
    public JsonResult<ActivityVO> addActivity(Activity activity) {
        validActivityAdd(activity);
        activityMapper.addActivity(activity);
        return new JsonResult<Activity>().setMeta(new Meta("添加成功", 201L))
                .setData(new ActivityVO(activity.getId(), activity.getContent(), activity.getImg()));
    }

    /**
     * 删除活动
     * @param id 活动id
     * @return
     */
    @Override
    public JsonResult deleteById(Long id) {
        activityMapper.deleteById(id);
        return new JsonResult().setMeta(new Meta("删除成功", 204L)).setData(null);
    }

    /**
     * 更新活动
     * @param id 活动id
     * @param activity
     * @return
     */
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

    /**
     * 报名活动
     * @param id 活动id
     * @param u_id 用户id
     * @return
     */
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

        //是否已经报名过
        ActivityPerson byAidAndUid = activityPersonDao.getByAidAndUid(id, u_id);
        if(!Objects.isNull(byAidAndUid))
            throw new PetBackendException("已经报名过这个活动");

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
