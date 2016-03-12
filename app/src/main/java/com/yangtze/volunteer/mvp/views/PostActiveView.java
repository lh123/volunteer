package com.yangtze.volunteer.mvp.views;

/**
 * Created by liuhui on 2016/3/3.
 */
public interface PostActiveView
{
    public String getActiveTitle();
    public String getActiveDes();
    public String getActiveNum();
    public String getActiveTime();
    public Long getTime();
    public void showTimeDialog();
    public void showToast(String msg);
    public void showProgressDialog();
    public void dismissProgressDialog();
}
