package com.fsy.bawayshop.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.fsy.bawayshop.R;
import com.fsy.bawayshop.bean.AddsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : FangShiKang
 * @date : 2019/01/08.
 * email : fangshikang@outlook.com
 * desc :   我的收货地址  适配器
 */
public class AddsAdapter extends RecyclerView.Adapter<AddsAdapter.AddsViewHolder> {

    private Context mContext;
    private List<AddsBean.ResultBean> mResultBeans = new ArrayList<>();

    public AddsAdapter(Context context, List<AddsBean.ResultBean> resultBeans) {
        mContext = context;
        mResultBeans = resultBeans;
    }

    public void del(int position){
        mResultBeans.remove(position);
        notifyItemRemoved(position);
    }

    @NonNull
    @Override
    public AddsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adds_item_view, viewGroup, false);
        return new AddsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AddsViewHolder addsViewHolder, final int i) {
        addsViewHolder.mTextViewName.setText(mResultBeans.get(i).getRealName());
        addsViewHolder.mTextViewPhone.setText(mResultBeans.get(i).getPhone());
        addsViewHolder.mTextViewTitle.setText(mResultBeans.get(i).getAddress());
        //点击删除
        addsViewHolder.mButtonDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnClickedListener.onChecked(view,i);
            }
        });
        //点击修改
        addsViewHolder.mButtonUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnClickedListener.onUpdate(i);

            }
        });
        //设置默认收货地址
        addsViewHolder.mCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    mResultBeans.get(i).setChecked(addsViewHolder.mCheckBox.isChecked());
                    notifyDataSetChanged();
                    if (mOnClickedListener != null) {
                        mOnClickedListener.onBoxChecked(i);
                    }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mResultBeans == null ? 0 : mResultBeans.size();
    }

    class AddsViewHolder extends RecyclerView.ViewHolder{
        TextView mTextViewName,mTextViewPhone,mTextViewTitle;
        CheckBox mCheckBox;
        Button mButtonUp,mButtonDel;
        public AddsViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewName = itemView.findViewById(R.id.adds_item_name);
            mTextViewPhone = itemView.findViewById(R.id.adds_item_phone);
            mTextViewTitle = itemView.findViewById(R.id.adds_item_title);
            mCheckBox = itemView.findViewById(R.id.adds_item_box);
            mButtonUp = itemView.findViewById(R.id.adds_item_up);
            mButtonDel = itemView.findViewById(R.id.adds_item_del);
        }
    }

    private onClickedListener mOnClickedListener;

    public void setOnClickedListener(onClickedListener onClickedListener) {
        mOnClickedListener = onClickedListener;
    }

    //自定义接口回调
    public interface onClickedListener {
        void onChecked(View view,int position);
        void onUpdate(int position);
        void onBoxChecked(int position);
    }

}
