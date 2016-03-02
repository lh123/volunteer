package com.yangtze.volunteer.utils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import com.yangtze.volunteer.R;

public class ToolbarUtils
{
    public static void initToolbar(Toolbar toolbar,AppCompatActivity aty)
    {
        if(toolbar==null||aty==null)
            return;
        toolbar.setTitle(R.string.app_name);
        aty.setSupportActionBar(toolbar);
        if(aty.getSupportActionBar()!=null)
        {
            aty.getSupportActionBar().setHomeButtonEnabled(true);
            aty.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
}
