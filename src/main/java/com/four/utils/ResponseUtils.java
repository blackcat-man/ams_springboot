package com.four.utils;

import com.alibaba.fastjson.JSON;
import com.four.controller.common.R;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResponseUtils {
    public static void responseJson(HttpServletResponse response,Object data) throws IOException {
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(JSON.toJSONString(data));
    }
}
