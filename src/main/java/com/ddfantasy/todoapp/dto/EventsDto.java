package com.ddfantasy.todoapp.dto;

import com.ddfantasy.todoapp.entity.Events;
import com.ddfantasy.todoapp.entity.NormalTodo;
import lombok.Data;

import java.util.List;

/*
* dto是对返回数据的进一步封装
* */
@Data
public class EventsDto extends Events {

    //改event对应的todo
    private List<NormalTodo> normalTodoList;
}
