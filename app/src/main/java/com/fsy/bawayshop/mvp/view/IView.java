package com.fsy.bawayshop.mvp.view;

/**
 * @author : FangShiKang
 * @date : 2018/12/28.
 * email : fangshikang@outlook.com
 * desc :
 */
public interface IView<T> {

    void onISuccess(T data);
    void onIFailed(String error);

}
