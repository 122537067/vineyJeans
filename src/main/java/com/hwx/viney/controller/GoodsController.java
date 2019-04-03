package com.hwx.viney.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hwx.viney.entity.Goods;
import com.hwx.viney.entity.Result;
import com.hwx.viney.entity.ResultEnum;
import com.hwx.viney.entity.vo.GoodsVo;
import com.hwx.viney.oneUtils.ArrayUtil;
import com.hwx.viney.oneUtils.FileUtil;
import com.hwx.viney.oneUtils.ManagerOperationUtil;
import com.hwx.viney.oneUtils.ResultUtil;
import com.hwx.viney.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author onee123
 * @since 2019-03-29
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    GoodsService goodsService;
    @Autowired
    FileUtil fileUtil;
    @Autowired
    ArrayUtil arrayUtil;

    @Autowired
    ManagerOperationUtil managerOperationUtil;

    private int role = 4;

    /**
     * 显示所有商品
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("showAllGoodsVo")
    public Result showAllGoodsVo(int page,int limit){
        List<Goods> goodsList = goodsService.selectPage(new Page<>(page,limit),new EntityWrapper<Goods>().orderBy("create_time")).getRecords();
        int count = goodsService.selectCount(new EntityWrapper<>());
        List<GoodsVo> goodsVoList = goodsService.toGoodsVo(goodsList);
        return ResultUtil.success(goodsVoList,0,count,"商品列表");
    }

    /**
     * 搜索商品 -> 后台
     * @param map
     * @return
     */
    @GetMapping("showAllGoodsVoByParamas")
    public Result showAllGoodsVoByParamas(@RequestParam Map<String,Object> map){
        int curPage = Integer.parseInt(map.get("page").toString()) - 1;
        int curLimit = Integer.parseInt(map.get("limit").toString());
        map.put("page", curPage * curLimit);
        List<GoodsVo> goodsVos = goodsService.showGoodsByParams(map);
        map.remove("page");
        map.remove("limit");
        int count = goodsService.showGoodsCountByParams(map);
        return ResultUtil.success(goodsVos, 0, count, "查询操作记录");
    }

    /**
     * 显示所有商品 ->轮播图选择用
     * @return
     */
    @GetMapping("showAllGoods")
    public Result showAllGoods(){
        List<Goods> goodsList = goodsService.selectList(new EntityWrapper<>());
        return ResultUtil.success(goodsList);
    }

    /**
     * 显示所有商品->用户
     * @return
     */
    @GetMapping("showGoodsByPage")
    public Map showGoodsByPage(int page,@RequestParam(required = false)String searchParam,@RequestParam(required = false)String selectParam){
        List<Goods> goodsList;
        int count;
        if(searchParam != null && selectParam == null){
            //只搜索不排序
            goodsList = goodsService.selectPage(new Page<>(page,9),
                    new EntityWrapper<Goods>().eq("status","1").like("id",searchParam).or().like("name",searchParam).orderBy("weight",false)).getRecords();
            count = goodsService.selectCount(new EntityWrapper<Goods>().like("id",searchParam).or().like("name",searchParam).eq("status","1"));
        }else if(searchParam == null && selectParam != null){
            //只排序不搜索
            String[] select = arrayUtil.stringToStringArray(selectParam);
            goodsList = goodsService.selectPage(new Page<>(page,9),
                    new EntityWrapper<Goods>().eq("status","1").orderBy(select[0],Boolean.parseBoolean(select[1]))).getRecords();
            count = goodsService.selectCount(new EntityWrapper<Goods>().eq("status","1"));
        }else if(selectParam != null && searchParam != null){
            //排序和搜索
            String[] select = arrayUtil.stringToStringArray(selectParam);
            goodsList = goodsService.selectPage(new Page<>(page,9),
                    new EntityWrapper<Goods>().eq("status","1").like("id",searchParam).or().like("name",searchParam).orderBy(select[0],Boolean.parseBoolean(select[1]))).getRecords();
            count = goodsService.selectCount(new EntityWrapper<Goods>().eq("status","1").like("id",searchParam).or().like("name",searchParam));
        }else{
            //既不排序也不搜索
            goodsList = goodsService.selectPage(new Page<>(page,9),new EntityWrapper<Goods>().eq("status","1").orderBy("weight",false)).getRecords();
            count = goodsService.selectCount(new EntityWrapper<Goods>().eq("status","1"));
        }
        int totalPage = count/9;
        Map<String,Object> map = new HashMap<>();
        map.put("list",goodsList);
        map.put("totalPage",totalPage+1);
        map.put("curPage",page);
        map.put("nextOne",page+1);
        return map;
    }

    /**
     * 添加商品
     * @param goods
     * @return
     */
    @PostMapping("insertGoods")
    public Result insertGoods(@RequestBody Goods goods, HttpServletRequest httpServletRequest){
        if(managerOperationUtil.mangerOperationNote(httpServletRequest,role,"添加服装",goods.toString()).getCode() == 0) {
            if (goodsService.selectById(goods.getId()) != null) {
                return ResultUtil.error(ResultEnum.GOODS_NUM_EXIST.getCode(), ResultEnum.GOODS_NUM_EXIST.getMsg());  //-2 存在相同编号
            }
            goods.setCreateTime(new Date());
            goods.setUpdateTime(new Date());
            goods.setStatus("0");

            goods.setCreateTime(new Date());
            goods.setUpdateTime(new Date());
            if (goods.insert()) {
                return ResultUtil.success(goods);
            }
            return ResultUtil.error(ResultEnum.UNKNOWN_ERROR.getCode(), ResultEnum.UNKNOWN_ERROR.getMsg());
        }else{
            return ResultUtil.error(ResultEnum.ERROR_PERMISSION_DENIED.getCode(),ResultEnum.ERROR_PERMISSION_DENIED.getMsg());
        }
    }

    /**
     * 更新商品
     * @param goods
     * @return
     */
    @PostMapping("updateGoods")
    public Result updateGoods(@RequestBody Goods goods, HttpServletRequest httpServletRequest){
        if(managerOperationUtil.mangerOperationNote(httpServletRequest,role,"更新服装",goods.toString()).getCode() == 0) {
            Goods goodsBefore = goodsService.selectById(goods.getId());
            goods.setUpdateTime(new Date());
            if (goods.updateById()) {
                if (!goodsBefore.getCover().equals(goods.getCover())) {
                    //封面更换->删除旧封面
                    fileUtil.delFile(goodsBefore.getCover());
                }
                return ResultUtil.success(goods);
            }
            return ResultUtil.error(ResultEnum.UNKNOWN_ERROR.getCode(), ResultEnum.UNKNOWN_ERROR.getMsg());
        }else{
            return ResultUtil.error(ResultEnum.ERROR_PERMISSION_DENIED.getCode(),ResultEnum.ERROR_PERMISSION_DENIED.getMsg());
        }
    }

    /**
     * 通过id返回商品
     * @param id
     * @return
     */
    @GetMapping("selectGoodsById")
    public Result selectGoodsById(String id){
        Goods goods = goodsService.selectById(id);
        GoodsVo goodsVo = goodsService.toGoodsVo(goods);
        return ResultUtil.success(goodsVo);
    }

    /**
     * 删除商品
     * @param goods
     * @return
     */
    @PostMapping("deleteGoods")
    public Result deleteGoods(@RequestBody Goods goods, HttpServletRequest httpServletRequest){
        if(managerOperationUtil.mangerOperationNote(httpServletRequest,role,"删除服装",goods.toString()).getCode() == 0) {
            int count = goodsService.selectBannerCountByGoodsId(goods.getId());
            if(count > 0){
                return ResultUtil.error(ResultEnum.BANNER_EXIST_GOODS.getCode(),ResultEnum.BANNER_EXIST_GOODS.getMsg());    //code -5 有轮播图存在此商品
            }
            ArrayUtil arrayUtil = new ArrayUtil();
            String picArray[] = arrayUtil.stringToStringArray(goods.getPicture());
            String cover = goods.getCover();
            if(goodsService.deleteById(goods)){
                //删除相关图片
                fileUtil.delFile(cover);
                for(String pic:picArray){
                    fileUtil.delFile(pic);
                }
                return ResultUtil.success(goods);
            }
            return ResultUtil.error(ResultEnum.UNKNOWN_ERROR.getCode(),ResultEnum.UNKNOWN_ERROR.getMsg());
        }else{
            return ResultUtil.error(ResultEnum.ERROR_PERMISSION_DENIED.getCode(),ResultEnum.ERROR_PERMISSION_DENIED.getMsg());
        }
    }

    /**
     * 显示5个促销商品
     * @return
     */
    @GetMapping("showMostDiscount")
    public List<Goods> showMostDiscount(){
        List<Goods> goodsList = goodsService.selectPage(new Page<>(1,5),new EntityWrapper<Goods>().eq("status","1").orderBy("discount")).getRecords();
        return goodsList;
    }
}

