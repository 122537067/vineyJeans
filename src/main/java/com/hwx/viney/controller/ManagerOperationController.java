package com.hwx.viney.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hwx.viney.entity.ManagerOperation;
import com.hwx.viney.entity.Result;
import com.hwx.viney.entity.ResultEnum;
import com.hwx.viney.oneUtils.ManagerOperationUtil;
import com.hwx.viney.oneUtils.ResultUtil;
import com.hwx.viney.service.ManagerOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author onee123
 * @since 2019-04-03
 */
@RestController
@RequestMapping("/managerOperation")
public class ManagerOperationController {
    @Autowired
    ManagerOperationService managerOperationService;

    @Autowired
    ManagerOperationUtil managerOperationUtil;

    private int role = 10;

    @GetMapping("showManagerOperation")
    public Result showManagerOperation(int page, int limit, HttpServletRequest httpServletRequest){
        if (managerOperationUtil.mangerOperationNote(httpServletRequest, role, "查询管理员操作信息", "页码:" + page + " 数量:" + limit).getCode() == 0) {
            List<ManagerOperation> managerOperations = managerOperationService.selectPage(new Page<>(page, limit), new EntityWrapper<ManagerOperation>().orderBy("time",false)).getRecords();
            int count = managerOperationService.selectCount(new EntityWrapper<>());
            return ResultUtil.success(managerOperations, 0, count, "管理员操作信息");
        }else {
            return ResultUtil.error(ResultEnum.ERROR_PERMISSION_DENIED.getCode(), ResultEnum.ERROR_PERMISSION_DENIED.getMsg());
        }
    }

    /**
     * 模糊查询
     * @param map
     * @param httpServletRequest
     * @return
     */
    @GetMapping("showManagerOperationByParams")
    public Result showManagerOperationByParams(@RequestParam Map<String,Object> map, HttpServletRequest httpServletRequest){
        if (managerOperationUtil.mangerOperationNote(httpServletRequest, role, "查询管理员操作信息", "模糊查询").getCode() == 0) {
            int curPage = Integer.parseInt(map.get("page").toString()) - 1;
            int curLimit = Integer.parseInt(map.get("limit").toString());
            map.put("page", curPage * curLimit);
            List<ManagerOperation> managerOperations = managerOperationService.showManagerOperationByParams(map);
            map.remove("page");
            map.remove("limit");
            int count = managerOperationService.showManagerOperationCountByParams(map);
            return ResultUtil.success(managerOperations, 0, count, "管理员操作信息");
        }else {
            return ResultUtil.error(ResultEnum.ERROR_PERMISSION_DENIED.getCode(), ResultEnum.ERROR_PERMISSION_DENIED.getMsg());
        }
    }
}

