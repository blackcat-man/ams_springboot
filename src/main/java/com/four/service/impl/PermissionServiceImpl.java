package com.four.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.four.entity.Permission;
import com.four.mapper.PermissionMapper;
import com.four.service.IPermissionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Mr_xu
 * @since 2022-05-16
 */
@Service
@Transactional
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

}