package com.yangtze.volunteer;
import android.app.Application;
import cn.bmob.v3.Bmob;
import android.os.Handler;

import com.bumptech.glide.Glide;
import com.tencent.bugly.crashreport.CrashReport;
import com.yangtze.volunteer.crash.MyCrashHandler;

public class App extends Application
{
    private static App app;
    private Handler handler;

    @Override
    public void onCreate()
    {
        super.onCreate();
        MyCrashHandler.getInstance().init(getApplicationContext());
        Thread.setDefaultUncaughtExceptionHandler(MyCrashHandler.getInstance());
        Bmob.initialize(this, "052059421878348df6a50c1447a25a39");
        CrashReport.initCrashReport(getApplicationContext(), "900021669", false);
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
