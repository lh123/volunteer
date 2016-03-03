package com.yangtze.volunteer.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by liuhui on 2016/3/3.
 */
public class ActiveDetailViewpagerAdapter extends FragmentPagerAdapter
{
    private Fragment[] fragments;

    public ActiveDetailViewpagerAdapter(FragmentManager fm)
    {
        super(fm);

    }

    @Override
    public Fragment getItem(int position)
    {
        return null;
    }

    @Override
    public int getCount()
    {
        return 0;
    }
}
