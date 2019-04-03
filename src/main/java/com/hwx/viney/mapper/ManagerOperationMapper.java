package com.hwx.viney.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hwx.viney.entity.ManagerOperation;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author onee123
 * @since 2019-04-03
 */
@Component
public interface ManagerOperationMapper extends BaseMapper<ManagerOperation> {
    /**
     * 模糊查询
     * @param map
     * @return
     */
    List<ManagerOperation> showManagerOperationByParams(Map<String,Object> map);
    int showManagerOperationCountByParams(Map<String,Object> map);
}
