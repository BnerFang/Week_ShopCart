package com.fsy.bawayshop.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fsy.bawayshop.R;
import com.fsy.bawayshop.bean.CartBean;
import com.fsy.bawayshop.bean.QueryCartBean;
import com.fsy.bawayshop.customer.CustomView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author : FangShiKang
 * @date : 2019/01/08.
 * email : fangshikang@outlook.com
 * desc :
 */
public class CartShopAdapter extends RecyclerView.Adapter<CartShopAdapter.CartShopViewHolder> {
    private Context mContext;
    private List<QueryCartBean.ResultBean> mResultBeans = new ArrayList<>();

    public CartShopAdapter(Context context, List<QueryCartBean.ResultBean> resultBeans) {
        mContext = context;
        mResultBeans = resultBeans;
    }

  /*  public void addData(List<QueryCartBean.ResultBean> resultBeans) {
        mResultBeans = resultBeans;
        notifyDataSetChanged();
    }*/

    public List<QueryCartBean.ResultBean> getList() {
        return mResultBeans;
    }

    @NonNull
    @Override
    public CartShopViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.cart_item_view, viewGroup, false);
        return new CartShopViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CartShopViewHolder cartShopViewHolder, final int i) {
        //防止CheckBox滑动错乱
        cartShopViewHolder.mCartItemBox.setChecked(mResultBeans.get(i).isCheCked());
        cartShopViewHolder.mCartItemTitle.setText(mResultBeans.get(i).getCommodityName());
        Glide.with(mContext).load(mResultBeans.get(i).getPic()).into(cartShopViewHolder.mCartItemImg);
        cartShopViewHolder.mCartItemPrice.setText("￥" + mResultBeans.get(i).getPrice() + "");
        cartShopViewHolder.mCustomview.setEditText(mResultBeans.get(i).getCount());
        //获取自定义view页面EditText输入框的值
        cartShopViewHolder.mCustomview.setEditText(mResultBeans.get(i).getCount());
        //复选按钮的选中点击事件,获取数据价格
        cartShopViewHolder.mCartItemBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mResultBeans.get(i).setCheCked(cartShopViewHolder.mCartItemBox.isChecked());
                notifyDataSetChanged();
                if (checkBoxListener != null) {
                    checkBoxListener.check(i, cartShopViewHolder.mCustomview.getCurrentCount(), cartShopViewHolder.mCartItemBox.isChecked(), mResultBeans);
                }
            }
        });
        //加减按钮的监听事件 ,更新当前条目数据价格
        cartShopViewHolder.mCustomview.setListener(new CustomView.ClickListener() {
            @Override
            public void click(int count) {
                //更改变化后的商品数量
                mResultBeans.get(i).setCount(count);
                notifyDataSetChanged();

                if (customViewListener != null) {
                    customViewListener.click(count, mResultBeans);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mResultBeans == null ? 0 : mResultBeans.size();
    }

    class CartShopViewHolder extends RecyclerView.ViewHolder {
        CheckBox mCartItemBox;
        ImageView mCartItemImg;
        TextView mCartItemTitle;
        TextView mCartItemPrice;
        CustomView mCustomview;
        RelativeLayout mCartItemLayout;
        Button mCartItemDel;

        public CartShopViewHolder(@NonNull View itemView) {
            super(itemView);
            mCartItemBox = itemView.findViewById(R.id.cart_item_box);
            mCartItemImg = itemView.findViewById(R.id.cart_item_img);
            mCartItemTitle = itemView.findViewById(R.id.cart_item_title);
            mCartItemPrice = itemView.findViewById(R.id.cart_item_price);
            mCustomview = itemView.findViewById(R.id.customview);
            mCartItemLayout = itemView.findViewById(R.id.cart_item_layout);
            mCartItemDel = itemView.findViewById(R.id.cart_item_del);
        }
    }

    //定义checkbox 点击事件的回调接口
    public CheckBoxListener checkBoxListener;

    public void setCheckBoxListener(CheckBoxListener listener) {
        this.checkBoxListener = listener;
    }

    public interface CheckBoxListener {
        public void check(int position, int count, boolean check, List<QueryCartBean.ResultBean> list);
    }

    //定义加减按钮 点击事件的回调接口
    public CustomViewListener customViewListener;

    public void setCustomViewListener(CustomViewListener customViewListener) {
        this.customViewListener = customViewListener;
    }

    public interface CustomViewListener {
        public void click(int count, List<QueryCartBean.ResultBean> list);
    }
}
