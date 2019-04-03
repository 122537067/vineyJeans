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
@TableName("_comment")
public class Comment extends Model<Comment> {

    private static final long serialVersionUID = 1L;

    /**
     * 评论id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 内容
     */
    private String content;
    /**
     * 联系方式
     */
    private String contact;
    /**
     * 用户名
     */
    private String customer;
    /**
     * ip地址
     */
    private String ip;
    /**
     * (0:禁用,1:启用)
     */
    private String status;
    @TableField("create_time")
    private Date createTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
