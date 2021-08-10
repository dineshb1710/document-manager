package com.dinesh.app.dm.controller;

import com.dinesh.app.dm.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/files")
public class FileUploadController {

    private final FileUploadService fileUploadService;

    @RequestMapping("/")
    public String loadHomePage() {
        return "upload";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("myFile") MultipartFile multipartFile, RedirectAttributes redirectAttributes) {
        // Display File details onto the console..
        String fileName = multipartFile.getOriginalFilename();
        long fileSize = multipartFile.getSize();
        log.info("Inside uploadFile of FileUploadController..");
        log.info("File Name : " + fileName);
        log.info("File Size : " + fileSize);
        log.info("Content Type : " + multipartFile.getContentType());
        try {
            return fileUploadService.uploadBlob(multipartFile.getOriginalFilename(),
                    multipartFile.getInputStream(), multipartFile.getSize(), redirectAttributes);
        } catch (IOException e) {
            log.error("Unable to upload file with name " + fileName + " due to exception : " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
