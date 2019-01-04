package com.fsy.bawayshop.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fsy.bawayshop.R;
import com.fsy.bawayshop.bean.PopupBeans;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : FangShiKang
 * @date : 2019/01/03.
 * email : fangshikang@outlook.com
 * desc :
 */
public class MyPopupsAdapter extends RecyclerView.Adapter<MyPopupsAdapter.MyPPsViewHolder> {
    private Context mContext;
    private List<PopupBeans.ResultBean> mResultBeans = new ArrayList<>();

    public MyPopupsAdapter(Context context, List<PopupBeans.ResultBean> resultBeans) {
        mContext = context;
        mResultBeans = resultBeans;
    }

    @NonNull
    @Override
    public MyPPsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.popups_item_view, viewGroup, false);
        return new MyPPsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyPPsViewHolder myPPsViewHolder, final int i) {
        myPPsViewHolder.mTextView.setText(mResultBeans.get(i).getName());
        myPPsViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
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

    class MyPPsViewHolder extends RecyclerView.ViewHolder {

        TextView mTextView;

        public MyPPsViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.pps_title);
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
