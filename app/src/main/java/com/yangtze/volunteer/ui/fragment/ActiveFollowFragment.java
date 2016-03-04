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
import com.yangtze.volunteer.model.bean.User;
import com.yangtze.volunteer.model.bean.VolunteerActive;
import com.yangtze.volunteer.ui.ActiveDetailActivity;
import com.yangtze.volunteer.ui.adapter.ActiveFollowRecyclerViewAdapter;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.BmobUser;

/**
 * Created by liuhui on 2016/3/3.
 */
public class ActiveFollowFragment extends Fragment
{
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private ActiveFollowRecyclerViewAdapter adapter;
    private boolean canExit=false;

    public void setCanExit(boolean canExit)
    {
        this.canExit = canExit;
    }

    public boolean isCanExit()
    {
        return canExit;
    }

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
        refreshData();
    }

    public void refreshData()
    {
        BmobQuery<User> query=new BmobQuery<>();
        final VolunteerActive active = ((ActiveDetailActivity) getActivity()).getActive();
        query.addWhereRelatedTo("attendee",new BmobPointer(active));
        query.findObjects(getContext(), new FindListener<User>()
        {
            @Override
            public void onSuccess(List<User> list)
            {
                canExit=false;
                adapter.setList(list);
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
                for(User u:list)
                {
                    if(u.getObjectId().equals(BmobUser.getCurrentUser(getContext()).getObjectId()))
                    {
                        canExit=true;
                        break;
                    }
                }
                if(canExit)
                {
                    ((ActiveDetailActivity)getActivity()).setBtnText("退出");
                }
                else
                {
                    ((ActiveDetailActivity)getActivity()).setBtnText("加入");
                }
                ((ActiveDetailActivity)getActivity()).hidePb();
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
        adapter = new ActiveFollowRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);
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
                refreshData();
            }
        });
    }


}
