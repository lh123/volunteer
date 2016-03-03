package com.yangtze.volunteer.mvp.views;

import com.yangtze.volunteer.model.bean.VolunteerActive;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhui on 2016/3/3.
 */
public interface ActiveView
{
    public void setData(List<VolunteerActive> data);
    public void setRefreshState(boolean state);
    public void showToast(String msg);
}
