package com.fsy.bawayshop.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fsy.bawayshop.R;

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
public class OrderFragment extends Fragment {
    @BindView(R.id.order_all_list)
    ImageView mOrderAllList;
    @BindView(R.id.order_money)
    ImageView mOrderMoney;
    @BindView(R.id.order_receive)
    ImageView mOrderReceive;
    @BindView(R.id.order_comment)
    ImageView mOrderComment;
    @BindView(R.id.order_finish)
    ImageView mOrderFinish;
    private View view;
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);

        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    //点击事件
    @OnClick({R.id.order_all_list, R.id.order_money, R.id.order_receive, R.id.order_comment, R.id.order_finish})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.order_all_list:
                break;
            case R.id.order_money:
                break;
            case R.id.order_receive:
                break;
            case R.id.order_comment:
                break;
            case R.id.order_finish:
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
