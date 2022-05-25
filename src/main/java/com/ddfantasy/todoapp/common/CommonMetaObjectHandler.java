package com.ddfantasy.todoapp.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

import java.time.LocalTime;

/*
* 元数据对象处理器
*  类似于针对某一特定查询的拦截器
* */
@Slf4j
@Component
public class CommonMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
//        log.info("----123-{}",metaObject.toString());
//        Long id=BaseContext.getCurrentId();

        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime", LocalDateTime.now());
//        metaObject.setValue("createUser", new Long(id));
//        metaObject.setValue("updateUser", new Long(id));
    }

    @Override
    public void updateFill(MetaObject metaObject) {
//        log.info("----logggggg-{}",metaObject.toString());

//        Long id=BaseContext.getCurrentId();
        metaObject.setValue("updateTime", LocalDateTime.now());
//        metaObject.setValue("updateUser", new Long(id));


    }
}
