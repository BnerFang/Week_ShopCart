package com.fsy.bawayshop.activity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fsy.bawayshop.MainActivity;
import com.fsy.bawayshop.R;
import com.fsy.bawayshop.adapter.MyHomePageAdapter;

import java.util.ArrayList;
import java.util.List;


public class HomePageActivity extends AppCompatActivity {

    private ViewPager viewPage;
    // 图片
    private int[] imageView = {R.drawable.home_page_one, R.drawable.home_page_two, R.drawable.home_page_three,
            R.drawable.home_page_four, R.drawable.home_page_five };
    private List<View> list;
    // 底部小点的图片
    private LinearLayout llPoint;
    //立即进入按钮
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        //初始化控件
        initview();
        //点击跳转监听
        initoper();
        //添加图片到view
        addView();
        //添加小圆点
        addPoint();
        //SharedPreferences 判断是否第一次进入
        //shipToNavigationOrFrame();

    }
    //判断且实现应跳转导航动画还是主界面
    private void shipToNavigationOrFrame(){
        boolean firstFlag; //是否首次安装
        SharedPreferences sharedPreferences = getSharedPreferences("flag", MODE_PRIVATE);
        firstFlag = sharedPreferences.getBoolean("first", true);

        final Intent intent = new Intent();
        if (firstFlag){
            intent.setClass(this,LoginActivity.class);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("first", false);
            editor.apply(); //apply与commit作用相同，虽没返回值，但效率更高
        }else {
            intent.setClass(this, MainActivity.class);
        }


        new Handler().postDelayed(new Runnable() { //延时1.5秒
            @Override
            public void run() {
                startActivity(intent);
                HomePageActivity.this.finish();
            }
        },0);
    }

    //点击跳转监听
    private void initoper() {
        // 进入按钮
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                startActivity(new Intent(HomePageActivity.this,LoginActivity.class));
                finish();
            }
        });

        // 2.监听当前显示的页面，将对应的小圆点设置为选中状态，其它设置为未选中状态
        viewPage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                monitorPoint(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });

    }

    //初始化控件
    private void initview() {
        viewPage = (ViewPager) findViewById(R.id.viewpage);
        llPoint = (LinearLayout) findViewById(R.id.llPoint);
        textView = (TextView) findViewById(R.id.guideTv);

    }

    /**
     * 添加图片到view
     */
    private void addView() {
        list = new ArrayList<View>();
        // 将imageview添加到view
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        for (int i = 0; i < imageView.length; i++) {
            ImageView iv = new ImageView(this);
            iv.setLayoutParams(params);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            iv.setImageResource(imageView[i]);
            list.add(iv);
        }
        // 加入适配器
        viewPage.setAdapter(new MyHomePageAdapter(list));

    }

    /**
     * 添加小圆点
     */
    private void addPoint() {
        // 1.根据图片多少，添加多少小圆点
        for (int i = 0; i < imageView.length; i++) {
            LinearLayout.LayoutParams pointParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            if (i < 1) {
                pointParams.setMargins(0, 0, 0, 0);
            } else {
                pointParams.setMargins(5, 0, 0, 0);
            }
            ImageView iv = new ImageView(this);
            iv.setLayoutParams(pointParams);
            iv.setBackgroundResource(R.drawable.point_normal);
            llPoint.addView(iv);
        }
        llPoint.getChildAt(0).setBackgroundResource(R.drawable.point_select);

    }

    /**
     * 判断小圆点
     *
     * @param position
     */
    private void monitorPoint(int position) {
        for (int i = 0; i < imageView.length; i++) {
            if (i == position) {
                llPoint.getChildAt(position).setBackgroundResource(
                        R.drawable.point_select);
            } else {
                llPoint.getChildAt(i).setBackgroundResource(
                        R.drawable.point_normal);
            }
        }
        // 3.当滑动到最后一个添加按钮点击进入，
        if (position == imageView.length - 1) {
            textView.setVisibility(View.VISIBLE);
        } else {
            textView.setVisibility(View.GONE);
        }
    }


    // oppo手机安装APk密码  159870asd

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.finish();
    }
}
