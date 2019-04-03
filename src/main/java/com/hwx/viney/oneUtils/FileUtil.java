package com.hwx.viney.oneUtils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Eangaie
 * @date 2018/9/4 0004 上午 8:45
 */
@Component
@PropertySource({"classpath:application.properties"})
public class FileUtil {

    @Value("${URL_PATH}")
    String URL_PATH;
    @Value("${ROOT_PATH}")
    String ROOT_PATH;

    String LOG_PATH = "GzdmLog/";

    /**
     * 单文件上传
     *
     * @param path
     * @param file
     * @param fix
     * @return
     */
    public static synchronized String singleUpload(String path, byte[] file, String fix) {
        String ROOT_PATH="C:/hwx/";
        TimeUtil timeUtil = new TimeUtil();
        File targetFile = new File(ROOT_PATH+path);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        FileOutputStream out = null;
        String fileName=timeUtil.dateToRandom(5);
        try {
            out = new FileOutputStream(ROOT_PATH+path + fileName + fix);
            out.write(file);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path + fileName + fix;
    }

    /**
     * 多文件上传接口
     * @param path 图片保存的地址
     * @param bytes 字节数组
     * @param fix 后缀名
     * @return 图片地址数组
     */
    public synchronized List<String> multipartUploads(String path,List<byte[]> bytes, String fix) {
        List<String> resultUrls=new ArrayList<>();
        BufferedOutputStream stream = null;
        String fileName="";
        FileOutputStream out = null;
        String pathTemp=ROOT_PATH+path;
        //创建文件夹
        File targetFile = new File(pathTemp);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        //写入文件，并获取相应的url地址到动态数组
        for (int i = 0; i < bytes.size(); i++) {
            try {
                byte[] bytearr=bytes.get(i);
                fileName=System.currentTimeMillis()+"";
                out = new FileOutputStream(pathTemp + fileName + fix);
                out.write(bytearr);
                out.flush();
                out.close();
//                path = path.substring(ROOT_PATH.length());
//                path = URL_PATH + path;
                resultUrls.add(path + fileName + fix);
            } catch (Exception e) {
                stream = null;
                continue;
            }
        }
        return resultUrls;
    }


    /**
     * 文件删除
     * @param fileName
     * @return
     */
    public boolean delFile(String fileName) {
        File file = new File(ROOT_PATH+fileName);
        if (file.exists() && file.isFile()) {
            file.delete();
            return true;
        }
        return false;
        }

    /**
     * 获取后缀
     * @param fileName
     * @return
     */
    public static String getFix(String fileName) {
        String fix;
        fix = fileName.substring(fileName.lastIndexOf("."));
        return fix;
    }

    /**
     * 读入TXT文件
     */
    public void readFile(String fileName) {
        String pathname = fileName; // 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
        //防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw;
        //不关闭文件会导致资源的泄露，读写文件都同理
        //Java7的try-with-resources可以优雅关闭文件，异常时自动关闭文件
        try (FileReader reader = new FileReader(pathname);
             BufferedReader br = new BufferedReader(reader) // 建立一个对象，它把文件内容转成计算机能读懂的语言
        ) {
            String line;
            //网友推荐更加简洁的写法
            while ((line = br.readLine()) != null) {
                // 一次读入一行数据
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 写入TXT文件
     */
    public void writeFile(String fileName,String content) {
        TimeUtil timeUtil = new TimeUtil();
        File targetFile = new File(LOG_PATH+timeUtil.getYMD(new Date()));
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        try {
            File writeName = new File(LOG_PATH+timeUtil.getYMD(new Date())+"/"+fileName); // 相对路径，如果没有则要建立一个新的xxx.txt文件
            if (!writeName.exists()) {
                writeName.createNewFile();// 不存在则创建
            }
            try (BufferedWriter out = new BufferedWriter(new FileWriter(writeName,true))     //true,则追加写入text文本
            ) {
                out.write(content); // \r\n即为换行
                out.flush(); // 把缓存区内容压入文件
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
