package com.example.lenovo.demoo2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import adapter.MyAdapter;
import bean.Demo;
import db.DBUtlis;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRec;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//张苗苗
        initView();
        initData();
    }

    private void initData() {
        for (int i = 0; i < 20; i++) {
            DBUtlis.insert(new Demo(R.mipmap.ic_launcher,"标题"+i));
        }
        List<Demo> demos = DBUtlis.queryArr();
        adapter.addData(demos);
    }

    private void initView() {
        mRec = (RecyclerView) findViewById(R.id.rec);

        mRec.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter(this);
        mRec.setAdapter(adapter);
    }
}
