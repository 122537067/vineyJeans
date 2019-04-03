package com.hwx.viney.entity;

/**
 * @author onee
 * @date 2018/8/25 0025 下午 16:36
 */
public enum ResultEnum {
    //这里是可以自己定义的，方便与前端交互即可
    UNKNOWN_ERROR(-1, "未知错误"),
    //存在编号相同的衣服
    GOODS_NUM_EXIST(-2,"商品编号存在"),
    //此分类存在商品
    GOODS_EXIST_CATE(-3,"此分类存在商品"),
    //此父级分类存在下级分类
    CATE_EXIST_SUP(-4,"此父级分类存在下级分类"),
    //此商品存在轮播图
    BANNER_EXIST_GOODS(-5,"此商品存在轮播图"),
    DATA_IS_NULL(-6,"空数据"),
    LOGIN_ERROR(-7,"账号或密码错误"),
    UPDATE_ERROR(-8,"更新失败"),
    MANAGER_EXIS(-9,"账号存在"),
    ERROR_PERMISSION_DENIED(-10,"权限不足")
    ;

    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }
}
