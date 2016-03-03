package com.yangtze.volunteer.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yangtze.volunteer.R;
import com.yangtze.volunteer.model.bean.VolunteerActive;
import com.yangtze.volunteer.ui.ActiveDetailActivity;

/**
 * Created by liuhui on 2016/3/3.
 */
public class ActiveIntroduceFragment extends Fragment
{
    private TextView tvTitle;
    private TextView tvDes;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.fragment_active_introduce,container,false);
        tvTitle= (TextView) view.findViewById(R.id.tv_title);
        tvDes= (TextView) view.findViewById(R.id.tv_des);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        VolunteerActive active=((ActiveDetailActivity)getActivity()).getActive();
        tvTitle.setText(active.getTitle());
        tvDes.setText(active.getDesc());
    }
}
