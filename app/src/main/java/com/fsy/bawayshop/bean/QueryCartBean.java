package com.fsy.bawayshop.bean;

import java.util.List;

/**
 * @author : FangShiKang
 * @date : 2019/01/07.
 * email : fangshikang@outlook.com
 * desc :       查询购物车实体类
 */
public class QueryCartBean {

    private String message;
    private String status;
    private List<ResultBean> result;

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

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        private int commodityId;
        private String commodityName;
        private int count;
        private String pic;
        private double price;
        private boolean cheCked = false;

        public boolean isCheCked() {
            return cheCked;
        }

        public void setCheCked(boolean cheCked) {
            this.cheCked = cheCked;
        }

        public int getCommodityId() {
            return commodityId;
        }

        public void setCommodityId(int commodityId) {
            this.commodityId = commodityId;
        }

        public String getCommodityName() {
            return commodityName;
        }

        public void setCommodityName(String commodityName) {
            this.commodityName = commodityName;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }
    }
}
