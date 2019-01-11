package com.fsy.bawayshop.bean;

/**
 * @author : FangShiKang
 * @date : 2019/01/10.
 * email : fangshikang@outlook.com
 * desc :
 */
public class AddCartBean {
    private int commodityId;
    private int count;

    public AddCartBean(int commodityId, int count) {
        this.count = count;
        this.commodityId = commodityId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(int commodityId) {
        this.commodityId = commodityId;
    }
}
