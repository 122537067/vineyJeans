package com.hwx.viney.oneUtils;

import com.hwx.viney.entity.Manager;
import com.hwx.viney.entity.ManagerOperation;
import com.hwx.viney.entity.Result;
import com.hwx.viney.entity.ResultEnum;
import com.hwx.viney.service.ManagerOperationService;
import com.hwx.viney.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @program:viney
 * @author:one
 * @creatTime:2019/04/03
 **/

@Component
public class ManagerOperationUtil {
    @Autowired
    ManagerService managerService;
    @Autowired
    ManagerOperationService managerOperationService;

    /**
     * 管理权限控制-记录
     * @param httpServletRequest
     * @param roleId    功能权限id
     * @param operation 操作
     * @param msg       操作结果信息
     * @return
     */
    public Result mangerOperationNote(HttpServletRequest httpServletRequest, int roleId, String operation, String msg) {
        //获取管理员session
        Manager manager = (Manager) httpServletRequest.getSession().getAttribute("manager");
        //获取操作IP
        String opIp = IpUtil.getIpAddr(httpServletRequest);

        //未登陆操作
        if (manager == null) {
            //增删改信息 -> 插入数据库
            ManagerOperation managerOperation = new ManagerOperation();
            managerOperation.setManagerId("null");
            managerOperation.setIp(opIp);
            managerOperation.setOperation(operation);
            managerOperation.setTime(new Date());
            managerOperation.setMsg(msg);
            managerOperation.setOpResult("noting login-fail");
            managerOperation.insert();
            return ResultUtil.error(ResultEnum.ERROR_PERMISSION_DENIED.getCode(), ResultEnum.ERROR_PERMISSION_DENIED.getMsg()); //-10权限不足
        }

        //越权操作
        manager = managerService.selectById(manager.getId());
        if((!manager.getRoleId().contains(","+roleId+",")) || (manager.getStatus().equals("0"))){
            //权限不足
            //增删改信息 -> 插入数据库
            ManagerOperation managerOperation = new ManagerOperation();
            managerOperation.setManagerId(manager.getId());
            managerOperation.setIp(opIp);
            managerOperation.setOperation(operation);
            managerOperation.setTime(new Date());
            managerOperation.setMsg(msg);
            managerOperation.setOpResult("role limit-fail");
            managerOperation.insert();
            return ResultUtil.error(ResultEnum.ERROR_PERMISSION_DENIED.getCode(), ResultEnum.ERROR_PERMISSION_DENIED.getMsg()); //-10权限不足
        }

        //权限足够->正常操作
        ManagerOperation managerOperation = new ManagerOperation();
        managerOperation.setManagerId(manager.getId());
        managerOperation.setIp(opIp);
        managerOperation.setOperation(operation);
        managerOperation.setTime(new Date());
        managerOperation.setMsg(msg);
        managerOperation.setOpResult("success");
        managerOperation.insert();
        return ResultUtil.success();
    }
}
