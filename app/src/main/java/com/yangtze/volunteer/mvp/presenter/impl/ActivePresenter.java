package com.yangtze.volunteer.mvp.presenter.impl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.yangtze.volunteer.App;
import com.yangtze.volunteer.model.bean.VolunteerActive;
import com.yangtze.volunteer.mvp.presenter.Presenter;
import com.yangtze.volunteer.mvp.views.ActiveView;
import com.yangtze.volunteer.ui.PostActiveActivity;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by liuhui on 2016/3/3.
 */
public class ActivePresenter implements Presenter
{
    private ActiveView mView;
    private Context context;

    public ActivePresenter(ActiveView mView)
    {
        this.mView = mView;
        context=((Fragment)mView).getContext();
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        refreshData();
    }

    public void refreshData()
    {
        BmobQuery<VolunteerActive> query=new BmobQuery<>();
        query.include("author");
        query.findObjects(context, new FindListener<VolunteerActive>()
        {
            @Override
            public void onSuccess(List<VolunteerActive> list)
            {
                mView.setData(list);
                mView.setRefreshState(false);
            }

            @Override
            public void onError(int i, String s)
            {
                mView.showToast("加载失败");
                mView.setRefreshState(false);
            }
        });
    }

    public void onFloatButtonClick()
    {
        Intent i=new Intent();
        i.setClass(context, PostActiveActivity.class);
        context.startActivity(i);
    }

    @Override
    public void onDestroy()
    {

    }
}
