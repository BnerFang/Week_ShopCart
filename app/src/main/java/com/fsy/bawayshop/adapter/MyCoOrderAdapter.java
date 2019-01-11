package com.fsy.bawayshop.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.fsy.bawayshop.R;
import com.fsy.bawayshop.bean.OrderBean;

import java.util.ArrayList;
import java.util.List;


/**
 * @author : FangShiKang
 * @date : 2019/01/10.
 * email : fangshikang@outlook.com
 * desc :       全部订单适配器
 */
public class MyCoOrderAdapter extends RecyclerView.Adapter<MyCoOrderAdapter.MyCoOrderViewHolder> {
    private Context mContext;
    private List<OrderBean.OrderListBean> mOrderListBeans = new ArrayList<>();

    public MyCoOrderAdapter(Context context, List<OrderBean.OrderListBean> orderBean) {
        mContext = context;
        mOrderListBeans = orderBean;
    }


    @NonNull
    @Override
    public MyCoOrderViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.order_comment, viewGroup, false);
        return new MyCoOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyCoOrderViewHolder myCoOrderViewHolder, int i) {
        String pic = mOrderListBeans.get(i).getDetailList().get(i).getCommodityPic().split("\\,")[i];
        Uri uri = Uri.parse(pic);
        myCoOrderViewHolder.mSimpleDraweeView.setImageURI(uri);
        myCoOrderViewHolder.mTextViewName.setText(mOrderListBeans.get(i).getDetailList().get(i).getCommodityName());
        myCoOrderViewHolder.mTextViewOrderId.setText(mOrderListBeans.get(i).getOrderId());
        myCoOrderViewHolder.mTextViewPrice.setText("￥"+mOrderListBeans.get(i).getDetailList().get(i).getCommodityPrice() + "");
    }

    @Override
    public int getItemCount() {
        return mOrderListBeans == null ? 0 : mOrderListBeans.size();
    }

    class MyCoOrderViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView mSimpleDraweeView;
        ImageView mImageView;
        TextView  mTextViewOrderId,mTextViewName, mTextViewPrice,mTextViewBtn;
        public MyCoOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            mSimpleDraweeView = itemView.findViewById(R.id.co_order_img);
            mTextViewName = itemView.findViewById(R.id.co_order_name);
            mTextViewPrice = itemView.findViewById(R.id.co_order_price);
            mTextViewOrderId = itemView.findViewById(R.id.co_order_orderid);
            mImageView = itemView.findViewById(R.id.co_order_img_btn);
            mTextViewBtn = itemView.findViewById(R.id.co_order_btn);
        }
    }
}
