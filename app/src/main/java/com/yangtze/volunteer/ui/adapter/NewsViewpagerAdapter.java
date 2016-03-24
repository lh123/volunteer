package com.yangtze.volunteer.ui.adapter;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import com.yangtze.volunteer.ui.fragment.FocusFragment;
import com.yangtze.volunteer.ui.fragment.LocationFragment;
public class NewsViewpagerAdapter extends FragmentPagerAdapter
{
    private Fragment[] fragments;
    
    public NewsViewpagerAdapter(FragmentManager manager)
    {
        super(manager);
    }

    public void setFragments(Fragment[] fragments)
    {
        this.fragments = fragments;
    }

    public Fragment[] getFragments()
    {
        return fragments;
    }
    
    @Override
    public Fragment getItem(int p1)
    {
        return fragments[p1];
    }

    @Override
    public int getCount()
    {
        return fragments.length;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return position==0?"焦点":"区域";
    }
}
