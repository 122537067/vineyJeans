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
 * @since 2019-03-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("_manager")
public class Manager extends Model<Manager> {

    private static final long serialVersionUID = 1L;

    /**
     * 管理员id
     */
    @TableId(type = IdType.UUID)
    private String id;
    /**
     * 登陆名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * (0:禁用,1:启用)
     */
    private String status;
    /**
     * 权限id
     */
    @TableField("role_id")
    private String roleId;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 登陆时间
     */
    @TableField("login_time")
    private Date loginTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
