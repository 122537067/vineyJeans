package com.hwx.viney.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.hwx.viney.entity.HeaderFooter;
import com.hwx.viney.entity.Result;
import com.hwx.viney.entity.ResultEnum;
import com.hwx.viney.oneUtils.FileUtil;
import com.hwx.viney.oneUtils.ManagerOperationUtil;
import com.hwx.viney.oneUtils.ResultUtil;
import com.hwx.viney.service.HeaderFooterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author onee123
 * @since 2019-03-29
 */
@RestController
@RequestMapping("/headerFooter")
public class HeaderFooterController {
    @Autowired
    HeaderFooterService headerFooterService;
    @Autowired
    FileUtil fileUtil;

    @Autowired
    ManagerOperationUtil managerOperationUtil;

    private int role = 12;

    /**
     * 页首页尾内容
     *
     * @return
     */
    @GetMapping("showHeaderFooter")
    public HeaderFooter showHeaderFooter() {
        return headerFooterService.selectById(1);
    }

    /**
     * 更新页首页尾
     *
     * @param headerFooter
     * @return
     */
    @PostMapping("updateHeaderFooter")
    public Result updateHeaderFooter(@RequestBody HeaderFooter headerFooter, HttpServletRequest httpServletRequest) {
        if (managerOperationUtil.mangerOperationNote(httpServletRequest, role, "修改页首页尾信息", headerFooter.toString()).getCode() == 0) {
            HeaderFooter headerFooterBefore = headerFooterService.selectById(1);
            if (headerFooterService.updateById(headerFooter)) {
                if (!headerFooterBefore.getTopLogo().equals(headerFooter.getTopLogo())) {
                    fileUtil.delFile(headerFooterBefore.getTopLogo());
                }
                if (!headerFooterBefore.getFooterLogo().equals(headerFooter.getFooterLogo())) {
                    fileUtil.delFile(headerFooterBefore.getFooterLogo());
                }
                return ResultUtil.success(headerFooter);
            }
            return ResultUtil.error(ResultEnum.UNKNOWN_ERROR.getCode(), ResultEnum.UNKNOWN_ERROR.getMsg());
        } else {
            return ResultUtil.error(ResultEnum.ERROR_PERMISSION_DENIED.getCode(), ResultEnum.ERROR_PERMISSION_DENIED.getMsg());
        }
    }
}

