package com.four.controller;



import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.four.controller.common.R;
import com.four.entity.User;
import com.four.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Mr_xu
 * @since 2022-05-23
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户管理")
public class UserController {

    @Autowired
    private IUserService userService;

    /**
     * 分页查询
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/findAll")
    @ApiOperation("查询所有用户")
    public R findAll(Integer page,Integer size)
    {
        Page<User> userList = userService.getPage(page, size, "");

        return R.success("请求成功",userList);
    }

    @PostMapping("/save")
    @ApiOperation("添加用户")
    public R save(User user)
    {
        userService.save(user);
        return R.success("添加成功");
    }
}