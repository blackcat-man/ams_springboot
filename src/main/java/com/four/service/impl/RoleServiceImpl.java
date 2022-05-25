package com.four.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.four.entity.Permission;
import com.four.entity.PermissionRole;
import com.four.entity.Role;
import com.four.mapper.RoleMapper;
import com.four.service.IPermissionRoleService;
import com.four.service.IPermissionService;
import com.four.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    /**
     * 添加权限到角色
     *
     * @param roleId
     * @param permissionIds
     */
    @Override
    public void addPermissionToRole(Integer roleId, Integer[] permissionIds) {
        List<PermissionRole> permissionRoles = new ArrayList<>();
        for (Integer permissionId : permissionIds) {
            PermissionRole permissionRole = new PermissionRole(permissionId,roleId);
            permissionRoles.add(permissionRole);
        }
        permissionRoleService.saveBatch(permissionRoles);
    }

    /**
     * 根据id查找角色
     *
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
}
