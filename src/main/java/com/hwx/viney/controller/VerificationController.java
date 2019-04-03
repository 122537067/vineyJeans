package com.hwx.viney.controller;

import com.hwx.viney.oneUtils.VerificationCodeUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @program:gzdm
 * @author:one
 * @creatTime:2019/02/26
 **/

@RestController
@RequestMapping("/verification")
public class VerificationController {

    /**
     * 生成验证码
     * @param request
     * @param response
     */
    @RequestMapping(value="/createVerify")
    public void createVerify(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
            response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expire", 0);
            VerificationCodeUtil randomValidateCode = new VerificationCodeUtil();
            randomValidateCode.getRandcode(request, response);//输出验证码图片方法
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * 校验验证码
     */
    @PostMapping("/getVerify")
    public boolean getVerify(@RequestBody Map<String, Object> requestMap, HttpSession session) {
        try{
            //从session中获取随机数
            String inputStr = requestMap.get("inputStr").toString();
            String random = (String) session.getAttribute("RANDOMVALIDATECODEKEY");
            if (random == null) {
                return false;
            }
            if (random.equals(inputStr)) {
                return true;
            } else {
                return false;
            }
        }catch (Exception e){
            System.out.println(e);
            return false;
        }
    }
}
