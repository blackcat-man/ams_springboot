package com.four.controller;


import com.four.controller.common.R;
import com.four.entity.PermissionRole;
import com.four.entity.Role;
import com.four.service.IPermissionRoleService;
import com.four.service.IPermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
@RequestMapping("/permissionRole")
@Api(tags = "权限管理")
public class PermissionRoleController {


    @Autowired
    private IPermissionRoleService permissionRoleService;

    @PostMapping
    @ApiOperation("添加权限到角色")
    public R insertRoleAuth(@RequestBody Role role)
    {
        permissionRoleService.addRolePermission(role);
        return R.success("修改权限成功！");
    }
}

