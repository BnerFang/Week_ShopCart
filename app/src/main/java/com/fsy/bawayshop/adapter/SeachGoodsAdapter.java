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
import com.fsy.bawayshop.bean.SeachGoodsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : FangShiKang
 * @date : 2019/01/04.
 * email : fangshikang@outlook.com
 * desc :
 */
public class SeachGoodsAdapter extends RecyclerView.Adapter<SeachGoodsAdapter.SeachGoodsViewHolder> {
    private Context mContext;
    private List<SeachGoodsBean.ResultBean> mResultBeans = new ArrayList<>();

    public SeachGoodsAdapter(Context context, List<SeachGoodsBean.ResultBean> resultBeans) {
        mContext = context;
        mResultBeans = resultBeans;
    }

    @NonNull
    @Override
    public SeachGoodsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.seach_goods_item_view, viewGroup, false);
        return new SeachGoodsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeachGoodsViewHolder seachGoodsViewHolder, final int i) {
        String pic = mResultBeans.get(i).getMasterPic();
        Uri uri = Uri.parse(pic);
        seachGoodsViewHolder.mSimpleDraweeView.setImageURI(uri);
        seachGoodsViewHolder.mTextViewName.setText(mResultBeans.get(i).getCommodityName());
        seachGoodsViewHolder.mTextViewPrice.setText("￥" + mResultBeans.get(i).getPrice() + "");
        seachGoodsViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
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

    class SeachGoodsViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView mSimpleDraweeView;
        TextView mTextViewName, mTextViewPrice;

        public SeachGoodsViewHolder(@NonNull View itemView) {
            super(itemView);
            mSimpleDraweeView = itemView.findViewById(R.id.seach_goods_item_img);
            mTextViewName = itemView.findViewById(R.id.seach_goods_item_name);
            mTextViewPrice = itemView.findViewById(R.id.seach_goods_item_price);
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
