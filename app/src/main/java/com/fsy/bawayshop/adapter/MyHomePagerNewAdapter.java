package com.fsy.bawayshop.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fsy.bawayshop.R;
import com.fsy.bawayshop.bean.ShowBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : FangShiKang
 * @date : 2019/01/01.
 * email : fangshikang@outlook.com
 * desc :
 */
public class MyHomePagerNewAdapter extends RecyclerView.Adapter<MyHomePagerNewAdapter.MyNewViewHolder> {

    private Context mContext;
    private List<ShowBean.ResultBean.RxxpBean.CommodityListBean> mRxxpBeans = new ArrayList<>();

    public MyHomePagerNewAdapter(Context context, List<ShowBean.ResultBean.RxxpBean.CommodityListBean> rxxpBeans) {
        mContext = context;
        mRxxpBeans = rxxpBeans;
    }

    public void setList(List<ShowBean.ResultBean.RxxpBean.CommodityListBean> rxxpBeans) {
        mRxxpBeans.addAll(rxxpBeans);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyNewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.new_rv_item_view, viewGroup, false);
        return new MyNewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyNewViewHolder myNewViewHolder, final int i) {

        String masterPic = mRxxpBeans.get(i).getMasterPic();
        Glide.with(mContext).load(masterPic).into(myNewViewHolder.mImageViewImg);
        myNewViewHolder.mTextViewTitle.setText(mRxxpBeans.get(i).getCommodityName());
        myNewViewHolder.mTextViewPrice.setText("￥" + mRxxpBeans.get(i).getPrice() + "");
        myNewViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnClickedListener.onChecked(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRxxpBeans == null ? 0 : mRxxpBeans.size();
    }

    public class MyNewViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageViewImg;
        TextView mTextViewName, mTextViewTitle, mTextViewPrice;

        public MyNewViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageViewImg = itemView.findViewById(R.id.new_img);
            mTextViewTitle = itemView.findViewById(R.id.new_title);
            mTextViewPrice = itemView.findViewById(R.id.new_price);
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
