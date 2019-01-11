package com.fsy.bawayshop.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.fsy.bawayshop.MainActivity;
import com.fsy.bawayshop.R;
import com.fsy.bawayshop.activity.AddressActivity;
import com.fsy.bawayshop.activity.CircleActivity;
import com.fsy.bawayshop.activity.FootActivity;
import com.fsy.bawayshop.activity.InformationActivity;
import com.fsy.bawayshop.activity.WalletActivity;

/**
 * @author : FangShiKang
 * @date : 2018/12/28.
 * email : fangshikang@outlook.com
 * desc :
 */
public class MyFragment extends Fragment implements View.OnClickListener {
    private View view;
    /**
     * 我是吃不胖的小姐姐
     */
    private TextView mMyName;
    private ImageView mInformation;
    /**
     * 个人资料
     */
    private TextView mMyInformation;
    private ImageView mCircle;
    /**
     * 我的圈子
     */
    private TextView mMyCircle;
    private ImageView mFoot;
    /**
     * 我的足迹
     */
    private TextView mMyFoot;
    private ImageView mWallet;
    /**
     * 我的钱包
     */
    private TextView mMyWallet;
    private ImageView mAddress;
    /**
     * 我的收货地址
     */
    private TextView mMyAddress;
    private SimpleDraweeView mMyImg;
    private String mNickName;
    private String mHeadPic;
    private Intent mIntent;
    private int mUserId;
    private String mSessionId;
    private String mString;
    private String mPhone;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        mIntent = getActivity().getIntent();
        mUserId = mIntent.getIntExtra("userId", 0);
        mSessionId = mIntent.getStringExtra("sessionId");
        mString = mNickName = mIntent.getStringExtra("nickName");
        mHeadPic = mIntent.getStringExtra("headPic");
        mPhone = mIntent.getStringExtra("phone");
        initView(view);
        return view;
    }

    private void initView(View view) {
        mMyName = (TextView) view.findViewById(R.id.my_name);
        mInformation = (ImageView) view.findViewById(R.id.information);
        mMyInformation = (TextView) view.findViewById(R.id.my_information);
        mCircle = (ImageView) view.findViewById(R.id.circle);
        mMyCircle = (TextView) view.findViewById(R.id.my_circle);
        mFoot = (ImageView) view.findViewById(R.id.foot);
        mMyFoot = (TextView) view.findViewById(R.id.my_foot);
        mWallet = (ImageView) view.findViewById(R.id.wallet);
        mMyWallet = (TextView) view.findViewById(R.id.my_wallet);
        mAddress = (ImageView) view.findViewById(R.id.address);
        mMyAddress = (TextView) view.findViewById(R.id.my_address);
        mMyImg = (SimpleDraweeView) view.findViewById(R.id.my_img);
        mMyInformation.setOnClickListener(this);
        mMyCircle.setOnClickListener(this);
        mMyFoot.setOnClickListener(this);
        mMyWallet.setOnClickListener(this);
        mMyAddress.setOnClickListener(this);
        mMyImg.setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mMyName.setText(mNickName + "");
        Uri uri = Uri.parse(mHeadPic);
        mMyImg.setImageURI(uri);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.my_information://个人资料
                mIntent = new Intent(getActivity(), InformationActivity.class);
                mIntent.putExtra("userId", mUserId);
                mIntent.putExtra("sessionId", mSessionId);
                mIntent.putExtra("nickName", mNickName);
                mIntent.putExtra("headPic", mHeadPic);
                mIntent.putExtra("phone", mPhone);
                getActivity().startActivity(mIntent);
                break;
            case R.id.my_circle://我的圈子
                getActivity().startActivity(new Intent(getActivity(), CircleActivity.class));

                break;
            case R.id.my_foot://我的足迹
                getActivity().startActivity(new Intent(getActivity(), FootActivity.class));

                break;
            case R.id.my_wallet://我的钱包
                getActivity().startActivity(new Intent(getActivity(), WalletActivity.class));

                break;
            case R.id.my_address://我的收货地址
                getActivity().startActivity(new Intent(getActivity(), AddressActivity.class));

                break;
            case R.id.my_img:
                break;
        }
    }

    //    进行获取焦点，点击返回键返回上一级
    /*@Override
    public void onResume() {
        super.onResume();

        getFours();

    }*/
    //    进行获取焦点
    /*long exitTime = 0;
    private void getFours() {
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
                    if ((System.currentTimeMillis() - exitTime) > 2000){
                       *//* Toast.makeText(getActivity(),"再按一次就退出了哟",Toast.LENGTH_SHORT).show();
                        exitTime = System.currentTimeMillis();*//*
                        startActivity(new Intent(getActivity(), MainActivity.class));
                    }else {
                        getActivity().finish();
                        System.exit(0);
                    }
                    return true;
                }
                return false;
            }
        });
    }*///    进行获取焦点

}
