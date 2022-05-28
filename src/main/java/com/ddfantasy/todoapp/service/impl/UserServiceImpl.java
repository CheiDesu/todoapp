package com.ddfantasy.todoapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ddfantasy.todoapp.entity.User;
import com.ddfantasy.todoapp.entity.Workspace;
import com.ddfantasy.todoapp.entity.WorkspaceUser;
import com.ddfantasy.todoapp.mapper.UserMapper;
import com.ddfantasy.todoapp.service.UserService;
import com.ddfantasy.todoapp.service.WorkspaceService;
import com.ddfantasy.todoapp.service.WorkspaceUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chei
 * @since 2022-05-25
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    @Autowired
    private WorkspaceUserService workspaceUserService;

    @Autowired
    private WorkspaceService workspaceService;

    /*
    * 删除用户和对应工作区的项，以及关系表：
    * 涉及的表：user，workspace，workspace_user
    * */
    @Override
    @Transactional
    public boolean deleteWithWorkspaceUser(List<Integer> ids) {
        //先删除用户
        //DELETE FROM user WHERE id IN ( ? , ? , ? )
        boolean b = this.removeByIds(ids);

        //删除关系表
        //DELETE FROM workspace_user WHERE (user_id IN (?,?,?))
        LambdaQueryWrapper<WorkspaceUser> workspaceUserLambdaQueryWrapper = new LambdaQueryWrapper<>();
        workspaceUserLambdaQueryWrapper.in(WorkspaceUser::getUserId,ids);
        workspaceUserService.remove(workspaceUserLambdaQueryWrapper);

        //删除workspace的owner_id对应的workspace
        //DELETE FROM workspace WHERE (owner_id IN (?,?,?))
        LambdaQueryWrapper<Workspace> workspaceLambdaQueryWrapper = new LambdaQueryWrapper<>();
        workspaceLambdaQueryWrapper.in(Workspace::getOwnerId,ids);
        workspaceService.remove(workspaceLambdaQueryWrapper);

        return b;

    }
}
