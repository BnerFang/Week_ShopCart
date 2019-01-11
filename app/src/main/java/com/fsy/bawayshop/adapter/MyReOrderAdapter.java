package com.fsy.bawayshop.adapter;

        import android.content.Context;
        import android.net.Uri;
        import android.support.annotation.NonNull;
        import android.support.v4.app.FragmentActivity;
        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
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
public class MyReOrderAdapter extends RecyclerView.Adapter<MyReOrderAdapter.MyReOrderViewHolder> {
    private Context mContext;
    private List<OrderBean.OrderListBean> mOrderListBeans = new ArrayList<>();

    public MyReOrderAdapter(Context context, List<OrderBean.OrderListBean> orderBean) {
        mContext = context;
        mOrderListBeans = orderBean;
    }


    @NonNull
    @Override
    public MyReOrderViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.order_receive, viewGroup, false);
        return new MyReOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyReOrderViewHolder myReOrderViewHolder, int i) {
        String pic = mOrderListBeans.get(i).getDetailList().get(i).getCommodityPic().split("\\,")[i];
        Uri uri = Uri.parse(pic);
        myReOrderViewHolder.mSimpleDraweeView.setImageURI(uri);
        //获取订单号,截取前面时间段
        String orderId = mOrderListBeans.get(i).getOrderId();
        String s = orderId.substring(0, 4);
        String a = orderId.substring(4, 6);
        String d = orderId.substring(6, 8);
        myReOrderViewHolder.mTextViewOrderId.setText(mOrderListBeans.get(i).getOrderId());
        myReOrderViewHolder.mTextViewName.setText(mOrderListBeans.get(i).getDetailList().get(i).getCommodityName());
        myReOrderViewHolder.mTextViewPrice.setText("￥"+mOrderListBeans.get(i).getDetailList().get(i).getCommodityPrice() + "");
        myReOrderViewHolder.mTextViewCode.setText(orderId);
        myReOrderViewHolder.mTextViewGs.setText(mOrderListBeans.get(i).getExpressCompName());
        //获取时间
        myReOrderViewHolder.mTextViewTiem.setText(s + "-" + a + "-" + d);
    }

    @Override
    public int getItemCount() {
        return mOrderListBeans == null ? 0 : mOrderListBeans.size();
    }

    class MyReOrderViewHolder extends RecyclerView.ViewHolder {

        SimpleDraweeView mSimpleDraweeView;
        TextView mTextViewOrderId, mTextViewTiem, mTextViewName, mTextViewPrice,mTextViewGs,mTextViewCode,mTextViewBtn;
        public MyReOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            mSimpleDraweeView = itemView.findViewById(R.id.re_order_img);
            mTextViewOrderId = itemView.findViewById(R.id.re_order_orderid);
            mTextViewTiem = itemView.findViewById(R.id.re_order_tiem);
            mTextViewName = itemView.findViewById(R.id.re_order_name);
            mTextViewPrice = itemView.findViewById(R.id.re_order_price);
            mTextViewGs = itemView.findViewById(R.id.re_order_gs);
            mTextViewCode = itemView.findViewById(R.id.re_order_code);
            mTextViewBtn = itemView.findViewById(R.id.re_order_btn);
        }
    }
}
