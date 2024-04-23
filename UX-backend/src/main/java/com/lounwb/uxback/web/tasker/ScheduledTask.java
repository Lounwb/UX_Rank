package com.lounwb.uxback.web.tasker;

import com.alibaba.fastjson.JSON;
import com.lounwb.uxback.web.handler.UploadProgressWebSocketHandler;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class ScheduledTask {
    @Resource
    private TaskScheduler taskScheduler;

    public void scheduleDelayTask(String sessionId, String type, double progress, Date date) {
        taskScheduler.schedule(() -> pushMessage(sessionId, type, progress), date);
    }

    public void pushMessage(String sessionId, String type, double progress) {
        Map<String, Object> map = new HashMap<>();
        map.put("type", type);
        map.put("progress", progress);
        // 实时发送进度信息给前端
        String message = JSON.toJSONString(map);
        try {
            if (!UploadProgressWebSocketHandler.isOnline(sessionId)){
                log.error("User: " + sessionId + " is not online!");
                return;
            }
            UploadProgressWebSocketHandler.sendInfo(message, sessionId);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
