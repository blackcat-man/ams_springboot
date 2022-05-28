package com.four.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.four.controller.common.R;
import com.four.entity.LoginHistory;
import com.four.service.ILoginHistoryService;
import com.four.utils.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Mr_xu
 * @since 2022-05-28
 */
@RestController
@RequestMapping("/historyLogin")
@Api("登录")
public class LoginHistoryController {

    @Autowired
    private ILoginHistoryService loginHistoryService;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping
    @ApiOperation("获取登录日志")
    public R getLoginHistory(HttpServletRequest request) {
        String username = jwtUtils.getUsername(request.getHeader("token"));
        List<LoginHistory> list = loginHistoryService.list(
                new LambdaQueryWrapper<LoginHistory>()
                        .eq(LoginHistory::getLoginName, username)
                        .orderByDesc(LoginHistory::getTime)
                        .last("limit 10"));
        List<Integer> ids = list.stream().map(LoginHistory::getHistoryId).collect(Collectors.toList());
        loginHistoryService.remove(
                new LambdaQueryWrapper<LoginHistory>()
                        .eq(LoginHistory::getLoginName, username)
                        .notIn(LoginHistory::getHistoryId, ids));
        return R.success("查询成功！", list);
    }
}

