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
import com.fsy.bawayshop.bean.CartBean;
import com.fsy.bawayshop.bean.DetailsBean;
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
    private int mStock;
    private int mCommodityId;
    private Intent mIntent;
    private WebSettings mWebSettings;

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
                mStock = result.getSaleNum();
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
        } else if (data instanceof CartBean) {
            CartBean bean = (CartBean) data;
            if (bean.getStatus().equals("0000")) {
                Toast.makeText(getActivity(), bean.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void onIFailed(String error) {

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
                Map<String, String> map = new HashMap<>();
                map.put(ParamsApis.POST_CART_DATA_KEY, "[{\"commodityId\"+\":\"" + mCommodityId + "\",\"+\"count\"+\":\"" + mStock + "}]");
                mIPresenterImplement.onPutDatas(Apis.URL_SYNC_SHOPPING_CART_PUT, map, CartBean.class);
                break;
            case R.id.dateils_img_buy:
                break;
            case R.id.dateils_layout:
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mIPresenterImplement != null) {
            mIPresenterImplement = null;
        }
    }
}
