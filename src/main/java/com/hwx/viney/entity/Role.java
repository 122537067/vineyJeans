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
@TableName("_role")
public class Role extends Model<Role> {

    private static final long serialVersionUID = 1L;

    /**
     * 权限id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 权限名
     */
    @TableField("name")
    private String name;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
