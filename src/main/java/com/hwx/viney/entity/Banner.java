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
@TableName("_banner")
public class Banner extends Model<Banner> {

    private static final long serialVersionUID = 1L;

    /**
     * 轮播图id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 标题
     */
    private String title;
    /**
     * 介绍
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
     * 状态(0:禁用，1:启动)
     */
    private String status;
    /**
     * 商品id
     */
    @TableField("goods_id")
    private String goodsId;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
