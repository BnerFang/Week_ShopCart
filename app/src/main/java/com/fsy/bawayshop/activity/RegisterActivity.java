package com.fsy.bawayshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fsy.bawayshop.R;
import com.fsy.bawayshop.api.Apis;
import com.fsy.bawayshop.api.ParamsApis;
import com.fsy.bawayshop.bean.RegisterBean;
import com.fsy.bawayshop.mvp.presenter.IPresenterImplement;
import com.fsy.bawayshop.mvp.view.IView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity implements IView {

    @BindView(R.id.phone_iv_img)
    ImageView mPhoneIvImg;
    @BindView(R.id.reg_phone)
    EditText mRegPhone;
    @BindView(R.id.view_phone_reg)
    View mViewPhoneReg;
    @BindView(R.id.verification_reg)
    ImageView mVerificationReg;
    @BindView(R.id.reg_verification)
    EditText mRegVerification;
    @BindView(R.id.reg_hq_verification)
    TextView mRegHqVerification;
    @BindView(R.id.view_verification_reg)
    View mViewVerificationReg;
    @BindView(R.id.lock_reg)
    ImageView mLockReg;
    @BindView(R.id.reg_pwd)
    EditText mRegPwd;
    @BindView(R.id.view_pwd_reg)
    View mViewPwdReg;
    @BindView(R.id.reg_login_back)
    TextView mRegLoginBack;
    @BindView(R.id.reg_btn_go)
    Button mRegBtnGo;
    private IPresenterImplement mIPresenterImplement;
    private String phone;
    private Map<String, String> mMap;
    private String pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        mIPresenterImplement = new IPresenterImplement(this);
    }

    @OnClick({R.id.reg_phone, R.id.reg_verification, R.id.reg_hq_verification, R.id.reg_btn_go, R.id.reg_login_back})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.reg_phone:
                break;
            case R.id.reg_verification:
                break;
            case R.id.reg_hq_verification:
                break;
            case R.id.reg_btn_go:
                isMobileNO(phone);
                mMap = new HashMap<>();
                phone = mRegPhone.getText().toString().trim();
                pwd = mRegPwd.getText().toString().trim();
                mMap.put(ParamsApis.POST_REG_BODY_PHONE, phone);
                mMap.put(ParamsApis.POST_REG_BODY_PWD, pwd);
                mIPresenterImplement.onPostDatas(Apis.POST_REG_URL, mMap, RegisterBean.class);
                break;
            case R.id.reg_login_back:
                finish();
                break;
        }
    }

    public static boolean isMobileNO(String phones) {
        /**
         * 判断字符串是否符合手机号码格式
         * 移动号段: 134,135,136,137,138,139,147,150,151,152,157,158,159,170,178,182,183,184,187,188
         * 联通号段: 130,131,132,145,155,156,170,171,175,176,185,186
         * 电信号段: 133,149,153,170,173,177,180,181,189
         * @param str
         * @return 待检测的字符串
         */
        // "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        String telRegex = "^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$";
        if (TextUtils.isEmpty(phones)) {
            return false;
        } else {
            return phones.matches(telRegex);
        }
    }

    @Override
    public void onISuccess(Object data) {
        RegisterBean registerBean = (RegisterBean) data;
        if (registerBean.getStatus().equals("0000")) {
            Toast.makeText(this, registerBean.getMessage(), Toast.LENGTH_SHORT).show();
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        } else {
            Toast.makeText(this, registerBean.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onIFailed(String error) {

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
