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
 * @since 2019-03-31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("_header_footer")
public class HeaderFooter extends Model<HeaderFooter> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 头部logo
     */
    @TableField("top_logo")
    private String topLogo;
    /**
     * 尾部logo
     */
    @TableField("footer_logo")
    private String footerLogo;
    /**
     * 版权
     */
    private String copyright;
    /**
     * 公司名
     */
    private String name;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
