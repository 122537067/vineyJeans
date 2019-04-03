package com.hwx.viney.entity.vo;

import com.hwx.viney.entity.Goods;
import lombok.Data;

/**
 * @program:viney
 * @author:one
 * @creatTime:2019/03/30
 **/

@Data
public class GoodsVo extends Goods{
    String categoryName;    //分类名
    String supCategoryName; //父级分类名
    Integer supCategoryId;   //父级分类id
}
