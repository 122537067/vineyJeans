package com.hwx.viney.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hwx.viney.entity.Result;
import com.hwx.viney.entity.ResultEnum;
import com.hwx.viney.entity.UserIp;
import com.hwx.viney.oneUtils.IpUtil;
import com.hwx.viney.oneUtils.ManagerOperationUtil;
import com.hwx.viney.oneUtils.ResultUtil;
import com.hwx.viney.service.UserIpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author onee123
 * @since 2019-03-29
 */
@RestController
@RequestMapping("/userIp")
public class UserIpController {
    @Autowired
    UserIpService userIpService;
    @Autowired
    ManagerOperationUtil managerOperationUtil;

    private int role = 11;

    /**
     * 用户上网记录
     * @param request
     * @return
     */
    @PostMapping("/getIp")
    public Result getIp(HttpServletRequest request) {
        String ip = IpUtil.getIpAddr(request);
        if(!ip.equals("0:0:0:0:0:0:0:1")){
                UserIp userIp = new UserIp();
                userIp.setIp(ip);
                userIp.setCreateTime(new Date());
                userIp.insert();
            }
        return ResultUtil.success(ip);
    }

    /**
     * 获取访客记录
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("showAllUserIp")
    public Result showAllUserIp(int page, int limit, HttpServletRequest httpServletRequest){
        if (managerOperationUtil.mangerOperationNote(httpServletRequest, role, "查询访客记录", "页码:" + page + " 数量:" + limit).getCode() == 0) {
            List<UserIp> userIpList = userIpService.selectPage(new Page<>(page, limit), new EntityWrapper<UserIp>().orderBy("create_time", false)).getRecords();
            int count = userIpService.selectCount(new EntityWrapper<>());
            return ResultUtil.success(userIpList, 0, count, "访客记录");
        }else {
            return ResultUtil.error(ResultEnum.ERROR_PERMISSION_DENIED.getCode(), ResultEnum.ERROR_PERMISSION_DENIED.getMsg());
        }
    }

    /**
     * 批量删除访客记录
     * @param ids
     * @return
     */
    @PostMapping("deleteUserIp")
    public Result deleteUserIp(@RequestParam String[] ids, HttpServletRequest httpServletRequest){
        String idStr = "";
        for(String id:ids){
            idStr += id + ",";
        }
        if (managerOperationUtil.mangerOperationNote(httpServletRequest, role, "删除访客记录", idStr).getCode() == 0) {
            userIpService.delete(new EntityWrapper<UserIp>().in("id", ids));
            return ResultUtil.success(ids);
        }else {
            return ResultUtil.error(ResultEnum.ERROR_PERMISSION_DENIED.getCode(), ResultEnum.ERROR_PERMISSION_DENIED.getMsg());
        }
    }
}

