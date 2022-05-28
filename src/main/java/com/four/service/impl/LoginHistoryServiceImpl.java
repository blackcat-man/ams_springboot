package com.four.service.impl;

import com.four.entity.LoginHistory;
import com.four.mapper.LoginHistoryMapper;
import com.four.service.ILoginHistoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Mr_xu
 * @since 2022-05-28
 */
@Service
public class LoginHistoryServiceImpl extends ServiceImpl<LoginHistoryMapper, LoginHistory> implements ILoginHistoryService {
}
