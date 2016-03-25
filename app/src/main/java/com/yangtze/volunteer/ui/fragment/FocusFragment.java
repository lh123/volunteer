package com.yangtze.volunteer.ui.fragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import com.yangtze.volunteer.App;
import com.yangtze.volunteer.R;
import com.yangtze.volunteer.model.NewsModel;
import com.yangtze.volunteer.model.bean.NewsItem;
import com.yangtze.volunteer.ui.adapter.NewsRecyclerViewAdapter;
import java.util.ArrayList;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class FocusFragment extends Fragment
{
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private ArrayList<NewsItem> list;

    private NewsRecyclerViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.recyclerview, container, false);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        LinearLayoutManager lm=new LinearLayoutManager(getContext());
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(lm);
        SlideInUpAnimator slideInUpAnimator = new SlideInUpAnimator();
        slideInUpAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        //slideInUpAnimator.setAddDuration(500);
        recyclerView.setItemAnimator(slideInUpAnimator);
        adapter = new NewsRecyclerViewAdapter(getContext());
        recyclerView.setAdapter(adapter);
        initData();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){

                @Override
                public void onRefresh()
                {
                    refreshData();
                }
            });
    }
    
    private void initData()
    {
        if (list == null)
        {
            refreshData();
        }
        else
        {
            adapter.setData(list);
            changeData();
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
        new Thread(new Runnable(){

                @Override
                public void run()
                {
                    NewsModel model=new NewsModel();
                    list = model.getFocus(NewsModel.FOCUS);
                    adapter.setData(list);
                    App.getApp().getMainHandler().post(new Runnable(){

                            @Override
                            public void run()
                            {
                                swipeRefreshLayout.setRefreshing(false);
                                changeData();
                            }
                        });
                }
            }).start();
    }

    
    private void changeData()
    {
        int preConut=adapter.getItemCount();
        //adapter.setData(data);
        if(preConut==0)
        {
            adapter.notifyItemRangeInserted(0,list.size());
        }
        else
        {
            adapter.notifyItemRangeChanged(0,list.size());
        }
    }
}
