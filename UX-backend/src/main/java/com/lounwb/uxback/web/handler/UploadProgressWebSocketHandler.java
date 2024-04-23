package com.lounwb.uxback.web.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lounwb.uxback.web.tasker.PythonRunner;
import com.lounwb.uxback.web.tasker.ScheduledTask;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.*;

import javax.annotation.Resource;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
@RestController
@RequiredArgsConstructor
@ServerEndpoint("/websocket/{sessionId}")
public class UploadProgressWebSocketHandler {

    @Resource
    private ScheduledTask scheduledTask;

    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
     */
    private static int onlineCount = 0;
    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
     */
    private static ConcurrentHashMap<String, UploadProgressWebSocketHandler> webSocketMap = new ConcurrentHashMap<>();
    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;
    /**
     * 接收sessionId
     */
    private String sessionId = "";

    @SneakyThrows
    @OnOpen
    public void onOpen(Session session, @PathParam("sessionId") String sessionId) {
        this.session = session;
        this.sessionId = sessionId;
        if (webSocketMap.containsKey(sessionId)) {
            webSocketMap.remove(sessionId);
            webSocketMap.put(sessionId, this);
            //加入set中
        } else {
            webSocketMap.put(sessionId, this);
            //加入set中
            addOnlineCount();
            //在线数加1
        }

        log.info("用户连接:" + sessionId + ",当前在线人数为:" + getOnlineCount());

    }
    @SneakyThrows
    public static boolean isOnline(String sessionId){
        return webSocketMap.containsKey(sessionId);
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if (webSocketMap.containsKey(sessionId)) {
            webSocketMap.remove(sessionId);
            //从set中删除
            subOnlineCount();
        }
        log.info("用户退出:" + sessionId + ",当前在线人数为:" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("用户消息:" + sessionId + ",报文:" + message);
        if (!StringUtils.isEmpty(message)) {
            try {
                //解析发送的报文
                JSONObject jsonObject = JSON.parseObject(message);
                String type = (String) jsonObject.get("type");
                String sessionId = (String) jsonObject.get("sessionId");
                if(type.equals("analyse")) {
                    PythonRunner.runPythonScriptInCondaEnvironment(sessionId);
                }else if(type.equals("parse")) {
                    PythonRunner.sleep(sessionId, "parse", 1);
                }else if(type.equals("rating")){
                    PythonRunner.sleep(sessionId, "rating", 3);
                }else if(type.equals("report")){
                    PythonRunner.sleep(sessionId, "report", 3);
                }
            } catch (Exception e) {
                log.error("json解析失败");
            }
        }
    }

    /**
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("用户错误:" + this.sessionId + ",原因:" + error.getMessage());
        error.printStackTrace();
    }

    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) {
        try {
            synchronized (this.session){
                this.session.getBasicRemote().sendText(message);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }


    /**
     * 实现服务器主动推送
     */
    public static void sendAllMessage(String message) throws IOException {
        ConcurrentHashMap.KeySetView<String, UploadProgressWebSocketHandler> sessionIds = webSocketMap.keySet();
        for (String sessionId : sessionIds) {
            UploadProgressWebSocketHandler webSocketServer = webSocketMap.get(sessionId);
            webSocketServer.session.getBasicRemote().sendText(message);
            System.out.println("webSocket实现服务器主动推送成功sessionIds====" + sessionIds);
        }
    }

    /**
     * 发送自定义消息
     */
    public static void sendInfo(String message, String sessionId) throws IOException {
        log.info("发送消息到:" + sessionId + "，报文:" + message);
        if (!StringUtils.isEmpty(message) && webSocketMap.containsKey(sessionId)) {
            webSocketMap.get(sessionId).sendMessage(message);
        } else {
            log.error("用户" + sessionId + ",不在线！");
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        UploadProgressWebSocketHandler.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        UploadProgressWebSocketHandler.onlineCount--;
    }

}
