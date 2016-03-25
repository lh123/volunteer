package com.yangtze.volunteer.mvp.views;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import com.yangtze.volunteer.model.bean.VolunteerActive;
import java.util.List;

/**
 * Created by liuhui on 2016/3/3.
 */
public interface ActiveView
{
    public void setData(List<VolunteerActive> data);
    public void setRefreshState(boolean state);
    public void showToast(String msg);
    public FragmentActivity getActivity();
    public View getFloatButton();
}
