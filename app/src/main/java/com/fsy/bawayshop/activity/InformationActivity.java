package com.fsy.bawayshop.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.fsy.bawayshop.R;
import com.fsy.bawayshop.adapter.InFormationAdapter;
import com.fsy.bawayshop.api.Apis;
import com.fsy.bawayshop.bean.InFormationBean;
import com.fsy.bawayshop.mvp.presenter.IPresenterImplement;
import com.fsy.bawayshop.mvp.view.IView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InformationActivity extends AppCompatActivity implements IView {

    @BindView(R.id.information_rv)
    RecyclerView mInformationRv;
    private InFormationAdapter mInFormationAdapter;
    private IPresenterImplement mIPresenterImplement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        ButterKnife.bind(this);
        mIPresenterImplement = new IPresenterImplement(this);
        mInformationRv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mIPresenterImplement.onGetDatas(Apis.URL_QUERYBYID_GET,InFormationBean.class);
    }

    @Override
    public void onISuccess(Object data) {
        InFormationBean bean = (InFormationBean) data;
        if (bean.getStatus().equals("0000")) {
            InFormationBean.ResultBean result = bean.getResult();
            mInFormationAdapter = new InFormationAdapter(this, result);
            mInformationRv.setAdapter(mInFormationAdapter);
        }
    }

    @Override
    public void onIFailed(String error) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mIPresenterImplement != null) {
            mIPresenterImplement = null;
        }
    }
}
