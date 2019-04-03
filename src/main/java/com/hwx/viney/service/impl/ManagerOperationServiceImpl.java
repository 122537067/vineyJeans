package com.hwx.viney.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hwx.viney.entity.ManagerOperation;
import com.hwx.viney.mapper.ManagerOperationMapper;
import com.hwx.viney.service.ManagerOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author onee123
 * @since 2019-04-03
 */
@Service
public class ManagerOperationServiceImpl extends ServiceImpl<ManagerOperationMapper, ManagerOperation> implements ManagerOperationService {
    @Autowired
    ManagerOperationMapper managerOperationMapper;

    @Override
    public List<ManagerOperation> showManagerOperationByParams(Map<String, Object> map) {
        return managerOperationMapper.showManagerOperationByParams(map);
    }

    @Override
    public int showManagerOperationCountByParams(Map<String, Object> map) {
        return managerOperationMapper.showManagerOperationCountByParams(map);
    }
}
