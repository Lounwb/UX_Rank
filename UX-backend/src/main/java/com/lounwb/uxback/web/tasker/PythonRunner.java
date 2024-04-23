package com.lounwb.uxback.web.tasker;

import com.lounwb.uxback.web.handler.UploadProgressWebSocketHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

@Slf4j

public class PythonRunner {

    public static void runPythonScriptInCondaEnvironment(String sessionId) {
        // 构建命令行
        String condaEnvironment = "py311"; // 替换为你的Conda环境名称
        String pythonScriptPath = "E:\\projects\\UX-rank\\core\\app.py";
        String[] cmd = {"conda", "run", "-n", condaEnvironment,
                "python", pythonScriptPath,
                "--url", "www.baidu.com", "--output_dir", "E:\\projects\\UX-rank\\report\\"};

        // 创建进程并执行命令行
//            ProcessBuilder pb = new ProcessBuilder(cmd);
//            Process process = pb.start();

//            // 读取命令行输出
//            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//            String line;
//            while ((line = reader.readLine()) != null) {
//                // 处理命令行输出
//                System.out.println(line);
//            }
        // sleep
        sleep(sessionId, "analyse", 8);


    }
//    @Async
    public static void sleep(String sessionId, String type, int loopCount){
        ScheduledTask scheduledTask = new ScheduledTask();
        double cnt = 0;
        for (int i = 1; i <= loopCount; i++) {
            try {
                double sleepTime = 1 + Math.random() * 0.5;
                Thread.sleep((long) (sleepTime * 1000));
                cnt = cnt + 100.0/loopCount;
                cnt = Math.min(cnt, 100);
                scheduledTask.pushMessage(sessionId, type, cnt);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}