package com.ddfantasy.todoapp.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ddfantasy.todoapp.common.ResultData;
import com.ddfantasy.todoapp.dto.EventsDto;
import com.ddfantasy.todoapp.dto.WorkspaceDto;
import com.ddfantasy.todoapp.entity.User;
import com.ddfantasy.todoapp.entity.Workspace;
import com.ddfantasy.todoapp.entity.WorkspaceUser;
import com.ddfantasy.todoapp.service.UserService;
import com.ddfantasy.todoapp.service.WorkspaceService;
import com.ddfantasy.todoapp.service.WorkspaceUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author chei
 * @since 2022-05-24
 */
@RestController
@RequestMapping("/workspace")
@Slf4j
public class WorkspaceController {


    @Autowired
    private WorkspaceService workspaceService;


    @Autowired
    private WorkspaceUserService workspaceUserService;

    @Autowired
    private UserService userService;

//    不使用sql语句，太麻烦了
//    @GetMapping("/list")
    public ResultData list2(HttpServletRequest request){
        //先获取登录账号的工作区
        Integer userId = (Integer)request.getSession().getAttribute("user");
        if(userId==null){
            //也可以抛事务异常
            return ResultData.error("出现异常！");
        }

        LambdaQueryWrapper<Workspace> workspaceLambdaQueryWrapper = new LambdaQueryWrapper<>();

        //相当于where owner_id=user.id
        workspaceLambdaQueryWrapper.eq(Workspace::getOwnerId,userId);
        List<Workspace> workspaceList = workspaceService.list(workspaceLambdaQueryWrapper);

//        dto是最终要封装的返回对象
        List<WorkspaceDto> workspaceDtoList = new LinkedList();

        workspaceList.forEach(item->{
            //获取工作区对应的user列表
            LambdaQueryWrapper<WorkspaceUser> workspaceUserLambdaQueryWrapper = new LambdaQueryWrapper<>();
            //where workspaceList.workspace_id=WorkspaceUser.workspace_id
            workspaceUserLambdaQueryWrapper.eq(WorkspaceUser::getWorkspaceId,item.getId());
            List<WorkspaceUser> userList = workspaceUserService.list(workspaceUserLambdaQueryWrapper);

            //现在要查询获取User实体类
            List<Integer> ids=new LinkedList();
            userList.forEach(user->{
                ids.add(user.getUserId());
            });
            //打个断点，不知道能不能复制ids

            //通过ids获取用户列表
            List<User> users = userService.listByIds(ids);

            WorkspaceDto workspaceDto = new WorkspaceDto();
            //将父类的值给子类dto
            BeanUtils.copyProperties(item,workspaceDto);
            workspaceDto.setUserList(users);

            workspaceDtoList.add(workspaceDto);

        });


        return ResultData.success(workspaceDtoList);
    }


    @GetMapping("/list")
    public ResultData list(HttpServletRequest request){
        //先获取登录账号的工作区
        Integer userId = (Integer)request.getSession().getAttribute("user");
        if(userId==null){
            //也可以抛事务异常
            return ResultData.error("出现异常！");
        }

        log.info("----{}",userId);





        return ResultData.success("成功!");
    }

}

