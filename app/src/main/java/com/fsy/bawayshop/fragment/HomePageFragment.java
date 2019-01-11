package com.fsy.bawayshop.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fsy.bawayshop.R;
import com.fsy.bawayshop.activity.DetailsActivity;
import com.fsy.bawayshop.adapter.MyHomePagerLiveAdapter;
import com.fsy.bawayshop.adapter.MyHomePagerNewAdapter;
import com.fsy.bawayshop.adapter.MyHomePagerVogueAdapter;
import com.fsy.bawayshop.adapter.MyPopupAdapter;
import com.fsy.bawayshop.adapter.MyPopupsAdapter;
import com.fsy.bawayshop.adapter.MySearchAdapter;
import com.fsy.bawayshop.adapter.SeachGoodsAdapter;
import com.fsy.bawayshop.api.Apis;
import com.fsy.bawayshop.bean.BannerBeans;
import com.fsy.bawayshop.bean.PopupBean;
import com.fsy.bawayshop.bean.PopupBeans;
import com.fsy.bawayshop.bean.SeachGoodsBean;
import com.fsy.bawayshop.bean.SearchBean;
import com.fsy.bawayshop.bean.ShowBean;
import com.fsy.bawayshop.mvp.presenter.IPresenterImplement;
import com.fsy.bawayshop.mvp.view.IView;
import com.fsy.bawayshop.netWork.OkHttpUtil;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.stx.xhb.xbanner.XBanner;
import com.stx.xhb.xbanner.transformers.Transformer;

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
 * desc :   首页 fragment
 */
public class HomePageFragment extends Fragment implements IView {

    private static final String TAG = "HomePageFragment";
    @BindView(R.id.home_page_sort)
    ImageView mHomePageSort;
    @BindView(R.id.home_page_search)
    ImageView mHomePageSearch;
    @BindView(R.id.home_page_rv_new)
    RecyclerView mHomePageRvNew;
    @BindView(R.id.home_page_rv_vogue)
    RecyclerView mHomePageRvVogue;
    @BindView(R.id.home_page_rv_live)
    RecyclerView mHomePageRvLive;
    @BindView(R.id.home_page_vp)
    XBanner mHomePageVp;
    @BindView(R.id.ed_txt)
    EditText mEdTxt;
    @BindView(R.id.ed_btn_search)
    TextView mEdBtnSearch;
    @BindView(R.id.ed_search)
    RelativeLayout mEdSearch;
    @BindView(R.id.search_x_rv)
    XRecyclerView mSearchXRv;
    @BindView(R.id.s_rl)
    RelativeLayout mSRl;
    @BindView(R.id.sl)
    ScrollView mSl;
    @BindView(R.id.search_rv)
    RecyclerView mSearchRv;
    @BindView(R.id.seach_rl)
    RelativeLayout mSeachRl;
    private View view;
    private Unbinder unbinder;
    private List<String> mListImgUrl;
    private MyHomePagerNewAdapter mMyHomePagerNewAdapter;
    private List<ShowBean.ResultBean.MlssBean.CommodityListBeanXX> mList;
    private MyHomePagerVogueAdapter mMyHomePagerVogueAdapter;
    private MyHomePagerLiveAdapter mMyHomePagerLiveAdapter;
    private boolean isChecked = false;
    private IPresenterImplement mIPresenterImplement;
    private MyPopupAdapter mMyPopupAdapter;
    private RecyclerView mMRlRv, mMRlRvr;
    private MyPopupsAdapter mMyPopupsAdapter;
    private int mId = 1001002;
    private String mEdTxts;
    private MySearchAdapter mMySearchAdapter;
    private int pagers = 1;
    private int count = 10;
    private SeachGoodsAdapter mSeachGoodsAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);
        unbinder = ButterKnife.bind(this, view);
        mHomePageRvNew.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        mHomePageRvVogue.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mHomePageRvLive.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        //创建  List 集合,为存储图片做铺垫
        mListImgUrl = new ArrayList<>();
        //初始化数据
        new OkHttpUtil().OkHttpGet(Apis.URL_BANNER_SHOW_GET).setOkHttpListener(new OkHttpUtil.OkHttpListener() {
            @Override
            public void OkHttpSuccess(String data) {
                Gson gson = new Gson();
                BannerBeans beans = gson.fromJson(data, BannerBeans.class);
                for (int i = 0; i < beans.getResult().size(); i++) {//循环遍历数据
                    //像集合里添加数据
                    mListImgUrl.add(beans.getResult().get(i).getImageUrl());
                    //初始化XBanner
                    initData();
                }
            }

            @Override
            public void OkHttpError(String error) {
                Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
            }
        });
        //初始化  Recyclerview  数据         热销新品
        initRVNewData();
        //初始化  Recyclerview  数据         魔力时尚
        initRVVogueData();
        //初始化  Recyclerview  数据         品质生活
        initRVLive();
        return view;
    }

    //初始化  Recyclerview  数据         品质生活
    private void initRVLive() {
        new OkHttpUtil().OkHttpGet(Apis.URL_SHOW_GET).setOkHttpListener(new OkHttpUtil.OkHttpListener() {
            @Override
            public void OkHttpSuccess(String data) {
                Gson gson = new Gson();
                ShowBean showBean = gson.fromJson(data, ShowBean.class);
                if (showBean.getStatus().equals("0000")) {
                    List<ShowBean.ResultBean.PzshBean> pzsh = showBean.getResult().getPzsh();
                    for (int i = 0; i < pzsh.size(); i++) {
                        final List<ShowBean.ResultBean.PzshBean.CommodityListBeanX> list = pzsh.get(i).getCommodityList();
                        mMyHomePagerLiveAdapter = new MyHomePagerLiveAdapter(getActivity(), list);
                        mHomePageRvLive.setAdapter(mMyHomePagerLiveAdapter);
                        mMyHomePagerLiveAdapter.setOnClickedListener(new MyHomePagerLiveAdapter.onClickedListener() {
                            @Override
                            public void onChecked(int position) {
                                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("commodityId", list.get(position).getCommodityId() + "");
                                intent.putExtras(bundle);
                                getActivity().startActivity(intent);
                            }
                        });
                    }
                }
            }

            @Override
            public void OkHttpError(String error) {

            }
        });
    }

    //初始化  Recyclerview  数据         魔力时尚
    private void initRVVogueData() {
        new OkHttpUtil().OkHttpGet(Apis.URL_SHOW_GET).setOkHttpListener(new OkHttpUtil.OkHttpListener() {
            @Override
            public void OkHttpSuccess(String data) {
                Gson gson = new Gson();
                ShowBean showBean = gson.fromJson(data, ShowBean.class);
                if (showBean.getStatus().equals("0000")) {
                    List<ShowBean.ResultBean.MlssBean> mlssBeans = showBean.getResult().getMlss();
                    for (int i = 0; i < mlssBeans.size(); i++) {
                        mList = mlssBeans.get(i).getCommodityList();
                        mMyHomePagerVogueAdapter = new MyHomePagerVogueAdapter(getActivity(), mList);
                        mHomePageRvVogue.setAdapter(mMyHomePagerVogueAdapter);
                        mMyHomePagerVogueAdapter.setOnClickedListener(new MyHomePagerVogueAdapter.onClickedListener() {
                            @Override
                            public void onChecked(int position) {
                                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("commodityId", mList.get(position).getCommodityId() + "");
                                intent.putExtras(bundle);
                                getActivity().startActivity(intent);
                            }
                        });
                    }

                }
            }

            @Override
            public void OkHttpError(String error) {

            }
        });
    }

    //初始化  Recyclerview  数据      热销新品
    private void initRVNewData() {
        new OkHttpUtil().OkHttpGet(Apis.URL_SHOW_GET).setOkHttpListener(new OkHttpUtil.OkHttpListener() {
            @Override
            public void OkHttpSuccess(String data) {
                Gson gson = new Gson();
                final ShowBean showBean = gson.fromJson(data, ShowBean.class);
                if (showBean.getStatus().equals("0000")) {
                    List<ShowBean.ResultBean.RxxpBean> rxxp = showBean.getResult().getRxxp();
                    for (int i = 0; i < rxxp.size(); i++) {
                        final List<ShowBean.ResultBean.RxxpBean.CommodityListBean> list = rxxp.get(i).getCommodityList();
                        mMyHomePagerNewAdapter = new MyHomePagerNewAdapter(getContext(), list);
                        mHomePageRvNew.setAdapter(mMyHomePagerNewAdapter);
                        mMyHomePagerNewAdapter.setOnClickedListener(new MyHomePagerNewAdapter.onClickedListener() {
                            @Override
                            public void onChecked(int position) {
                                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("commodityId", list.get(position).getCommodityId() + "");
                                intent.putExtras(bundle);
                                getActivity().startActivity(intent);

                            }
                        });
                    }

                }
            }

            @Override
            public void OkHttpError(String error) {

            }
        });
    }

    //初始化XBanner
    private void initData() {
        // 为XBanner绑定数据
        //第二个参数为提示文字资源集合
        mHomePageVp.setData(mListImgUrl, null);
        mHomePageVp.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                Glide.with(getActivity()).load(mListImgUrl.get(position)).into((ImageView) view);
            }
        });

        // 设置XBanner的页面切换特效，选择一个即可，总的大概就这么多效果啦，欢迎使用
        mHomePageVp.setPageTransformer(Transformer.Default);//横向移动

        mHomePageVp.setPageTransformer(Transformer.Alpha); //渐变，效果不明显

        mHomePageVp.setPageTransformer(Transformer.ZoomFade); // 缩小本页，同时放大另一页

        mHomePageVp.setPageTransformer(Transformer.ZoomCenter); //本页缩小一点，另一页就放大

        mHomePageVp.setPageTransformer(Transformer.ZoomStack); // 本页和下页同事缩小和放大

        mHomePageVp.setPageTransformer(Transformer.Stack);  //本页和下页同时左移

        mHomePageVp.setPageTransformer(Transformer.Depth);  //本页左移，下页从后面出来

        mHomePageVp.setPageTransformer(Transformer.Zoom);  //本页刚左移，下页就在后面
        // 设置XBanner页面切换的时间，即动画时长
        mHomePageVp.setPageChangeDuration(0);
    }

    //点击事件
    @OnClick({R.id.home_page_search, R.id.home_page_sort, R.id.ed_btn_search})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.home_page_search://搜索
                mEdSearch.setVisibility(View.VISIBLE);
                mHomePageSearch.setVisibility(View.GONE);

                break;
            case R.id.home_page_sort://分类
                setPopupWindow();//设置popupwindow
                break;
            case R.id.ed_btn_search://搜索联动
                mEdTxts = mEdTxt.getText().toString().trim();
                if (TextUtils.isEmpty(mEdTxts)) {
                    mEdSearch.setVisibility(View.GONE);
                    mHomePageSearch.setVisibility(View.VISIBLE);
                } else {
                    //根据关键字搜索联动
                    initSearch();
                }
                break;
        }
    }

    //根据关键字搜索联动   XRecyclerView
    private void initSearch() {
        getFocus();
        initShow();
        mSl.setVisibility(View.GONE);
        mSRl.setVisibility(View.VISIBLE);
        mSeachRl.setVisibility(View.GONE);
        mSearchXRv.setPullRefreshEnabled(true);
        mSearchXRv.setLoadingMoreEnabled(true);
        mSearchXRv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mSearchXRv.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {//下拉刷新
                initShow();
                mSearchXRv.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                mSearchXRv.refreshComplete();//关闭上拉加载在更多
            }
        });

    }

    //根据关键字搜索联动
    private void initShow() {
        new OkHttpUtil().OkHttpGet(Apis.URL_FIND_COMMODITY_BYKEYWORD_GET + mEdTxts + "&page=" + pagers + "&count=" + count).setOkHttpListener(new OkHttpUtil.OkHttpListener() {
            @Override
            public void OkHttpSuccess(String data) {
                Gson gson = new Gson();
                SearchBean bean = gson.fromJson(data, SearchBean.class);
                if (bean.getStatus().equals("0000")) {
                    final List<SearchBean.ResultBean> result = bean.getResult();
                    mMySearchAdapter = new MySearchAdapter(getActivity(), result);
                    mSearchXRv.setAdapter(mMySearchAdapter);
                    mMySearchAdapter.notifyDataSetChanged();
                    mMySearchAdapter.setOnClickedListener(new MySearchAdapter.onClickedListener() {
                        @Override
                        public void onChecked(int position) {
                            Intent intent = new Intent(getActivity(), DetailsActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("commodityId", result.get(position).getCommodityId() + "");
                            intent.putExtras(bundle);
                            getActivity().startActivity(intent);
                        }
                    });
                }
            }

            @Override
            public void OkHttpError(String error) {
                Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //设置popupwindow
    private void setPopupWindow() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.home_fragment_popupwindow, null, false);
        PopupWindow popupWindow = new PopupWindow(view, 1100, 300, true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);
        popupWindow.showAsDropDown(mHomePageSort, -10, 0);
        poPupWindowRv(view);
    }

    private void poPupWindowRv(View view) {
        mIPresenterImplement = new IPresenterImplement(this);
        mIPresenterImplement.onGetDatas(Apis.URL_FIND_FIRST_CATEGORY_GET, PopupBean.class);
        mMRlRv = view.findViewById(R.id.rl_rv);
        mMRlRvr = view.findViewById(R.id.rl_rv_r);
        mMRlRv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        mMRlRvr.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
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

    //成功
    @Override
    public void onISuccess(Object data) {
        if (data instanceof PopupBean) {
            PopupBean bean = (PopupBean) data;
            if (bean.getStatus().equals("0000")) {
                final List<PopupBean.ResultBean> result = bean.getResult();
                mMyPopupAdapter = new MyPopupAdapter(getActivity(), result);
                mMRlRv.setAdapter(mMyPopupAdapter);
                mMyPopupAdapter.setOnListener(new MyPopupAdapter.onListener() {
                    @Override
                    public void onClicked(int position) {
                        mId = result.get(position).getId();
                        mIPresenterImplement.onGetDatas(Apis.URL_FIND_SECOND_CATEGORY_GET + mId, PopupBeans.class);
                    }
                });
            }
        } else if (data instanceof PopupBeans) {
            PopupBeans bean = (PopupBeans) data;
            if (bean.getStatus().equals("0000")) {
                final List<PopupBeans.ResultBean> result = bean.getResult();
                mMyPopupsAdapter = new MyPopupsAdapter(getActivity(), result);
                mMRlRvr.setAdapter(mMyPopupsAdapter);
                mMyPopupsAdapter.setOnListener(new MyPopupsAdapter.onListener() {
                    @Override
                    public void onClicked(int position) {
                        init();
                        String id = result.get(position).getId();
                        mIPresenterImplement.onGetDatas(Apis.URL_FIND_COMMODITY_BYCATEGORY_GET + id + "&page=" + pagers + "&count=" + count, SeachGoodsBean.class);
                    }
                });
            }
        } else if (data instanceof SeachGoodsBean) {//
            SeachGoodsBean bean = (SeachGoodsBean) data;
            if (bean.getStatus().equals("0000")) {
                final List<SeachGoodsBean.ResultBean> result = bean.getResult();
                mSeachGoodsAdapter = new SeachGoodsAdapter(getActivity(), result);
                mSearchRv.setAdapter(mSeachGoodsAdapter);
                mSeachGoodsAdapter.setOnClickedListener(new SeachGoodsAdapter.onClickedListener() {
                    @Override
                    public void onChecked(int position) {
                        Intent intent = new Intent(getActivity(), DetailsActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("commodityId", result.get(position).getCommodityId() + "");
                        intent.putExtras(bundle);
                        getActivity().startActivity(intent);
                    }
                });
            }
        }
    }

    private void init() {
        mSl.setVisibility(View.GONE);
        mSeachRl.setVisibility(View.VISIBLE);
        mSearchRv.setLayoutManager(new GridLayoutManager(getContext(), 2));
    }

    //失败
    @Override
    public void onIFailed(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        getFocus();
    }

    private long exitTime = 0;
    //主界面获取焦点
    private void getFocus() {
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
                    mSeachRl.setVisibility(View.GONE);
                    mSl.setVisibility(View.VISIBLE);
                    mSRl.setVisibility(View.GONE);
                    // TODO: 2019/01/04 双击退出
                    if ((System.currentTimeMillis() - exitTime) > 2000) {
                        Toast.makeText(getActivity(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                        exitTime = System.currentTimeMillis();
                    } else {
                        // 这里是我做了保存数据操作
                        getActivity().finish();
                        System.exit(0);
                    }
                    return true;
                }
                return false;
            }
        });
    }
}
