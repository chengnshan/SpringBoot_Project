package com.cxp.springboot2upload.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author 程
 * @date 2019/6/16 下午5:16
 */
@Controller
public class UploadController {

    private static final Logger logger = LoggerFactory.getLogger(UploadController.class);

    @Value(value = "${path.upload}")
    private String uploadPath ;

    @GetMapping(value = "/toImageUploadPage")
    public String toImageUploadPage() {
        return "html/imageUpload";
    }

    @PostMapping(value = "/fileUpload")
    private String imageUpload(@RequestParam(value = "file") MultipartFile file,
                               ModelMap modelMap, HttpServletRequest request){
        if (file.isEmpty()) {
            logger.info("文件为空");
        }
        String fileName = file.getOriginalFilename();
        // 后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        fileName = UUID.randomUUID() + suffixName;
        File dest = new File(uploadPath,fileName);
        if (!dest.getParentFile().exists()){
            dest.getParentFile().mkdirs();
        }

        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }

        modelMap.addAttribute("filename", "/images/"+fileName);
        return "html/imageUpload";
    }
}
