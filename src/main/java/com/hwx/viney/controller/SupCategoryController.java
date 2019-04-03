package com.hwx.viney.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hwx.viney.entity.Result;
import com.hwx.viney.entity.ResultEnum;
import com.hwx.viney.entity.SupCategory;
import com.hwx.viney.oneUtils.ManagerOperationUtil;
import com.hwx.viney.oneUtils.ResultUtil;
import com.hwx.viney.service.SupCategoryService;
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
@RequestMapping("/supCategory")
public class SupCategoryController {
    @Autowired
    SupCategoryService supCategoryService;

    @Autowired
    ManagerOperationUtil managerOperationUtil;

    private int role = 2;

    /**
     * 显示父级分类
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("showAllSupCategory")
    public Result showAllSupCategory(@RequestParam(required = false)Integer page, @RequestParam(required = false)Integer limit){
        if(page == null){
            List<SupCategory> supCategories = supCategoryService.selectList(
                    new EntityWrapper<SupCategory>().eq("status","1"));
            return ResultUtil.success(supCategories);
        }else{
            List<SupCategory> supCategories = supCategoryService.selectPage(
                    new Page<>(page,limit),new EntityWrapper<>()).getRecords();
            int count = supCategoryService.selectCount(new EntityWrapper<>());
            return ResultUtil.success(supCategories,0,count,"父级分类");
        }
    }

    /**
     * 添加父级分类
     * @param supCategory
     * @return
     */
    @PostMapping("insertSupCategory")
    public Result insertSupCategory(@RequestBody SupCategory supCategory, HttpServletRequest httpServletRequest){
        if (managerOperationUtil.mangerOperationNote(httpServletRequest, role, "添加一级分类", supCategory.toString()).getCode() == 0) {
            if (supCategoryService.insert(supCategory)) {
                return ResultUtil.success(supCategory);
            }
            return ResultUtil.error(ResultEnum.UNKNOWN_ERROR.getCode(), ResultEnum.UNKNOWN_ERROR.getMsg());
        }else {
            return ResultUtil.error(ResultEnum.ERROR_PERMISSION_DENIED.getCode(), ResultEnum.ERROR_PERMISSION_DENIED.getMsg());
        }
    }

    /**
     * 更新父级分类
     * @param supCategory
     * @return
     */
    @PostMapping("updateSupCategory")
    public Result updateSupCategory(@RequestBody SupCategory supCategory,HttpServletRequest httpServletRequest){
        if (managerOperationUtil.mangerOperationNote(httpServletRequest, role, "更新一级分类", supCategory.toString()).getCode() == 0) {
            if (supCategoryService.updateById(supCategory)) {
                return ResultUtil.success(supCategory);
            }
            return ResultUtil.error(ResultEnum.UNKNOWN_ERROR.getCode(), ResultEnum.UNKNOWN_ERROR.getMsg());
        }else {
            return ResultUtil.error(ResultEnum.ERROR_PERMISSION_DENIED.getCode(), ResultEnum.ERROR_PERMISSION_DENIED.getMsg());
        }
    }

    /**
     * 删除父级分类
     * @param supCategory
     * @return
     */
    @PostMapping("deleteSupCategory")
    public Result deleteSupCategory(@RequestBody SupCategory supCategory,HttpServletRequest httpServletRequest){
        if (managerOperationUtil.mangerOperationNote(httpServletRequest, role, "删除一级分类", supCategory.toString()).getCode() == 0) {
            int count = supCategoryService.selectCountByCategoryId(supCategory.getId());
            if (count > 0) {
                return ResultUtil.error(ResultEnum.CATE_EXIST_SUP.getCode(), ResultEnum.CATE_EXIST_SUP.getMsg(), count);    //-4 存在分类
            }
            if (supCategoryService.deleteById(supCategory.getId())) {
                return ResultUtil.success(supCategory);
            }
            return ResultUtil.error(ResultEnum.UNKNOWN_ERROR.getCode(), ResultEnum.UNKNOWN_ERROR.getMsg());
        }else {
            return ResultUtil.error(ResultEnum.ERROR_PERMISSION_DENIED.getCode(), ResultEnum.ERROR_PERMISSION_DENIED.getMsg());
        }
    }
}

