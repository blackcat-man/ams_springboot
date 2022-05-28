package com.four.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.four.entity.Role;
import com.four.entity.User;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Mr_xu
 * @since 2022-05-16
 */
public interface IRoleService extends IService<Role> {


//    /**
//     * 添加权限到角色
//     * @param roleId
//     * @param permissionIds
//     */
//    void addPermissionToRole(Integer roleId,Integer [] permissionIds);


    /**
     * 根据id查找角色，包括角色的权限
     * @param rid
     * @return
     */
    Role getRoleById(Integer rid);

    Page<Role> getPage(Integer page, Integer size, Role role);
}
