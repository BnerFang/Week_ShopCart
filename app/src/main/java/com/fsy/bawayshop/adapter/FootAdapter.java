package com.fsy.bawayshop.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;
import com.fsy.bawayshop.R;
import com.fsy.bawayshop.bean.FootBean;
import com.fsy.bawayshop.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : FangShiKang
 * @date : 2019/01/07.
 * email : fangshikang@outlook.com
 * desc :   我的足迹  适配器
 */
public class FootAdapter extends RecyclerView.Adapter<FootAdapter.FootViewHolder> {

    private Context mContext;
    private List<FootBean.ResultBean> mResultBeans = new ArrayList<>();

    public FootAdapter(Context context, List<FootBean.ResultBean> resultBeans) {
        mContext = context;
        mResultBeans = resultBeans;
    }

    public void setList(List<FootBean.ResultBean> resultBeans){
        mResultBeans.addAll(resultBeans);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FootViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.foot_item_view, viewGroup, false);
        return new FootViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FootViewHolder footViewHolder, int i) {
        String pic = mResultBeans.get(i).getMasterPic();
        Uri uri = Uri.parse(pic);
        Glide.with(mContext).load(uri).into(footViewHolder.mSimpleDraweeView);
        footViewHolder.mTextViewName.setText(mResultBeans.get(i).getCommodityName());
        footViewHolder.mTextViewPrice.setText("￥"+mResultBeans.get(i).getPrice() + "");
        footViewHolder.mTextViewLook.setText("已浏览" + mResultBeans.get(i).getBrowseNum() + "次");
        long time = mResultBeans.get(i).getBrowseTime();
        String date = DateUtils.getDateToString(time);
        footViewHolder.mTextViewDate.setText(date);
    }

    @Override
    public int getItemCount() {
        return mResultBeans == null ? 0 : mResultBeans.size();
    }

    public class FootViewHolder extends RecyclerView.ViewHolder {
        ImageView mSimpleDraweeView;
        TextView mTextViewName, mTextViewPrice, mTextViewLook, mTextViewDate;

        public FootViewHolder(@NonNull View itemView) {
            super(itemView);
            mSimpleDraweeView = itemView.findViewById(R.id.foot_item_img);
            mTextViewName = itemView.findViewById(R.id.foot_item_name);
            mTextViewPrice = itemView.findViewById(R.id.foot_item_price);
            mTextViewLook = itemView.findViewById(R.id.foot_item_look);
            mTextViewDate = itemView.findViewById(R.id.foot_item_date);
        }
    }
}
