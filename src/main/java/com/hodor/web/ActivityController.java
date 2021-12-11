package com.hodor.web;

import com.hodor.constants.JsonResult;
import com.hodor.constants.Meta;
import com.hodor.dao.ActivityPersonDao;
import com.hodor.dto.UserAddDTO;
import com.hodor.pojo.Activity;
import com.hodor.pojo.ActivityPerson;
import com.hodor.service.ActivityService;
import com.hodor.vo.activity.ActivityUpdateVO;
import com.hodor.vo.activity.ActivityVO;
import com.hodor.vo.user.UserListVO;
import com.hodor.vo.user.UserUpdateStateVO;
import com.hodor.vo.user.UserUpdateVO;
import com.qiniu.util.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author limingli006
 * @Date 2021/10/17
 */
@RestController
@CrossOrigin
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    /**
     * 获取活动列表
     * @param query
     * @param pagenum
     * @param pagesize
     * @return
     */
    @GetMapping("/user/activities")
    public JsonResult<Map<String, Object>> getUserListByQuery(@RequestParam(required = false) String query,
                                                              @RequestParam(required = false, defaultValue = "1") Integer pagenum,
                                                              @RequestParam(required = false, defaultValue = "10") Integer pagesize) {
        try {
            JsonResult<Map<String, Object>> activityListByQuery = activityService.getActivityListByQuery(query, pagenum, pagesize);
            return activityListByQuery;
        } catch (Exception e) {
            return new JsonResult<Activity>().setMeta(new Meta("获取失败:" + e.getMessage(), 500L))
                    .setData(null);
        }
    }

    /**
     * 添加活动
     * @param activity
     * @return
     */
    @PostMapping("/user/activities")
    public JsonResult<ActivityVO> addActivity(@RequestBody Activity activity) {
        try {
            JsonResult<ActivityVO> activityJsonResult = activityService.addActivity(activity);
            return activityJsonResult;
        } catch (Exception e) {
            return new JsonResult<ActivityVO>().setMeta(new Meta("添加失败:" + e.getMessage(), 500L))
                    .setData(null);
        }
    }

    /**
     * 根据id查询活动
     * @param id
     * @return
     */
    @GetMapping("/user/activitys/{id}")
    public JsonResult<Activity> getUserListByQuery(@PathVariable Long id) {
        try {
            JsonResult<Activity> activityById = activityService.getActivityById(id);
            return activityById;
        } catch (Exception e) {
            return new JsonResult<Activity>().setMeta(new Meta("获取失败:" + e.getMessage(), 500L))
                    .setData(null);
        }
    }

    /**
     * 更新活动
     * @param id
     * @param activity
     * @return
     */
    @PutMapping("/user/activitys/{id}")
    public JsonResult<ActivityUpdateVO> updateUserById(@PathVariable Long id, @RequestBody Activity activity) {
        try {
            JsonResult<ActivityUpdateVO> activityUpdateVOJsonResult = activityService.updateActivity(id, activity);
            return activityUpdateVOJsonResult;
        } catch (Exception e) {
            return new JsonResult<ActivityUpdateVO>().setMeta(new Meta("修改失败:" + e.getMessage(), 500L))
                    .setData(null);
        }
    }

    /**
     * 根据id删除活动
     * @param id
     * @return
     */
    @DeleteMapping("/user/activities/{id}")
    public JsonResult deleteById(@PathVariable Long id) {
        try {
            JsonResult jsonResult = activityService.deleteById(id);
            return jsonResult;
        } catch (Exception e) {
            return new JsonResult().setMeta(new Meta("删除失败:" + e.getMessage(), 500L))
                    .setData(null);
        }
    }

    /**
     * 报名
     * @param id 活动id
     * @param u_id 用户id
     */
    @PostMapping("/user/activities/{id}/{u_id}")
    public JsonResult<ActivityPerson> signActivity(@PathVariable Long id, @PathVariable Long u_id) {
        try {
            JsonResult<ActivityPerson> activityPersonJsonResult = activityService.signActivity(id, u_id);
            return activityPersonJsonResult;
        } catch (Exception e) {
            return new JsonResult().setMeta(new Meta("报名失败:" + e.getMessage(), 500L))
                    .setData(null);
        }
    }
}
