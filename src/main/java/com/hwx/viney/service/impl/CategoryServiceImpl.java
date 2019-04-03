package com.hwx.viney.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hwx.viney.entity.Category;
import com.hwx.viney.entity.Goods;
import com.hwx.viney.entity.SupCategory;
import com.hwx.viney.entity.vo.CategoryVo;
import com.hwx.viney.mapper.CategoryMapper;
import com.hwx.viney.service.CategoryService;
import com.hwx.viney.service.GoodsService;
import com.hwx.viney.service.SupCategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author onee123
 * @since 2019-03-29
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    SupCategoryService supCategoryService;
    @Autowired
    GoodsService goodsService;

    @Override
    public CategoryVo toCategoryVo(Category category) {
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category,categoryVo);

        SupCategory supCategory = supCategoryService.selectById(category.getSupCategoryId());
        if(supCategory !=null){
            categoryVo.setSupName(supCategory.getName());
        }
        return categoryVo;
    }

    @Override
    public List<CategoryVo> toCategoryVo(List<Category> categories) {
        List<CategoryVo> categoryVos = new ArrayList<>();
        for(Category category:categories){
            categoryVos.add(toCategoryVo(category));
        }
        return categoryVos;
    }

    @Override
    public int selectGoodsNumByCategoryId(int categoryId) {
        return goodsService.selectCount(new EntityWrapper<Goods>().eq("category_id",categoryId));
    }
}
