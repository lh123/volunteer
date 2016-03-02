package com.yangtze.volunteer.mvp.presenter.impl;
import android.content.Context;
import android.os.Bundle;
import cn.bmob.v3.BmobUser;
import com.squareup.otto.Subscribe;
import com.yangtze.volunteer.R;
import com.yangtze.volunteer.domain.BusProvide;
import com.yangtze.volunteer.domain.event.LoginEvent;
import com.yangtze.volunteer.model.bean.User;
import com.yangtze.volunteer.mvp.presenter.Presenter;
import com.yangtze.volunteer.mvp.views.MainView;
import com.yangtze.volunteer.ui.fragment.ViewPagerFragment;

public class MainPresenter implements Presenter
{
    private MainView mView;
    private ViewPagerFragment viewpagerFragment;

    public MainPresenter(MainView mView)
    {
        this.mView = mView;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        BusProvide.getBus().register(this);
        User currentUser = BmobUser.getCurrentUser((Context)mView, User.class);
        if (currentUser==null)
        {
            mView.showToast("当前未登录");
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
