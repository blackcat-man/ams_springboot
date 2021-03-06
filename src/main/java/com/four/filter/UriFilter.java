package com.four.filter;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.four.controller.common.R;
import com.four.utils.JwtUtils;
import com.four.utils.RedisUtils;
import com.four.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Set;


@Component
public class UriFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 获取当请求头中的token
        String accessToken = request.getHeader("token");

        String uri = request.getRequestURI();
        if (uri.indexOf("/login") == 0)
        {
            // 如果访问的是登录接口，判断验证码
            String pCode = request.getParameter("pCode");
            String code = request.getParameter("code");
            String Rcode = redisUtils.getCode(pCode);
            if (StringUtils.isBlank(code) || StringUtils.isBlank(Rcode) || !code.equalsIgnoreCase(Rcode))
            {
                // 验证码为空 或 验证码不相同，则直接返回
                redisUtils.deleteCode(request.getParameter("pCode"));
                ResponseUtils.responseJson(response,R.error("验证码错误！"));
                return;
            }
        }

        if (!jwtUtils.checkToken(accessToken))
        {
            // 如果token不存在或不合法，无视放行，交给SpringSecurity处理
            if (uri.indexOf("/logout") == 0)
            {
                ResponseUtils.responseJson(response,R.error("无效的请求！"));
                return;
            }
            filterChain.doFilter(request,response);
            return;
        }

        // 解析token
        // 获取到当前用户的account
        String username = jwtUtils.getUsername(accessToken);
        // redis中的token
        String token = redisUtils.getToken(username);
        if (!jwtUtils.validityToken(token,accessToken))
        {
            ResponseUtils.responseJson(response,R.unLogin("登录信息过期！"));
            return;
        }

        System.out.println("自定义JWT过滤器获得用户名为"+username);

        // 刷新token有效期
        redisUtils.refreshExpired(username);

        Authentication authentication = redisUtils.getAuthentication(username);

        if (authentication == null)
        {
            ResponseUtils.responseJson(response,R.unLogin("用户登录信息过期！"));
            return;
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request,response);
    }
}
