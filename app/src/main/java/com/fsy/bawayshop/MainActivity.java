package com.fsy.bawayshop;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.fsy.bawayshop.fragment.CirclesFragment;
import com.fsy.bawayshop.fragment.HomePageFragment;
import com.fsy.bawayshop.fragment.MyFragment;
import com.fsy.bawayshop.fragment.OrderFragment;
import com.fsy.bawayshop.fragment.ShoppingCartFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {


    private HomePageFragment mHomePageFragment;
    private CirclesFragment mCirclesFragment;
    private ShoppingCartFragment mShoppingCartFragment;
    private OrderFragment mOrderFragment;
    private MyFragment mMyFragment;
    private ViewPager mMainVp;
    private RadioGroup mMainRadiogroup;
    private List<Fragment> list;
    private FragmentManager mManager;
    private MyFragmentAdpter mMyFragmentAdpter;
    private FragmentTransaction fragmentTransaction;
    private RadioButton mMainRadioHomepage;
    private RadioButton mMainRadioCircles;
    private RadioButton mMainRadioShoppingcart;
    private RadioButton mMainRadioOrder;
    private RadioButton mMainRadioMy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //全屏沉浸式
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        int userId = intent.getIntExtra("userId",0);
        String sessionId = intent.getStringExtra("sessionId");
        String nickName = intent.getStringExtra("nickName");
        String headPic = intent.getStringExtra("headPic");
        String phone = intent.getStringExtra("phone");
        initView();
        //获取管理工具
        mManager = getSupportFragmentManager();
        mHomePageFragment = new HomePageFragment();
        mCirclesFragment = new CirclesFragment();
        mShoppingCartFragment = new ShoppingCartFragment();
        mOrderFragment = new OrderFragment();
        mMyFragment = new MyFragment();
        list = new ArrayList<>();
        list.add(mHomePageFragment);
        list.add(mCirclesFragment);
        list.add(mShoppingCartFragment);
        list.add(mOrderFragment);
        list.add(mMyFragment);
        mMyFragmentAdpter = new MyFragmentAdpter(mManager);

        mMainRadiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //开启事务
                fragmentTransaction = mManager.beginTransaction();
                switch (checkedId) {
                    case R.id.main_radio_homepage:
                        //fragmentTransaction.replace(R.id.viewpager, firstFragment);
                        mMainVp.setCurrentItem(0);
                        break;
                    case R.id.main_radio_circles:
                        mMainVp.setCurrentItem(1);
                        break;
                    case R.id.main_radio_shoppingcart:
                        mMainVp.setCurrentItem(2);
                        break;
                    case R.id.main_radio_order:
                        mMainVp.setCurrentItem(3);
                        break;
                    case R.id.main_radio_my:
                        mMainVp.setCurrentItem(4);
                        break;
                }
                //提交数据
                fragmentTransaction.commit();
            }
        });
        mMainVp.setAdapter(mMyFragmentAdpter);
        mMainVp.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                mMainRadiogroup.check(R.id.main_radio_homepage);
                break;
            case 1:
                mMainRadiogroup.check(R.id.main_radio_circles);
                break;
            case 2:
                mMainRadiogroup.check(R.id.main_radio_shoppingcart);
                break;
            case 3:
                mMainRadiogroup.check(R.id.main_radio_order);
                break;
            case 4:
                mMainRadiogroup.check(R.id.main_radio_my);
                break;

        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void initView() {
        mMainRadioHomepage = (RadioButton) findViewById(R.id.main_radio_homepage);
        mMainRadioCircles = (RadioButton) findViewById(R.id.main_radio_circles);
        mMainRadioShoppingcart = (RadioButton) findViewById(R.id.main_radio_shoppingcart);
        mMainRadioOrder = (RadioButton) findViewById(R.id.main_radio_order);
        mMainRadioMy = (RadioButton) findViewById(R.id.main_radio_my);
        mMainVp = (ViewPager) findViewById(R.id.main_vp);
        mMainRadiogroup = (RadioGroup) findViewById(R.id.main_radiogroup);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    public class MyFragmentAdpter extends FragmentPagerAdapter {
        public MyFragmentAdpter(FragmentManager manager) {
            super(manager);

        }

        @Override
        public Fragment getItem(int i) {
            return list.get(i);
        }

        @Override
        public int getCount() {
            return list.size();
        }
    }
}
