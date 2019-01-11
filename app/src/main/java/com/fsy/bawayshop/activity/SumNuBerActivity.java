package com.fsy.bawayshop.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fsy.bawayshop.R;
import com.fsy.bawayshop.adapter.CartShopOrderAdapter;
import com.fsy.bawayshop.api.Apis;
import com.fsy.bawayshop.api.ParamsApis;
import com.fsy.bawayshop.bean.CreateOrderBean;
import com.fsy.bawayshop.bean.QueryCartBean;
import com.fsy.bawayshop.mvp.presenter.IPresenterImplement;
import com.fsy.bawayshop.mvp.view.IView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SumNuBerActivity extends AppCompatActivity implements IView {

    @BindView(R.id.sum_btn_add_ress)
    Button mSumBtnAddRess;
    @BindView(R.id.sum_rv)
    RecyclerView mSumRv;
    @BindView(R.id.sum_count)
    TextView mSumCount;
    @BindView(R.id.sum_price)
    TextView mSumPrice;
    @BindView(R.id.sum_order)
    TextView mSumOrder;
    private int num = 0;
    private double price = 0.00;
    private CartShopOrderAdapter mCartShopOrderAdapter;
    private IPresenterImplement mIPresenterImplement;
    private int mId;
    private int mCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sum_nu_ber);
        ButterKnife.bind(this);
        //注册EventBus
        EventBus.getDefault().register(this);
        mIPresenterImplement = new IPresenterImplement(this);
        mSumRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    //EventBus  接收
    @Subscribe(sticky = true)
    public void onTouch(List<QueryCartBean.ResultBean> beanList) {
        List<String> list = new ArrayList<>();

        for (int i = 0; i < beanList.size(); i++) {
            mId = beanList.get(i).getCommodityId();
            mCount = beanList.get(i).getCount();
            num += beanList.get(i).getCount();
            price += beanList.get(i).getPrice() * beanList.get(i).getCount();
            mSumCount.setText(num + "");
            mSumPrice.setText(price + "");
        }
        //初始化适配器
        mCartShopOrderAdapter = new CartShopOrderAdapter(this, beanList);
        mSumRv.setAdapter(mCartShopOrderAdapter);
    }

    //点击事件
    @OnClick({R.id.sum_btn_add_ress, R.id.sum_order})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.sum_btn_add_ress:
                break;
            case R.id.sum_order:

                break;
        }
    }

    @Override
    public void onISuccess(Object data) {

    }

    @Override
    public void onIFailed(String error) {

    }

    /**
     * 解注册（为防止内存泄漏）
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
