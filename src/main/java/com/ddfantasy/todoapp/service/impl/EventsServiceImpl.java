package com.ddfantasy.todoapp.service.impl;

import com.ddfantasy.todoapp.entity.Events;
import com.ddfantasy.todoapp.mapper.EventsMapper;
import com.ddfantasy.todoapp.service.EventsService;
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
public class EventsServiceImpl extends ServiceImpl<EventsMapper, Events> implements EventsService {

}
