package com.fsy.bawayshop.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fsy.bawayshop.R;
import com.fsy.bawayshop.bean.PopupBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : FangShiKang
 * @date : 2019/01/03.
 * email : fangshikang@outlook.com
 * desc :
 */
public class MyPopupAdapter extends RecyclerView.Adapter<MyPopupAdapter.MyPopupViewHolder> {

    private Context mContext;
    private List<PopupBean.ResultBean> mResultBeans = new ArrayList<>();

    public MyPopupAdapter(Context context, List<PopupBean.ResultBean> resultBeans) {
        mContext = context;
        mResultBeans = resultBeans;
    }

    @NonNull
    @Override
    public MyPopupViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.popup_item_view, viewGroup, false);
        return new MyPopupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyPopupViewHolder myPopupViewHolder, final int i) {
        myPopupViewHolder.mTextView.setText(mResultBeans.get(i).getName());
        myPopupViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnListener.onClicked(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mResultBeans == null ? 0 : mResultBeans.size();
    }

    class MyPopupViewHolder extends RecyclerView.ViewHolder{

        TextView mTextView;
        public MyPopupViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.pp_title);
        }
    }

    private onListener mOnListener;

    public void setOnListener(onListener onListener) {
        mOnListener = onListener;
    }

    public interface onListener{
        void onClicked(int position);
    }
}
