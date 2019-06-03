package com.qyt.hp.mywaterfallflow;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ProgressBar;

import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.qyt.hp.mywaterfallflow.adapter.MainAdapter;
import com.qyt.hp.mywaterfallflow.bean.TJBean;
import com.qyt.hp.mywaterfallflow.util.GT;
import com.qyt.hp.mywaterfallflow.util.MyApp;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.main_recycler)
    RecyclerView mainRecycler;
    @BindView(R.id.main_SmartRefreshLayout)
    SmartRefreshLayout mainSmartRefreshLayout;
    @BindView(R.id.main_ProgressBar)
    ProgressBar mainProgressBar;
    private MainAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mainRecycler.setHasFixedSize(true);
        mainRecycler.setLayoutManager(new StaggeredGridLayoutManager(2,  StaggeredGridLayoutManager.VERTICAL));
        adapter = new MainAdapter(this);
        mainRecycler.setAdapter(adapter);
        loadData();//加载数据
        refresh();//刷新
    }

    //加载数据
    private int page = 1;
    private void loadData() {

        Map<String,String> map = new HashMap<>();
        map.put("service","App.Mixed_Cnfol.Hjzx");
        map.put("channel","tj");
        map.put("page",String.valueOf(page));

        new GT.OkGo(MyApp.URL,map).loadData(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                GT.log_v("加载数据成功!");
                GT.JSON json = new GT.JSON(response.body());
                int code = json.getCode();
                GT.log_v("Code:" + code);
                if(code == 200){
                    TJBean bean = (TJBean) json.getBean(TJBean.class);
                    List<TJBean.DataBean> data = bean.getData();
                    if(data != null){
                        if(mainProgressBar != null){
                            mainProgressBar.setVisibility(View.INVISIBLE);
                        }
                      adapter.setList(data);
                    }

                }

            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                GT.log_v("加载数据失败!");
            }
        });
        closeRefresh();
    }


    //刷新、加载
    private void refresh() {

        mainSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                GT.log_v("下拉刷新");
                page = 1;
                loadData();
            }
        });

        mainSmartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                GT.log_v("上拉加载");
                page++;
                loadData();
            }
        });

    }

    private void closeRefresh(){
        if(mainSmartRefreshLayout != null){
            mainSmartRefreshLayout.finishRefresh();//结束刷新
            mainSmartRefreshLayout.finishLoadMore();//结束加载
        }
    }

}
