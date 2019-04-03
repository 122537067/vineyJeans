package com.hwx.viney.service;

import com.baomidou.mybatisplus.service.IService;
import com.hwx.viney.entity.ManagerOperation;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author onee123
 * @since 2019-04-03
 */
public interface ManagerOperationService extends IService<ManagerOperation> {
    /**
     * 模糊查询
     * @param map
     * @return
     */
    List<ManagerOperation> showManagerOperationByParams(Map<String,Object> map);
    int showManagerOperationCountByParams(Map<String,Object> map);
}
