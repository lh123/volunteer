package com.yangtze.volunteer.mvp.presenter.impl;
import com.yangtze.volunteer.mvp.presenter.Presenter;
import android.os.Bundle;
import com.yangtze.volunteer.mvp.views.RegisterView;
import com.yangtze.volunteer.model.bean.User;
import android.content.Context;
import cn.bmob.v3.listener.SaveListener;

public class RegisterPresenter implements Presenter
{
    private RegisterView view;

    public RegisterPresenter(RegisterView view)
    {
        this.view = view;
    }
    
    public void register()
    {
        User user=new User();
        user.setUsername(view.getAccount());
        user.setXuehao(view.getXuehao());
        user.setPassword(view.getPassword());
        user.signUp((Context)view, new SaveListener(){

                @Override
                public void onSuccess()
                {
                    view.showToast("注册成功");
                    view.finishActivity();
                }

                @Override
                public void onFailure(int p1, String p2)
                {
                    view.showToast(p2);
                }
            });
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        
    }

    @Override
    public void onDestroy()
    {
        
    }
    
}
