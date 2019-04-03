package com.hwx.viney.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.hwx.viney.entity.Result;
import com.hwx.viney.entity.Role;
import com.hwx.viney.oneUtils.ResultUtil;
import com.hwx.viney.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/role")
public class RoleController {
    @Autowired
    RoleService roleService;

    /**
     * 显示权限
     * @return
     */
    @GetMapping("showAllRole")
    public Result showAllRole(){
        List<Role> roleList = roleService.selectList(new EntityWrapper<>());
        return ResultUtil.success(roleList);
    }
}

