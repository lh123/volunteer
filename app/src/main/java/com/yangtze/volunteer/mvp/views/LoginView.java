package com.yangtze.volunteer.mvp.views;
import android.graphics.Bitmap;

public interface LoginView
{
    public void setAccount(String account);
    public String getAccount();
    public void setPassword(String password);
    public String getPassword();
    public void finishActivity();
    public void showToast(String msg);
}
