package com.hwx.viney.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.hwx.viney.entity.Contact;
import com.hwx.viney.entity.Result;
import com.hwx.viney.entity.ResultEnum;
import com.hwx.viney.oneUtils.FileUtil;
import com.hwx.viney.oneUtils.ManagerOperationUtil;
import com.hwx.viney.oneUtils.ResultUtil;
import com.hwx.viney.service.ContactService;
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
@RequestMapping("/contact")
public class ContactController {
    @Autowired
    ContactService contactService;
    @Autowired
    FileUtil fileUtil;
    @Autowired
    ManagerOperationUtil managerOperationUtil;

    private int role = 7;

    /**
     * 显示联系
     * @return
     */
    @GetMapping("showAllContact")
    public Result showAllContact(){
        List<Contact> contactList = contactService.selectList(new EntityWrapper<Contact>().orderBy("sort"));
        return ResultUtil.success(contactList,0,contactList.size(),"特色图");
    }

    /**
     * 添加联系
     * @param contact
     * @return
     */
    @PostMapping("insertContact")
    public Result insertContact(@RequestBody Contact contact, HttpServletRequest httpServletRequest){
        if(managerOperationUtil.mangerOperationNote(httpServletRequest,role,"添加联系方式",contact.toString()).getCode() == 0) {
            if (contact.insert()) {
                return ResultUtil.success(contact);
            } else {
                return ResultUtil.error(ResultEnum.UNKNOWN_ERROR.getCode(), ResultEnum.UNKNOWN_ERROR.getMsg());
            }
        }else{
            return ResultUtil.error(ResultEnum.ERROR_PERMISSION_DENIED.getCode(),ResultEnum.ERROR_PERMISSION_DENIED.getMsg());
        }
    }

    /**
     * 更新联系
     * @param contact
     * @return
     */
    @PostMapping("updateContact")
    public Result updateContact(@RequestBody Contact contact, HttpServletRequest httpServletRequest){
        if(managerOperationUtil.mangerOperationNote(httpServletRequest,role,"更新联系方式",contact.toString()).getCode() == 0) {
            Contact contactBefore = contactService.selectById(contact.getId());
            if (contact.updateById()) {
                //更新图片->删除旧图片
                if (!contactBefore.getPicture().equals(contact.getPicture())) {
                    fileUtil.delFile(contactBefore.getPicture());
                }
                return ResultUtil.success(contact);
            } else {
                return ResultUtil.error(ResultEnum.UNKNOWN_ERROR.getCode(), ResultEnum.UNKNOWN_ERROR.getMsg());
            }
        }else {
            return ResultUtil.error(ResultEnum.ERROR_PERMISSION_DENIED.getCode(),ResultEnum.ERROR_PERMISSION_DENIED.getMsg());
        }
    }

    /**
     * 删除联系
     * @param contact
     * @return
     */
    @PostMapping("deleteContact")
    public Result deleteContact(@RequestBody Contact contact,HttpServletRequest httpServletRequest){
        if(managerOperationUtil.mangerOperationNote(httpServletRequest,role,"删除联系方式",contact.toString()).getCode() == 0) {
            if (contact.deleteById()) {
                fileUtil.delFile(contact.getPicture());
                return ResultUtil.success(contact);
            }
            return ResultUtil.error(ResultEnum.UNKNOWN_ERROR.getCode(), ResultEnum.UNKNOWN_ERROR.getMsg());
        }else{
            return ResultUtil.error(ResultEnum.ERROR_PERMISSION_DENIED.getCode(),ResultEnum.ERROR_PERMISSION_DENIED.getMsg());
        }
    }
}

