package com.lounwb.uxback.web.tasker;

import com.alibaba.fastjson.JSON;
import com.lounwb.uxback.web.handler.UploadProgressWebSocketHandler;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@Slf4j
public class FileUploadTask {
    @Resource
    private ScheduledTask scheduledTask;

    @Async
    public void uploadFile(MultipartFile file, String sessionId) {
        long delaySeconds = 3L;
        long fileSize = 0;
        String fileName = "";
        String filePath = "";
        if(file == null || file.isEmpty() ){
            log.error("File is Empty!!");
            return;
        }
        fileSize = file.getSize();
        fileName = UUID.randomUUID() + "."
                + file.getContentType().substring(
                file.getContentType().lastIndexOf("/") + 1);    // 创建文件名
        filePath = "E:\\projects\\UX-rank\\data\\" + fileName; // 设置文件上传保存的路径

        try {
            InputStream inputStream = file.getInputStream();
            File targetFile = new File(filePath);
            FileOutputStream outputStream = new FileOutputStream(targetFile);
            // 一次传10Kb
            byte[] buffer = new byte[30 * 1024];
            int bytesRead;
            long totalBytesRead = 0;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                // 写入文件内容
                outputStream.write(buffer, 0, bytesRead);
                totalBytesRead += bytesRead;

                // 计算上传进度
                double progress = (double) totalBytesRead / fileSize * 100;

                scheduledTask.scheduleDelayTask(sessionId, "upload", progress, new Date(
                        System.currentTimeMillis() + delaySeconds * 1250));
            }
        } catch (IOException e) {
            log.error("文件上传失败");
        }
    }
}