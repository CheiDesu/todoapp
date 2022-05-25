package com.ddfantasy.todoapp.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ddfantasy.todoapp.common.ResultData;
import com.ddfantasy.todoapp.entity.NormalTodo;
import com.ddfantasy.todoapp.entity.User;
import com.ddfantasy.todoapp.service.NormalTodoService;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
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
@RequestMapping("/todo")
public class NormalTodoController {

    @Autowired
    private NormalTodoService todoService;

    /*
    *
    * */
    @GetMapping("/list")
    public ResultData getList(String title){
        LambdaQueryWrapper<NormalTodo> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotEmpty(title), NormalTodo::getTitle,title);
//        根据创建时间排序
        wrapper.orderByAsc(NormalTodo::getCreateTime);
        List<NormalTodo> list = todoService.list(wrapper);
        return ResultData.success(list);
    }

    /*
    * 添加
    * */
    @PostMapping
    public ResultData save(@RequestBody NormalTodo todo){
        todo.setCreateTime(LocalDateTime.now());
        todoService.save(todo);
        return ResultData.success("添加成功");
    }

    /*
    * 通过id获取
    * */
    @GetMapping("/{id}")
    public ResultData getById(@PathVariable Integer id){
        NormalTodo todoById = todoService.getById(id);
        return ResultData.success(todoById);
    }


    /*
    * 通过id修改todo
    * */
    @PutMapping
    public ResultData update(@RequestBody NormalTodo todo){
        todoService.updateById(todo);
        return ResultData.success("添加成功");
    }

}

