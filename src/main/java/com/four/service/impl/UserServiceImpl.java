package com.four.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.four.controller.common.LoginEntity;
import com.four.controller.common.R;
import com.four.entity.Role;
import com.four.entity.User;
import com.four.entity.UserRole;
import com.four.mapper.UserMapper;
import com.four.service.IRoleService;
import com.four.service.IUserRoleService;
import com.four.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private IUserRoleService userRoleService;


    @Autowired
    private IRoleService roleService;

    /**
     * 添加角色到用户
     *
     * @param userId
     * @param roleIds
     */
    @Override
    @Transactional
    public void addRoleToUser(Integer userId, Integer[] roleIds) {
        List<UserRole> userRoles = new ArrayList<>();
        // 循环构建用户角色关系集合
        for (Integer roleId : roleIds) {
            UserRole userRole = new UserRole(userId,roleId);
            userRoles.add(userRole);
        }
        // 批量添加
        userRoleService.saveBatch(userRoles);
    }

    /**
     * 获取用户所有用户包括用户的角色
     *
     * @param username
     * @return
     */
    @Override
    public User getUserWithRole(String username) {
        // 查询出user
        User user = this.getOne(new LambdaQueryWrapper<User>().eq(User::getName,username));

        // 开始封装用户的角色信息

        // 查询出用户对应的role列表
        List<UserRole> list = userRoleService.list(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId,user.getUserId()));

        // 如果用户没有对应的角色，直接返回
        if (list.size() < 1)
        {
            return user;
        }
        // 取出用户对应的role ID
        List<Integer> roleIds = list.stream().map(item -> {
            Integer roleId = item.getRoleId();
            return roleId;
        }).collect(Collectors.toList());

        // 得到role List
        List<Role> roles = roleService.list(new LambdaQueryWrapper<Role>().in(Role::getRoleId, roleIds));

        user.setRoles(roles);
        return user;
    }

    /**
     * 查询所有用户并分页
     * @param page
     * @param pageSize
     */
    @Override
    public Page<User> getPage(Integer page, Integer pageSize, String fuzzyName) {
        Page<User> iPage = new Page<>(page,pageSize);
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(fuzzyName),User::getName,fuzzyName);
        this.page(iPage,queryWrapper);
        return iPage;
    }
}