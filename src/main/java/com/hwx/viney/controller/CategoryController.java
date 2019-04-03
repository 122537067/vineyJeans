package com.hwx.viney.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hwx.viney.entity.Category;
import com.hwx.viney.entity.Result;
import com.hwx.viney.entity.ResultEnum;
import com.hwx.viney.entity.vo.CategoryVo;
import com.hwx.viney.oneUtils.ManagerOperationUtil;
import com.hwx.viney.oneUtils.ResultUtil;
import com.hwx.viney.service.CategoryService;
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
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    ManagerOperationUtil managerOperationUtil;

    private int role = 3;

    /**
     * 显示二级分类列表
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("showAllCategoryVo")
    public Result showAllCategoryVo(int page,int limit){
        List<Category> categories = categoryService.selectPage(new Page<>(page,limit),new EntityWrapper<>()).getRecords();
        List<CategoryVo> categoryVos = categoryService.toCategoryVo(categories);
        int count = categories.size();
        if(count>=10){
            count = categoryService.selectCount(new EntityWrapper<>());
        }
        return ResultUtil.success(categoryVos,0,count,"二级分类");
    }

    /**
     * 通过父级id显示分类
     * @param supCategoryId
     * @return
     */
    @GetMapping("showCategoryBySupId")
    public Result showCategoryBySupId(int supCategoryId){
        List<Category> categoryList = categoryService.selectList(new EntityWrapper<Category>().eq("sup_category_id",supCategoryId));
        return ResultUtil.success(categoryList);
    }

    /**
     * 显示分类->下拉框
     * @return
     */
    @GetMapping("showCategory")
    public Result showCategory(){
        List<Category> categoryList = categoryService.selectList(new EntityWrapper<Category>());
        return ResultUtil.success(categoryList);
    }

    /**
     * 添加二级分类
     * @param category
     * @return
     */
    @PostMapping("insertCategory")
    public Result insertCategory(@RequestBody Category category, HttpServletRequest httpServletRequest){
        if(managerOperationUtil.mangerOperationNote(httpServletRequest,role,"添加二级分类",category.toString()).getCode() == 0) {
            if (categoryService.insert(category)) {
                return ResultUtil.success(category);
            }
            return ResultUtil.error(ResultEnum.UNKNOWN_ERROR.getCode(), ResultEnum.UNKNOWN_ERROR.getMsg());
        }else{
            return ResultUtil.error(ResultEnum.ERROR_PERMISSION_DENIED.getCode(),ResultEnum.ERROR_PERMISSION_DENIED.getMsg());
        }
    }

    /**
     * 更新二级分类
     * @param category
     * @return
     */
    @PostMapping("updateCategory")
    public Result updateCategory(@RequestBody Category category, HttpServletRequest httpServletRequest){
        if(managerOperationUtil.mangerOperationNote(httpServletRequest,role,"修改二级分类",category.toString()).getCode() == 0) {
            if (categoryService.updateById(category)) {
                return ResultUtil.success(category);
            }
            return ResultUtil.error(ResultEnum.UNKNOWN_ERROR.getCode(), ResultEnum.UNKNOWN_ERROR.getMsg());
        }else{
            return ResultUtil.error(ResultEnum.ERROR_PERMISSION_DENIED.getCode(),ResultEnum.ERROR_PERMISSION_DENIED.getMsg());
        }
    }

    /**
     * 删除二级分类
     * @param category
     * @return
     */
    @PostMapping("deleteCategory")
    public Result deleteCategory(@RequestBody Category category, HttpServletRequest httpServletRequest){
        int count = categoryService.selectGoodsNumByCategoryId(category.getId());
        if(count > 0){
            return ResultUtil.error(ResultEnum.GOODS_EXIST_CATE.getCode(),ResultEnum.GOODS_EXIST_CATE.getMsg(),count);  //-3 ,分类存在商品
        }
        if(managerOperationUtil.mangerOperationNote(httpServletRequest,role,"删除二级分类",category.toString()).getCode() == 0) {
            if (categoryService.deleteById(category)) {
                return ResultUtil.success(category);
            }
            return ResultUtil.error(ResultEnum.UNKNOWN_ERROR.getCode(), ResultEnum.UNKNOWN_ERROR.getMsg());
        }else{
            return ResultUtil.error(ResultEnum.ERROR_PERMISSION_DENIED.getCode(),ResultEnum.ERROR_PERMISSION_DENIED.getMsg());
        }
    }
}

