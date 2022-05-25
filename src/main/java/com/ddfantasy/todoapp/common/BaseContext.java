package com.ddfantasy.todoapp.common;

/*
* 用于保存当前线程的用户id的工具类
* */
public class BaseContext {
    private static ThreadLocal<Integer> threadLocal=new ThreadLocal<>();

    public static void setCurrendId(Integer id){
        threadLocal.set(id);
    }

    public static Integer getCurrentId(){
        return threadLocal.get();
    }

}
