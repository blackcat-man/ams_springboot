package com.four;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.four.entity.LoginHistory;
import com.four.entity.Permission;
import com.four.entity.User;
import com.four.mapper.UserMapper;
import com.four.mapper.UserRoleMapper;
import com.four.service.ILoginHistoryService;
import com.four.service.ILoginService;
import com.four.service.IUserRoleService;
import com.four.service.IUserService;
import com.four.utils.RedisUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;


@SpringBootTest
class SpringBootAmsApplicationTests {

    @Autowired
    private IUserService userService;


    @Autowired
    private UserMapper userMapper;


    @Autowired
    private ILoginHistoryService loginHistoryService;

    @Test
    void test() {

        List<LoginHistory> list = loginHistoryService.list(
                new LambdaQueryWrapper<LoginHistory>()
                        .eq(LoginHistory::getLoginName, "admin")
                        .orderByDesc(LoginHistory::getTime)
                        .last("limit 10"));

        System.out.println(list);

    }

    @Test
    void testMd5() {
        String encode = new BCryptPasswordEncoder().encode("202cb962ac59075b964b07152d234b70");
        System.out.println(encode);
    }

    @Test
    void testOne() {
        Page<User> page = userService.getPage(1, 3, new User());
        System.out.println(page.getRecords());
        System.out.println(page.getPages());
    }

    @Test
    void name() {
        User user = userMapper.selectById(1);
        System.out.println(user);
    }
}
