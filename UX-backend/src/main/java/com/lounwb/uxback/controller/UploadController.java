package com.lounwb.uxback.controller;

import com.lounwb.uxback.web.handler.UploadProgressWebSocketHandler;
import com.lounwb.uxback.web.tasker.FileUploadTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
public class UploadController {
    private FileUploadTask fileUploadTask;
    private UploadProgressWebSocketHandler webSocketHandler;
    @Autowired
    public UploadController(FileUploadTask fileUploadTask, UploadProgressWebSocketHandler webSocketHandler) {
        this.fileUploadTask = fileUploadTask;
        this.webSocketHandler = webSocketHandler;
    }

    @PostMapping("/upload")
    public String uploadFile(MultipartFile file, @RequestParam("sessionId") String sessionId) {
        // 处理文件上传逻辑
        // 这里可以根据业务需求进行文件存储、数据库记录等操作

        // 启动后台任务，处理文件上传并更新进度
        fileUploadTask.uploadFile(file, sessionId);

        return "文件上传开始";
    }
}
