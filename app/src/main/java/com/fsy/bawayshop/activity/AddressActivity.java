package com.fsy.bawayshop.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fsy.bawayshop.R;
import com.fsy.bawayshop.adapter.AddsAdapter;
import com.fsy.bawayshop.api.Apis;
import com.fsy.bawayshop.api.ParamsApis;
import com.fsy.bawayshop.bean.AddsBean;
import com.fsy.bawayshop.loginAndregister.bean.RegisterBean;
import com.fsy.bawayshop.mvp.presenter.IPresenterImplement;
import com.fsy.bawayshop.mvp.view.IView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的收货地址列表
 */
public class AddressActivity extends AppCompatActivity implements IView {

    @BindView(R.id.adds_heander)
    TextView mAddsHeander;
    @BindView(R.id.adda_commit)
    TextView mAddaCommit;
    @BindView(R.id.adds_layout)
    RelativeLayout mAddsLayout;
    @BindView(R.id.adds_rv)
    RecyclerView mAddsRv;
    @BindView(R.id.adds_btn)
    Button mAddsBtn;
    private IPresenterImplement mIPresenterImplement;
    private AddsAdapter mAddsAdapter;
    private List<AddsBean.ResultBean> mResult;
    private String mRealName;
    private String mPhone;
    private String mAddress;
    private String mZipCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        ButterKnife.bind(this);
        //创建presenter实例
        mIPresenterImplement = new IPresenterImplement(this);
        //访问presenter层get请求
        mIPresenterImplement.onGetDatas(Apis.URL_RECEIVE_ADDRESS_GET, AddsBean.class);
        mAddsRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @OnClick({R.id.adda_commit, R.id.adds_btn})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.adda_commit:
                finish();
                break;
            case R.id.adds_btn:
                startActivity(new Intent(this, AllRessActivity.class));
                break;
        }
    }

    //成功
    @Override
    public void onISuccess(Object data) {
        if (data instanceof AddsBean) {
            AddsBean addsBean = (AddsBean) data;
            if (addsBean.getStatus().equals("0000")) {
                mResult = addsBean.getResult();
                mAddsAdapter = new AddsAdapter(this, mResult);
                mAddsRv.setAdapter(mAddsAdapter);
                mAddsAdapter.setOnClickedListener(new AddsAdapter.onClickedListener() {

                    //点击删除
                    @Override
                    public void onChecked(View view, int position) {
                        mAddsAdapter.del(position);
                        mAddsAdapter.notifyItemRemoved(position);
                    }

                    //点击修改收货地址
                    @Override
                    public void onUpdate(int position) {
                        mRealName = mResult.get(position).getRealName();
                        mPhone = mResult.get(position).getPhone();
                        mAddress = mResult.get(position).getAddress();
                        mZipCode = mResult.get(position).getZipCode();
                        setPopupWindow();
                    }

                    //点击设置默认地址
                    @Override
                    public void onBoxChecked(int position) {
                        int id = mResult.get(position).getId();
                        Map<String, String> map = new HashMap<>();
                        //RequestBody  入参
                        map.put(ParamsApis.POST_RESS_DEFAULT_ID_KEY, id + "");
                        //访问presenter层postFormBody表单请求
                        mIPresenterImplement.onPostDatas(Apis.URL_SET_DEFAULT_RECEIVE_ADDRESS_POST, map, RegisterBean.class);
                    }
                });
            }
        } else if (data instanceof RegisterBean) {
            RegisterBean registerBean = (RegisterBean) data;
            if (registerBean.getStatus().equals("0000")) {
                Toast.makeText(this, registerBean.getMessage(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, registerBean.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }

    //失败
    @Override
    public void onIFailed(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    //设置popupwindow
    private void setPopupWindow() {
        View view = LayoutInflater.from(this).inflate(R.layout.all_address_popupwindow, null, false);
        //设置弹出窗口大小
        PopupWindow popupWindow = new PopupWindow(view, 1000, 640, true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //在中间弹出
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);
        backgroundAlpha(1f);
        popupWindow.showAsDropDown(view, 0, 0);
        //初始化popupwindow
        initpopup(view);
    }

    //初始化popupwindow
    private void initpopup(View view) {
        EditText mName = view.findViewById(R.id.pop_item_name);
        EditText mPhones = view.findViewById(R.id.pop_item_phone);
        EditText mRess = view.findViewById(R.id.pop_item_ress);
        EditText mCode = view.findViewById(R.id.pop_item_code);
        mName.setText(mRealName);
        mPhones.setText(mPhone);
        mRess.setText(mAddress);
        mCode.setText(mZipCode);
    }

    /**
     * 防止内存泄露
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mIPresenterImplement != null) {
            mIPresenterImplement = null;
        }
    }
}
