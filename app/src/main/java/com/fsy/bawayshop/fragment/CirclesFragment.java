package com.fsy.bawayshop.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fsy.bawayshop.MainActivity;
import com.fsy.bawayshop.R;
import com.fsy.bawayshop.adapter.MyCirclesAdapter;
import com.fsy.bawayshop.api.Apis;
import com.fsy.bawayshop.api.ParamsApis;
import com.fsy.bawayshop.bean.CircleDZBean;
import com.fsy.bawayshop.bean.CirclesBean;
import com.fsy.bawayshop.mvp.presenter.IPresenterImplement;
import com.fsy.bawayshop.mvp.view.IView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author : FangShiKang
 * @date : 2018/12/28.
 * email : fangshikang@outlook.com
 * desc :       圈子页面
 */
public class CirclesFragment extends Fragment implements IView {
    @BindView(R.id.circles_rv)
    XRecyclerView mCirclesRv;
    private View view;
    private Unbinder unbinder;
    private IPresenterImplement mIPresenterImplement;
    private MyCirclesAdapter mMyCirclesAdapter;
    private int count = 5;
    private int pager = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_circles, container, false);
        unbinder = ButterKnife.bind(this, view);
        mIPresenterImplement = new IPresenterImplement(this);
        mIPresenterImplement.onGetDatas(Apis.URL_FIND_CIRCLE_LIST_GET + "?page=" + pager + "&count=" + count, CirclesBean.class);
        mCirclesRv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        return view;
    }

    //成功
    @Override
    public void onISuccess(Object data) {
        if (data instanceof CirclesBean) {
            CirclesBean circlesBean = (CirclesBean) data;
            if (circlesBean.getStatus().equals("0000")) {
                final List<CirclesBean.ResultBean> result = circlesBean.getResult();
                mMyCirclesAdapter = new MyCirclesAdapter(getContext(), result);
                mCirclesRv.setAdapter(mMyCirclesAdapter);
                mCirclesRv.setPullRefreshEnabled(true);
                mCirclesRv.setLoadingMoreEnabled(true);
                mCirclesRv.setLoadingListener(new XRecyclerView.LoadingListener() {
                    @Override
                    public void onRefresh() {
                        pager = 1;
                        mIPresenterImplement.onGetDatas(Apis.URL_FIND_CIRCLE_LIST_GET + "?page=" + pager + "&count=" + count, CirclesBean.class);
                        mCirclesRv.postDelayed(new Runnable() {
                            @Override
                            public void run() {//设置刷新时间
                                mCirclesRv.refreshComplete();
                            }
                        }, 1500);
                    }

                    @Override
                    public void onLoadMore() {
                        if (pager > 2) {
                            Toast.makeText(getActivity(), "没有更多数据了！！！", Toast.LENGTH_SHORT).show();
                        } else {
                            pager++;
                            mIPresenterImplement.onGetDatas(Apis.URL_FIND_CIRCLE_LIST_GET + "?page=" + pager + "&count=" + count, CirclesBean.class);
                            mCirclesRv.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mCirclesRv.refreshComplete();
                                }
                            }, 1500);
                        }
                    }
                });
                //圈子点赞
                mMyCirclesAdapter.setOnClickedListener(new MyCirclesAdapter.onClickedListener() {
                    @Override
                    public void onChecked(int position) {
                        Map<String, String> map = new HashMap<>();
                        map.put(ParamsApis.POST_DZ_KEY, String.valueOf(result.get(position).getId()));
                        mIPresenterImplement.onPostDatas(Apis.URL_ADD_CIRCLE_GREAT_POST, map, CircleDZBean.class);
                    }
                });
            }

        } else if (data instanceof CircleDZBean) {
            CircleDZBean bean = (CircleDZBean) data;
            if (bean.getStatus().equals("0000")) {
                Toast.makeText(getActivity(), bean.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onIFailed(String error) {

    }


    //    进行获取焦点，点击返回键返回上一级
    @Override
    public void onResume() {
        super.onResume();

        getFours();

    }
    //    进行获取焦点
    long exitTime = 0;
    private void getFours() {
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
                    if ((System.currentTimeMillis() - exitTime) > 2000){
                       /* Toast.makeText(getActivity(),"再按一次就退出了哟",Toast.LENGTH_SHORT).show();
                        exitTime = System.currentTimeMillis();*/
                        startActivity(new Intent(getActivity(), MainActivity.class));
                    }else {
                        getActivity().finish();
                        System.exit(0);
                    }
                    return true;
                }
                return false;
            }
        });
    }//    进行获取焦点


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
