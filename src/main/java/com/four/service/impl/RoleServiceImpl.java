package com.four.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.four.entity.Permission;
import com.four.entity.PermissionRole;
import com.four.entity.Role;
import com.four.entity.User;
import com.four.mapper.RoleMapper;
import com.four.service.IPermissionRoleService;
import com.four.service.IPermissionService;
import com.four.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Mr_xu
 * @since 2022-05-16
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {
    @Autowired
    private IPermissionRoleService permissionRoleService;
    @Autowired
    private IPermissionService permissionService;


    @Override
    @Transactional
    public boolean removeByIds(Collection<? extends Serializable> idList) {
        boolean flag = false;
        for (Serializable rid : idList) {
            // 删除角色对应的权限
            permissionRoleService.remove(new LambdaQueryWrapper<PermissionRole>().eq(PermissionRole::getRoleId,rid));
            // 删除角色
            flag = removeById(rid);
        }
        return flag;
    }
    /**
     * 根据id查找角角色，并封装权限
     * @param rid
     * @return
     */
    @Override
    public Role getRoleById(Integer rid) {
        // 查询role
        Role role = this.getById(rid);
        // 查询角色的权限列表  封装permission
        LambdaQueryWrapper<PermissionRole> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(PermissionRole::getRoleId,rid);
        List<PermissionRole> list = permissionRoleService.list(lambdaQueryWrapper);
        // 如果权限为空，直接返回role 不需要封装权限
        if (list.isEmpty())
        {
            return role;
        }
        // 得到用户对应的权限id集合
        List<Integer> ids = list.stream().map(item -> {
            Integer permissionId = item.getPermissionId();
            return permissionId;
        }).collect(Collectors.toList());
        // 查询所有权限
        LambdaQueryWrapper<Permission> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Permission::getPermissionId,ids);
        List<Permission> permissionList = permissionService.list(queryWrapper);
        role.setPermissions(permissionList);
        return role;
    }

    /**
     * 分页查询
     * @param page
     * @param size
     * @return
     */
    @Override
    public Page<Role> getPage(Integer page, Integer size, Role role) {

        Page<Role> rolePage = new Page<>(page, size);
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(role.getRoleName()),Role::getRoleName,role.getRoleName());
        this.page(rolePage,queryWrapper);

        return rolePage;
    }
}
