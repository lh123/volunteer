package com.yangtze.volunteer.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.yangtze.volunteer.App;
import com.yangtze.volunteer.R;

/**
 * Created by liuhui on 2016/3/3.
 */
public class LogoActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        App.getApp().getMainHandler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                Intent i=new Intent();
                i.setClass(LogoActivity.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        },2000);
    }
}
