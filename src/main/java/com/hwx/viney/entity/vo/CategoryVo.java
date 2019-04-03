package com.hwx.viney.entity.vo;

import com.hwx.viney.entity.Category;
import lombok.Data;

/**
 * @program:viney
 * @author:one
 * @creatTime:2019/03/30
 **/

@Data
public class CategoryVo extends Category {
    String supName; //上级分类名
}
