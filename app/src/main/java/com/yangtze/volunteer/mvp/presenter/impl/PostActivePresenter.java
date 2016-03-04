package com.yangtze.volunteer.mvp.presenter.impl;

import android.content.Context;
import android.os.Bundle;

import com.yangtze.volunteer.model.bean.User;
import com.yangtze.volunteer.model.bean.VolunteerActive;
import com.yangtze.volunteer.mvp.presenter.Presenter;
import com.yangtze.volunteer.mvp.views.PostActiveView;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by liuhui on 2016/3/3.
 */
public class PostActivePresenter implements Presenter
{
    private PostActiveView mView;
    private Context context;

    public PostActivePresenter(PostActiveView mView)
    {
        this.mView = mView;
        context= (Context) mView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
    }

    @Override
    public void onDestroy()
    {
    }

    public void postActive()
    {
        if(BmobUser.getCurrentUser(context,User.class)==null)
        {
            mView.showToast("未登录");
        }
        if(checkInf())
        {
            VolunteerActive active = new VolunteerActive();
            active.setTitle(mView.getActiveTitle());
            active.setDesc(mView.getActiveDes());
            active.setAuthor(BmobUser.getCurrentUser(context, User.class));
            active.setMaxNumber(Integer.parseInt(mView.getActiveNum()));
            active.setStartTime(mView.getActiveTime());
            mView.showProgressDialog();
            active.save(context, new SaveListener()
            {
                @Override
                public void onSuccess()
                {
                    mView.dismissProgressDialog();
                }

                @Override
                public void onFailure(int i, String s)
                {
                    mView.showToast("发布失败");
                }
            });
        }
    }

    public boolean checkInf()
    {
        boolean flag=true;
        if(mView.getActiveTitle()==null||mView.getActiveDes()==null||mView.getActiveNum()==null||
                mView.getActiveTime()==null)
        {
            mView.showToast("信息不完整");
            flag = false;
        }
        else
        {
            flag=true;
        }
        return flag;
    }
}
