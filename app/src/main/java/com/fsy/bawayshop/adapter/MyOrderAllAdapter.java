package com.fsy.bawayshop.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.fsy.bawayshop.R;
import com.fsy.bawayshop.bean.OrderBean;
import com.fsy.bawayshop.customer.CustomView;

import java.util.ArrayList;
import java.util.List;


/**
 * @author : FangShiKang
 * @date : 2019/01/10.
 * email : fangshikang@outlook.com
 * desc :       全部订单适配器
 */
public class MyOrderAllAdapter extends RecyclerView.Adapter<MyOrderAllAdapter.MyOrderAllViewHolder> {
    private Context mContext;
    private List<OrderBean.OrderListBean> mOrderListBeans = new ArrayList<>();

    public MyOrderAllAdapter(Context context, List<OrderBean.OrderListBean> orderBean) {
        mContext = context;
        mOrderListBeans = orderBean;
    }


    @NonNull
    @Override
    public MyOrderAllViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.order_item_view, viewGroup, false);
        return new MyOrderAllViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrderAllViewHolder myOrderAllViewHolder, int i) {
        String pic = mOrderListBeans.get(i).getDetailList().get(i).getCommodityPic().split("\\,")[i];
        Uri uri = Uri.parse(pic);
        myOrderAllViewHolder.mSimpleDraweeView.setImageURI(uri);
        //获取订单号,截取前面时间段
        String orderId = mOrderListBeans.get(i).getOrderId();
        String s = orderId.substring(0, 4);
        String a = orderId.substring(4, 6);
        String d = orderId.substring(6, 8);
        myOrderAllViewHolder.mTextViewOrderId.setText(mOrderListBeans.get(i).getOrderId());
        myOrderAllViewHolder.mTextViewName.setText(mOrderListBeans.get(i).getDetailList().get(i).getCommodityName());
        myOrderAllViewHolder.mTextViewPrice.setText("￥"+mOrderListBeans.get(i).getDetailList().get(i).getCommodityPrice() + "");
        //获取时间
        myOrderAllViewHolder.mTextViewTiem.setText(s + "-" + a + "-" + d);
    }

    @Override
    public int getItemCount() {
        return mOrderListBeans == null ? 0 : mOrderListBeans.size();
    }

    class MyOrderAllViewHolder extends RecyclerView.ViewHolder {

        SimpleDraweeView mSimpleDraweeView;
        TextView mTextViewOrderId, mTextViewTiem, mTextViewName, mTextViewPrice;
        CustomView mCustomView;

        public MyOrderAllViewHolder(@NonNull View itemView) {
            super(itemView);
            mSimpleDraweeView = itemView.findViewById(R.id.order_item_img);
            mTextViewOrderId = itemView.findViewById(R.id.order_item_orderid);
            mTextViewTiem = itemView.findViewById(R.id.order_item_tiem);
            mTextViewName = itemView.findViewById(R.id.order_item_name);
            mTextViewPrice = itemView.findViewById(R.id.order_item_price);
            mCustomView = itemView.findViewById(R.id.order_item_addsubsum);
        }
    }
}
