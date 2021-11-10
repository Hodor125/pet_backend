package com.hodor.service;

import com.hodor.constants.JsonResult;
import com.hodor.pojo.Activity;
import com.hodor.vo.activity.ActivityUpdateVO;
import com.hodor.vo.activity.ActivityVO;

import java.util.Map;

public interface ActivityService {

    JsonResult<Map<String, Object>> getActivityListByQuery(String query, Integer pageno, Integer pagesize, String order);

    JsonResult<Activity> getActivityById(Long id);

    JsonResult<ActivityVO> addActivity(Activity activity);

//    JsonResult<Activity> updateUserState(Activity activity);
//
    JsonResult deleteById(Long id);

    JsonResult<ActivityUpdateVO> updateActivity(Long id, Activity activity);
}
