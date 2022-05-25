package com.four.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.four.entity.Permission;
import com.four.entity.SecurityUser;
import com.four.entity.User;
import com.four.mapper.UserRoleMapper;
import com.four.service.ILoginService;
import com.four.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service
public class LoginServiceImpl implements ILoginService {


    @Autowired
    private IUserService userService;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public SecurityUser loadByUsername(String username) {
        // 查询数据库，判断用户是否存在，如果不存在，抛出UsernameNotFoundException异常
        if (StringUtils.isBlank(username)) {
            throw new UsernameNotFoundException("用户名不合法！");
        }

        User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getName, username));
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("用户名不存在！");
        }
        List<Permission> userPermission = userRoleMapper.getUserPermission(user.getUserId());
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        if (userPermission.get(0) != null) {
            userPermission.stream().forEach(item -> {
                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(item.getUrl());
                grantedAuthorities.add(grantedAuthority);
            });
        }

        return new SecurityUser(user, grantedAuthorities);
    }
}
