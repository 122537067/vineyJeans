package com.hwx.viney.oneUtils;


import com.hwx.viney.entity.Result;

/**
 * @author onee
 * @date 2018/2/25 0025 下午 16:31
 */
public class ResultUtil {

    //当正确时返回的值
    public static Result success(Object data){
        Result result = new Result();
        result.setCode(0);
        result.setMsg("OK");
        result.setData(data);
        return result;
    }

    //当正确时返回的值
    public static Result success(Object data,String msg){
        Result result = new Result();
        result.setCode(0);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    //返回layui表单
    public static Result success(Object data,int code,int count,String msg){
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        result.setCount(count);
        return result;
    }

    public static Result success(){
        return success(null);
    }

    //当错误时返回的值
    public static Result error(int code,String msg){
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    //当错误时返回的值
    public static Result error(int code,String msg,Object data){
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }
}
