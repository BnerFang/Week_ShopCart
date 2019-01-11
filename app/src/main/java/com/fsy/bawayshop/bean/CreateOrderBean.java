package com.fsy.bawayshop.bean;

/**
 * @author : FangShiKang
 * @date : 2019/01/11.
 * email : fangshikang@outlook.com
 * desc :   创建订单
 */
public class CreateOrderBean {

    private String orderId;
    private String message;
    private String status;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
