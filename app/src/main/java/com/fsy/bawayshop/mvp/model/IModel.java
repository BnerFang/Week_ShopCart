package com.fsy.bawayshop.mvp.model;

import com.fsy.bawayshop.mvp.MyCallBack;

import java.util.Map;

/**
 * @author : FangShiKang
 * @date : 2018/12/28.
 * email : fangshikang@outlook.com
 * desc :
 */
public interface IModel {

    void onPostData(String url, Map<String, String> map, Class clazz, MyCallBack callBack);
    void onGetData(String url,Class clazz,MyCallBack callBack);

}
