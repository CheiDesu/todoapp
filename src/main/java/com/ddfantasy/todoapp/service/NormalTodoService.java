package com.ddfantasy.todoapp.service;

import com.ddfantasy.todoapp.dto.EventsDto;
import com.ddfantasy.todoapp.entity.NormalTodo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chei
 * @since 2022-05-24
 */
public interface NormalTodoService extends IService<NormalTodo> {

    /*
    * 通过ids（批量）删除todo，以及处理对应的关系表
    * */
    boolean removeByIdsWithEvents(List<Integer> ids);


    /*
     * 将todo添加到事件，处理关系表的方法;
     * */
    void saveWithEvents(EventsDto eventsDto);

}
