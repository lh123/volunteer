package com.yangtze.volunteer.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yangtze.volunteer.R;
import com.yangtze.volunteer.model.bean.User;
import com.yangtze.volunteer.ui.adapter.ActiveRecyclerViewAdapter;
import com.yangtze.volunteer.ui.adapter.RankRecyclerViewAdapter;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by liuhui on 2016/3/12.
 */
public class RankFragment extends Fragment
{
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private RankRecyclerViewAdapter adapter;

    private List<User> data;
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.recyclerview,container,false);
        swipeRefreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        recyclerView= (RecyclerView) view.findViewById(R.id.recycler_view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        initRecyclerView();
        initData();
    }

    private void initData()
    {
        if(data==null)
        {
            refreshData();
        }
        else
        {
            adapter.setList(data);
            adapter.notifyDataSetChanged();
            swipeRefreshLayout.post(new Runnable(){

                    @Override
                    public void run()
                    {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
        }
    }
    
    private void refreshData()
    {
        swipeRefreshLayout.post(new Runnable(){

                @Override
                public void run()
                {
                    swipeRefreshLayout.setRefreshing(true);
                }
            });
        BmobQuery<User> query =new BmobQuery<>();
        query.setLimit(50);
        query.order("-coint");
        query.findObjects(getContext(), new FindListener<User>()
        {
            @Override
            public void onSuccess(List<User> list)
            {
                data=list;
                adapter.setList(list);
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onError(int i, String s)
            {
                Toast.makeText(getContext(),"加载失败",Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void initRecyclerView()
    {
        LinearLayoutManager lm=new LinearLayoutManager(getContext());
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        adapter=new RankRecyclerViewAdapter();
        recyclerView.setLayoutManager(lm);
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout.post(new Runnable()
        {
            @Override
            public void run()
            {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                refreshData();
            }
        });
    }
}
