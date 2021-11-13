package com.hodor.service.impl;

import com.hodor.constants.JsonResult;
import com.hodor.service.UploadService;
import org.springframework.stereotype.Service;

/**
 * @Author limingli006
 * @Date 2021/11/13
 */
@Service
public class UploadServiceImpl implements UploadService {
    @Override
    public JsonResult<Object> upload(String url) {
        return new JsonResult().setData("ok");
    }
}
