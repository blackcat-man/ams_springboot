package com.four.service;

import com.four.entity.PermissionRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.four.entity.Role;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Mr_xu
 * @since 2022-05-18
 */
public interface IPermissionRoleService extends IService<PermissionRole> {

    void addRolePermission(Role role);
}
