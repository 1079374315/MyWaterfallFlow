package com.qyt.hp.mywaterfallflow.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qyt.hp.mywaterfallflow.R;
import com.qyt.hp.mywaterfallflow.bean.TJBean;
import com.qyt.hp.mywaterfallflow.util.GT;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private Context context;
    private List<TJBean.DataBean> list;
    private GT.Window window;

    public MainAdapter(Context context) {
        this.context = context;
        window = new GT.Window((AppCompatActivity) context);
    }

    public List<TJBean.DataBean> getList() {
        return list;
    }

    public void setList(List<TJBean.DataBean> list) {
     if(this.list == null){
         this.list = list;
     }
     else{
         this.list.addAll(this.list.size(),list);
     }
     notifyDataSetChanged();//刷新
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_main, viewGroup, false);
        return new ViewHolder(view, context);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        TJBean.DataBean dataBean = list.get(i);

        GT.log_v("进入适配器:" + i + ": " + dataBean.getTitle());

        String title = dataBean.getTitle();
        String createdTime = dataBean.getCreatedTime();
        String thumb = dataBean.getThumb();
        viewHolder.itemTitle.setText(title);
        viewHolder.itemTitme.setText(createdTime);

        int w = window.getWindowWidth();


        ViewGroup.LayoutParams layoutParams = viewHolder.itemImg.getLayoutParams();
        layoutParams.width = w/2;
        layoutParams.height = (int) (200 + Math.random() * 400);
        viewHolder.itemImg.setLayoutParams(layoutParams);

        if(thumb != null){
            Glide.with(context).asDrawable().load(thumb).into(viewHolder.itemImg);
        }else{
            String thumb2 = dataBean.getThumb2();
            if(thumb2 != null){
                Glide.with(context).asDrawable().load(thumb2).into(viewHolder.itemImg);
            }else{
                viewHolder.itemImg.setVisibility(View.INVISIBLE);
            }
        }


    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_img)
        ImageView itemImg;
        @BindView(R.id.item_title)
        TextView itemTitle;
        @BindView(R.id.item_titme)
        TextView itemTitme;

        public ViewHolder(@NonNull View itemView, final Context context) {
            super(itemView);
            ButterKnife.bind(this, itemView);


        }
    }

}
