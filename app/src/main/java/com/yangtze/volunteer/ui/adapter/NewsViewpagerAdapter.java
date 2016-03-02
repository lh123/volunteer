package com.yangtze.volunteer.ui.adapter;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import com.yangtze.volunteer.ui.fragment.FocusFragment;
import com.yangtze.volunteer.ui.fragment.LocationFragment;
public class NewsViewpagerAdapter extends FragmentPagerAdapter
{
    private FocusFragment focusFragment;
    private LocationFragment locationFragment;
    public NewsViewpagerAdapter(FragmentManager manager)
    {
        super(manager);
        focusFragment=new FocusFragment();
        locationFragment=new LocationFragment();
    }
    
    @Override
    public Fragment getItem(int p1)
    {
        return p1==0?focusFragment:locationFragment;
    }

    @Override
    public int getCount()
    {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return position==0?"焦点":"区域";
    }
}
