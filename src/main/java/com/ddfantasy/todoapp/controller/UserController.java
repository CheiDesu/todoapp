package com.ddfantasy.todoapp.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ddfantasy.todoapp.common.ResultData;
import com.ddfantasy.todoapp.entity.User;
import com.ddfantasy.todoapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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

    /*
    * 添加用户
    * */
    @PostMapping
    public ResultData<String> save(@RequestBody User user){
        userService.save(user);
        return ResultData.success("新增用户成功");
    }


    /**
     * @author: chei
     * @description: 登录，request用于查找会话中缓存信息
     * @params: [request, user]
     */
    @PostMapping("/login")
    public ResultData<User> login(HttpServletRequest request, @RequestBody User user){

        //密码加密
        String password = user.getPassword();
//        password = DigestUtils.md5DigestAsHex(password.getBytes());

//        条件查询
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername,user.getUsername());
        User userInData = userService.getOne(wrapper);

        //判断查询的数据
        if(userInData==null){
            return ResultData.error("登录失败！");
        }

        //匹配密码
        if(!userInData.getPassword().equals(password)){
            return ResultData.error("登录失败！");
        }


        request.getSession().setAttribute("user",userInData.getId());

        return ResultData.success(userInData);
    }



    /*
     * 更新用户
     * */
    @PutMapping
    public ResultData<String> updateEmp(HttpServletRequest request, @RequestBody User user){
//        Long userId = (Long)request.getSession().getAttribute("user");
//        user.setUpdateUser(userId);
        boolean flag = userService.updateById(user);

        return flag?ResultData.success("更新成功"):ResultData.error("更新失败");

    }


    /*
    * 查询单个用户
    * */
    @GetMapping("{id}")
    public ResultData getById(@PathVariable Integer id){
        User byId = userService.getById(id);
        return ResultData.success(byId);
    }




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


    /**
     * @author: chei
     * @description: 登出
     * @params: request
     */
    @PostMapping("/logout")
    public ResultData<String> logout(HttpServletRequest request){
        request.getSession().removeAttribute("user");
        return ResultData.success("退出成功");
    }


    /*
    * 删除用户以及处理工作区上的对应用户（如果有）
    * */
    @DeleteMapping
    public ResultData deleteUser(@RequestBody List<Integer> ids){
        boolean flag=userService.deleteWithWorkspaceUser(ids);
        return flag?ResultData.success("删除成功"): ResultData.error("删除失败");
    }
}

