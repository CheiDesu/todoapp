package com.ddfantasy.todoapp.dto;

import com.ddfantasy.todoapp.entity.User;
import com.ddfantasy.todoapp.entity.Workspace;
import lombok.Data;

import java.util.List;

@Data
public class WorkspaceDto extends Workspace {

    //工作区下的user
    private List<User> userList;

}
