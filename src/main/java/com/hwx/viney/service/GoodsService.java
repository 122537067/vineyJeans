package com.hwx.viney.service;

import com.baomidou.mybatisplus.service.IService;
import com.hwx.viney.entity.Goods;
import com.hwx.viney.entity.vo.GoodsVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author onee123
 * @since 2019-03-29
 */
public interface GoodsService extends IService<Goods> {
    /**
     * 转vo
     * @param goods
     * @return
     */
    GoodsVo toGoodsVo(Goods goods);
    List<GoodsVo> toGoodsVo(List<Goods> goods);

    /**
     * 通过商品id返回banner个数
     * @param goodsId
     * @return
     */
    int selectBannerCountByGoodsId(String goodsId);

    /**
     * 参数查询
     * @param map
     * @return
     */
    List<GoodsVo> showGoodsByParams(Map<String,Object> map);
    int showGoodsCountByParams(Map<String,Object> map);
}
