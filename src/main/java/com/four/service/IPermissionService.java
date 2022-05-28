package com.four.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.four.entity.Permission;
import com.four.entity.Role;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Mr_xu
 * @since 2022-05-16
 */
public interface IPermissionService extends IService<Permission> {

    Page<Permission> getPage(Integer page, Integer size, Permission permission);

}