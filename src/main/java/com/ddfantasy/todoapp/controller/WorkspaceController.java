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
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

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

    /*
     * 获取工作区和对应用户
     * */
    @GetMapping("/list")
    public ResultData list(){
        return  workspaceService.listWithUser();

    }

    /*
    * 获取工作区和对应用户,返回一个dto
    * */
    @GetMapping("/{id}")
    public ResultData<WorkspaceDto> getOne(@PathVariable Integer id){
        WorkspaceDto workspaceDto = workspaceService.getOneWithUser(id);
        if(workspaceDto!=null)
            return ResultData.success(workspaceDto);
        else {
            return ResultData.error("获取失败!!");
        }
    }


    /*
    * 更新工作区以及对应的成员
    * */
    @PutMapping
    public ResultData update(@RequestBody WorkspaceDto workspaceDto){

        workspaceService.updateWithUser(workspaceDto);

        return ResultData.success("修改成功");
    }


    /*
    * 添加工作区
    * */
    @PostMapping
    public ResultData save(@RequestBody Workspace workspace){
        workspaceService.save(workspace);
        return ResultData.success("添加成功");
    }

    /*
     * 获取工作区和对应用户
     * ids是工作区的ids
     * */
    @DeleteMapping
    public ResultData delete(@RequestBody List<Integer> ids){
//        前端要提醒用户
        boolean flag=workspaceService.deleteWithUser(ids);
        return flag?ResultData.success("删除成功"):ResultData.error("删除失败");
    }
}

