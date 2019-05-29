package com.example.lenovo.myapplication;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

import api.ApiService;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class UploadingActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * ok上传
     */
    private Button mOk;
    /**
     * retrofit上传
     */
    private Button mRetrofit;
    private TextView mShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploading);
        initView();
    }

    private void initView() {
        mOk = (Button) findViewById(R.id.ok);
        mOk.setOnClickListener(this);
        mRetrofit = (Button) findViewById(R.id.retrofit);
        mRetrofit.setOnClickListener(this);
        mShow = (TextView) findViewById(R.id.show);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.ok:
                initOk();
                break;
            case R.id.retrofit:
                initRetrofit();
                break;
        }
    }

    private void initRetrofit() {
        String filePath = Environment.getExternalStorageDirectory() + File.separator;
        File file = new File(filePath + "aar.png");
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);

        RequestBody fileRequestBody = RequestBody.create(MediaType.parse("application/octet-stream"), "ss");

        MultipartBody.Part keyRequestBody = MultipartBody.Part.createFormData("file", file.getName(), requestBody);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.uploadingUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        apiService.getRetrofit(fileRequestBody,keyRequestBody).enqueue(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(retrofit2.Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                try {
                    String string = response.body().string();
                    Log.d(TAG, "onResponse: "+string);
                    mShow.setText(string);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {

            }
        });


    }

    private static final String TAG = "UploadingActivity";
    private void initOk() {
        String filePath = Environment.getExternalStorageDirectory() + File.separator;
        File file = new File(filePath + "ball.jpg");

        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), file);
        MultipartBody multipartBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("key", "aaa")
                .addFormDataPart("file", file.getName(), requestBody)
                .build();

        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://yun918.cn/study/public/file_upload.php")
                .post(multipartBody)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: "+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String string = response.body().string();
                Log.d(TAG, "onResponse: "+string);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mShow.setText(string);
                    }
                });
            }
        });
    }
}
