package com.ddfantasy.todoapp;


import com.ddfantasy.todoapp.entity.NormalTodo;
import com.ddfantasy.todoapp.service.NormalTodoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.webservices.client.AutoConfigureMockWebServiceServer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.ContentResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.StatusResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//在java中测试web
@AutoConfigureMockMvc
//事务的注解
@Transactional
//事务默认回滚
@Rollback(true)
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class WebTest {

    @Test
    public void testWeb(@Autowired MockMvc mockMvc) throws Exception {
        RequestBuilder builder= MockMvcRequestBuilders.get("/todo");
        ResultActions actions = mockMvc.perform(builder);

        StatusResultMatchers status = MockMvcResultMatchers.status();
//        判断请求状态码是否为200
        ResultMatcher ok = status.isOk();
//      有点像是封装了断言的匹配
        ResultActions resultActions = actions.andExpect(ok);
        log.info("状态码为200，controller没有问题");

    }

    @Autowired
    private NormalTodoService todoService;

    @Test
    public void testServiceTemplate(){
        //以测试todo的查询为例
        List<NormalTodo> list = todoService.list();
        list.forEach(item->{
            log.info("{}",item.toString());
        });

        //测试save
        NormalTodo normalTodo=new NormalTodo();
        normalTodo.setImportant(1);
        normalTodo.setTitle("测试测试");
        todoService.save(normalTodo);

        log.info("{}",normalTodo.toString());

        normalTodo.setTitle("测试测试修改");
        //测试update
        boolean b = todoService.updateById(normalTodo);

        log.info("测试update:{}",b);
        b=todoService.removeById(normalTodo.getId());
        log.info("测试delete:{}",b);


    }



    @Test
    public void testBody(@Autowired MockMvc mockMvc) throws Exception {
        RequestBuilder builder= MockMvcRequestBuilders.get("/todo");
        ResultActions actions = mockMvc.perform(builder);

        ContentResultMatchers content = MockMvcResultMatchers.content();
//        ResultMatcher ok = content.string("{a:todos}");

//        ResultActions resultActions = actions.andExpect(ok);

    }


    @Test
    public void testJson(@Autowired MockMvc mockMvc) throws Exception {
        RequestBuilder builder= MockMvcRequestBuilders.get("/todos");
        ResultActions actions = mockMvc.perform(builder);

        ContentResultMatchers content = MockMvcResultMatchers.content();
        ResultMatcher ok = content.json("todos");
//      有点像是封装了断言的匹配
        ResultActions resultActions = actions.andExpect(ok);

    }


}
