package com.four.service.impl;

import com.four.entity.SecurityUser;
import com.four.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private ILoginService loginService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails securityUser = loginService.loadByUsername(username);

        if (Objects.isNull(securityUser))
        {
            throw new UsernameNotFoundException("用户或密码错误！");
        }

        return securityUser;
    }
}
