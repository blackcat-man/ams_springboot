package com.four.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.four.controller.common.R;
import com.four.entity.Role;
import com.four.entity.User;
import com.four.entity.UserPassword;
import com.four.entity.UserRole;
import com.four.mapper.UserMapper;
import com.four.mapper.UserRoleMapper;
import com.four.service.IUserRoleService;
import com.four.service.IUserService;
import com.four.utils.JwtUtils;
import com.four.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
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
    private RoleServiceImpl roleService;

    @Autowired
    private JwtUtils jwtUtils;

    /**
     * 根据ids删除用户，包括用户的角色
     * @param ids
     * @return
     */
    @Override
    public boolean removeByIds(List<Integer> ids) {
        boolean falg = false;
        for (Serializable id : ids) {
            // 1.删除用户角色关系
            userRoleService.remove(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId,id));
            // 2.删除用户
            falg = removeById(id);
        }
        return falg;
    }

    /**
     * 获取用户所有用户包括用户的角色
     * @param id
     * @return
     */
    @Override
    public User getUserWithRole(Integer id) {
        // 查询出user
        User user = this.getOne(new LambdaQueryWrapper<User>().eq(User::getUserId,id));
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


        List<Role> roles = roleService.list(new LambdaQueryWrapper<Role>().in(Role::getRoleId,roleIds));
        // 设置用户的角色id
        user.setRoles(roles);
        return user;
    }

    /**
     * 查询所有用户并分页
     * @param page
     * @param pageSize
     */
    @Override
    public Page<User> getPage(Integer page, Integer pageSize, User user) {
        Page<User> iPage = new Page<>(page,pageSize);
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(user.getName()),User::getName,user.getName());
        queryWrapper.like(StringUtils.isNotBlank(user.getTelephone()),User::getTelephone,user.getTelephone());
        this.page(iPage,queryWrapper);

        // 封装roles
        List<User> records = iPage.getRecords();
        records = records.stream().map(item -> {
            List<UserRole> userRoles = userRoleService.list(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, item.getUserId()));
            if (userRoles.isEmpty())
            {
                // 如果用户没有对应角色，直接跳过
                return item;
            }
            // 用户有角色，开始封装
            List<Integer> roleids = userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList());
            // 封装roles
            List<Role> roles = roleService.list(new LambdaQueryWrapper<Role>().in(Role::getRoleId,roleids));
            item.setRoles(roles);
            return item;
        }).collect(Collectors.toList());
        iPage.setRecords(records);

        return iPage;
    }

    /**
     * 修改用户，包括角色信息
     * @param user
     */
    @Override
    @Transactional
    public void updateByIdWithRoles(User user) {
        // 1.修改用户信息
        updateById(user);
        // 2.修改用户角色
        addOrUpdateUserRoles(user);
    }

    @Override
    @Transactional
    public void saveUserWithRole(User user) {
        // 1.保存用户
        save(user);
        // 2.保存用户角色信息
        addOrUpdateUserRoles(user);
    }

    // 修改用户密码
    @Override
    public R updatePwd(HttpServletRequest request, UserPassword userPassword) {
        BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
        // 得到用户名
        String userName = jwtUtils.getUsername(request.getHeader("token"));
        if (userName == null)
        {
            return R.error("不合法的请求");
        }
        // 查出数据库中的用户
        User user = getOne(new LambdaQueryWrapper<User>().eq(User::getUsername, userName));
        // 对比密码
        if (!bcpe.matches(userPassword.getOldPwd(),user.getPassword()))
        {
            return R.error("原密码错误！");
        }
        // 密码正确，修改密码
        user.setPassword(new BCryptPasswordEncoder().encode(userPassword.getNewPwd()));
        updateById(user);
        return R.success("修改成功！");
    }
    // 添加或修改用户的角色信息
    private void addOrUpdateUserRoles(User user) {

        Integer userId = user.getUserId(); // 获取用户id
        List<Role> roles = user.getRoles(); // 获取用户的角色列表
        userRoleService.remove(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId,userId));// 删除用户对应的角色信息

        // 添加用户角色
        if (roles.isEmpty()) {
            // 如果用户中的用户列表为空，则不添加，直接返回
            return;
        }
        // 构建用户角色列表
        List<UserRole> collect = roles.stream().map(item -> {
            UserRole userRole = new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(item.getRoleId());
            return userRole;
        }).collect(Collectors.toList());
        // 批量添加
        userRoleService.saveBatch(collect);
    }
}