package com.ddfantasy.todoapp.service;

import com.ddfantasy.todoapp.common.ResultData;
import com.ddfantasy.todoapp.entity.Workspace;
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
public interface WorkspaceService extends IService<Workspace> {

    /*
    * 获取工作区和对应用户
    * */
    ResultData listWithUser();

    /*
    * 获取工作区和对应用户
    * */
    boolean deleteWithUser();
}
