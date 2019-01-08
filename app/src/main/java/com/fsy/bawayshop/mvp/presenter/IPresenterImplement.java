package com.fsy.bawayshop.mvp.presenter;

import com.fsy.bawayshop.mvp.MyCallBack;
import com.fsy.bawayshop.mvp.model.IModelImplement;
import com.fsy.bawayshop.mvp.view.IView;

import java.util.Map;

/**
 * @author : FangShiKang
 * @date : 2018/12/28.
 * email : fangshikang@outlook.com
 * desc :
 */
public class IPresenterImplement implements IPresenter {

    private IView mIView;
    private IModelImplement mIModelImplement;

    public IPresenterImplement(IView IView) {
        mIView = IView;
        mIModelImplement = new IModelImplement();
    }

    /**
     * postFormBody  请求
     * @param url
     * @param map
     * @param clazz
     */
    @Override
    public void onPostDatas(String url, Map<String, String> map, Class clazz) {

        mIModelImplement.onPostData(url, map, clazz, new MyCallBack() {
            @Override
            public void onSuccess(Object data) {
                mIView.onISuccess(data);
            }

            @Override
            public void onFailed(String error) {
                mIView.onIFailed(error);
            }
        });
    }

    /**
     * put请求
     * @param url
     * @param map
     * @param clazz
     */
    @Override
    public void onPutDatas(String url, Map<String, String> map, Class clazz) {
        mIModelImplement.onPutData(url, map, clazz, new MyCallBack() {
            @Override
            public void onSuccess(Object data) {
                mIView.onISuccess(data);
            }

            @Override
            public void onFailed(String error) {
                mIView.onIFailed(error);
            }
        });
    }


    /**
     * post  请求
     * @param url
     * @param map
     * @param clazz
     */
    @Override
    public void onPostDatass(String url, Map<String, String> map, Class clazz) {
        mIModelImplement.onPostDatas(url, map, clazz, new MyCallBack() {
            @Override
            public void onSuccess(Object data) {
                mIView.onISuccess(data);
            }

            @Override
            public void onFailed(String error) {
                mIView.onIFailed(error);
            }
        });
    }

    /**
     * get 请求
     * @param url
     * @param clazz
     */
    @Override
    public void onGetDatas(String url, Class clazz) {

        mIModelImplement.onGetData(url, clazz, new MyCallBack() {
            @Override
            public void onSuccess(Object data) {
                mIView.onISuccess(data);
            }

            @Override
            public void onFailed(String error) {
                mIView.onIFailed(error);
            }
        });
    }
}
