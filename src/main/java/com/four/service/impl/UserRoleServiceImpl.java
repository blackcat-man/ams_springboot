package com.four.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.four.entity.Permission;
import com.four.entity.UserRole;
import com.four.mapper.UserRoleMapper;
import com.four.service.IUserRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Mr_xu
 * @since 2022-05-18
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

}
