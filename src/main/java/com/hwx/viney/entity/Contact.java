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
 * @since 2019-03-31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("_contact")
public class Contact extends Model<Contact> {

    private static final long serialVersionUID = 1L;

    /**
     * 联系id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String intro;
    /**
     * 图片
     */
    private String picture;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * (0:禁用，1:启用)
     */
    private String status;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
