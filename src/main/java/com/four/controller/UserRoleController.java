package com.four.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.four.controller.common.R;
import com.four.entity.UserRole;
import com.four.service.IUserRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Mr_xu
 * @since 2022-05-23
 */
@RestController
@RequestMapping("/userRole")
@Api(tags = "用户管理")
public class UserRoleController {

    @Autowired
    private IUserRoleService userRoleService;

    @PostMapping("/addRole")
    @ApiOperation("添加角色到用户")
    public R addRole(UserRole userRole)
    {
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getUserId,userRole.getUserId());
        queryWrapper.eq(UserRole::getRoleId,userRole.getRoleId());
        userRoleService.remove(queryWrapper);
        return R.success("移除成功！");
    }

    @DeleteMapping("delRole")
    @ApiOperation("从用户中移除角色")
    public R delRole(UserRole userRole)
    {
        userRoleService.save(userRole);
        return R.success("添加成功");
    }
}