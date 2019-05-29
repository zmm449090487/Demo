package com.example.lenovo.myapplication;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import fragment.Fragment_A;
import fragment.Fragment_B;

public class MainActivity extends AppCompatActivity {

    private FrameLayout mFl;
    private TabLayout mTl;
    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initTab();
        initFragment();
    }

    private void initFragment() {
        fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fl,new Fragment_A());
        transaction.commit();
    }

    private void initTab() {
        mTl.addTab(mTl.newTab().setText("首页"));
        mTl.addTab(mTl.newTab().setText("我的"));
        mTl.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (position == 0){
                    FragmentTransaction transaction = fm.beginTransaction();
                    transaction.replace(R.id.fl,new Fragment_A());
                    transaction.commit();
                }else {
                    FragmentTransaction transaction = fm.beginTransaction();
                    transaction.replace(R.id.fl,new Fragment_B());
                    transaction.commit();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initView() {
        mFl = (FrameLayout) findViewById(R.id.fl);
        mTl = (TabLayout) findViewById(R.id.tl);
    }
}
