package com.hwx.viney.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hwx.viney.entity.Comment;
import com.hwx.viney.entity.Result;
import com.hwx.viney.entity.ResultEnum;
import com.hwx.viney.oneUtils.IpUtil;
import com.hwx.viney.oneUtils.ManagerOperationUtil;
import com.hwx.viney.oneUtils.ResultUtil;
import com.hwx.viney.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    CommentService commentService;
    @Autowired
    ManagerOperationUtil managerOperationUtil;

    private int role = 9;

    /**
     * 用户留言
     * @param comment
     * @return
     */
    @PostMapping("insertComment")
    public Result insertComment(@RequestBody Comment comment, HttpServletRequest httpServletRequest){
        String opIp = IpUtil.getIpAddr(httpServletRequest);
        comment.setCreateTime(new Date());
        comment.setIp(opIp);
        if(comment.insert()){
            return ResultUtil.success(comment);
        }
        return ResultUtil.error(ResultEnum.UNKNOWN_ERROR.getCode(),ResultEnum.UNKNOWN_ERROR.getMsg());
    }

    /**
     * 查找留言信息
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("showAllComment")
    public Result showAllComment(int page,int limit, HttpServletRequest httpServletRequest){
        if(managerOperationUtil.mangerOperationNote(httpServletRequest,role,"查询留言信息","页码:" + page + " 个数:" + limit).getCode() == 0) {
            List<Comment> commentList = commentService.selectPage(new Page<>(page, limit), new EntityWrapper<Comment>().eq("status", "1").orderBy("create_time")).getRecords();
            int count = commentService.selectCount(new EntityWrapper<Comment>().eq("status", "1"));
            return ResultUtil.success(commentList, 0, count, "留言列表");
        }else{
            return ResultUtil.error(ResultEnum.ERROR_PERMISSION_DENIED.getCode(),ResultEnum.ERROR_PERMISSION_DENIED.getMsg());
        }
    }

    /**
     * 批量删除留言
     * @param comments
     * @return
     */
    @PostMapping("deleteComment")
    public Result deleteComment(@RequestBody List<Comment> comments, HttpServletRequest httpServletRequest){
        if(managerOperationUtil.mangerOperationNote(httpServletRequest,role,"删除留言信息",comments.toString()).getCode() == 0) {
            String res = "";
            for (Comment comment : comments) {
                comment.setStatus("0");
                if (comment.updateById()) {
                    res += comment.getId() + ",";
                } else {
                    return ResultUtil.error(ResultEnum.UNKNOWN_ERROR.getCode(), ResultEnum.UNKNOWN_ERROR.getMsg(), comment);
                }
            }
            return ResultUtil.success(res);
        }else{
            return ResultUtil.error(ResultEnum.ERROR_PERMISSION_DENIED.getCode(),ResultEnum.ERROR_PERMISSION_DENIED.getMsg());
        }
    }
}

