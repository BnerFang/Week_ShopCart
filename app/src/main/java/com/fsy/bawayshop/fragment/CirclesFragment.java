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
import android.widget.Toast;

import com.fsy.bawayshop.R;
import com.fsy.bawayshop.adapter.MyCirclesAdapter;
import com.fsy.bawayshop.api.Apis;
import com.fsy.bawayshop.bean.CirclesBean;
import com.fsy.bawayshop.mvp.presenter.IPresenterImplement;
import com.fsy.bawayshop.mvp.view.IView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author : FangShiKang
 * @date : 2018/12/28.
 * email : fangshikang@outlook.com
 * desc :
 */
public class CirclesFragment extends Fragment implements IView {
    @BindView(R.id.circles_rv)
    RecyclerView mCirclesRv;
    private View view;
    private Unbinder unbinder;
    private IPresenterImplement mIPresenterImplement;
    private MyCirclesAdapter mMyCirclesAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_circles, container, false);
        unbinder = ButterKnife.bind(this, view);
        mIPresenterImplement = new IPresenterImplement(this);
        mIPresenterImplement.onGetDatas(Apis.URL_FIND_CIRCLE_LIST_GET, CirclesBean.class);
        mCirclesRv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        return view;
    }

    @Override
    public void onISuccess(Object data) {
        CirclesBean circlesBean = (CirclesBean) data;
        if (circlesBean.getStatus().equals("0000")) {
            List<CirclesBean.ResultBean> result = circlesBean.getResult();
            mMyCirclesAdapter = new MyCirclesAdapter(getContext(), result);
        }
        mCirclesRv.setAdapter(mMyCirclesAdapter);

        mMyCirclesAdapter.setOnClickedListener(new MyCirclesAdapter.onClickedListener() {
            @Override
            public void onChecked(int position) {
                Toast.makeText(getActivity(), "点击了", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onIFailed(String error) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
