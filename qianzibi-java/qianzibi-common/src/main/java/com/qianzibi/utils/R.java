package com.qianzibi.utils;

import com.qianzibi.common.ResultCode;
import lombok.Data;

//统一返回结果的类
//在类中，每个方法都返回该对象，是为了实现链式编程
@Data
public class R<T> {

    private Boolean success;

    private Integer code;

    private String message;

    private T data;

    //让无参构造不能被随意使用
    private R() {
    }

    //成功的静态方法
    public static R ok() {
        R r = new R();
        r.setSuccess(true);
        r.setCode(ResultCode.SUCCESS);
        r.setMessage("成功");
        return r;
    }

    //失败的静态方法
    public static R error_other() {
        R r = new R();
        r.setSuccess(false);
        r.setCode(ResultCode.ERROR_OTHER);
        r.setMessage("失败");
        return r;
    }

    ///找不到地址
    public static R error_not_found() {
        R r = new R();
        r.setSuccess(false);
        r.setCode(ResultCode.NOT_FOUND);
        r.setMessage("地址不存在");
        return r;
    }

    //绕过前端的参数访问
    public static R error_600() {
        R r = new R();
        r.setSuccess(false);
        r.setCode(ResultCode.ERROR_600);
        r.setMessage("非法参数访问");
        return r;
    }

    public R success(Boolean success) {
        this.setSuccess(success);
        return this;
    }

    public R message(String message) {
        this.setMessage(message);
        return this;
    }

    public R code(Integer code) {
        this.setCode(code);
        return this;
    }

    public R data(T data) {
        this.data = data;
        return this;
    }
}
