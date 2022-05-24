package com.ddfantasy.todoapp.mapper;

import com.ddfantasy.todoapp.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chei
 * @since 2022-05-24
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
