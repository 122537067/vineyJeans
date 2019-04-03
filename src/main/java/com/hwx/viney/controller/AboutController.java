package com.hwx.viney.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.hwx.viney.entity.About;
import com.hwx.viney.entity.ManagerOperation;
import com.hwx.viney.entity.Result;
import com.hwx.viney.entity.ResultEnum;
import com.hwx.viney.oneUtils.ManagerOperationUtil;
import com.hwx.viney.oneUtils.ResultUtil;
import com.hwx.viney.service.AboutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
@RequestMapping("/about")
public class AboutController {
    @Autowired
    AboutService aboutService;
    @Autowired
    ManagerOperationUtil managerOperationUtil;

    private int role = 8;

    /**
     * 显示关于
     * @return
     */
    @GetMapping("showAllAbout")
    public Result showAllAbout(){
        List<About> aboutList = aboutService.selectList(new EntityWrapper<>());
        return ResultUtil.success(aboutList,0,aboutList.size(),"关于");
    }

    /**
     * 显示关于->前台
     * @return
     */
    @GetMapping("showAbout")
    public List<About> showAbout(){
        return aboutService.selectList(new EntityWrapper<About>().eq("status","1").orderBy("sort"));
    }

    /**
     * 通过id显示about
     * @param aboutId
     * @return
     */
    @GetMapping("showAboutById")
    public About showAboutById(int aboutId){
        return aboutService.selectById(aboutId);
    }

    /**
     * 添加关于
     * @param about
     * @return
     */
    @PostMapping("insertAbout")
    public Result insertAbout(@RequestBody About about, HttpServletRequest httpServletRequest){
        if(managerOperationUtil.mangerOperationNote(httpServletRequest,role,"添加关于信息",about.toString()).getCode() == 0) {
            if (about.insert()) {
                return ResultUtil.success(about);
            } else {
                return ResultUtil.error(ResultEnum.UNKNOWN_ERROR.getCode(), ResultEnum.UNKNOWN_ERROR.getMsg());
            }
        }else{
            return ResultUtil.error(ResultEnum.ERROR_PERMISSION_DENIED.getCode(),ResultEnum.ERROR_PERMISSION_DENIED.getMsg());
        }
    }

    /**
     * 更新关于
     * @param about
     * @return
     */
    @PostMapping("updateAbout")
    public Result updateAbout(@RequestBody About about, HttpServletRequest httpServletRequest){
        if(managerOperationUtil.mangerOperationNote(httpServletRequest,role,"更新关于信息",about.toString()).getCode() == 0) {
            if (about.updateById()) {
                return ResultUtil.success(about);
            } else {
                return ResultUtil.error(ResultEnum.UNKNOWN_ERROR.getCode(), ResultEnum.UNKNOWN_ERROR.getMsg());
            }
        }else{
            return ResultUtil.error(ResultEnum.ERROR_PERMISSION_DENIED.getCode(),ResultEnum.ERROR_PERMISSION_DENIED.getMsg());
        }
    }

    /**
     * 删除关于
     * @param about
     * @return
     */
    @PostMapping("deleteAbout")
    public Result deleteAbout(@RequestBody About about,HttpServletRequest httpServletRequest){
        if(managerOperationUtil.mangerOperationNote(httpServletRequest,role,"删除关于信息",about.toString()).getCode() == 0) {
            if (about.deleteById()) {
                return ResultUtil.success(about);
            }
            return ResultUtil.error(ResultEnum.UNKNOWN_ERROR.getCode(), ResultEnum.UNKNOWN_ERROR.getMsg());
        }else{
            return ResultUtil.error(ResultEnum.ERROR_PERMISSION_DENIED.getCode(),ResultEnum.ERROR_PERMISSION_DENIED.getMsg());
        }
    }
}

