package com.fsy.bawayshop.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fsy.bawayshop.R;
import com.fsy.bawayshop.bean.WalletBean;
import com.fsy.bawayshop.utils.DateUtils;

/**
 * @author : FangShiKang
 * @date : 2019/01/04.
 * email : fangshikang@outlook.com
 * desc :
 */
public class WalletAdapter extends RecyclerView.Adapter<WalletAdapter.MyWalletViewHolder> {

    private Context mContext;
    private WalletBean.ResultBean mResultBean;

    public WalletAdapter(Context context, WalletBean.ResultBean resultBean) {
        mContext = context;
        mResultBean = resultBean;
    }

    @NonNull
    @Override
    public MyWalletViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.wallet_item_view, viewGroup, false);
        return new MyWalletViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyWalletViewHolder myWalletViewHolder, int i) {
        myWalletViewHolder.mTextViewPrice.setText(mResultBean.getBalance() + "");
        myWalletViewHolder.mTextViewAllPrice.setText(mResultBean.getDetailList().get(i).getAmount() + "");
        String date = DateUtils.getDateToString(mResultBean.getDetailList().get(i).getCreateTime());
        myWalletViewHolder.mTextViewAlldate.setText(date);
    }

    @Override
    public int getItemCount() {
        return mResultBean.getDetailList().size();
    }

    class MyWalletViewHolder extends RecyclerView.ViewHolder {

        TextView mTextViewPrice, mTextViewAllPrice, mTextViewAlldate;

        public MyWalletViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewAlldate = itemView.findViewById(R.id.wallet_all_date);
            mTextViewAllPrice = itemView.findViewById(R.id.wallet_all_price);
            mTextViewPrice = itemView.findViewById(R.id.wallet_price);
        }
    }
}
