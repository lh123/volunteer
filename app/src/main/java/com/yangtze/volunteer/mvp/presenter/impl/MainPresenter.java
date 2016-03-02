package com.yangtze.volunteer.mvp.presenter.impl;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import com.squareup.otto.Subscribe;
import com.yangtze.volunteer.R;
import com.yangtze.volunteer.domain.BusProvide;
import com.yangtze.volunteer.domain.event.LoginEvent;
import com.yangtze.volunteer.model.bean.User;
import com.yangtze.volunteer.mvp.presenter.Presenter;
import com.yangtze.volunteer.mvp.views.MainView;
import com.yangtze.volunteer.ui.LoginActivity;
import com.yangtze.volunteer.ui.UserDetailActivity;
import com.yangtze.volunteer.ui.fragment.ViewPagerFragment;

import java.nio.channels.Pipe;
import java.util.Currency;

public class MainPresenter implements Presenter
{
    private MainView mView;
    private ViewPagerFragment viewpagerFragment;
    private User currentUser;
    private Context context;

    public MainPresenter(MainView mView)
    {
        this.mView = mView;
        context= (Context) mView;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        BusProvide.getBus().register(this);
        currentUser = BmobUser.getCurrentUser(context, User.class);
        if (currentUser==null)
        {
            mView.showToast("当前未登录");
        }
        else
        {
            mView.setUserImg(currentUser.getImg());
            mView.setUserName(currentUser.getXuehao());
        }
        if(savedInstanceState==null)
        {
            if(viewpagerFragment==null)
            {
                viewpagerFragment=new ViewPagerFragment();
            }
            mView.getSupportFragmentManager().beginTransaction().replace(R.id.container,viewpagerFragment).commit();
            
        }
    }

    public void onUserImgClick()
    {
        Intent i=new Intent();
        if(currentUser==null)
        {
            i.setClass(context, LoginActivity.class);
            context.startActivity(i);
        }
        else
        {
            i.setClass(context, UserDetailActivity.class);
            context.startActivity(i);
        }
    }

    public void onUserSignClick()
    {
    }

    @Subscribe
    public void onLoginEventReceive(LoginEvent event)
    {
        
    }
    
    @Override
    public void onDestroy()
    {
        BusProvide.getBus().unregister(this);
    }


}
