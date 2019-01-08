package com.fsy.bawayshop.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fsy.bawayshop.R;
import com.fsy.bawayshop.adapter.CartShopAdapter;
import com.fsy.bawayshop.api.Apis;
import com.fsy.bawayshop.bean.CartBean;
import com.fsy.bawayshop.bean.QueryCartBean;
import com.fsy.bawayshop.mvp.presenter.IPresenterImplement;
import com.fsy.bawayshop.mvp.view.IView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author : FangShiKang
 * @date : 2018/12/28.
 * email : fangshikang@outlook.com
 * desc :
 */
public class ShoppingCartFragment extends Fragment implements IView {
    @BindView(R.id.cart_rv)
    RecyclerView mCartRv;
    @BindView(R.id.cart_box)
    CheckBox mCartBox;
    @BindView(R.id.cart_all)
    TextView mCartAll;
    @BindView(R.id.cart_sumNuber)
    Button mCartSumNuber;
    @BindView(R.id.cart_layout)
    RelativeLayout mCartLayout;
    private View view;
    private Unbinder unbinder;
    private CartShopAdapter mCartShopAdapter;
    private IPresenterImplement mIPresenterImplement;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopping_cart, container, false);
        unbinder = ButterKnife.bind(this, view);
        mCartRv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mIPresenterImplement = new IPresenterImplement(this);
        mIPresenterImplement.onGetDatas(Apis.URL_FIND_SHOPPING_CART_GET, QueryCartBean.class);
        return view;
    }

    //点击事件
    @OnClick({R.id.cart_box, R.id.cart_sumNuber})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.cart_box:
                break;
            case R.id.cart_sumNuber:
                break;
        }
    }

    @Override
    public void onISuccess(Object data) {
        if (data instanceof QueryCartBean) {
            QueryCartBean bean = (QueryCartBean) data;
            if (bean.getStatus().equals("0000")) {
                List<QueryCartBean.ResultBean> result = bean.getResult();
                mCartShopAdapter = new CartShopAdapter(getActivity(), result);
                mCartRv.setAdapter(mCartShopAdapter);
                mCartShopAdapter.notifyDataSetChanged();
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
        if (mIPresenterImplement != null) {
            mIPresenterImplement = null;
        }
    }
}
