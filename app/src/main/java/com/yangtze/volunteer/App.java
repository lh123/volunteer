package com.yangtze.volunteer;
import android.app.Application;
import cn.bmob.v3.Bmob;
import android.os.Handler;

public class App extends Application
{
    private static Handler handler;
    
    public static Handler getMainHandler()
    {
        return handler;
    }
    
    @Override
    public void onCreate()
    {
        super.onCreate();
        Bmob.initialize(this,"052059421878348df6a50c1447a25a39");
        handler=new Handler();
    }
}
