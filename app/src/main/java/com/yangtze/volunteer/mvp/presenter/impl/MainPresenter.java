package com.yangtze.volunteer.mvp.presenter.impl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.squareup.otto.Subscribe;
import com.yangtze.volunteer.R;
import com.yangtze.volunteer.domain.BusProvide;
import com.yangtze.volunteer.domain.event.UserEvent;
import com.yangtze.volunteer.model.bean.User;
import com.yangtze.volunteer.mvp.presenter.Presenter;
import com.yangtze.volunteer.mvp.views.MainView;
import com.yangtze.volunteer.ui.IntroduceViewActivity;
import com.yangtze.volunteer.ui.LoginActivity;
import com.yangtze.volunteer.ui.UserDetailActivity;
import com.yangtze.volunteer.ui.fragment.ActiveFragment;
import com.yangtze.volunteer.ui.fragment.JointedFragment;
import com.yangtze.volunteer.ui.fragment.RankFragment;
import com.yangtze.volunteer.ui.fragment.ViewPagerFragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.UpdateListener;

public class MainPresenter implements Presenter
{
    private MainView mView;
    private Fragment[] fragments;
    private User currentUser;
    private Context context;

    public MainPresenter(MainView mView)
    {
        this.mView = mView;
        context= (Context) mView;
        fragments=new Fragment[]{new ViewPagerFragment(),new ActiveFragment(),new JointedFragment(),new RankFragment()};
    }

    
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        BusProvide.getBus().register(this);
        loadUserInfo();
        FragmentTransaction transaction= mView.getSupportFragmentManager().beginTransaction();
        if(savedInstanceState==null)
        {
            if(fragments[0]==null)
            {
                fragments[0]=new ViewPagerFragment();
            }
           // hideAll(transaction);
//            if(!fragments[0].isAdded())
//            {
//                transaction.add(R.id.container, fragments[0],ViewPagerFragment.TAG);
//            }
            transaction.replace(R.id.container,fragments[0],ViewPagerFragment.TAG).commit();
            mView.setTitle(context.getResources().getString(R.string.app_name));
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
            mView.setSignState(null);
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
            newUser.setCoint((currentUser.getCoint() == null ? 0 : currentUser.getCoint()) + 2);
            newUser.update(context, currentUser.getObjectId(), new UpdateListener()
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


    public void onActiveItemClick()
    {
        FragmentTransaction transaction= mView.getSupportFragmentManager().beginTransaction();
        if(fragments[1]==null)
        {
            fragments[1]=new ActiveFragment();
        }
        //hideAll(transaction);
//        if(!fragments[1].isAdded())
//        {
//            transaction.add(R.id.container,fragments[1]);
//        }
        transaction.replace(R.id.container,fragments[1]).commit();
        mView.setTitle("活动预告");
        //mView.closeDrawer();
    }

    public void onHomeItemClick()
    {
        FragmentTransaction transaction= mView.getSupportFragmentManager().beginTransaction();
        if(fragments[0]==null)
        {
            fragments[0]=new ViewPagerFragment();
        }
       // hideAll(transaction);
//        if(!fragments[0].isAdded())
//        {
//           transaction.add(R.id.container, fragments[0],ViewPagerFragment.TAG);
//        }
        transaction.replace(R.id.container,fragments[0],ViewPagerFragment.TAG).commit();
        mView.setTitle(context.getResources().getString(R.string.app_name));
        //mView.closeDrawer();
    }

    public void onJoinedItemClick()
    {
        FragmentTransaction transaction= mView.getSupportFragmentManager().beginTransaction();
        if(fragments[2]==null)
        {
            fragments[2]=new JointedFragment();
        }
        //hideAll(transaction);
//        if(!fragments[2].isAdded())
//        {
//             transaction.add(R.id.container, fragments[2]);
//        }
        transaction.replace(R.id.container,fragments[2]).commit();
        mView.setTitle(context.getResources().getString(R.string.app_name));
        //mView.closeDrawer();
    }

    public void onRankItemClick()
    {
        FragmentTransaction transaction= mView.getSupportFragmentManager().beginTransaction();
        if(fragments[3]==null)
        {
            fragments[3]=new RankFragment();
        }
       // hideAll(transaction);
//        if(!fragments[3].isAdded())
//        {
//            transaction.add(R.id.container, fragments[3]);
//        }
        transaction.replace(R.id.container,fragments[3]).commit();
        mView.setTitle(context.getResources().getString(R.string.app_name));
        //mView.closeDrawer();
    }

    public void onIntroduceItemClick()
    {
        Intent i=new Intent();
        i.setClass(context, IntroduceViewActivity.class);
        context.startActivity(i);
    }
    
    public boolean onBackPressed()
    {
        if(!fragments[0].isAdded())
        {
            mView.getSupportFragmentManager().beginTransaction().replace(R.id.container,fragments[0],ViewPagerFragment.TAG).commit();
            return true;
        }
        return false;
    }

//    private void hideAll(FragmentTransaction transaction)
//    {
//        for(Fragment f:fragments)
//        {
//            transaction.detach(f);
//        }
//    }
}
