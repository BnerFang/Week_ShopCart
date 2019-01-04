package com.fsy.bawayshop.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.fsy.bawayshop.R;
import com.fsy.bawayshop.adapter.WalletAdapter;
import com.fsy.bawayshop.api.Apis;
import com.fsy.bawayshop.bean.WalletBean;
import com.fsy.bawayshop.mvp.presenter.IPresenterImplement;
import com.fsy.bawayshop.mvp.view.IView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WalletActivity extends AppCompatActivity implements IView {

    /*@BindView(R.id.wallet_rv)
    RecyclerView mWalletRv;*/
    private WalletAdapter mWalletAdapter;
    private IPresenterImplement mIPresenterImplement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        ButterKnife.bind(this);
        mIPresenterImplement = new IPresenterImplement(this);
        mIPresenterImplement.onGetDatas(Apis.URL_FIND_USER_WALLET_GET + 1 + "&count=" + 1, WalletBean.class);
        //mWalletRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void onISuccess(Object data) {
        WalletBean bean = (WalletBean) data;
        if (bean.getStatus().equals("0000")) {
            WalletBean.ResultBean result = bean.getResult();
            mWalletAdapter = new WalletAdapter(this, result);
            //mWalletRv.setAdapter(mWalletAdapter);
        }
    }

    @Override
    public void onIFailed(String error) {

    }
}
