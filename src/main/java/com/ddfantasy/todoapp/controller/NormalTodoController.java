package com.ddfantasy.todoapp.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ddfantasy.todoapp.common.BaseContext;
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
    * 列出所有todo---根据时间排序（默认）
    * */
    @GetMapping("/list")
    public ResultData getList(String title){
        LambdaQueryWrapper<NormalTodo> wrapper = new LambdaQueryWrapper<>();

//        获取当前登录用户id
        Integer currentId = BaseContext.getCurrentId();
        wrapper.eq(NormalTodo::getUserId,currentId);

//        可以进行模糊查询
        wrapper.like(StringUtils.isNotEmpty(title), NormalTodo::getTitle,title);
//        根据创建时间排序
        wrapper.orderByAsc(NormalTodo::getCreateTime);
        List<NormalTodo> list = todoService.list(wrapper);
        return ResultData.success(list);
    }

    /*
     * 列出所有todo---根据重要程度排序
     * */
    @GetMapping("/list/important")
    public ResultData getListOrderByImp(String title){
        LambdaQueryWrapper<NormalTodo> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotEmpty(title), NormalTodo::getTitle,title);
//        根据创建时间排序
        wrapper.orderByDesc(NormalTodo::getImportant);
        List<NormalTodo> list = todoService.list(wrapper);
        return ResultData.success(list);
    }


    /*
    * 添加
    * */
    @PostMapping
    public ResultData save(@RequestBody NormalTodo todo){
        todo.setUserId(BaseContext.getCurrentId());
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
    * 通过id修改todo，前端可以用来标记事件完成
    * */
    @PutMapping
    public ResultData update(@RequestBody NormalTodo todo){
        todoService.updateById(todo);
        return ResultData.success("修改成功");
    }

    /*
    * （批量）删除todo
    *   处理关系表,关系表里的todo也要删除？
    * */
    @DeleteMapping("/delete")
    public ResultData delete(@RequestBody List<Integer> ids){

        boolean b = todoService.removeByIdsWithEvents(ids);
        return b?ResultData.success("删除成功"):ResultData.error("删除失败");

    }

}

