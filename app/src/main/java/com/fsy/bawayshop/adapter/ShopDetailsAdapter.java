package com.fsy.bawayshop.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fsy.bawayshop.R;
import com.fsy.bawayshop.bean.DetailsBean;

/**
 * @author : FangShiKang
 * @date : 2019/01/05.
 * email : fangshikang@outlook.com
 * desc :
 */
public class ShopDetailsAdapter extends RecyclerView.Adapter<ShopDetailsAdapter.DetailsViewHolder> {

    private Context mContext;
    private DetailsBean.ResultBean mResultBeans;

    public ShopDetailsAdapter(Context context, DetailsBean.ResultBean resultBeans) {
        mContext = context;
        mResultBeans = resultBeans;
    }

    @NonNull
    @Override
    public DetailsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.details_item_view, viewGroup, false);
        return new DetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailsViewHolder detailsViewHolder, int i) {
        detailsViewHolder.mTextViewPrice.setText("￥"+mResultBeans.getPrice() + "");
        detailsViewHolder.mTextViewCount.setText("已售" + mResultBeans.getCommentNum() + "件");
        detailsViewHolder.mTextViewTitle.setText(mResultBeans.getCommodityName());
        detailsViewHolder.mTextViewKg.setText("重量   " + mResultBeans.getWeight() + "kg");

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    class DetailsViewHolder extends RecyclerView.ViewHolder {
        TextView mTextViewPrice, mTextViewCount, mTextViewTitle, mTextViewKg;

        public DetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewPrice = itemView.findViewById(R.id.details_price);
            mTextViewCount = itemView.findViewById(R.id.details_count);
            mTextViewTitle = itemView.findViewById(R.id.details_title);
            mTextViewKg = itemView.findViewById(R.id.details_kg);
        }
    }
}
