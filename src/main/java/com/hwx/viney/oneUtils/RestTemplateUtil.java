package com.hwx.viney.oneUtils;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @author onee
 * @date 2018/10/12 0012 下午 14:53
 * 网络请求,RestTemplate工具类
 */
@Component
public class RestTemplateUtil {

    private RestTemplate restTemplate;

    /**
     * 发送GET请求
     *
     * @param url
     * @param param
     * @return
     */
    public String GetData(String url, Map<String, String> param) {
        restTemplate = new RestTemplate();
        //  请勿轻易改变此提交方式，大部分的情况下，提交方式都是表单提交
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return restTemplate.getForEntity(url, String.class, param).getBody();

    }

    /**
     * 发送POST-JSON请求
     *
     * @param url
     * @param param
     * @return
     */
    public String PostJsonData(String url, JSONObject param) {
        restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        HttpEntity<JSONObject> requestEntity = new HttpEntity<JSONObject>(param, headers);
        return restTemplate.postForEntity(url, requestEntity, String.class).getBody();
    }

    /**
     * 发送POST 表单请求
     *
     * @param url
     * @param param
     * @return
     */
    public String PostFormData(String url,MultiValueMap<String, String> param) {
        restTemplate = new RestTemplate();
        //  请勿轻易改变此提交方式，大部分的情况下，提交方式都是表单提交
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<Object> requestEntity = new HttpEntity<Object>(param, headers);
        return restTemplate.postForEntity(url, requestEntity, String.class).getBody();

    }

    /**
     * post请求,返回二进制文件流
     * @param url
     * @param param
     * @return
     */
    public byte[] PostJsonDataQRCode(String url, JSONObject param) {
        restTemplate = new RestTemplate();
        //  请勿轻易改变此提交方式，大部分的情况下，提交方式都是表单提交
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        HttpEntity<JSONObject> requestEntity = new HttpEntity<JSONObject>(param, headers);
        return restTemplate.postForEntity(url, requestEntity,  byte[].class).getBody();
    }
}
