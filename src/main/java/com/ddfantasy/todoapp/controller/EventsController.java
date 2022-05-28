package com.ddfantasy.todoapp.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ddfantasy.todoapp.common.ResultData;
import com.ddfantasy.todoapp.dto.EventsDto;
import com.ddfantasy.todoapp.entity.Events;
import com.ddfantasy.todoapp.entity.EventsTodo;
import com.ddfantasy.todoapp.entity.NormalTodo;
import com.ddfantasy.todoapp.service.EventsService;
import com.ddfantasy.todoapp.service.EventsTodoService;
import com.ddfantasy.todoapp.service.NormalTodoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
//import sun.security.x509.RDN;

import java.util.Arrays;
import java.util.LinkedList;
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
@Slf4j
@RequestMapping("/events")
public class EventsController {

    @Autowired
    private EventsService eventsService;

    @Autowired
    private NormalTodoService todoService;

    @Autowired
    private EventsTodoService eventsTodoService;

    /*
    * 按照创建时间排序
    * */
    @GetMapping("/list")
    public ResultData list(String title){
        LambdaQueryWrapper<Events> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotEmpty(title), Events::getTitle,title);
//        根据创建时间排序
        wrapper.orderByAsc(Events::getCreateTime);
        List<Events> list = eventsService.list(wrapper);
        List<EventsDto> eventsDtoList = new LinkedList();
        list.forEach(item->{
            EventsDto temp=new EventsDto();
            //将事件子类赋值过去
            BeanUtils.copyProperties(item,temp);

//            查询大事件对应的todo信息
//            NormalTodo todo = todoService.getById(item.getNormalTodoId());
//            temp.setNormalTodo(todo);
            //列表添加dto封装的对象
            eventsDtoList.add(temp);

        });
        return ResultData.success(eventsDtoList);

    }

    /*
    * 根据id获取单个事件和对应todo,返回一个包含事件和对应todo列表的dto对象
    * */
    @GetMapping("/{id}")
    public ResultData getById(@PathVariable Integer id){

//        todo:封装成service方法：getByIdWtihTodo(Integer id)
//        EventsDto eventsDto=eventsService.getByIdWtihTodo(id);

        Events events = eventsService.getById(id);
        EventsDto eventsDto = new EventsDto();
        if(events==null)return ResultData.error("获取失败");

        BeanUtils.copyProperties(events,eventsDto);
//        对dto里面的todolist赋值
//        要查询关系表：events_todo
        LambdaQueryWrapper<EventsTodo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EventsTodo::getEventsId,events.getId());
        List<EventsTodo> todolist = eventsTodoService.list(wrapper);

        //该event妹有todo
        if(todolist.size()==0)return ResultData.success(eventsDto);

        LinkedList<Integer> ids = new LinkedList<>();

//        获取todo的id列表
        todolist.forEach(item->{
            ids.add(item.getNormalTodoId());
        });

//        转化为normal_todo 的list
        List<NormalTodo> todoList = todoService.listByIds(ids);
        eventsDto.setNormalTodoList(todoList);

        return ResultData.success(eventsDto);
    }


    /*
    * 更改events里面的todo的状态
    * 流程：
    * 通过id获取eventsDto,然后修改
    * */
    @PutMapping
    public ResultData update(@RequestBody EventsDto eventsDto){

        eventsService.updateById(eventsDto);

        List<NormalTodo> normalTodoList = eventsDto.getNormalTodoList();
        todoService.updateBatchById(normalTodoList);

        return ResultData.success("修改成功");
    }


    /*
    * 添加event
    * 传入dto，以操作两个基本表和和关系表
    * */
    @PostMapping
    public ResultData addEvent(@RequestBody EventsDto eventsDto){

        //先保存event
        eventsService.save(eventsDto);
        List<NormalTodo> normalTodoList = eventsDto.getNormalTodoList();

        //若底下的todoList为空，则直接返回
        if(normalTodoList!=null) {
            todoService.saveWithEvents(eventsDto);
        }

        return ResultData.success("添加成功");
    }

    /*
    * 要点进去某个events才能编辑/addtodo
    * 可以添加，events里面的todo
    * */
    @PostMapping("/addTodo")
    public ResultData addTodo(@RequestBody EventsDto eventsDto){
        //封装成service，或者直接调用todoService的方法
        todoService.saveWithEvents(eventsDto);

        return ResultData.success("添加成功");
    }


    /*
     * 要点进去某个events才能编辑/addtodo
     * 可以删除，events里面的todo
     * */
    @DeleteMapping("/deleteTodo")
    public ResultData deleteTodo(@RequestBody List<Integer> ids){
        boolean b = todoService.removeByIdsWithEvents(ids);
        return b?ResultData.success("删除成功"):ResultData.error("删除失败");
    }

}

