package com.yangtze.volunteer.mvp.views;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;

public interface MainView
{
    public FragmentManager getSupportFragmentManager();
    public void setUserImg(String path);
    public void setUserName(String name);
    public void setSignState(Boolean state);
    public void showToast(String msg);
    public void closeDrawer();
    public void setTitle(String title);
    
}
