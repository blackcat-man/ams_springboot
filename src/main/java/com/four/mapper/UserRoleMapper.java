package com.four.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.four.entity.Permission;
import com.four.entity.UserRole;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Mr_xu
 * @since 2022-05-18
 */
public interface UserRoleMapper extends BaseMapper<UserRole> {

    /**
     * 根据用户id得到用户所有权限
     * @param uid
     * @return
     */
    List<Permission> getUserPermission(Integer uid);

    /**
     * 根据uid得到用户的roleNames
     * @param uid
     * @return
     */
    List<String> getRoleNamesByUserId(Integer uid);
}
