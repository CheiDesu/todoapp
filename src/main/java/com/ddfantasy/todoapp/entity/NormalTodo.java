package com.ddfantasy.todoapp.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
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
public class NormalTodo extends Model<NormalTodo> {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String title;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /*
    * 等级如下
    * 4. 重要紧急，
    * 3. 重要不紧急，
    * 2. 非重要紧急，
    * 1. 非重要不紧急
    * */
    private Integer important;

    private LocalDateTime deadline;

    private Boolean finished;

    private Integer userId;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
