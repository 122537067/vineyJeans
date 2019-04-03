package com.hwx.viney.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hwx.viney.entity.Category;
import com.hwx.viney.entity.SupCategory;
import com.hwx.viney.mapper.SupCategoryMapper;
import com.hwx.viney.service.CategoryService;
import com.hwx.viney.service.SupCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author onee123
 * @since 2019-03-29
 */
@Service
public class SupCategoryServiceImpl extends ServiceImpl<SupCategoryMapper, SupCategory> implements SupCategoryService {

    @Autowired
    CategoryService categoryService;

    @Override
    public int selectCountByCategoryId(int categoryId) {
        return categoryService.selectCount(new EntityWrapper<Category>().eq("sup_category_id",categoryId));
    }
}
