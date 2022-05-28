package com.four.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.four.controller.common.R;
import com.four.entity.Permission;
import com.four.entity.Role;
import com.four.service.IPermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Mr_xu
 * @since 2022-05-23
 */
@RestController
@RequestMapping("/permission")
@Api(tags = "权限管理")
public class PermissionController {

    @Autowired
    private IPermissionService permissionService;

    /**
     * 分页查询
     * @param page
     * @param size
     * @return
     */
    @PostMapping("/{page}/{size}")
    @ApiOperation("查询权限列表·分页")
    @PreAuthorize("hasAuthority('/permission/find')")
    public R findAll(@PathVariable Integer page,
                     @PathVariable Integer size,
                     @RequestBody Permission permission) {
        Page<Permission> permissionPage = permissionService.getPage(page, size, permission);

        return R.success("请求成功", permissionPage);
    }


    @GetMapping
    @ApiOperation("查询所有权限")
    @PreAuthorize("hasAuthority('/permission/find')")
    public R findAll()
    {
        List<Permission> permissions = permissionService.list();
        return R.success("查询成功！",permissions);
    }

    @GetMapping("/{id}")
    @ApiOperation("根据ID查找权限")
    @PreAuthorize("hasAuthority('/permission/find')")
    public R findById(@PathVariable Integer id) {
        Permission permission = permissionService.getById(id);
        return R.success("查询成功！", permission);
    }


    @PostMapping
    @ApiOperation("添加权限")
    @PreAuthorize("hasAuthority('/permission/save')")
    public R save(@RequestBody Permission permission) {
        permissionService.save(permission);
        return R.success("添加成功！");
    }

    @DeleteMapping
    @ApiOperation("删除权限")
    @PreAuthorize("hasAuthority('/permission/del')")
    public R delete(Integer[] ids) {
        permissionService.removeByIds(Arrays.asList(ids));
        return R.success("删除成功！");
    }

    @PutMapping
    @ApiOperation("修改权限")
    @PreAuthorize("hasAuthority('/permission/update')")
    public R update(@RequestBody Permission permission)
    {
        permissionService.updateById(permission);
        return R.success("修改成功！");
    }

}

