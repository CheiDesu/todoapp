package com.ddfantasy.todoapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ddfantasy.todoapp.dto.EventsDto;
import com.ddfantasy.todoapp.entity.EventsTodo;
import com.ddfantasy.todoapp.entity.NormalTodo;
import com.ddfantasy.todoapp.mapper.NormalTodoMapper;
import com.ddfantasy.todoapp.service.EventsTodoService;
import com.ddfantasy.todoapp.service.NormalTodoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chei
 * @since 2022-05-24
 */
@Service
@Slf4j
public class NormalTodoServiceImpl extends ServiceImpl<NormalTodoMapper, NormalTodo> implements NormalTodoService {

    @Autowired
    private EventsTodoService eventsTodoService;

    /*
     * 通过ids（批量）删除todo，以及处理对应的关系表
     * */
    @Override
    @Transactional
    public boolean removeByIdsWithEvents(List<Integer> ids) {
        boolean b = this.removeByIds(ids);

        LambdaQueryWrapper<EventsTodo> eventsTodoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        eventsTodoLambdaQueryWrapper.in(EventsTodo::getNormalTodoId,ids);
        boolean remove = eventsTodoService.remove(eventsTodoLambdaQueryWrapper);

        return remove&&b;
    }


    /*
     * 将todo添加到事件，处理关系表的方法;
     * */
    @Override
    @Transactional
    public void saveWithEvents(EventsDto eventsDto) {
        List<NormalTodo> normalTodoList = eventsDto.getNormalTodoList();
        this.saveBatch(normalTodoList);

        List<EventsTodo> eventsTodoList = new LinkedList<>();

        normalTodoList.forEach(item->{
            EventsTodo eventsTodo=new EventsTodo();
            eventsTodo.setEventsId(eventsDto.getId());
            eventsTodo.setNormalTodoId(item.getId());
            eventsTodoList.add(eventsTodo);
        });

        //处理关系表的添加
        eventsTodoService.saveBatch(eventsTodoList);
    }

    /*
     * 根据event_id获取todo列表
     * */
    @Override
    public List<NormalTodo> getTodoByEventId(Integer id) {

        LambdaQueryWrapper<EventsTodo> eventsTodoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        eventsTodoLambdaQueryWrapper.eq(EventsTodo::getEventsId,id);
        List<EventsTodo> eventsTodoList = eventsTodoService.list(eventsTodoLambdaQueryWrapper);

        log.info("{}",eventsTodoList);

        List<Integer> todoids=new LinkedList<>();
        List<NormalTodo> todos=null;

        //没有todo，直接返回
        if (eventsTodoList.size()==0){
            return todos;
        }

        //从关系表获取todo的ids
        eventsTodoList.forEach(item->{
            todoids.add(item.getNormalTodoId());
        });

        LambdaQueryWrapper<NormalTodo> normalTodoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        normalTodoLambdaQueryWrapper.in(!todoids.isEmpty(),NormalTodo::getId,todoids);
        todos = this.list(normalTodoLambdaQueryWrapper);


        return todos;
    }
}
