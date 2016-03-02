package com.yangtze.volunteer.utils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.yangtze.volunteer.R;

public class ToolbarUtils
{
    public static void initToolbar(Toolbar toolbar, final AppCompatActivity aty)
    {
        if(toolbar==null||aty==null)
            return;
        aty.setSupportActionBar(toolbar);
        if(aty.getSupportActionBar()!=null)
        {
            aty.getSupportActionBar().setHomeButtonEnabled(true);
            aty.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
}
