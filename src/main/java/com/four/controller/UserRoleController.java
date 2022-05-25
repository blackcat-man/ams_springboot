package com.four.controller;


import com.four.controller.common.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Mr_xu
 * @since 2022-05-23
 */
@Controller
@RequestMapping("/userRole")
@Api(tags = "用户管理")
public class UserRoleController {

    @PostMapping("/addRole")
    @ApiOperation("添加角色到用户")
    public R addRole()
    {
        return null;
    }
}