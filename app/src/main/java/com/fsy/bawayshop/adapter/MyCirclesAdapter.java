package com.fsy.bawayshop.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.fsy.bawayshop.R;
import com.fsy.bawayshop.bean.CirclesBean;
import com.fsy.bawayshop.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author : FangShiKang
 * @date : 2019/01/03.
 * email : fangshikang@outlook.com
 * desc :
 */
public class MyCirclesAdapter extends RecyclerView.Adapter<MyCirclesAdapter.MyCirclesViewHolder> {

    private Context mContext;
    private List<CirclesBean.ResultBean> mResultBeans = new ArrayList<>();

    public MyCirclesAdapter(Context context, List<CirclesBean.ResultBean> resultBeans) {
        mContext = context;
        mResultBeans = resultBeans;
    }

    @NonNull
    @Override
    public MyCirclesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.circles_rv_item_view, viewGroup, false);
        return new MyCirclesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyCirclesViewHolder myCirclesViewHolder, final int i) {

        Glide.with(mContext).load(mResultBeans.get(i).getHeadPic()).into(myCirclesViewHolder.mCircleImageViewImg);
        String image = mResultBeans.get(i).getImage();
        Uri uri = Uri.parse(image);
        if (uri != null) {
            myCirclesViewHolder.mCircleImageViewImg.setImageURI(uri);
        } else {
            myCirclesViewHolder.mCircleImageViewImg.setVisibility(View.GONE);
        }
        myCirclesViewHolder.mTextViewName.setText(mResultBeans.get(i).getNickName());
        myCirclesViewHolder.mTextViewTitle.setText(mResultBeans.get(i).getContent());
        myCirclesViewHolder.mTextViewNum.setText(mResultBeans.get(i).getGreatNum() + "");

        String date = DateUtils.getDateToString(mResultBeans.get(i).getCreateTime());
        myCirclesViewHolder.mTextViewTiem.setText(date);

        myCirclesViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
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

    public class MyCirclesViewHolder extends RecyclerView.ViewHolder {

        CircleImageView mCircleImageViewImg;
        SimpleDraweeView mImageViewIcon;
        ImageView mImageViewPrise;
        TextView mTextViewName, mTextViewTiem, mTextViewTitle, mTextViewNum;

        public MyCirclesViewHolder(@NonNull View itemView) {
            super(itemView);
            mCircleImageViewImg = itemView.findViewById(R.id.circles_img);
            mImageViewIcon = itemView.findViewById(R.id.circles_icon);
            mImageViewPrise = itemView.findViewById(R.id.circles_prise);
            mTextViewName = itemView.findViewById(R.id.circles_name);
            mTextViewTiem = itemView.findViewById(R.id.circles_tiem);
            mTextViewTitle = itemView.findViewById(R.id.circles_title);
            mTextViewNum = itemView.findViewById(R.id.circles_num);
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
