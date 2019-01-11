package com.fsy.bawayshop.api;

/**
 * @author : FangShiKang
 * @date : 2018/12/28.
 * email : fangshikang@outlook.com
 * desc :
 */
public class ParamsApis {
    /**
     * 登录.注册
     */
    public static final String POST_LOGIN_BODY_PHONE = "phone";
    public static final String POST_LOGIN_BODY_PWD = "pwd";
    public static final String POST_REG_BODY_PHONE = "phone";
    public static final String POST_REG_BODY_PWD = "pwd";

    //点赞
    public static final String POST_DZ_KEY = "circleId";
    //加入购物车
    public static final String POST_CART_DATA_KEY = "data";

    //新增收货地址
    public static final String POST_RESS_NAME_KEY = "realName";
    public static final String POST_RESS_PHONE_KEY = "phone";
    public static final String POST_RESS_ADDRESS_KEY = "address";
    public static final String POST_RESS_CODE_KEY = "zipCode";
    //默认收货地址
    public static final String POST_RESS_DEFAULT_ID_KEY = "id";

    //创建订单
    public static final String POST_ORDER_INFO_KEY = "orderInfo";
    public static final String POST_ORDER_PRICE_KEY = "totalPrice";
    public static final String POST_ORDER_RESSID_KEY = "addressId";

}
