package com.example.lenovo.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ThreadTack implements Runnable{

    private static final String TAG = "ThreadTack";
    private String apk_url = "http://cdn.banmi.com/banmiapp/apk/banmi_330.apk";

    private Context context;
    public ThreadTack(Context context) {
        this.context = context;
    }

    @Override
    public void run() {
        String fileName = apk_url.substring(apk_url.lastIndexOf("/"), apk_url.length());
        String filePath = Environment.getExternalStorageDirectory() + File.separator + fileName;
        final File file = new File(filePath);

        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(apk_url)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: "+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody body = response.body();
                InputStream inputStream = body.byteStream();
                long maxLength = body.contentLength();
                FileOutputStream outputStream = new FileOutputStream(file);
                byte[] by = new byte[1024*4];
                int len = 0;
                int readLength = 0;
                while ((len = inputStream.read(by))!= -1){
                    outputStream.write(by,0,len);
                    readLength += len;
                    long l = (readLength * 100) / maxLength;
                    Log.d(TAG, "onResponse: "+l);

//                    Intent action = new Intent("action");
//                    action.putExtra("l",l);
//                    context.sendBroadcast(action);
                    //发送数据
                    EventBus.getDefault().post(new MyEventBus(l));

                }
                inputStream.close();
                outputStream.close();

            }
        });
    }
}
