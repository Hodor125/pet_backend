package com.hodor.service;

import com.hodor.constants.JsonResult;
import com.hodor.pojo.Pet;
import com.hodor.vo.pet.PetAddVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface UploadService {

    String uploadImg(MultipartFile file);

    String getPrivateFile(String fileKey);

    boolean removeFile(String bucketName, String fileKey);

    boolean removeFile(String fileKey);
}

