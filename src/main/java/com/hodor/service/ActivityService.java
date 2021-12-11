package com.hodor.service;

import com.hodor.constants.JsonResult;
import com.hodor.pojo.Activity;
import com.hodor.pojo.ActivityPerson;
import com.hodor.vo.activity.ActivityUpdateVO;
import com.hodor.vo.activity.ActivityVO;

import java.util.Date;
import java.util.Map;

public interface ActivityService {

    JsonResult<Map<String, Object>> getActivityListByQuery(String query, String startTime, String endTime, Integer pageno, Integer pagesize);

    JsonResult<Activity> getActivityById(Long id);

    JsonResult<ActivityVO> addActivity(Activity activity);

//    JsonResult<Activity> updateUserState(Activity activity);
//
    JsonResult deleteById(Long id);

    JsonResult<ActivityUpdateVO> updateActivity(Long id, Activity activity);

    JsonResult<ActivityPerson> signActivity(Long id, Long u_id);
}
