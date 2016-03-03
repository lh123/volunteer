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

import com.yangtze.volunteer.R;
import com.yangtze.volunteer.ui.adapter.ActiveFollowRecyclerViewAdapter;

/**
 * Created by liuhui on 2016/3/3.
 */
public class ActiveFollowFragment extends Fragment
{
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.recyclerview,container,false);
        swipeRefreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        recyclerView= (RecyclerView) view.findViewById(R.id.recycler_view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        initRecyclerView();
    }

    private void initRecyclerView()
    {
        LinearLayoutManager lm=new LinearLayoutManager(getContext());
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        ActiveFollowRecyclerViewAdapter activeFollowRecyclerViewAdapter=new ActiveFollowRecyclerViewAdapter();
        recyclerView.setAdapter(activeFollowRecyclerViewAdapter);
        recyclerView.setLayoutManager(lm);
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

            }
        });
    }


}
