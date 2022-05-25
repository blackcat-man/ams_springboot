package com.four.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.four.entity.Role;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Mr_xu
 * @since 2022-05-16
 */
public interface IRoleService extends IService<Role> {


    /**
     * 添加权限到角色
     * @param roleId
     * @param permissionIds
     */
    void addPermissionToRole(Integer roleId,Integer [] permissionIds);


    /**
     * 根据id查找角色
     * @param rid
     * @return
     */
    Role getRoleById(Integer rid);
}
