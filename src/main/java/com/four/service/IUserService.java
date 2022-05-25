package com.four.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.four.controller.common.LoginEntity;
import com.four.controller.common.R;
import com.four.entity.User;

import javax.servlet.http.HttpSession;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Mr_xu
 * @since 2022-05-16
 */
public interface IUserService extends IService<User> {


    /**
     * 添加角色到用户
     * @param userId
     * @param RoleIds
     */
    void addRoleToUser(Integer userId,Integer [] RoleIds);

    /**
     * 获取用户所有用户包括用户的角色
     * @return
     */
    User getUserWithRole(String username);

    Page<User> getPage(Integer page, Integer pageSize, String fuzzyName);

}