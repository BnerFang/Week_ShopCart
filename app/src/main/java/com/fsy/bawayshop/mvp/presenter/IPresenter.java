package com.fsy.bawayshop.mvp.presenter;

import java.util.Map;

/**
 * @author : FangShiKang
 * @date : 2018/12/28.
 * email : fangshikang@outlook.com
 * desc :
 */
public interface IPresenter {
    void onPostDatas(String url, Map<String, String> map, Class clazz);
    void onGetDatas(String url, Class clazz);

}
