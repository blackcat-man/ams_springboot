package com.four.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.four.controller.common.R;
import com.four.entity.Role;
import com.four.entity.User;
import com.four.service.IRoleService;
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
@RequestMapping("/role")
@Api(tags = "角色管理")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    /**
     * 分页查询
     * @param page
     * @param size
     * @return
     */
    @PostMapping("/{page}/{size}")
    @ApiOperation("查询角色列表·分页")
    @PreAuthorize("hasAuthority('/role/find')")
    public R findAll(@PathVariable Integer page,
                     @PathVariable Integer size,
                     @RequestBody Role role) {
        Page<Role> roleList = roleService.getPage(page, size, role);
        return R.success("请求成功", roleList);
    }

    @GetMapping("/findAll")
    @ApiOperation("查询所有角色")
    @PreAuthorize("hasAuthority('/role/find')")
    public R findAll()
    {
        List<Role> roles = roleService.list();
        return R.success("查询成功！",roles);
    }

    @GetMapping("/{id}")
    @ApiOperation("根据ID查找角色")
    @PreAuthorize("hasAuthority('/role/find')")
    public R findById(@PathVariable("id") Integer id) {
        Role role = roleService.getRoleById(id);
        return R.success("查询成功！", role);
    }

    /**
     * 增加角色
     * @param role
     * @return
     */
    @PostMapping
    @ApiOperation("添加角色")
    @PreAuthorize("hasAuthority('/role/save')")
    public R save(@RequestBody Role role) {
        roleService.save(role);
        return R.success("添加成功");
    }

    /**
     * 删除角色
     *
     * @param ids
     * @return
     */
    @DeleteMapping
    @ApiOperation("删除角色")
    @PreAuthorize("hasAuthority('/role/del')")
    public R delete(Integer[] ids) {
        roleService.removeByIds(Arrays.asList(ids));
        return R.success("删除成功！");
    }
}

