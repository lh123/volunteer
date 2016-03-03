package com.yangtze.volunteer.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yangtze.volunteer.model.bean.VolunteerActive;
import com.yangtze.volunteer.ui.fragment.ActiveFollowFragment;
import com.yangtze.volunteer.ui.fragment.ActiveIntroduceFragment;

/**
 * Created by liuhui on 2016/3/3.
 */
public class ActiveDetailViewpagerAdapter extends FragmentPagerAdapter
{
    private Fragment[] fragments;

    public ActiveDetailViewpagerAdapter(FragmentManager fm)
    {
        super(fm);
        fragments=new Fragment[]{new ActiveFollowFragment(),new ActiveIntroduceFragment()};
    }

    @Override
    public Fragment getItem(int position)
    {
        return fragments[position];
    }

    @Override
    public int getCount()
    {
        return fragments.length;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        if (position==0)
        {
            return "参加者";
        }
        else
        {
            return "介绍";
        }
    }
}
