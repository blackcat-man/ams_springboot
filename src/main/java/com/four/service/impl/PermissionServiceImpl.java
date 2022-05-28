package com.four.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.four.entity.Permission;
import com.four.entity.PermissionRole;
import com.four.entity.Role;
import com.four.mapper.PermissionMapper;
import com.four.service.IPermissionService;
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
 * @since 2022-05-16
 */
@Service
@Transactional
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

    @Override
    public Page<Permission> getPage(Integer page, Integer size, Permission permission) {
        Page<Permission> perPage = new Page<>(page, size);
        LambdaQueryWrapper<Permission> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(permission.getPermissionName()),Permission::getPermissionName,permission.getPermissionName());
        this.page(perPage,queryWrapper);
        return perPage;
    }


}