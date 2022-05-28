package com.ddfantasy.todoapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ddfantasy.todoapp.common.BaseContext;
import com.ddfantasy.todoapp.common.ResultData;
import com.ddfantasy.todoapp.dto.WorkspaceDto;
import com.ddfantasy.todoapp.entity.User;
import com.ddfantasy.todoapp.entity.Workspace;
import com.ddfantasy.todoapp.entity.WorkspaceUser;
import com.ddfantasy.todoapp.mapper.WorkspaceMapper;
import com.ddfantasy.todoapp.service.UserService;
import com.ddfantasy.todoapp.service.WorkspaceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ddfantasy.todoapp.service.WorkspaceUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
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
@Slf4j
public class WorkspaceServiceImpl extends ServiceImpl<WorkspaceMapper, Workspace> implements WorkspaceService {

    @Autowired
    private WorkspaceUserService workspaceUserService;

    @Autowired
    private UserService userService;

    /*
     * 获取工作区和对应用户
     * */
    @Override
    public ResultData listWithUser() {

        //先获取登录账号的工作区
//        Integer userId = (Integer)request.getSession().getAttribute("user");

        Integer userId= BaseContext.getCurrentId();
        if(userId==null){
            //也可以抛事务异常
            return ResultData.error("出现异常！");
        }

        log.info("----{}",userId);

        LambdaQueryWrapper<Workspace> workspaceLambdaQueryWrapper = new LambdaQueryWrapper<>();

        //相当于where owner_id=user.id
        workspaceLambdaQueryWrapper.eq(Workspace::getOwnerId,userId);
        List<Workspace> workspaceList = this.list(workspaceLambdaQueryWrapper);

//        dto是最终要封装的返回对象
        List<WorkspaceDto> workspaceDtoList = new LinkedList();

        workspaceList.forEach(item->{
            //获取工作区对应的user列表
            LambdaQueryWrapper<WorkspaceUser> workspaceUserLambdaQueryWrapper = new LambdaQueryWrapper<>();
            //sql:where workspaceList.workspace_id=WorkspaceUser.workspace_id
            workspaceUserLambdaQueryWrapper.eq(WorkspaceUser::getWorkspaceId,item.getId());
            List<WorkspaceUser> userList = workspaceUserService.list(workspaceUserLambdaQueryWrapper);

            //现在要查询获取User实体类
            List<Integer> ids=new LinkedList();
            userList.forEach(user->{
                ids.add(user.getUserId());
            });
            //打个断点，不知道能不能复制ids

            //通过ids获取用户列表
            List<User> users=new LinkedList<>();
            if(!ids.isEmpty()){
                users = userService.listByIds(ids);
            }

            WorkspaceDto workspaceDto = new WorkspaceDto();
            //将父类的值给子类dto
            BeanUtils.copyProperties(item,workspaceDto);
            workspaceDto.setUserList(users);

            workspaceDtoList.add(workspaceDto);

        });

        log.info("{}",workspaceDtoList);

        return ResultData.success(workspaceDtoList);

    }

    /*
     * 获取工作区和对应用户
     * ids是工作区的ids
     * */
    @Override
    @Transactional
    public boolean deleteWithUser(List<Integer> ids) {

//        删除工作区,先要删除该工作区关系表上的行
        LambdaQueryWrapper<WorkspaceUser> workspaceUserLambdaQueryWrapper = new LambdaQueryWrapper<>();
        workspaceUserLambdaQueryWrapper.in(WorkspaceUser::getWorkspaceId,ids);
        workspaceUserService.remove(workspaceUserLambdaQueryWrapper);

//        删除工作区
        boolean isRemoved = this.removeByIds(ids);

        return isRemoved;
    }

    /*
     * 更新工作区以及对应的成员
     * 开启事务注解
     * */
    @Override
    @Transactional
    public boolean updateWithUser(WorkspaceDto workspaceDto) {
        //更新workspace表基本信息
        this.updateById(workspaceDto);
        Integer workspaceId = workspaceDto.getId();

        //清理当前工作区对应用户,处理关系表
        LambdaQueryWrapper<WorkspaceUser> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(WorkspaceUser::getWorkspaceId,workspaceId);

        workspaceUserService.remove(queryWrapper);

        //添加当前提交过来的工作区对应用户
        List<User> users = workspaceDto.getUserList();
        List<WorkspaceUser> workspaceUsers=new LinkedList<>();

        users.forEach((item) -> {
            WorkspaceUser workspaceUser = new WorkspaceUser();
//            给关系表赋上值
            workspaceUser.setUserId(item.getId());
            workspaceUser.setWorkspaceId(workspaceId);
            workspaceUsers.add(workspaceUser);
        });
//      处理关系表
        workspaceUserService.saveBatch(workspaceUsers);

        return false;
    }


    /*
     * 根据id获取工作区以及对应的成员
     * */
    @Override
    public WorkspaceDto getOneWithUser(Integer id) {
        Workspace workspaceById = this.getById(id);

        if(workspaceById==null){
            return null;
        }

        WorkspaceDto workspaceDto = new WorkspaceDto();
        BeanUtils.copyProperties(workspaceById,workspaceDto);

        //从关系表查询用户
        LambdaQueryWrapper<WorkspaceUser> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(WorkspaceUser::getWorkspaceId,id);
        List<WorkspaceUser> list = workspaceUserService.list(userLambdaQueryWrapper);

//        获取用户id
        LinkedList<Integer> ids = new LinkedList<>();

        list.forEach(item->{
            ids.add(item.getUserId());
        });

        List<User> users = userService.listByIds(ids);
        workspaceDto.setUserList(users);
        log.info("{}",workspaceDto);

        return workspaceDto;
    }
}
