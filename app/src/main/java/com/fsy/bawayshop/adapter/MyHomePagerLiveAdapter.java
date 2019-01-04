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
import com.fsy.bawayshop.bean.ShowBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : FangShiKang
 * @date : 2019/01/02.
 * email : fangshikang@outlook.com
 * desc :
 */
public class MyHomePagerLiveAdapter extends RecyclerView.Adapter<MyHomePagerLiveAdapter.MyLiveViewHolder> {
    private Context mContext;
    private List<ShowBean.ResultBean.PzshBean.CommodityListBeanX> mBeanXES = new ArrayList<>();

    public MyHomePagerLiveAdapter(Context context, List<ShowBean.ResultBean.PzshBean.CommodityListBeanX> beanXES) {
        mContext = context;
        mBeanXES = beanXES;
    }

    @NonNull
    @Override
    public MyLiveViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.live_rv_item_view, viewGroup, false);
        return new MyLiveViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyLiveViewHolder myLiveViewHolder, final int i) {
        String pic = mBeanXES.get(i).getMasterPic();
        Uri uri = Uri.parse(pic);
        myLiveViewHolder.mSimpleDraweeView.setImageURI(uri);
        myLiveViewHolder.mTextViewTitle.setText(mBeanXES.get(i).getCommodityName());
        myLiveViewHolder.mTextViewPrice.setText("￥" + mBeanXES.get(i).getPrice() + "");
        myLiveViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnClickedListener.onChecked(i);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mBeanXES == null ? 0 : mBeanXES.size();
    }

    public class MyLiveViewHolder extends RecyclerView.ViewHolder {

        SimpleDraweeView mSimpleDraweeView;
        TextView mTextViewTitle, mTextViewPrice;

        public MyLiveViewHolder(@NonNull View itemView) {
            super(itemView);
            mSimpleDraweeView = itemView.findViewById(R.id.live_img);
            mTextViewTitle = itemView.findViewById(R.id.live_title);
            mTextViewPrice = itemView.findViewById(R.id.live_price);
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
