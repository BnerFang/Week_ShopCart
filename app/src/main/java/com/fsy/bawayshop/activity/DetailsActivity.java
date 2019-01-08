package com.fsy.bawayshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.fsy.bawayshop.R;
import com.fsy.bawayshop.fragment.DetailsCommentaryFragment;
import com.fsy.bawayshop.fragment.DetailsFragment;
import com.fsy.bawayshop.fragment.DetailsShopFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 详情页面
 */
public class DetailsActivity extends AppCompatActivity {

    @BindView(R.id.details_back)
    ImageView mDetailsBack;
    @BindView(R.id.details_tab)
    TabLayout mDetailsTab;
    @BindView(R.id.details_layout)
    RelativeLayout mDetailsLayout;
    @BindView(R.id.details_vp)
    ViewPager mDetailsVp;
    private List<Fragment> mFragments;
    private String[] titleUrl = {
      "商品","详情","评论"
    };
    private MyDetailsAdapter mMyDetailsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String commodityId = intent.getStringExtra("commodityId");
        mFragments = new ArrayList<>();
        for (int i = 0; i < titleUrl.length; i++) {
            mFragments.add(new DetailsShopFragment());
            mFragments.add(new DetailsFragment());
            mFragments.add(new DetailsCommentaryFragment());
        }

        mMyDetailsAdapter = new MyDetailsAdapter(getSupportFragmentManager());
        mDetailsVp.setAdapter(mMyDetailsAdapter);
        mDetailsTab.setupWithViewPager(mDetailsVp);
    }

    class MyDetailsAdapter extends FragmentPagerAdapter {

        public MyDetailsAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return mFragments.get(i);
        }

        @Override
        public int getCount() {
            return titleUrl.length;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titleUrl[position];
        }

    }

    @OnClick(R.id.details_back)
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.details_back:
                this.finish();
                break;
        }
    }
}
