package com.ddfantasy.todoapp.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ddfantasy.todoapp.common.ResultData;
import com.ddfantasy.todoapp.entity.User;
import com.ddfantasy.todoapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author chei
 * @since 2022-05-24
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;


    //    分页查询
    @GetMapping("/page")
    public ResultData<Page> getPage(Integer page,
                                    Integer pageSize,
                                    String username
    ){
        log.info("{},{},{}",page,pageSize,username);
        Page pageInfo = new Page(page, pageSize);
        LambdaQueryWrapper<User> lambdaQueryWrapper=new LambdaQueryWrapper();
//        添加like
        lambdaQueryWrapper.like(StringUtils.isNotEmpty(username),User::getUsername,username);
//       添加order by
        lambdaQueryWrapper.orderByAsc(User::getId);

//        执行查询
        userService.page(pageInfo,lambdaQueryWrapper);
        return ResultData.success(pageInfo);
    }


}

