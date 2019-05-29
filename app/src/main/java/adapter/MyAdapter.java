package adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lenovo.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import bean.DatasBean;
import bean.Demo;

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<DatasBean> list;

    public MyAdapter(Context context,ArrayList<DatasBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == 0){
            View view = LayoutInflater.from(context).inflate(R.layout.xrec_tu_layout,null);
            return new ViewHolder2(view);
        }else {
            View view = LayoutInflater.from(context).inflate(R.layout.xrec_item_layout,null);
            return new ViewHolder1(view);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        int itemViewType = getItemViewType(i);
        if (itemViewType == 0){
            ViewHolder2 holder2 = (ViewHolder2) viewHolder;
            Glide.with(context).load(list.get(i).getEnvelopePic()).into(holder2.img2);

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick.OnClick(v,i);
                }
            });
        }else {
            ViewHolder1 holder1 = (ViewHolder1) viewHolder;
            Glide.with(context).load(list.get(i).getEnvelopePic()).into(holder1.img);
            holder1.title.setText(list.get(i).getTitle());

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick.OnClick2(v,i);
                }
            });
        }
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onItemClick.OnLongClick(v,i);
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 3 || position == 10 || position == 18) {
            return 0;
        }else {
            return 1;
        }
    }

    public void addData(List<DatasBean> datasBeans) {
        if (list != null){
            list.clear();
            list.addAll(datasBeans);
        }
        notifyDataSetChanged();
    }

    class ViewHolder1 extends RecyclerView.ViewHolder{
        ImageView img;
        TextView title;
        public ViewHolder1(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            title = itemView.findViewById(R.id.title);
        }
    }

    class ViewHolder2 extends RecyclerView.ViewHolder{
        ImageView img2;
        public ViewHolder2(@NonNull View itemView) {
            super(itemView);
            img2 = itemView.findViewById(R.id.img2);
        }
    }
    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public interface OnItemClick{
        void OnClick(View view,int position);
        void OnClick2(View view,int position);
        void OnLongClick(View view,int position);
    }
}
