package com.four.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.four.controller.common.LoginEntity;
import com.four.controller.common.R;
import com.four.entity.User;
import com.four.entity.UserPassword;
import io.swagger.models.auth.In;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Mr_xu
 * @since 2022-05-16
 */
public interface IUserService extends IService<User> {


    boolean removeByIds(List<Integer> ids);

    /**
     * 获取用户所有用户包括用户的角色
     * @return
     */
    User getUserWithRole(Integer id);

    /**
     * 分页查询
     * @param page
     * @param pageSize
     * @param user
     * @return
     */
    Page<User> getPage(Integer page, Integer pageSize, User user);

    /**
     * 修改用户以及用户的角色
     * @param user
     */
    void updateByIdWithRoles(User user);

    /**
     * 保存用户以及用户的角色
     * @param user
     */
    void saveUserWithRole(User user);

    R updatePwd(HttpServletRequest request, UserPassword userPassword);
}