package com.fsy.bawayshop.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.fsy.bawayshop.R;
import com.fsy.bawayshop.bean.QueryCartBean;
import com.fsy.bawayshop.customer.CustomView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : FangShiKang
 * @date : 2019/01/10.
 * email : fangshikang@outlook.com
 * desc :
 */
public class CartShopOrderAdapter extends RecyclerView.Adapter<CartShopOrderAdapter.CartShopOrderViewHolder> {
    private Context mContext;
    private List<QueryCartBean.ResultBean> mResultBeans = new ArrayList<>();

    public CartShopOrderAdapter(Context context, List<QueryCartBean.ResultBean> resultBeans) {
        mContext = context;
        mResultBeans = resultBeans;
    }

    @NonNull
    @Override
    public CartShopOrderViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.sum_item_view, viewGroup, false);
        return new CartShopOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartShopOrderViewHolder cartShopOrderViewHolder, final int i) {
        cartShopOrderViewHolder.mTextViewName.setText(mResultBeans.get(i).getCommodityName());
        cartShopOrderViewHolder.mTextViewPrice.setText("￥"+mResultBeans.get(i).getPrice() + "");
        String pic = mResultBeans.get(i).getPic();
        Uri uri = Uri.parse(pic);
        cartShopOrderViewHolder.mSimpleDraweeView.setImageURI(uri);
    }

    @Override
    public int getItemCount() {
        return mResultBeans == null ? 0 : mResultBeans.size();
    }

    class CartShopOrderViewHolder extends RecyclerView.ViewHolder {

        SimpleDraweeView mSimpleDraweeView;
        TextView mTextViewName, mTextViewPrice;
        CustomView mCustomView;

        public CartShopOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            mSimpleDraweeView = itemView.findViewById(R.id.sum_item_img);
            mTextViewName = itemView.findViewById(R.id.sum_item_name);
            mTextViewPrice = itemView.findViewById(R.id.sum_item_price);
            mCustomView = itemView.findViewById(R.id.sum_item_addsubnum);
        }
    }
}
