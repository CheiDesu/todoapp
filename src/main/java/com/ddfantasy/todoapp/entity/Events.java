package com.ddfantasy.todoapp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author chei
 * @since 2022-05-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Events extends Model<Events> {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String title;

    private Integer normalTodoId;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
