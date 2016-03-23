package com.yangtze.volunteer.ui.fragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.yangtze.volunteer.R;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import com.yangtze.volunteer.ui.adapter.NewsViewpagerAdapter;

public class ViewPagerFragment extends Fragment
{
    public static final String TAG="ViewPagerFragment";
    
    private TabLayout tablayout;
    private ViewPager viewpager;
    private NewsViewpagerAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v=inflater.inflate(R.layout.fragment_viewpager,container,false);
        tablayout=(TabLayout) v.findViewById(R.id.tab_layout);
        viewpager=(ViewPager) v.findViewById(R.id.viewpager);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        adapter=new NewsViewpagerAdapter(getChildFragmentManager());
        viewpager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewpager);
    }
    
}
