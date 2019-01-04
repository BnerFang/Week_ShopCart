package com.fsy.bawayshop.mvp;

/**
 * @author : FangShiKang
 * @date : 2018/12/28.
 * email : fangshikang@outlook.com
 * desc :
 */
public interface MyCallBack<T> {

    void onSuccess(T data);
    void onFailed(String error);
}
