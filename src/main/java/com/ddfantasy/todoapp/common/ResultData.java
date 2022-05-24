package com.ddfantasy.todoapp.common;

import java.io.Serializable;
import lombok.Data;
import java.util.HashMap;
import java.util.Map;


/**
 * @author: chei
 * @description: 通用返回结果，用于和前端匹配数据，不用泛型可以使用Object的类作为形参传入
 * @date: 2022/5/24
 * @params: <T>
 */
@Data
public class ResultData<T> implements Serializable {

    private Integer code; //编码：1成功，0和其它数字为失败

    private String msg; //错误信息

    private T data; //数据

    private Map map = new HashMap(); //动态数据

    public static <T> ResultData<T> success(T object) {
        ResultData<T> r = new ResultData<T>();
        r.data = object;
        r.code = 1;
        return r;
    }

    public static <T> ResultData<T> error(String msg) {
        ResultData r = new ResultData();
        r.msg = msg;
        r.code = 0;
        return r;
    }

    public ResultData<T> add(String key, Object value) {
        this.map.put(key, value);
        return this;
    }
}
