package com.yangtze.volunteer.ui.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.yangtze.volunteer.R;
import com.yangtze.volunteer.model.bean.VolunteerActive;
import com.yangtze.volunteer.mvp.presenter.impl.ActivePresenter;
import com.yangtze.volunteer.mvp.views.ActiveView;
import com.yangtze.volunteer.ui.adapter.ActiveRecyclerViewAdapter;
import java.util.List;
import jp.wasabeef.recyclerview.animators.SlideInDownAnimator;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * Created by liuhui on 2016/3/3.
 */
public class ActiveFragment extends Fragment implements ActiveView
{
    private RecyclerView recyclerView;
    private FloatingActionButton actionButton;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ActiveRecyclerViewAdapter adapter;
    private ActivePresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP)
        {
            setEnterTransition(new Slide());
            setExitTransition(new Slide());
        }
        View view=inflater.inflate(R.layout.fragment_active, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        actionButton = (FloatingActionButton) view.findViewById(R.id.floatButton);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        if(presenter==null)
        {
            presenter = new ActivePresenter(this);
        }
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        LinearLayoutManager lm=new LinearLayoutManager(getContext());
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        adapter = new ActiveRecyclerViewAdapter(this);
        recyclerView.setLayoutManager(lm);
        SlideInUpAnimator slideInUpAnimator = new SlideInUpAnimator();
        slideInUpAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        //slideInUpAnimator.setAddDuration(500);
        recyclerView.setItemAnimator(slideInUpAnimator);
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
            {
                @Override
                public void onRefresh()
                {
                    presenter.refreshData();
                }
            });
        actionButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    presenter.onFloatButtonClick();
                }
            });
        swipeRefreshLayout.post(new Runnable()
            {
                @Override
                public void run()
                {
                    swipeRefreshLayout.setRefreshing(true);
                }
            });
        presenter.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy()
    {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void setData(List<VolunteerActive> data)
    {
        int preConut=adapter.getItemCount();
        adapter.setList(data);
        if(preConut==0)
        {
            adapter.notifyItemRangeInserted(0,data.size());
        }
        else
        {
            adapter.notifyItemRangeChanged(0,data.size());
        }
    }

    @Override
    public void setRefreshState(final boolean state)
    {
        swipeRefreshLayout.post(new Runnable(){

                @Override
                public void run()
                {
                    swipeRefreshLayout.setRefreshing(state);
                }
            });
    }

    @Override
    public void showToast(String msg)
    {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }
    

    @Override
    public View getFloatButton()
    {
        return actionButton;
    }
}
