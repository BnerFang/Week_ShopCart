package com.fsy.bawayshop.loginAndregister.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fsy.bawayshop.MainActivity;
import com.fsy.bawayshop.R;
import com.fsy.bawayshop.api.Apis;
import com.fsy.bawayshop.api.ParamsApis;
import com.fsy.bawayshop.loginAndregister.bean.LoginBean;
import com.fsy.bawayshop.mvp.presenter.IPresenterImplement;
import com.fsy.bawayshop.mvp.view.IView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements IView {

    @BindView(R.id.phone_login)
    ImageView mPhoneLogin;
    @BindView(R.id.login_phone)
    EditText mLoginPhone;
    @BindView(R.id.login_pwd)
    EditText mLoginPwd;
    @BindView(R.id.login_hint)
    ImageView mLoginHint;
    @BindView(R.id.view_pwd)
    View mViewPwd;
    @BindView(R.id.login_checkbox)
    CheckBox mLoginCheckbox;
    @BindView(R.id.login_reg)
    TextView mLoginReg;
    @BindView(R.id.login_btn_go)
    Button mLoginBtnGo;
    private IPresenterImplement mIPresenterImplement;
    private Map<String, String> mMap;
    private String mPhone;
    private String mPwd;
    private boolean showPassword = true;
    private SharedPreferences.Editor mEdit;
    private SharedPreferences mSP;
    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //注册 ButterKnife
        ButterKnife.bind(this);
        mIPresenterImplement = new IPresenterImplement(this);
        //创建SharedPreferences储存数据
        mSP = getSharedPreferences("config", MODE_PRIVATE);
        boolean isCheck = mSP.getBoolean("isCheck", false);//是否记住密码
        mEdit = mSP.edit();
        if (isCheck) {
            mLoginPhone.setText(mSP.getString("phone", mPhone));
            mLoginPwd.setText(mSP.getString("pwd", mPwd));
            mLoginCheckbox.setChecked(true);
        }

    }


    @OnClick({R.id.login_hint, R.id.login_reg, R.id.login_btn_go})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.login_hint:
                if (showPassword) {// 显示密码
                    mLoginHint.setImageDrawable(getResources().getDrawable(R.mipmap.login_icon_eye_n));
                    mLoginPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    mLoginPwd.setSelection(mLoginPwd.getText().toString().length());
                    showPassword = !showPassword;
                } else {// 隐藏密码
                    mLoginHint.setImageDrawable(getResources().getDrawable(R.mipmap.login_icon_eye_y));
                    mLoginPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    mLoginPwd.setSelection(mLoginPwd.getText().toString().length());
                    showPassword = !showPassword;
                }
                break;
            case R.id.login_reg:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
            case R.id.login_btn_go:
                isMobileNO(mPhone);
                mMap = new HashMap<>();
                mPhone = mLoginPhone.getText().toString().trim();
                mPwd = mLoginPwd.getText().toString().trim();
                mMap.put(ParamsApis.POST_LOGIN_BODY_PHONE, mPhone);
                mMap.put(ParamsApis.POST_LOGIN_BODY_PWD, mPwd);
                mIPresenterImplement.onPostDatas(Apis.POST_LOGIN_URL, mMap, LoginBean.class);
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
        LoginBean loginBean = (LoginBean) data;
        LoginBean.Results result = loginBean.getResults();
        if (loginBean.getStatus().equals("0000")) {
            if (mLoginCheckbox.isChecked()) {
                mEdit.putBoolean("isCheck", true);
                mEdit.putString("phone", mPhone);
                mEdit.putString("pwd", mPwd);
                mEdit.commit();
            } else {//清除记住密码
                mEdit.clear();
                mEdit.commit();
            }
            Toast.makeText(this, loginBean.getMessage(), Toast.LENGTH_SHORT).show();
            mIntent = new Intent(this, MainActivity.class);
            mIntent.putExtra("userId", result.getUserId());
            mIntent.putExtra("sessionId", result.getSessionId());
            mIntent.putExtra("nickName", result.getNickName());
            mIntent.putExtra("headPic", result.getHeadPic());
            mIntent.putExtra("phone", result.getPhone());
            startActivity(mIntent);
            mSP.edit()
                    .putString("userId", loginBean.getResults().getUserId() + "")
                    .putString("sessionId", loginBean.getResults().getSessionId())
                    .commit();
            finish();
        } else {
            Toast.makeText(this, loginBean.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onIFailed(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
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
