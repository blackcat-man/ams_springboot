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

    List<Permission> getUserPermission(Integer uid);
}
