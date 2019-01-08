package com.fsy.bawayshop.mvp.model;

import com.fsy.bawayshop.mvp.MyCallBack;
import com.fsy.bawayshop.netWork.RetrofitManager;
import com.google.gson.Gson;


import java.util.Map;

import okhttp3.RequestBody;


/**
 * @author : FangShiKang
 * @date : 2018/12/28.
 * email : fangshikang@outlook.com
 * desc :
 */
public class IModelImplement<T> implements IModel {
    /**
     * postFormBody  请求
     *
     * @param url
     * @param map
     * @param clazz
     * @param callBack
     */
    @Override
    public void onPostData(String url, Map<String, String> map, final Class clazz, final MyCallBack callBack) {
        Map<String, RequestBody> mMap = RetrofitManager.getInstance().generateRequestBody(map);
        RetrofitManager.getInstance().postFormBody(url, mMap, new RetrofitManager.HttpListener() {
            @Override
            public void onSuccess(String data) {
                try {
                    Object o = new Gson().fromJson(data, clazz);
                    if (callBack != null) {
                        callBack.onSuccess(o);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (callBack != null) {
                        callBack.onFailed(e.getMessage());
                    }
                }
            }

            @Override
            public void onFailed(String error) {
                if (callBack != null) {
                    callBack.onFailed(error);
                }
            }
        });
    }

    /**
     * put 请求
     * @param url
     * @param map
     * @param clazz
     * @param callBack
     */
    @Override
    public void onPutData(String url, Map<String, String> map, final Class clazz, final MyCallBack callBack) {
        Map<String ,RequestBody> body = RetrofitManager.getInstance().generateRequestBody(map);
        RetrofitManager.getInstance().put(url, body, new RetrofitManager.HttpListener() {
            @Override
            public void onSuccess(String data) {
                try {
                    Object o = new Gson().fromJson(data, clazz);
                    if (callBack != null) {
                        callBack.onSuccess(o);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (callBack != null) {
                        callBack.onFailed(e.getMessage());
                    }
                }
            }

            @Override
            public void onFailed(String error) {
                if (callBack != null) {
                    callBack.onFailed(error);
                }
            }
        });
    }

    /**
     * post 请求
     * @param url
     * @param map
     * @param clazz
     * @param callBack
     */
    @Override
    public void onPostDatas(String url, Map<String, String> map, final Class clazz, final MyCallBack callBack) {
        RetrofitManager.getInstance().post(url, map, new RetrofitManager.HttpListener() {
            @Override
            public void onSuccess(String data) {
                try {
                    Object o = new Gson().fromJson(data, clazz);
                    if (callBack != null) {
                        callBack.onSuccess(o);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (callBack != null) {
                        callBack.onFailed(e.getMessage());
                    }
                }
            }

            @Override
            public void onFailed(String error) {
                if (callBack != null) {
                    callBack.onFailed(error);
                }
            }
        });
    }

    /**
     * get  请求
     * @param url
     * @param clazz
     * @param callBack
     */
    @Override
    public void onGetData(String url, final Class clazz, final MyCallBack callBack) {
        RetrofitManager.getInstance().get(url, new RetrofitManager.HttpListener() {
            @Override
            public void onSuccess(String data) {
                try {
                    Object o = new Gson().fromJson(data, clazz);
                    if (callBack != null) {
                        callBack.onSuccess(o);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (callBack != null) {
                        callBack.onFailed(e.getMessage());
                    }
                }
            }

            @Override
            public void onFailed(String error) {
                if (callBack != null) {
                    callBack.onFailed(error);
                }
            }
        });
    }
}
