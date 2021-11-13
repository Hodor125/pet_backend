package com.hodor.web;

import com.hodor.constants.JsonResult;
import com.hodor.constants.Meta;
import com.hodor.dto.UserAddDTO;
import com.hodor.service.UploadService;
import com.hodor.service.UserService;
import com.hodor.vo.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @Author limingli006
 * @Date 2021/10/17
 */
@RestController
@CrossOrigin
public class UpLoadController {
    @Autowired
    private UploadService uploadService;

    @PostMapping("/image")
    public String upload(@RequestParam("file") MultipartFile file) {

        String s = uploadService.uploadImg(file);
        return s;
    }

    @DeleteMapping("/delete/image")
    public Boolean deleteImage(String bucketName, String fileName) {
        boolean b = uploadService.removeFile(bucketName, fileName);
        return b;
    }
}
