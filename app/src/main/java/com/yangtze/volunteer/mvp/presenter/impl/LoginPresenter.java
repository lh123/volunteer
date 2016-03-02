package com.yangtze.volunteer.mvp.presenter.impl;
import android.content.Context;
import android.os.Bundle;
import cn.bmob.v3.BmobUser;
import com.yangtze.volunteer.mvp.presenter.Presenter;
import com.yangtze.volunteer.mvp.views.LoginView;
import com.yangtze.volunteer.model.bean.User;
import cn.bmob.v3.listener.SaveListener;
import com.yangtze.volunteer.domain.event.LoginEvent;
import com.yangtze.volunteer.domain.BusProvide;

public class LoginPresenter implements Presenter
{
    private LoginView mView;

    public LoginPresenter(LoginView mView)
    {
        this.mView = mView;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        User user=BmobUser.getCurrentUser((Context)mView, User.class);
        if (user != null)
        {
            mView.setAccount(user.getUsername());
        }
    }

    public void login()
    {
        final User u=new User();
        u.setUsername(mView.getAccount());
        u.setPassword(mView.getPassword());
        u.login((Context)mView, new SaveListener(){

                @Override
                public void onSuccess()
                {
                    mView.showToast("登录成功");
                    LoginEvent e=new LoginEvent();
                    e.user=u;
                    e.status=LoginEvent.SUCCESS;
                    BusProvide.getBus().post(e);
                    mView.finishActivity();
                }

                @Override
                public void onFailure(int p1, String p2)
                {
                    mView.showToast(p2);
                }
            });
    }
    
    @Override
    public void onDestroy()
    {
    }
}
