package com.hwx.viney.service;

import com.baomidou.mybatisplus.service.IService;
import com.hwx.viney.entity.Category;
import com.hwx.viney.entity.vo.CategoryVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author onee123
 * @since 2019-03-29
 */
public interface CategoryService extends IService<Category> {
    /**
     * category转vo
     * @param category
     * @return
     */
    CategoryVo toCategoryVo(Category category);
    List<CategoryVo> toCategoryVo(List<Category> categories);

    /**
     * 通过分类id返回商品id
     * @param categoryId
     * @return
     */
    int selectGoodsNumByCategoryId(int categoryId);
}
