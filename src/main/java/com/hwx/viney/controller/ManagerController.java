package com.hwx.viney.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hwx.viney.entity.Manager;
import com.hwx.viney.entity.Result;
import com.hwx.viney.entity.ResultEnum;
import com.hwx.viney.oneUtils.ManagerOperationUtil;
import com.hwx.viney.oneUtils.ResultUtil;
import com.hwx.viney.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author onee123
 * @since 2019-03-29
 */
@RestController
@RequestMapping("/manager")
public class ManagerController {
    @Autowired
    ManagerService managerService;
    @Autowired
    ManagerOperationUtil managerOperationUtil;

    private int role = 1;
    /**
     * 管理员登陆
     *
     * @param username
     * @param password
     * @param request
     * @return
     */
    @PostMapping("/manageLogin")
    public Result manageLogin(String username, String password, HttpServletRequest request) {
        if (username == null || password == null) {
            //账户或密码为空
            return ResultUtil.error(ResultEnum.DATA_IS_NULL.getCode(), ResultEnum.DATA_IS_NULL.getMsg());  //code -6
        }
        Manager manager = managerService.selectOne(new EntityWrapper<Manager>().eq("username", username).eq("password", password).eq("status", "1"));
        if (manager != null) {
            //用户存在
            HttpSession session = request.getSession(); //存入session
            session.setAttribute("manager", manager);

            manager.setLoginTime(new Date());    //更新登陆时间
            manager.updateById();

            return ResultUtil.success(manager);
        }
        if (manager == null) {
            //用户不存在
            return ResultUtil.error(ResultEnum.LOGIN_ERROR.getCode(), ResultEnum.LOGIN_ERROR.getMsg());   //code -7
        }
        return ResultUtil.error(ResultEnum.UNKNOWN_ERROR.getCode(), ResultEnum.UNKNOWN_ERROR.getMsg());
    }

    /**
     * 退出登陆 清除session
     *
     * @param request
     * @return
     */
    @GetMapping("/manageLogout")
    public void manageLogout(HttpServletRequest request, HttpServletResponse httpServletResponse) throws Exception {
        HttpSession session = request.getSession();
        session.removeAttribute("manager");
        httpServletResponse.sendRedirect("/login.html");
    }

    /**
     * 显示所有管理员
     *
     * @param page
     * @param limit
     */
    @GetMapping("/showAllManager")
    public Result showAllManager(int page, int limit, HttpServletRequest httpServletRequest) {
        if (managerOperationUtil.mangerOperationNote(httpServletRequest, role, "显示管理员信息", "页码:" + page + " 数量:" + limit).getCode() == 0) {
            List<Manager> managers = managerService.selectPage(new Page<>(page, limit), new EntityWrapper<>()).getRecords();
            int count = managerService.selectCount(new EntityWrapper<>());
            return ResultUtil.success(managers, 0, count, "管理员列表");
        }else {
            return ResultUtil.error(ResultEnum.ERROR_PERMISSION_DENIED.getCode(), ResultEnum.ERROR_PERMISSION_DENIED.getMsg());
        }
    }

    /**
     * 更新管理员信息
     *
     * @param manager
     * @return
     */
    @PostMapping("/updateManager")
    public Result updateManager(@RequestBody Manager manager,HttpServletRequest httpServletRequest) {
        if (managerOperationUtil.mangerOperationNote(httpServletRequest, role, "更新管理员", manager.toString()).getCode() == 0) {
            Manager managerBefore = managerService.selectOne(new EntityWrapper<Manager>().eq("username", manager.getUsername()));
            if (managerBefore != null) {
                if (!managerBefore.getId().equals(manager.getId())) {
                    return ResultUtil.error(ResultEnum.MANAGER_EXIS.getCode(), ResultEnum.MANAGER_EXIS.getMsg()); //-9 账号存在
                }
            }
            if (manager.updateById()) {
                return ResultUtil.success(manager);
            } else {
                return ResultUtil.error(ResultEnum.UPDATE_ERROR.getCode(), ResultEnum.UPDATE_ERROR.getMsg()); //-8 更改失败
            }
        }else {
            return ResultUtil.error(ResultEnum.ERROR_PERMISSION_DENIED.getCode(), ResultEnum.ERROR_PERMISSION_DENIED.getMsg());
        }
    }

    /**
     * 通过id返回管理员信息
     *
     * @param id
     * @return
     */
    @PostMapping("/getManagerById")
    public Result getManagerById(String id,HttpServletRequest httpServletRequest) {
        if (managerOperationUtil.mangerOperationNote(httpServletRequest, role, "通过id搜索管理员", id).getCode() == 0) {
            Manager manager = managerService.selectById(id);
            return ResultUtil.success(manager);
        }else {
            return ResultUtil.error(ResultEnum.ERROR_PERMISSION_DENIED.getCode(), ResultEnum.ERROR_PERMISSION_DENIED.getMsg());
        }
    }

    /**
     * 新增管理员
     *
     * @param manager
     * @return
     */
    @PostMapping("/insertManager")
    public Result insertManager(@RequestBody Manager manager,HttpServletRequest httpServletRequest) {
        if (managerOperationUtil.mangerOperationNote(httpServletRequest, role, "添加管理员", manager.toString()).getCode() == 0) {
            Manager managerBefore = managerService.selectOne(new EntityWrapper<Manager>().eq("username", manager.getUsername()));
            if (managerBefore != null) {
                return ResultUtil.error(ResultEnum.MANAGER_EXIS.getCode(), ResultEnum.MANAGER_EXIS.getMsg()); //-9 账号存在
            }
            manager.setCreateTime(new Date());
            manager.setLoginTime(new Date());
            if (managerService.insert(manager)) {
                return ResultUtil.success(manager);
            } else {
                return ResultUtil.error(ResultEnum.UNKNOWN_ERROR.getCode(), ResultEnum.UNKNOWN_ERROR.getMsg());
            }
        }else {
            return ResultUtil.error(ResultEnum.ERROR_PERMISSION_DENIED.getCode(), ResultEnum.ERROR_PERMISSION_DENIED.getMsg());
        }
    }

    /**
     * 删除管理员
     *
     * @param manager
     * @return
     */
    @PostMapping("/deleteManager")
    public Result deleteManager(@RequestBody Manager manager,HttpServletRequest httpServletRequest) {
        if (managerOperationUtil.mangerOperationNote(httpServletRequest, role, "删除管理员", manager.toString()).getCode() == 0) {
            manager.deleteById();
            return ResultUtil.success();
        }else {
            return ResultUtil.error(ResultEnum.ERROR_PERMISSION_DENIED.getCode(), ResultEnum.ERROR_PERMISSION_DENIED.getMsg());
        }
    }
}

