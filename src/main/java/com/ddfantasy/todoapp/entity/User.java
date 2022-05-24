package com.ddfantasy.todoapp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 继承Model应该是实现了序列化
 * </p>
 *
 * @author chei
 * @since 2022-05-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class User extends Model<User> {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String username;

    private String password;

    /*
    * 权限等级如下：
    * 1.普通用户
    * 2. 企业用户
    * 3. 管理员
    * */
    private Integer permissions;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
