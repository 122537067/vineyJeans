package com.hwx.viney.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author onee123
 * @since 2019-04-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("_manager_operation")
public class ManagerOperation extends Model<ManagerOperation> {

    private static final long serialVersionUID = 1L;

    /**
     * 操作id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * id地址
     */
    private String ip;
    /**
     * 操作
     */
    private String operation;
    /**
     * 信息
     */
    private String msg;
    /**
     * 时间
     */
    private Date time;
    /**
     * 管理员id
     */
    @TableField("manager_id")
    private String managerId;
    /**
     * 操作结果
     */
    @TableField("op_result")
    private String opResult;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
