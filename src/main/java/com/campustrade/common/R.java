package com.campustrade.common;

/**
 * 统一API响应格式
 * 前端收到的JSON格式: {"code": 200, "msg": "success", "data": {...}}
 *
 * code: 200成功, 500失败
 * msg:  提示信息
 * data: 业务数据,成功时返回,失败时为null
 */
public class R<T> {
    private int code;       // 状态码: 200成功 500失败 (Integer)
    private String msg;     // 提示信息 (String)
    private T data;         // 响应数据,泛型 (Object/Array/null)

    public int getCode() { return code; }
    public void setCode(int code) { this.code = code; }
    public String getMsg() { return msg; }
    public void setMsg(String msg) { this.msg = msg; }
    public T getData() { return data; }
    public void setData(T data) { this.data = data; }

    /** 成功响应(带数据) */
    public static <T> R<T> ok(T data) {
        R<T> r = new R<>();
        r.code = 200;
        r.msg = "success";
        r.data = data;
        return r;
    }

    /** 成功响应(无数据) */
    public static <T> R<T> ok() { return ok(null); }

    /** 失败响应 */
    public static <T> R<T> fail(String msg) {
        R<T> r = new R<>();
        r.code = 500;
        r.msg = msg;
        return r;
    }

    /** 失败响应(自定义code) */
    public static <T> R<T> fail(int code, String msg) {
        R<T> r = new R<>();
        r.code = code;
        r.msg = msg;
        return r;
    }
}
