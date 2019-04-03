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
import java.math.BigDecimal;
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
@TableName("_goods")
public class Goods extends Model<Goods> {

    private static final long serialVersionUID = 1L;

    /**
     * 服装编号
     */
    @TableId(type = IdType.INPUT)
    private String id;
    /**
     * 分类id
     */
    @TableField("category_id")
    private Integer categoryId;
    /**
     * 服装名
     */
    private String name;
    /**
     * 简介
     */
    private String intro;
    /**
     * 原价
     */
    @TableField("original_price")
    private BigDecimal originalPrice;
    /**
     * 折后价
     */
    @TableField("discount_price")
    private BigDecimal discountPrice;
    /**
     * 折扣
     */
    private Integer discount;
    /**
     * 封面图
     */
    private String cover;
    /**
     * 商品图(,隔开)
     */
    private String picture;
    /**
     * 商品描述
     */
    private String description;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;
    /**
     * (0:禁用,1:启用,2:下架)
     */
    private String status;
    /**
     * 权重
     */
    private Integer weight;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
