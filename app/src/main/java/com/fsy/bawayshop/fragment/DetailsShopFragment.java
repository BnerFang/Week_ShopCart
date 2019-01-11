package com.fsy.bawayshop.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.fsy.bawayshop.R;
import com.fsy.bawayshop.adapter.ShopDetailsAdapter;
import com.fsy.bawayshop.api.Apis;
import com.fsy.bawayshop.api.ParamsApis;
import com.fsy.bawayshop.bean.AddCartBean;
import com.fsy.bawayshop.bean.CartBean;
import com.fsy.bawayshop.bean.DetailsBean;
import com.fsy.bawayshop.bean.QueryCartBean;
import com.fsy.bawayshop.mvp.presenter.IPresenterImplement;
import com.fsy.bawayshop.mvp.view.IView;
import com.recker.flybanner.FlyBanner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author : FangShiKang
 * @date : 2019/01/05.
 * email : fangshikang@outlook.com
 * desc :       商品详情
 */
public class DetailsShopFragment extends Fragment implements IView {

    @BindView(R.id.dateils_rv)
    RecyclerView mDateilsRv;
    @BindView(R.id.details_banner)
    FlyBanner mDetailsBanner;
    @BindView(R.id.dateils_img_cart)
    ImageView mDateilsImgCart;
    @BindView(R.id.dateils_img_buy)
    ImageView mDateilsImgBuy;
    @BindView(R.id.dateils_webview)
    WebView mDateilsWebview;
    @BindView(R.id.dateils_rv_pj)
    RecyclerView mDateilsRvPj;
    @BindView(R.id.dateils_layout)
    RelativeLayout mDateilsLayout;
    private View view;
    private Unbinder unbinder;
    private ShopDetailsAdapter mShopDetailsAdapter;
    private IPresenterImplement mIPresenterImplement;
    private Intent mIntent;
    private WebSettings mWebSettings;
    private int mCommodityId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details_shop, container, false);
        unbinder = ButterKnife.bind(this, view);
        mIntent = getActivity().getIntent();
        String commodityId = mIntent.getStringExtra("commodityId");
        mIPresenterImplement = new IPresenterImplement(this);
        mIPresenterImplement.onGetDatas(Apis.URL_FIND_COMMODITY_DETAILS_BYID_GET + commodityId, DetailsBean.class);
        return view;
    }

    @Override
    public void onISuccess(Object data) {
        if (data instanceof DetailsBean) {
            DetailsBean bean = (DetailsBean) data;
            if (bean.getStatus().equals("0000")) {
                DetailsBean.ResultBean result = bean.getResult();
                List<String> list = new ArrayList<>();
                String[] split = result.getPicture().split("\\,");
                for (int i = 0; i < split.length; i++) {
                    list.add(split[i]);
                }
                mCommodityId = result.getCommodityId();
                mShopDetailsAdapter = new ShopDetailsAdapter(getActivity(), result);
                //WebView  展示
                mWebSettings = mDateilsWebview.getSettings();
                // 缩放至屏幕的大小
                mWebSettings.setLoadWithOverviewMode(true);
                mWebSettings.setUseWideViewPort(true);
                //支持缩放
                mWebSettings.setSupportZoom(true);
                mDateilsWebview.loadDataWithBaseURL(null, result.getDetails(), "text/html", "utf-8", null);
                //设置不用系统浏览器打开,直接显示在当前Webview
                mDateilsWebview.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return true;
                    }
                });
                mDateilsRv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                mDateilsRv.setAdapter(mShopDetailsAdapter);
                mDetailsBanner.setImagesUrl(list);
            }
        } else if (data instanceof CartBean) {//添加购物车
            CartBean bean = (CartBean) data;
            if (bean.getStatus().equals("0000")) {
                Toast.makeText(getActivity(), bean.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else if (data instanceof QueryCartBean) {//查询购物车
            QueryCartBean queryCartBean = (QueryCartBean) data;
            if (queryCartBean.getStatus().equals("0000")) {
                List<QueryCartBean.ResultBean> beanResult = queryCartBean.getResult();
                List<AddCartBean> list = new ArrayList<>();
                if (beanResult != null) {
                    for (int i = 0; i < beanResult.size(); i++) {
                        list.add(new AddCartBean(beanResult.get(i).getCommodityId(), beanResult.get(i).getCount()));
                    }
                    getAddCart(list);
                } else {
                    getAddCart(list);
                    Toast.makeText(getActivity(), "失败", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void getAddCart(List<AddCartBean> list) {
        String str = "[";
        for (int i = 0; i < list.size(); i++) {
            if (mCommodityId == list.get(i).getCommodityId()) {
                int count = list.get(i).getCount();
                count++;
                list.get(i).setCount(count);
                break;
            } else if (i == list.size() - 1) {
                list.add(new AddCartBean(mCommodityId, 1));
                break;
            }
        }
        for (AddCartBean addBean : list) {
            str += "{\"commodityId\":" + addBean.getCommodityId() + ",\"count\":" + addBean.getCount() + "},";
        }
        String substring = str.substring(0, str.length() - 1);
        substring += "]";
        //添加购物车
        Map<String, String> map = new HashMap<>();
        map.put(ParamsApis.POST_CART_DATA_KEY, substring);
        mIPresenterImplement.onPutDatas(Apis.URL_SYNC_SHOPPING_CART_PUT, map, CartBean.class);
    }


    @Override
    public void onIFailed(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    //点击事件
    @OnClick({R.id.dateils_img_cart, R.id.dateils_img_buy, R.id.dateils_layout})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.dateils_img_cart:
                /*Map<String, String> map = new HashMap<>();
                map.put(ParamsApis.POST_CART_DATA_KEY, "[{commodityId:" + mCommodityId + ",count:" + 1 + "},]");
                mIPresenterImplement.onPutDatas(Apis.URL_SYNC_SHOPPING_CART_PUT, map, CartBean.class);*/
                //当点击添加购物车时,先查询购物车里的数据
                mIPresenterImplement.onGetDatas(Apis.URL_FIND_SHOPPING_CART_GET, QueryCartBean.class);
                break;
            case R.id.dateils_img_buy:
                break;
            case R.id.dateils_layout:
                break;
        }
    }

    //防止内存泄露
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mIPresenterImplement != null) {
            mIPresenterImplement = null;
        }
    }
}
