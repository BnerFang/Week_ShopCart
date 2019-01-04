package com.fsy.bawayshop.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fsy.bawayshop.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CircleActivity extends AppCompatActivity {

    @BindView(R.id.circle_header)
    TextView mCircleHeader;
    @BindView(R.id.circle_dele)
    ImageView mCircleDele;
    @BindView(R.id.circle_rv_r)
    RecyclerView mCircleRvR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.circle_dele)
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.circle_dele:
                break;
        }
    }
}
