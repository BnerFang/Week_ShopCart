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
public class MyHomePagerVogueAdapter extends RecyclerView.Adapter<MyHomePagerVogueAdapter.MyVogueViewHolder> {
    private Context mContext;
    private List<ShowBean.ResultBean.MlssBean.CommodityListBeanXX> mListBeanXES = new ArrayList<>();

    public MyHomePagerVogueAdapter(Context context, List<ShowBean.ResultBean.MlssBean.CommodityListBeanXX> listBeanXES) {
        mContext = context;
        mListBeanXES = listBeanXES;
    }

    @NonNull
    @Override
    public MyVogueViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.vogue_rv_item_view, viewGroup, false);
        return new MyVogueViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyVogueViewHolder myVogueViewHolder, final int i) {
        String pic = mListBeanXES.get(i).getMasterPic();
        Uri uri = Uri.parse(pic);
        myVogueViewHolder.mSimpleDraweeView.setImageURI(uri);
        myVogueViewHolder.mTextViewTitle.setText(mListBeanXES.get(i).getCommodityName());
        myVogueViewHolder.mTextViewPrice.setText("￥" + mListBeanXES.get(i).getPrice() + "");
        myVogueViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnClickedListener.onChecked(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListBeanXES == null ? 0 : mListBeanXES.size();
    }

    public class MyVogueViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView mSimpleDraweeView;
        TextView mTextViewTitle, mTextViewPrice;

        public MyVogueViewHolder(@NonNull View itemView) {
            super(itemView);
            mSimpleDraweeView = itemView.findViewById(R.id.vogue_img);
            mTextViewTitle = itemView.findViewById(R.id.vogue_title);
            mTextViewPrice = itemView.findViewById(R.id.vogue_price);
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
