package com.fsy.bawayshop.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.SearchEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.fsy.bawayshop.R;
import com.fsy.bawayshop.bean.SearchBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : FangShiKang
 * @date : 2019/01/03.
 * email : fangshikang@outlook.com
 * desc :
 */
public class MySearchAdapter extends RecyclerView.Adapter<MySearchAdapter.MySeachViewHolder> {

    private Context mContext;
    private List<SearchBean.ResultBean> mResultBeans = new ArrayList<>();

    public MySearchAdapter(Context context, List<SearchBean.ResultBean> resultBeans) {
        mContext = context;
        mResultBeans = resultBeans;
    }

    @NonNull
    @Override
    public MySeachViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.search_item_view, viewGroup, false);
        return new MySeachViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MySeachViewHolder mySeachViewHolder, final int i) {
        String pic = mResultBeans.get(i).getMasterPic();
        Uri uri = Uri.parse(pic);
        mySeachViewHolder.mSimpleDraweeView.setImageURI(uri);
        mySeachViewHolder.mTextViewName.setText(mResultBeans.get(i).getCommodityName());
        mySeachViewHolder.mTextViewPrice.setText("￥" + mResultBeans.get(i).getPrice() + "");
        mySeachViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnClickedListener.onChecked(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mResultBeans == null ? 0 : mResultBeans.size();
    }

    class MySeachViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView mSimpleDraweeView;
        TextView mTextViewName, mTextViewPrice;

        public MySeachViewHolder(@NonNull View itemView) {
            super(itemView);
            mSimpleDraweeView = itemView.findViewById(R.id.s_item_img);
            mTextViewName = itemView.findViewById(R.id.s_item_name);
            mTextViewPrice = itemView.findViewById(R.id.s_item_price);
        }
    }

    private onClickedListener mOnClickedListener;

    public void setOnClickedListener(onClickedListener onClickedListener) {
        mOnClickedListener = onClickedListener;
    }

    //自定义接口回调
    public interface onClickedListener {
        void onChecked(int position);
    }
}
