package com.example.lenovo.myapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTv;
    /**
     * 下载
     */
    private Button mClick;
    private MyReceiver myReceiver;
    private ProgressBar mPb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);//张苗苗
        initView();
        //注册
        EventBus.getDefault().register(this);

    }



    //EventBus
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void EventBus(MyEventBus myEventBus){
        long l = myEventBus.l;
        mPb.setProgress((int) l);
        mTv.setText("当前进度为："+(int) l+"%");
    }


    private void initView() {
        mTv = (TextView) findViewById(R.id.tv);
        mClick = (Button) findViewById(R.id.Click);
        mClick.setOnClickListener(this);
        mPb = (ProgressBar) findViewById(R.id.pb);
        mTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.Click:
                startService(new Intent(this,MyService.class));
                break;
            case R.id.tv:
                break;
        }
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        myReceiver = new MyReceiver();
//        IntentFilter action = new IntentFilter("action");
//        registerReceiver(myReceiver, action);
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        unregisterReceiver(myReceiver);
//    }

    private static final String TAG = "Main2Activity";
    /*public class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, final Intent intent) {
            final long l = intent.getLongExtra("l", 1);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mPb.setProgress((int) l);
                    mTv.setText("当前进度为："+l+"%");
                    Log.d(TAG, "run: "+l+"%");

                    if (l == 100){
                        NotificationManager systemService = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                        Intent intent1 = new Intent(Main2Activity.this, NotifiActivity.class);
                        PendingIntent activity = PendingIntent.getActivity(Main2Activity.this, 20, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
                        Notification notification = new NotificationCompat.Builder(Main2Activity.this, "11")
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setContentTitle("通知")
                                .setContentText("下载完成")
                                .setAutoCancel(true)
                                .setContentIntent(activity)
                                .build();
                        systemService.notify(1,notification);
                    }
                }
            });

        }
    }*/

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
