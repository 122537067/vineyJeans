package com.hwx.viney.controller;

import com.hwx.viney.entity.Result;
import com.hwx.viney.entity.ResultEnum;
import com.hwx.viney.oneUtils.FileUtil;
import com.hwx.viney.oneUtils.ResultUtil;
import org.apache.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program:viney
 * @author:one
 * @creatTime:2019/03/30
 **/

@RestController
@RequestMapping("/util")
public class UtilController {
    @Autowired
    FileUtil fileUtil;

    private String videoPath = "/goods/video/";

    /**
     * 上传图片
     * @param request
     * @return
     * @throws IOException
     */
    @PostMapping("uploadImg")
    public synchronized Result uploadImg(MultipartHttpServletRequest request) throws IOException {
        MultipartFile file = request.getFile("file");
        String path = request.getParameter("path");
        String resultUrl, fix;
        byte[] bytes = file.getBytes();
        fix = FileUtil.getFix(file.getOriginalFilename());
        resultUrl = fileUtil.singleUpload(path, bytes, fix);
        return ResultUtil.success(resultUrl);
    }

    /**
     * 删除图片
     * @param httpServletRequest
     * @return
     */
    @PostMapping("deleteImg")
    public synchronized Result deleteImg(HttpServletRequest httpServletRequest){
        String path = httpServletRequest.getParameter("path");
        if(fileUtil.delFile(path)){
            return ResultUtil.success(path);
        }else {
            return ResultUtil.error(ResultEnum.UNKNOWN_ERROR.getCode(),ResultEnum.UNKNOWN_ERROR.getMsg());
        }
    }

    /**
     * WangEditor上传图片
     * wangEditor
     * @param request
     * @return
     * @throws IOException
     */
    @PostMapping("wangEditorUploadImg")
    public synchronized Result wangEditorUploadImg(MultipartHttpServletRequest request) throws IOException {
        Map<String,Object> map = new HashMap<>();
        List<byte[]> bytes = new ArrayList<>();
        List<MultipartFile> files = request.getFiles("imgFile");
        String path = request.getParameter("path");
        String[] imgUrl = new String[files.size()];
        int i = -1;
        for(MultipartFile file:files){
            i++;
            byte[] bytes_one = file.getBytes();
            String fix = FileUtil.getFix(file.getOriginalFilename());
            String resultUrl = fileUtil.singleUpload(path,bytes_one,fix);
            imgUrl[i] = resultUrl;
        }
        return ResultUtil.success(imgUrl);
    }

    /**
     * WangEditor上传视频
     * @param request
     * @return
     * @throws IOException
     */
    @PostMapping("wangEditorUploadVideo")
    public synchronized Map wangEditorUploadVideo(MultipartHttpServletRequest request) throws IOException {
        Map<String,Object> map = new HashMap<>();
        MultipartFile file = request.getFile("file");
        String resultUrl, fix;
        byte[] bytes = file.getBytes();
        fix = FileUtil.getFix(file.getOriginalFilename());
        resultUrl = fileUtil.singleUpload(videoPath, bytes, fix);
        map.put("data",resultUrl);
        map.put("errno",0);
        return map;
    }
}
