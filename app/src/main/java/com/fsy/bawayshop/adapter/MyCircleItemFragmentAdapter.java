package com.fsy.bawayshop.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fsy.bawayshop.R;

/**
 * @author : FangShiKang
 * @date : 2019/01/04.
 * email : fangshikang@outlook.com
 * desc :
 */
public class MyCircleItemFragmentAdapter extends RecyclerView.Adapter<MyCircleItemFragmentAdapter.MyCircleItemFragmenViewHolder> {


    @NonNull
    @Override
    public MyCircleItemFragmenViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyCircleItemFragmenViewHolder myCircleItemFragmenViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class MyCircleItemFragmenViewHolder extends RecyclerView.ViewHolder{

        ImageView mImageView,mImageViews,mImageViewDz;
        TextView mTextViewTitle,mTextViewTiem,mTextViewNum;
        public MyCircleItemFragmenViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.my_circle_img);
            mImageViews = itemView.findViewById(R.id.my_circle_imgs);
            mImageViewDz = itemView.findViewById(R.id.my_circle_dz);
            mTextViewTitle = itemView.findViewById(R.id.my_circle_title);
            mTextViewTiem = itemView.findViewById(R.id.my_circle_tiem);
            mTextViewNum = itemView.findViewById(R.id.my_circle_num);
        }
    }

}
