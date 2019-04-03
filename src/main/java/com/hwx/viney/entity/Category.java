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
@TableName("_category")
public class Category extends Model<Category> {

    private static final long serialVersionUID = 1L;

    /**
     * 分类id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 父级分类id
     */
    @TableField("sup_category_id")
    private Integer supCategoryId;
    /**
     * 分类名
     */
    private String name;
    /**
     * (0:禁用,1:启用)
     */
    private String status;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
