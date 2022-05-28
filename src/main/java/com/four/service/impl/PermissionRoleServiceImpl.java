package com.four.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.four.entity.Permission;
import com.four.entity.PermissionRole;
import com.four.entity.Role;
import com.four.mapper.PermissionRoleMapper;
import com.four.service.IPermissionRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Mr_xu
 * @since 2022-05-18
 */
@Service
public class PermissionRoleServiceImpl extends ServiceImpl<PermissionRoleMapper, PermissionRole> implements IPermissionRoleService {

    @Override
    @Transactional
    public void addRolePermission(Role role) {

        // 1.清理角色拥有的权限
        remove(new LambdaQueryWrapper<PermissionRole>().eq(PermissionRole::getRoleId,role.getRoleId()));
        // 2.为角色添加权限
        List<Permission> permissions = role.getPermissions();
        List<PermissionRole> permissionRoles = permissions.stream().map(item -> {
            PermissionRole permissionRole = new PermissionRole();
            permissionRole.setRoleId(role.getRoleId());
            permissionRole.setPermissionId(item.getPermissionId());
            System.out.println(permissionRole);
            return permissionRole;
        }).collect(Collectors.toList());

        saveBatch(permissionRoles);
    }
}
