package com.hodor.service;

import com.hodor.constants.JsonResult;
import com.hodor.pojo.Pet;
import com.hodor.vo.pet.PetAddVO;

import java.util.Map;

public interface UploadService {

    JsonResult<Object> upload(String url);
}

