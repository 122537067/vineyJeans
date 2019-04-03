package com.hwx.viney.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hwx.viney.entity.Banner;
import com.hwx.viney.entity.Category;
import com.hwx.viney.entity.Goods;
import com.hwx.viney.entity.SupCategory;
import com.hwx.viney.entity.vo.GoodsVo;
import com.hwx.viney.mapper.GoodsMapper;
import com.hwx.viney.service.BannerService;
import com.hwx.viney.service.CategoryService;
import com.hwx.viney.service.GoodsService;
import com.hwx.viney.service.SupCategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author onee123
 * @since 2019-03-29
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {
    @Autowired
    CategoryService categoryService;
    @Autowired
    SupCategoryService supCategoryService;
    @Autowired
    BannerService bannerService;
    @Autowired
    GoodsMapper goodsMapper;

    @Override
    public GoodsVo toGoodsVo(Goods goods) {
        GoodsVo goodsVo = new GoodsVo();
        BeanUtils.copyProperties(goods,goodsVo);
        Category category = categoryService.selectById(goods.getCategoryId());
        if(category != null){
            goodsVo.setCategoryName(category.getName());
            SupCategory supCategory = supCategoryService.selectById(category.getSupCategoryId());
            if(supCategory != null){
                goodsVo.setSupCategoryName(supCategory.getName());
                goodsVo.setSupCategoryId(supCategory.getId());
            }
        }
        return goodsVo;
    }

    @Override
    public List<GoodsVo> toGoodsVo(List<Goods> goods) {
        List<GoodsVo> goodsVos = new ArrayList<>();
        for(Goods goods1:goods){
            goodsVos.add(toGoodsVo(goods1));
        }
        return goodsVos;
    }

    @Override
    public int selectBannerCountByGoodsId(String goodsId) {
        return bannerService.selectCount(new EntityWrapper<Banner>().eq("goods_id",goodsId));
    }

    @Override
    public List<GoodsVo> showGoodsByParams(Map<String, Object> map) {
        return toGoodsVo(goodsMapper.showGoodsByParams(map));
    }

    @Override
    public int showGoodsCountByParams(Map<String, Object> map) {
        return goodsMapper.showGoodsCountByParams(map);
    }
}
