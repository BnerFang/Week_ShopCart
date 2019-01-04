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
import com.fsy.bawayshop.bean.InFormationBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : FangShiKang
 * @date : 2019/01/04.
 * email : fangshikang@outlook.com
 * desc :
 */
public class InFormationAdapter extends RecyclerView.Adapter<InFormationAdapter.MyInFormationViewHolder> {
    private Context mContext;
    private InFormationBean.ResultBean mResultBeans;

    public InFormationAdapter(Context context, InFormationBean.ResultBean resultBeans) {
        mContext = context;
        mResultBeans = resultBeans;
    }


    @NonNull
    @Override
    public MyInFormationViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.information_item_view, viewGroup, false);
        return new MyInFormationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyInFormationViewHolder myInFormationViewHolder, int i) {
        String pic = mResultBeans.getHeadPic();
        Uri uri = Uri.parse(pic);
        myInFormationViewHolder.mSimpleDraweeView.setImageURI(uri);
        myInFormationViewHolder.mTextViewName.setText(mResultBeans.getNickName());
        myInFormationViewHolder.mTextViewPwd.setText(mResultBeans.getPassword());
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    class MyInFormationViewHolder extends RecyclerView.ViewHolder{

        SimpleDraweeView mSimpleDraweeView;
        TextView mTextViewName,mTextViewPwd;

        public MyInFormationViewHolder(@NonNull View itemView) {
            super(itemView);
            mSimpleDraweeView = itemView.findViewById(R.id.information_img);
            mTextViewName = itemView.findViewById(R.id.information_name);
            mTextViewPwd = itemView.findViewById(R.id.information_pwd);
        }
    }
}
