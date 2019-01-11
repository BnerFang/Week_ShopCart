package com.fsy.bawayshop.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fsy.bawayshop.MainActivity;
import com.fsy.bawayshop.R;
import com.fsy.bawayshop.activity.SumNuBerActivity;
import com.fsy.bawayshop.adapter.CartShopAdapter;
import com.fsy.bawayshop.api.Apis;
import com.fsy.bawayshop.bean.QueryCartBean;
import com.fsy.bawayshop.mvp.presenter.IPresenterImplement;
import com.fsy.bawayshop.mvp.view.IView;

import java.util.ArrayList;
import java.util.List;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author : FangShiKang
 * @date : 2018/12/28.
 * email : fangshikang@outlook.com
 * desc :   购物车界面
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
    private List<QueryCartBean.ResultBean> mList = new ArrayList<>();
    private CartShopAdapter mCartShopAdapter;
    private IPresenterImplement mIPresenterImplement;
    private List<QueryCartBean.ResultBean> mResult;
    private boolean mCheCked;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopping_cart, container, false);
        unbinder = ButterKnife.bind(this, view);
        mCartRv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mIPresenterImplement = new IPresenterImplement(this);
        mIPresenterImplement.onGetDatas(Apis.URL_FIND_SHOPPING_CART_GET, QueryCartBean.class);
        mCartBox.setTag(2);     //默认复选按钮都为 未选中 状态
        return view;
    }

    /**
     * 全选按钮 点击事件
     * 点击全选按钮设置所有复选框状态为true，否则为false
     * 1：未选中，2：选中
     */
    boolean select = false;

    @OnClick({R.id.cart_box, R.id.cart_sumNuber})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.cart_box:
                int tag = (Integer) mCartBox.getTag();
                if (tag == 1) {
                    mCartBox.setTag(2);
                    select = true;
                } else {
                    mCartBox.setTag(1);
                    select = false;
                }
                //遍历数据源,根据tag值设置选择状态，然后刷新改变后的数据，重新添加
                for (QueryCartBean.ResultBean bean : mList) {
                    bean.setCheCked(select);
                }
                mCartShopAdapter.notifyDataSetChanged();
                sumPrice(mCartShopAdapter.getList());
                break;
            case R.id.cart_sumNuber:
                if (mCheCked){
                    Intent intent = new Intent(getActivity(), SumNuBerActivity.class);
                    getActivity().startActivity(intent);
                }

                cartSumNuber();
                break;
        }
    }

    //结算
    private void cartSumNuber() {
        List<QueryCartBean.ResultBean> beans = new ArrayList<>();
        for (int i = 0; i < mResult.size(); i++) {
            mCheCked = mResult.get(i).isCheCked();
            if (mResult.get(i).isCheCked()) {
                QueryCartBean.ResultBean resultBean = mResult.get(i);
                beans.add(resultBean);//把解析出来的数据添加进去
            }
        }

        //EventBus传值
        EventBus.getDefault().postSticky(beans);
    }

    @Override
    public void onISuccess(Object data) {
        if (data instanceof QueryCartBean) {
            QueryCartBean bean = (QueryCartBean) data;
            if (bean.getStatus().equals("0000")) {
                mResult = bean.getResult();
                mList.addAll(mResult);
                mCartShopAdapter = new CartShopAdapter(getActivity(), mResult);
                mCartRv.setAdapter(mCartShopAdapter);
                mCartShopAdapter.notifyDataSetChanged();
                //适配器单个条目复选按钮的点击事件,价格变化
                mCartShopAdapter.setCheckBoxListener(new CartShopAdapter.CheckBoxListener() {
                    @Override
                    public void check(int position, int count, boolean check, List<QueryCartBean.ResultBean> list) {
                        sumPrice(list);
                    }
                });
            }
        }
    }

    //计算商品总价,设置没选择商品时总价为0，商品数量为0
    float price = 0;
    int count;

    private void sumPrice(List<QueryCartBean.ResultBean> list) {
        price = 0;
        count = 0;
        boolean allCheck = true;
        //遍历数据源
        for (QueryCartBean.ResultBean bean : list) {
            if (bean.isCheCked()) {        //商品选中时，计算总价
                price += bean.getPrice() * bean.getCount();
                count += bean.getCount();
            } else {                    //只要有一个商品未选中，全选按钮 应该设置成 未选中
                allCheck = false;
            }
        }
        //设置商品总数及总价
        mCartAll.setText("合计：￥" + price);
        mCartSumNuber.setText("去结算：(" + count + ")");
        //全选按钮是否选中时的tag值
        if (allCheck) {
            mCartBox.setTag(2);
            mCartBox.setChecked(true);
        } else {
            mCartBox.setTag(1);
            mCartBox.setChecked(false);
        }
    }

    @Override
    public void onIFailed(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
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
