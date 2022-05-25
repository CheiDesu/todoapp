package com.ddfantasy.todoapp.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ddfantasy.todoapp.common.ResultData;
import com.ddfantasy.todoapp.dto.EventsDto;
import com.ddfantasy.todoapp.entity.Events;
import com.ddfantasy.todoapp.entity.NormalTodo;
import com.ddfantasy.todoapp.service.EventsService;
import com.ddfantasy.todoapp.service.NormalTodoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import sun.security.x509.RDN;

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

    @GetMapping("/{id}")
    public ResultData getById(@PathVariable Integer id){
        Events events = eventsService.getById(id);
        return ResultData.success(events);
    }

}

