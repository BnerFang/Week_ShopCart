package com.fsy.bawayshop.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.fsy.bawayshop.R;
import com.fsy.bawayshop.api.Apis;
import com.fsy.bawayshop.api.ParamsApis;
import com.fsy.bawayshop.loginAndregister.bean.RegisterBean;
import com.fsy.bawayshop.mvp.presenter.IPresenterImplement;
import com.fsy.bawayshop.mvp.view.IView;
import com.lljjcoder.citypickerview.widget.CityPicker;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 添加收货地址
 */
public class AllRessActivity extends AppCompatActivity implements IView {

    @BindView(R.id.all_item_name)
    EditText mAllItemName;
    @BindView(R.id.all_item_phone)
    EditText mAllItemPhone;
    @BindView(R.id.all_item_ress)
    EditText mAllItemRess;
    @BindView(R.id.all_item_code)
    EditText mAllItemCode;
    @BindView(R.id.all_item_btn)
    Button mAllItemBtn;
    @BindView(R.id.all_item_img)
    ImageView mAllItemImg;
    private IPresenterImplement mIPresenterImplement;
    private String name;
    private String phone;
    private String ress;
    private String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_ress);
        ButterKnife.bind(this);
        mIPresenterImplement = new IPresenterImplement(this);

    }

    @OnClick({R.id.all_item_btn, R.id.all_item_img})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.all_item_btn:
                name = mAllItemName.getText().toString().trim();
                phone = mAllItemPhone.getText().toString().trim();
                ress = mAllItemRess.getText().toString().trim();
                code = mAllItemCode.getText().toString().trim();
                Map<String, String> map = new HashMap<>();
                map.put(ParamsApis.POST_RESS_NAME_KEY, name);
                map.put(ParamsApis.POST_RESS_PHONE_KEY, phone);
                map.put(ParamsApis.POST_RESS_ADDRESS_KEY, ress);
                map.put(ParamsApis.POST_RESS_CODE_KEY, code);
                //访问presenter层接口
                mIPresenterImplement.onPostDatas(Apis.URL_ADD_RECEIVE_ADDRESS_POST, map, RegisterBean.class);
                break;
            case R.id.all_item_img:
                selectAddress();//调用CityPicker选取区域
                break;
        }
    }


    //省市区三级联动
    private void selectAddress() {
        CityPicker cityPicker = new CityPicker.Builder(this)
                .textSize(14)
                .title("地址选择")
                .titleBackgroundColor("#FFFFFF")
                .confirTextColor("#696969")
                .cancelTextColor("#696969")
                .province("江苏省")
                .city("常州市")
                .district("天宁区")
                .textColor(Color.parseColor("#000000"))
                .provinceCyclic(true)
                .cityCyclic(false)
                .districtCyclic(false)
                .visibleItemsCount(7)
                .itemPadding(10)
                .onlyShowProvinceAndCity(false)
                .build();
        cityPicker.show();
        //监听方法，获取选择结果
        cityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
            @Override
            public void onSelected(String... citySelected) {
                //省份
                String province = citySelected[0];
                //城市
                String city = citySelected[1];
                //区县（如果设定了两级联动，那么该项返回空）
                String district = citySelected[2];
                //邮编
                String code = citySelected[3];
                //为TextView赋值
                mAllItemRess.setText(province.trim() + "-" + city.trim() + "-" + district.trim());
            }
        });
    }

    //成功
    @Override
    public void onISuccess(Object data) {
        if (data instanceof RegisterBean) {
            RegisterBean bean = (RegisterBean) data;
            if (bean.getStatus().equals("0000")) {
                Toast.makeText(this, bean.getMessage(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, AddressActivity.class);
                intent.putExtra("realName",name);
                intent.putExtra("phone",phone);
                intent.putExtra("address",ress);
                intent.putExtra("zipCode",code);
                startActivity(intent);
                finish();
            }
        }
    }

    //失败
    @Override
    public void onIFailed(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
}
