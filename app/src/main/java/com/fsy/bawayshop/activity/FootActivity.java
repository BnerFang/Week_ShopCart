package com.fsy.bawayshop.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.Toast;

import com.fsy.bawayshop.R;
import com.fsy.bawayshop.adapter.FootAdapter;
import com.fsy.bawayshop.api.Apis;
import com.fsy.bawayshop.bean.CirclesBean;
import com.fsy.bawayshop.bean.FootBean;
import com.fsy.bawayshop.mvp.presenter.IPresenterImplement;
import com.fsy.bawayshop.mvp.view.IView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FootActivity extends AppCompatActivity implements IView {

    @BindView(R.id.foot_rv)
    XRecyclerView mFootRv;
    private IPresenterImplement mIPresenterImplement;
    private int count = 6;
    private int pager = 1;
    private FootAdapter mFootAdapter;
    private List<FootBean.ResultBean> mResultBeans = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foot);
        ButterKnife.bind(this);
        mIPresenterImplement = new IPresenterImplement(this);
        mIPresenterImplement.onGetDatas(Apis.URL_BROWSE_LIST_GET + "?page=" + pager + "&count=" + count, FootBean.class);
    }

    //成功
    @Override
    public void onISuccess(Object data) {
        FootBean footBean = (FootBean) data;
        if (footBean.getStatus().equals("0000")) {
            final List<FootBean.ResultBean> result = footBean.getResult();
            mFootRv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
            mFootAdapter = new FootAdapter(this, result);
            mFootRv.setAdapter(mFootAdapter);
            mResultBeans.addAll(result);
            mFootRv.setLoadingMoreEnabled(true);
            mFootRv.setPullRefreshEnabled(true);
            mFootRv.setLoadingListener(new XRecyclerView.LoadingListener() {
                @Override
                public void onRefresh() {
                    pager = 1;
                    mFootAdapter.setList(mResultBeans);
                    mIPresenterImplement.onGetDatas(Apis.URL_BROWSE_LIST_GET + "?page=" + pager + "&count=" + count, FootBean.class);
                    mFootRv.postDelayed(new Runnable() {
                        @Override
                        public void run() {//设置刷新时间
                            mFootRv.refreshComplete();
                        }
                    }, 1500);
                }

                @Override
                public void onLoadMore() {
                    if (pager > 2) {
                        Toast.makeText(FootActivity.this, "没有更多数据了！！！", Toast.LENGTH_SHORT).show();
                    } else {
                        pager++;
                        mIPresenterImplement.onGetDatas(Apis.URL_BROWSE_LIST_GET + "?page=" + pager + "&count=" + count, FootBean.class);
                        mFootRv.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mFootRv.refreshComplete();
                            }
                        }, 1500);
                    }
                }
            });
        }
    }

    @Override
    public void onIFailed(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mIPresenterImplement != null) {
            mIPresenterImplement = null;
        }
    }
}
