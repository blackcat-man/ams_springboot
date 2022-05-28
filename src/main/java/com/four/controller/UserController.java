package com.four.controller;



import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.four.controller.common.R;
import com.four.entity.User;
import com.four.entity.UserPassword;
import com.four.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;


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
    @PostMapping("/{page}/{size}")
    @ApiOperation("查询所有用户")
    @PreAuthorize("hasAuthority('/user/find')")
    public R findAll(@PathVariable("page") Integer page,
                     @PathVariable("size") Integer size,
                     @RequestBody User user)
    {
        Page<User> userList = userService.getPage(page, size, user);
        return R.success("请求成功",userList);
    }

    @GetMapping("/{id}")
    @ApiOperation("根据ID查找用户")
    @PreAuthorize("hasAuthority('/user/find')")
    public R findById(@PathVariable Integer id)
    {
        User user = userService.getUserWithRole(id);
        return R.success("查询成功！",user);
    }

    /**
     * 增加用户
     * @param user
     * @return
     */
    @PostMapping
    @ApiOperation("添加用户")
    @PreAuthorize("hasAuthority('/user/save')")
    public R save(@RequestBody User user)
    {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userService.saveUserWithRole(user);
        return R.success("添加成功");
    }

    /**
     * 删除用户
     * @param ids
     * @return
     */
    @DeleteMapping
    @ApiOperation("删除用户")
    @PreAuthorize("hasAuthority('/user/del')")
    public R delete(Integer[] ids)
    {
        userService.removeByIds(Arrays.asList(ids));
        return R.success("删除成功！");
    }

    /**
     * 根据ID修改用户
     * @param user
     * @return
     */

    @PutMapping
    @ApiOperation("修改用户")
    @PreAuthorize("hasAuthority('/user/update')")
    public R update(@RequestBody User user)
    {
        userService.updateByIdWithRoles(user);
        return R.success("修改成功！");
    }


    @PostMapping("/updatePwd")
    @ApiOperation("修改密码")
    public R updatePwd(HttpServletRequest request, @RequestBody UserPassword userPassword)
    {
        return userService.updatePwd(request,userPassword);
    }
}