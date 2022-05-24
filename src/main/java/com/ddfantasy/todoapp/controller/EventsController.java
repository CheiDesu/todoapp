package com.ddfantasy.todoapp.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ddfantasy.todoapp.common.ResultData;
import com.ddfantasy.todoapp.entity.Events;
import com.ddfantasy.todoapp.service.EventsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import sun.security.x509.RDN;

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

    @GetMapping("/getPgae")
    public ResultData getPage(){
        return ResultData.success("获取成功");
    }

    @GetMapping("/{id}")
    public ResultData getById(@PathVariable Integer id){
        Events events = eventsService.getById(id);
        return ResultData.success(events);
    }

}

