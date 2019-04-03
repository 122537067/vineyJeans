package com.hwx.viney.service;

import com.baomidou.mybatisplus.service.IService;
import com.hwx.viney.entity.SupCategory;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author onee123
 * @since 2019-03-29
 */
public interface SupCategoryService extends IService<SupCategory> {
    /**
     * 找到二级分类数量
     * @param categoryId
     * @return
     */
    int selectCountByCategoryId(int categoryId);
}
