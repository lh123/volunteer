package com.yangtze.volunteer.mvp.presenter.impl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.squareup.otto.Subscribe;
import com.yangtze.volunteer.R;
import com.yangtze.volunteer.domain.BusProvide;
import com.yangtze.volunteer.domain.event.UserEvent;
import com.yangtze.volunteer.model.bean.User;
import com.yangtze.volunteer.mvp.presenter.Presenter;
import com.yangtze.volunteer.mvp.views.MainView;
import com.yangtze.volunteer.ui.LoginActivity;
import com.yangtze.volunteer.ui.UserDetailActivity;
import com.yangtze.volunteer.ui.fragment.ViewPagerFragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.UpdateListener;

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
        loadUserInfo();
        if(savedInstanceState==null)
        {
            if(viewpagerFragment==null)
            {
                viewpagerFragment=new ViewPagerFragment();
            }
            mView.getSupportFragmentManager().beginTransaction().replace(R.id.container,viewpagerFragment).commit();
            
        }

    }

    public void loadUserInfo()
    {
        currentUser = BmobUser.getCurrentUser(context, User.class);
        if (currentUser==null)
        {
            mView.showToast("当前未登录");
            mView.setUserImg(null);
            mView.setUserName("点击头像登陆");
            mView.setSignState(false);
        }
        else
        {
            mView.setUserImg(currentUser.getImg());
            mView.setUserName(currentUser.getXuehao());
            setUserSignState();
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
        if(currentUser==null)
        {
            return;
        }
        SimpleDateFormat sm=new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String time=sm.format(new Date(System.currentTimeMillis()));
        if(currentUser.getLastSign()==null||!time.equals(currentUser.getLastSign()))
        {
            User newUser=new User();
            newUser.setLastSign(time);
            newUser.setCoint(currentUser.getCoint()+2);
            newUser.update(context,currentUser.getObjectId(), new UpdateListener()
            {
                @Override
                public void onSuccess()
                {
                    currentUser = BmobUser.getCurrentUser(context, User.class);
                    Toast.makeText(context, "签到成功", Toast.LENGTH_SHORT).show();
                    setUserSignState();
                }

                @Override
                public void onFailure(int i, String s)
                {
                    Toast.makeText(context, "签到失败", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void setUserSignState()
    {
        SimpleDateFormat sm=new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String time=sm.format(new Date(System.currentTimeMillis()));
        if(currentUser.getLastSign()==null||!time.equals(currentUser.getLastSign()))
        {
            mView.setSignState(false);
        }
        else
        {
            mView.setSignState(true);
        }
    }
    @Subscribe
    public void onLoginEventReceive(UserEvent event)
    {
        loadUserInfo();
    }
    
    @Override
    public void onDestroy()
    {
        BusProvide.getBus().unregister(this);
    }


}
