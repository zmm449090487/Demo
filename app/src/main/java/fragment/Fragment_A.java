package fragment;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lenovo.myapplication.Main2Activity;
import com.example.lenovo.myapplication.Main3Activity;
import com.example.lenovo.myapplication.R;
import com.example.lenovo.myapplication.UploadingActivity;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import adapter.MyAdapter;
import bean.DatasBean;
import presenter.PresenterImpl;
import view.IView;

public class Fragment_A extends Fragment implements IView {
    private View view;
    private XRecyclerView mXrec;
    private MyAdapter adapter;
    private MyReceiver2 myReceiver2;
    private ArrayList<DatasBean> list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_a, null);
        initView(view);
        initData();
        return view;
    }

    private void initData() {
        PresenterImpl presenter = new PresenterImpl(this);
        presenter.updataData();
    }

    private static final String TAG = "Fragment_A";
    private void initView(View view) {
        mXrec = (XRecyclerView) view.findViewById(R.id.xrec);

        list = new ArrayList<>();
        mXrec.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new MyAdapter(getActivity(),list);
        mXrec.setAdapter(adapter);

        adapter.setOnItemClick(new MyAdapter.OnItemClick() {
            @Override
            public void OnClick(View view, int position) {
                DatasBean datasBean = list.get(position);
                String link = datasBean.getLink();

                Intent acc = new Intent("acc");
                acc.putExtra("link",link);
                getActivity().sendBroadcast(acc);
            }

            @Override
            public void OnClick2(View view, int position) {
                startActivity(new Intent(getActivity(),Main2Activity.class));
            }

            @Override
            public void OnLongClick(View view, int position) {
                startActivity(new Intent(getActivity(),UploadingActivity.class));
            }
        });
    }

    //广播接受者
    public class MyReceiver2 extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            String link = intent.getStringExtra("link");

            NotificationManager systemService = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
            Intent intent1 = new Intent(getActivity(), Main3Activity.class);
            Intent link1 = intent1.putExtra("link", link);
            PendingIntent activity = PendingIntent.getActivity(getActivity(), 20, link1, PendingIntent.FLAG_UPDATE_CURRENT);

            Notification notification = new NotificationCompat.Builder(getActivity(), "22")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("详情页通知")
                    .setContentText("点击跳转详情页")
                    .setAutoCancel(true)
                    .setContentIntent(activity)
                    .build();
            systemService.notify(1,notification);

        }
    }

    @Override
    public void updataSuccess(List<DatasBean> datasBeans) {
        adapter.addData(datasBeans);
    }

    @Override
    public void updataError(String error) {
        Log.d(TAG, "updataError: "+error);
    }

    @Override
    public void onResume() {
        super.onResume();
        myReceiver2 = new MyReceiver2();
        IntentFilter acc = new IntentFilter("acc");
        getActivity().registerReceiver(myReceiver2,acc);
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(myReceiver2);
    }
}
