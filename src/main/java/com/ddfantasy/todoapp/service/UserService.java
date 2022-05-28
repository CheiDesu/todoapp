package com.ddfantasy.todoapp.service;

import com.ddfantasy.todoapp.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chei
 * @since 2022-05-25
 */
public interface UserService extends IService<User> {

    /*
    * 删除用户和对应工作区的项
    * */
    boolean deleteWithWorkspaceUser(List<Integer> ids);
}
