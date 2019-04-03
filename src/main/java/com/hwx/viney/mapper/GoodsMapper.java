package com.hwx.viney.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hwx.viney.entity.Goods;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author onee123
 * @since 2019-03-29
 */
@Component
public interface GoodsMapper extends BaseMapper<Goods> {
    /**
     * 参数查询
     * @param map
     * @return
     */
    List<Goods> showGoodsByParams(Map<String,Object> map);
    int showGoodsCountByParams(Map<String,Object> map);
}
