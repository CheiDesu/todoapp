package com.ddfantasy.todoapp.service.impl;

import com.ddfantasy.todoapp.entity.User;
import com.ddfantasy.todoapp.mapper.UserMapper;
import com.ddfantasy.todoapp.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chei
 * @since 2022-05-24
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
