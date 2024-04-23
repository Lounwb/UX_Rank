package com.lounwb.uxback.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusCodeEnum {
    SC200(200, "OK:操作成功"),
    SC400(400, "Bad Request:请求报文存在语法错误"),
    SC401(401, "Unauthorized:匿名用户访问权限资源时的异常"),
    SC403(403, "Forbidden:无访问权限，请联系管理员授予权限"),
    SC404(404, "Not Found:请求的资源不存在"),
    SC500(500, "Inter Server Error:系统异常，请稍后重试");
    private final Integer code;
    private final String msg;
}
