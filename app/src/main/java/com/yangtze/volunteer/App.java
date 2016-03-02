package com.yangtze.volunteer;
import android.app.Application;
import cn.bmob.v3.Bmob;
import android.os.Handler;

import com.bumptech.glide.Glide;

public class App extends Application
{
    private static App app;
    private Handler handler;

    @Override
    public void onCreate()
    {
        super.onCreate();
        Bmob.initialize(this, "052059421878348df6a50c1447a25a39");
        handler=new Handler();
        app=this;
    }

    public static App getApp()
    {
        return app;
    }

    public Handler getMainHandler()
    {
        return handler;
    }
}
