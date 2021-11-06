package com.hodor.service;

import com.hodor.constants.JsonResult;
import com.hodor.dao.ActivityDao;
import com.hodor.dto.UserAddDTO;
import com.hodor.pojo.Activity;
import com.hodor.vo.user.*;

import java.util.Map;

public interface ActivityService {

//    JsonResult<Map<String, Object>> getActivityListByQuery(String query, String order, Long pageno, Long pagesize);
//
//    JsonResult<Activity> getActivityById(Long id);

    JsonResult<Activity> addUser(Activity activity);

    JsonResult<Activity> updateUserState(Activity activity);
//
//    JsonResult deleteById(Long id);
//
//    JsonResult<Activity> updateActivity(Long id, Activity activity);
}
