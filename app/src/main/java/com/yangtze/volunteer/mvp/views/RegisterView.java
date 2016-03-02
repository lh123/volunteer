package com.yangtze.volunteer.mvp.views;

public interface RegisterView
{
    public void setAccount(String account);
    public String getAccount();
    public void setXuehao(String xuehao);
    public String getXuehao();
    public void setPassword(String password);
    public String getPassword();
    public void showToast(String msg);
    public void finishActivity();
}
