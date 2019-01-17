package com.fsy.bawayshop.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.fsy.bawayshop.MainActivity;
import com.fsy.bawayshop.R;
import com.fsy.bawayshop.activity.AddressActivity;
import com.fsy.bawayshop.activity.CircleActivity;
import com.fsy.bawayshop.activity.FootActivity;
import com.fsy.bawayshop.activity.InformationActivity;
import com.fsy.bawayshop.activity.WalletActivity;
import com.fsy.bawayshop.api.Apis;
import com.fsy.bawayshop.api.ParamsApis;
import com.fsy.bawayshop.bean.PricBean;
import com.fsy.bawayshop.mvp.presenter.IPresenterImplement;
import com.fsy.bawayshop.mvp.view.IView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

/**
 * @author : FangShiKang
 * @date : 2018/12/28.
 * email : fangshikang@outlook.com
 * desc :
 */
public class MyFragment extends Fragment implements View.OnClickListener, IView {
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
    private AlertDialog alertDialog;
    private Button bntPai;
    private Button bntPick;
    private IPresenterImplement mIPresenterImplement;
    private String path = Environment.getExternalStorageDirectory()
            + "/head.jpg";
    private static final int OK_REQUEST_CODE = 100;
    private static final int OK_REQUEST = 99;
    private static final int OK_REQUEST_S = 89;
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
        mIPresenterImplement = new IPresenterImplement(this);
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
                // 弹出对话框
                createDialog();
                break;
            case R.id.btn_pai:
                // 打开系统相机
                Intent pIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                pIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(new File(path)));
                startActivityForResult(pIntent, OK_REQUEST_CODE);
                // 关闭对话框
                alertDialog.dismiss();
                break;
            case R.id.btn_pick:
                // 打开系统相册
                // 打开系统相册
                Intent xIntent = new Intent(Intent.ACTION_PICK);
                xIntent.setType("image/*");
                startActivityForResult(xIntent, OK_REQUEST);
                // 关闭对话框
                alertDialog.dismiss();
                break;
        }
    }


    private void createDialog() {
        alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle("请选择");
        View diaLogView = View.inflate(getActivity(),
                R.layout.img_price_popup, null);
        alertDialog.setView(diaLogView);
        // 找出popupwindow里的控件
        bntPai = (Button) diaLogView.findViewById(R.id.btn_pai);
        bntPick = (Button) diaLogView.findViewById(R.id.btn_pick);
        // 点击事件
        bntPai.setOnClickListener(this);
        bntPick.setOnClickListener(this);
        alertDialog.show();
    }


    @Override
    public void onISuccess(Object data) {
        if (data instanceof PricBean) {
            PricBean bean = (PricBean) data;
            if (bean.getStatus().equals("0000")) {
                Toast.makeText(getActivity(), bean.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onIFailed(String error) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == OK_REQUEST && resultCode == RESULT_OK) {
            // 裁剪
            crop(data.getData());
        }
        if (requestCode == OK_REQUEST_S && resultCode == RESULT_OK) {
            mMyImg.setImageBitmap((Bitmap) data.getParcelableExtra("data"));
        }
        if (requestCode == OK_REQUEST_CODE && resultCode == RESULT_OK) {
            crop(Uri.fromFile(new File(path)));
        }
    }

    // 裁剪方法
    private void crop(Uri data) {
        Intent cIntent = new Intent("com.android.camera.action.CROP");
        cIntent.setDataAndType(data, "image/*");
        cIntent.putExtra("crop", true);
        cIntent.putExtra("aspectX", 1);
        cIntent.putExtra("aspectY", 1);
        cIntent.putExtra("outputX", 249);
        cIntent.putExtra("outputY", 249);
        cIntent.putExtra("return-data", true);
        startActivityForResult(cIntent, OK_REQUEST_S);
    }

    //    进行获取焦点，点击返回键返回上一级
    @Override
    public void onResume() {
        super.onResume();
        getFours();
    }

    //    进行获取焦点
    long exitTime = 0;

    private void getFours() {
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
                    if ((System.currentTimeMillis() - exitTime) > 2000) {
                        //* Toast.makeText(getActivity(),"再按一次就退出了哟",Toast.LENGTH_SHORT).show();
                        exitTime = System.currentTimeMillis();
                        startActivity(new Intent(getActivity(), MainActivity.class));
                        getActivity().finish();
                    } else {
                        System.exit(0);
                    }
                    return true;
                }
                return false;
            }
        });
    }//进行获取焦点

}
