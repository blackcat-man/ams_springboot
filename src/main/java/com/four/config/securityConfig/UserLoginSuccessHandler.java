package com.four.config.securityConfig;

import com.four.controller.common.R;
import com.four.entity.LoginHistory;
import com.four.entity.SecurityUser;
import com.four.service.ILoginHistoryService;
import com.four.utils.JwtUtils;
import com.four.utils.RedisUtils;
import com.four.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;


/**
 * 登录成功处理
 */
@Component
public class UserLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private ILoginHistoryService loginHistoryService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        // 获取当前用户
        SecurityUser user = (SecurityUser) authentication.getPrincipal();
        // 将用户身份写到上下文中
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 将用户信息存入redis
        redisUtils.save(user.getUsername(),authentication);
        // 生成token并保存到redis中
        String token = jwtUtils.generateToken(user.getUsername());
        redisUtils.save(user.getUsername(), token);

        // 清除redis验证码缓存
        redisUtils.deleteCode(httpServletRequest.getParameter("pCode"));

        // 写入登录日志
        LoginHistory loginHistory = new LoginHistory();
        loginHistory.setLoginName(user.getUser().getUsername());
        loginHistory.setIpAddr(httpServletRequest.getRemoteAddr());
        loginHistory.setTime(LocalDateTime.now());
        loginHistoryService.save(loginHistory);


        // 相应数据以及token
        Map<String,Object> map = new HashMap<>();
        map.put("token",token);
        map.put("user",user);
        map.put("time", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        ResponseUtils.responseJson(httpServletResponse, R.success("登录成功！",map));
    }
}
