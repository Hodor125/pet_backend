package com.hodor.web;

import com.hodor.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 七牛云上传图片
 * @Author limingli006
 * @Date 2021/10/17
 */
@RestController
@CrossOrigin
public class UpLoadController {
    @Autowired
    private UploadService uploadService;

    /**
     * 上传图片
     * @param file
     * @return
     */
    @PostMapping("/image")
    public String upload(@RequestParam("file") MultipartFile file) {

        String s = uploadService.uploadImg(file);
        String privateFile = uploadService.getPrivateFile(s);
        return privateFile;
    }

    /**
     * 删除图片
     * @param fileName
     * @return
     */
    @DeleteMapping("/delete/image")
    public Boolean deleteImage(@RequestParam String fileName) {
        boolean b = uploadService.removeFile(fileName);
        return b;
    }
}
