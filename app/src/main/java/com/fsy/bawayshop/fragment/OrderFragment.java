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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.fsy.bawayshop.R;
import com.fsy.bawayshop.adapter.MyCoOrderAdapter;
import com.fsy.bawayshop.adapter.MyFOrderAdapter;
import com.fsy.bawayshop.adapter.MyOrderAllAdapter;
import com.fsy.bawayshop.adapter.MyReOrderAdapter;
import com.fsy.bawayshop.api.Apis;
import com.fsy.bawayshop.bean.OrderBean;
import com.fsy.bawayshop.mvp.presenter.IPresenterImplement;
import com.fsy.bawayshop.mvp.view.IView;

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
public class OrderFragment extends Fragment implements IView {
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
    @BindView(R.id.order_rv)
    RecyclerView mOrderRv;
    @BindView(R.id.order_money_rv)
    RecyclerView mOrderMoneyRv;
    @BindView(R.id.order_receive_rv)
    RecyclerView mOrderReceiveRv;
    @BindView(R.id.order_comment_rv)
    RecyclerView mOrderCommentRv;
    @BindView(R.id.order_finish_rv)
    RecyclerView mOrderFinishRv;
    @BindView(R.id.rv)
    RelativeLayout mRelativeLayoutRv;
    @BindView(R.id.money_rv)
    RelativeLayout mRelativeLayoutMoneyRv;
    @BindView(R.id.receive_rv)
    RelativeLayout mRelativeLayoutReceiveRv;
    @BindView(R.id.comment_rv)
    RelativeLayout mRelativeLayoutCommentRv;
    @BindView(R.id.finish_rv)
    RelativeLayout mRelativeLayoutFinishRv;
    private View view;
    private Unbinder unbinder;
    private IPresenterImplement mIPresenterImplement;
    private MyOrderAllAdapter mMyOrderAllAdapter;
    private int position = 0;
    private int pager = 1;
    private int count = 5;
    private MyReOrderAdapter mMyReOrderAdapter;
    private MyCoOrderAdapter mMyCoOrderAdapter;
    private MyFOrderAdapter mMyFOrderAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        unbinder = ButterKnife.bind(this, view);
        mIPresenterImplement = new IPresenterImplement(this);
        mIPresenterImplement.onGetDatas(Apis.URL_ORDER_LIST_BY_STATUS_GET + position + "&page=" + pager + "&count=" + count, OrderBean.class);
        mOrderRv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        return view;
    }


    @Override
    public void onISuccess(Object data) {
        if (data instanceof OrderBean) {
            OrderBean orderBean = (OrderBean) data;
            if (orderBean.getStatus().equals("0000")) {
                List<OrderBean.OrderListBean> orderList = orderBean.getOrderList();
                for (int i = 0; i < orderList.size(); i++) {
                    mMyOrderAllAdapter = new MyOrderAllAdapter(getContext(), orderList);
                    mMyReOrderAdapter = new MyReOrderAdapter(getActivity(), orderList);
                    mMyCoOrderAdapter = new MyCoOrderAdapter(getActivity(), orderList);
                    mMyFOrderAdapter = new MyFOrderAdapter(getActivity(), orderList);
                    mOrderRv.setAdapter(mMyOrderAllAdapter);
                }
            } else {
                Toast.makeText(getActivity(), orderBean.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void onIFailed(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }

    //点击事件
    @OnClick({R.id.order_all_list, R.id.order_money, R.id.order_receive, R.id.order_comment, R.id.order_finish, R.id.order_rv})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.order_all_list:
                mRelativeLayoutRv.setVisibility(View.VISIBLE);
                mRelativeLayoutMoneyRv.setVisibility(View.GONE);
                mRelativeLayoutReceiveRv.setVisibility(View.GONE);
                mRelativeLayoutCommentRv.setVisibility(View.GONE);
                mRelativeLayoutFinishRv.setVisibility(View.GONE);
                mIPresenterImplement.onGetDatas(Apis.URL_ORDER_LIST_BY_STATUS_GET + position + "&page=" + pager + "&count=" + count, OrderBean.class);
                break;
            case R.id.order_money:
                mRelativeLayoutRv.setVisibility(View.GONE);
                mRelativeLayoutMoneyRv.setVisibility(View.VISIBLE);
                mRelativeLayoutReceiveRv.setVisibility(View.GONE);
                mRelativeLayoutCommentRv.setVisibility(View.GONE);
                mRelativeLayoutFinishRv.setVisibility(View.GONE);
                mOrderMoneyRv.setAdapter(mMyOrderAllAdapter);
                mOrderMoneyRv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                mMyOrderAllAdapter.notifyDataSetChanged();
                mIPresenterImplement.onGetDatas(Apis.URL_ORDER_LIST_BY_STATUS_GET + 1 + "&page=" + pager + "&count=" + count, OrderBean.class);
                break;
            case R.id.order_receive:
                mRelativeLayoutRv.setVisibility(View.GONE);
                mRelativeLayoutMoneyRv.setVisibility(View.GONE);
                mRelativeLayoutReceiveRv.setVisibility(View.VISIBLE);
                mRelativeLayoutCommentRv.setVisibility(View.GONE);
                mRelativeLayoutFinishRv.setVisibility(View.GONE);
                mOrderReceiveRv.setAdapter(mMyReOrderAdapter);
                mOrderReceiveRv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                mMyReOrderAdapter.notifyDataSetChanged();
                mIPresenterImplement.onGetDatas(Apis.URL_ORDER_LIST_BY_STATUS_GET + 2 + "&page=" + pager + "&count=" + count, OrderBean.class);
                break;
            case R.id.order_comment:
                mRelativeLayoutRv.setVisibility(View.GONE);
                mRelativeLayoutMoneyRv.setVisibility(View.GONE);
                mRelativeLayoutReceiveRv.setVisibility(View.GONE);
                mRelativeLayoutCommentRv.setVisibility(View.VISIBLE);
                mRelativeLayoutFinishRv.setVisibility(View.GONE);
                mOrderCommentRv.setAdapter(mMyCoOrderAdapter);
                mOrderCommentRv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                mMyCoOrderAdapter.notifyDataSetChanged();
                mIPresenterImplement.onGetDatas(Apis.URL_ORDER_LIST_BY_STATUS_GET + 3 + "&page=" + pager + "&count=" + count, OrderBean.class);
                break;
            case R.id.order_finish:
                mRelativeLayoutRv.setVisibility(View.GONE);
                mRelativeLayoutMoneyRv.setVisibility(View.GONE);
                mRelativeLayoutReceiveRv.setVisibility(View.GONE);
                mRelativeLayoutCommentRv.setVisibility(View.GONE);
                mRelativeLayoutFinishRv.setVisibility(View.VISIBLE);
                mOrderFinishRv.setAdapter(mMyFOrderAdapter);
                mOrderFinishRv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                mMyFOrderAdapter.notifyDataSetChanged();
                mIPresenterImplement.onGetDatas(Apis.URL_ORDER_LIST_BY_STATUS_GET + 9 + "&page=" + pager + "&count=" + count, OrderBean.class);
                break;
        }
    }

    /**
     * 防止内存泄露
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (mIPresenterImplement != null) {
            mIPresenterImplement = null;
        }
    }

}
