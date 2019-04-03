package com.hwx.viney.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hwx.viney.entity.Feature;
import com.hwx.viney.entity.Result;
import com.hwx.viney.entity.ResultEnum;
import com.hwx.viney.oneUtils.FileUtil;
import com.hwx.viney.oneUtils.ManagerOperationUtil;
import com.hwx.viney.oneUtils.ResultUtil;
import com.hwx.viney.service.FeatureService;
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
@RequestMapping("/feature")
public class FeatureController {
    @Autowired
    FeatureService featureService;
    @Autowired
    FileUtil fileUtil;
    @Autowired
    ManagerOperationUtil managerOperationUtil;

    private int role = 6;

    /**
     * 显示特色
     * @return
     */
    @GetMapping("showAllFeature")
    public Result showAllFeature(){
        List<Feature> featureList = featureService.selectList(new EntityWrapper<>());
        return ResultUtil.success(featureList,0,featureList.size(),"特色");
    }

    /**
     * 显示特色->前台
     * @return
     */
    @GetMapping("showFeature")
    public List<Feature> showFeature(){
        return featureService.selectList(new EntityWrapper<Feature>().eq("status","1").orderBy("sort"));
    }

    /**
     * 添加特色
     * @param feature
     * @return
     */
    @PostMapping("insertFeature")
    public Result insertFeature(@RequestBody Feature feature, HttpServletRequest httpServletRequest){
        if(managerOperationUtil.mangerOperationNote(httpServletRequest,role,"添加首页信息",feature.toString()).getCode() == 0) {
            if (feature.insert()) {
                return ResultUtil.success(feature);
            } else {
                return ResultUtil.error(ResultEnum.UNKNOWN_ERROR.getCode(), ResultEnum.UNKNOWN_ERROR.getMsg());
            }
        }else{
            return ResultUtil.error(ResultEnum.ERROR_PERMISSION_DENIED.getCode(),ResultEnum.ERROR_PERMISSION_DENIED.getMsg());
        }
    }

    /**
     * 更新特色
     * @param feature
     * @return
     */
    @PostMapping("updateFeature")
    public Result updateFeature(@RequestBody Feature feature, HttpServletRequest httpServletRequest){
        if(managerOperationUtil.mangerOperationNote(httpServletRequest,role,"更新首页信息",feature.toString()).getCode() == 0) {
            Feature featureBefore = featureService.selectById(feature.getId());
            if (feature.updateById()) {
                //更新图片->删除旧图片
                if (!featureBefore.getPicture().equals(feature.getPicture())) {
                    System.out.println(featureBefore.getPicture());
                    fileUtil.delFile(featureBefore.getPicture());
                }
                return ResultUtil.success(feature);
            } else {
                return ResultUtil.error(ResultEnum.UNKNOWN_ERROR.getCode(), ResultEnum.UNKNOWN_ERROR.getMsg());
            }
        }else {
            return ResultUtil.error(ResultEnum.ERROR_PERMISSION_DENIED.getCode(),ResultEnum.ERROR_PERMISSION_DENIED.getMsg());
        }
    }

    /**
     * 删除轮播图
     * @param feature
     * @return
     */
    @PostMapping("deleteFeature")
    public Result deleteFeature(@RequestBody Feature feature, HttpServletRequest httpServletRequest){
        if(managerOperationUtil.mangerOperationNote(httpServletRequest,role,"删除首页信息",feature.toString()).getCode() == 0) {
            if (feature.deleteById()) {
                fileUtil.delFile(feature.getPicture());
                return ResultUtil.success(feature);
            }
            return ResultUtil.error(ResultEnum.UNKNOWN_ERROR.getCode(), ResultEnum.UNKNOWN_ERROR.getMsg());
        }else {
            return ResultUtil.error(ResultEnum.ERROR_PERMISSION_DENIED.getCode(),ResultEnum.ERROR_PERMISSION_DENIED.getMsg());
        }
    }
}

