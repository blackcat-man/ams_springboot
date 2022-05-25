package com.four.config.securityConfig;

import com.four.controller.common.R;
import com.four.utils.RedisUtils;
import com.four.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 退出登录
 */
@Component
public class UserLogoutSuccessHandler implements LogoutSuccessHandler {

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        // 清除redis缓存
        String name = authentication.getName();
        redisUtils.delete(name);
        // 清除上下文信息
        SecurityContextHolder.clearContext();

        ResponseUtils.responseJson(httpServletResponse, R.success("退出登录！"));
    }
}
