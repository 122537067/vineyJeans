package com.hwx.viney.entity;

import com.baomidou.mybatisplus.activerecord.Model;
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
 * @since 2019-04-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("_about")
public class About extends Model<About> {

    private static final long serialVersionUID = 1L;

    /**
     * 关于id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 标题
     */
    private String title;
    /**
     * 小标题
     */
    private String subtitle;
    /**
     * 介绍
     */
    private String intro;
    /**
     * 内容
     */
    private String content;
    private Integer sort;
    /**
     * 状态(0:禁用，1:启动)
     */
    private String status;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
