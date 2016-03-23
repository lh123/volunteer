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
import com.yangtze.volunteer.model.bean.VolunteerActive;
import com.yangtze.volunteer.ui.adapter.ActiveRecyclerViewAdapter;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by liuhui on 2016/3/3.
 */
public class JointedFragment extends Fragment
{
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private ActiveRecyclerViewAdapter adapter;
    
    private List<VolunteerActive> data;

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
        if(data==null)
        {
            refreshData();
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
        User user= BmobUser.getCurrentUser(getContext(),User.class);
        if(user==null)
        {
            Toast.makeText(getContext(),"未登录",Toast.LENGTH_SHORT).show();
            swipeRefreshLayout.setRefreshing(false);
            return;
        }
        BmobQuery<VolunteerActive> query=new BmobQuery<>();
        BmobQuery<User> innerQuery=new BmobQuery<>();
        innerQuery.addWhereEqualTo("objectId",user.getObjectId());
        query.addWhereMatchesQuery("attendee","_User",innerQuery);
        query.findObjects(getContext(), new FindListener<VolunteerActive>()
        {
            @Override
            public void onSuccess(List<VolunteerActive> list)
            {
                data=list;
                adapter.setList(list);
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onError(int i, String s)
            {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }



    private void initRecyclerView()
    {
        LinearLayoutManager lm=new LinearLayoutManager(getContext());
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        adapter=new ActiveRecyclerViewAdapter();
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
