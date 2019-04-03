package com.hwx.viney.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hwx.viney.entity.Banner;
import com.hwx.viney.entity.Result;
import com.hwx.viney.entity.ResultEnum;
import com.hwx.viney.oneUtils.FileUtil;
import com.hwx.viney.oneUtils.ManagerOperationUtil;
import com.hwx.viney.oneUtils.ResultUtil;
import com.hwx.viney.service.BannerService;
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
@RequestMapping("/banner")
public class BannerController {
    @Autowired
    BannerService bannerService;
    @Autowired
    FileUtil fileUtil;

    @Autowired
    ManagerOperationUtil managerOperationUtil;

    private int role = 5;

    /**
     * 显示轮播图
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("showAllBanner")
    public Result showAllBanner(int page,int limit){
        List<Banner> bannerList = bannerService.selectPage(new Page<>(page,limit),new EntityWrapper<>()).getRecords();
        int count = bannerList.size();
        if(count >9){
            count = bannerService.selectCount(new EntityWrapper<>());
        }
        return ResultUtil.success(bannerList,0,count,"轮播图");
    }

    /**
     * 显示轮播图->前台
     * @return
     */
    @GetMapping("showBanner")
    public List<Banner> showBanner(){
        return bannerService.selectList(new EntityWrapper<Banner>().eq("status","1"));
    }

    /**
     * 添加轮播图
     * @param banner
     * @return
     */
    @PostMapping("insertBanner")
    public Result insertBanner(@RequestBody Banner banner, HttpServletRequest httpServletRequest){
        if(managerOperationUtil.mangerOperationNote(httpServletRequest,role,"添加轮播图信息",banner.toString()).getCode() == 0) {
            if (banner.insert()) {
                return ResultUtil.success(banner);
            } else {
                return ResultUtil.error(ResultEnum.UNKNOWN_ERROR.getCode(), ResultEnum.UNKNOWN_ERROR.getMsg());
            }
        }else{
            return ResultUtil.error(ResultEnum.ERROR_PERMISSION_DENIED.getCode(),ResultEnum.ERROR_PERMISSION_DENIED.getMsg());
        }
    }

    /**
     * 更新轮播图
     * @param banner
     * @return
     */
    @PostMapping("updateBanner")
    public Result updateBanner(@RequestBody Banner banner, HttpServletRequest httpServletRequest){
        if(managerOperationUtil.mangerOperationNote(httpServletRequest,role,"更新轮播图信息",banner.toString()).getCode() == 0) {
            Banner bannerBefore = bannerService.selectById(banner.getId());
            if (banner.updateById()) {
                //更新图片->删除旧图片
                if (!bannerBefore.getPicture().equals(banner.getPicture())) {
                    fileUtil.delFile(bannerBefore.getPicture());
                }
                return ResultUtil.success(banner);
            } else {
                return ResultUtil.error(ResultEnum.UNKNOWN_ERROR.getCode(), ResultEnum.UNKNOWN_ERROR.getMsg());
            }
        }else{
            return ResultUtil.error(ResultEnum.ERROR_PERMISSION_DENIED.getCode(),ResultEnum.ERROR_PERMISSION_DENIED.getMsg());
        }
    }

    /**
     * 删除轮播图
     * @param banner
     * @return
     */
    @PostMapping("deleteBanner")
    public Result deleteBanner(@RequestBody Banner banner, HttpServletRequest httpServletRequest){
        if(managerOperationUtil.mangerOperationNote(httpServletRequest,role,"删除轮播图信息",banner.toString()).getCode() == 0) {
            if (banner.deleteById()) {
                fileUtil.delFile(banner.getPicture());
                return ResultUtil.success(banner);
            }
            return ResultUtil.error(ResultEnum.UNKNOWN_ERROR.getCode(), ResultEnum.UNKNOWN_ERROR.getMsg());
        }else{
            return ResultUtil.error(ResultEnum.ERROR_PERMISSION_DENIED.getCode(),ResultEnum.ERROR_PERMISSION_DENIED.getMsg());
        }
    }
}

