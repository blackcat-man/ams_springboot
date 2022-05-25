package com.four.controller.common;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class R {
    private String code; //响应码 1 正常   0 失败
    private String msg; //返回消息
    private Object data; //返回数据




    private static final String SUCCESS = "200"; // 成功
    private static final String ERROR = "400"; // 未知错误
    private static final String ACCESS_DENIED = "403"; // 拒绝访问
    private static final String UN_LOGIN = "401"; // 未登录



    public static R accessDenied()
    {
        return new R(ACCESS_DENIED,"你没有权限访问这个资源~",null);
    }

    public static R unLogin()
    {
        return new R(UN_LOGIN,"请先登录在操作哦~",null);
    }

    public static R success(String msg , Object data)
    {
        return new R(SUCCESS,msg,data);
    }

    public static R success(String msg)
    {
        return new R(SUCCESS,msg,null);
    }

    public static R error(String msg)
    {
        return new R(ERROR,msg,null);
    }
}
