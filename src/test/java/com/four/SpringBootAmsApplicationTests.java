package com.four;

import com.four.entity.Permission;
import com.four.entity.User;
import com.four.mapper.UserRoleMapper;
import com.four.service.ILoginService;
import com.four.service.IUserService;
import com.four.utils.RedisUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootTest
class SpringBootAmsApplicationTests {

    @Autowired
    private IUserService userService;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private ILoginService loginService;


    @Autowired
    private RedisUtils redisUtils;


    @Test
    void testString() {
        String str = "hahahxfsd";
        int xf = str.indexOf("haha");
        System.out.println(xf);
    }

    @Test
    void testRedis() {
        redisUtils.delete("xuhuan");
    }

    @Test
    void testRedisGet() {
        System.out.println(redisUtils.getToken("token_key"));
    }

    @Test
    void testLoginService() {
        UserDetails xuhuan = loginService.loadByUsername("xuhuan");
        System.out.println(xuhuan.getAuthorities());
        System.out.println(xuhuan.getUsername());
        System.out.println(xuhuan.getPassword());
    }

    @Test
    void testUserRoleMapper() {
        List<Permission> userPermission = userRoleMapper.getUserPermission(2);
        System.out.println(userPermission);
    }

    @Test
    void contextLoads() {

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode("123");
        System.out.println(encode);
        System.out.println(encode.equals("$2a$10$1GQ8qTHjmq94YdfAvLGsmeij1ZactutEF0XjWhWi4.4kP0r4QU.A."));

    }

    @Test
    void testUserService() {
        User user = userService.getUserWithRole("xuhuan");
        System.out.println(user);
    }
}
