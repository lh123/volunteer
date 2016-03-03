package com.yangtze.volunteer.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yangtze.volunteer.R;
import com.yangtze.volunteer.model.bean.VolunteerActive;
import com.yangtze.volunteer.ui.adapter.ActiveDetailViewpagerAdapter;
import com.yangtze.volunteer.utils.ToolbarUtils;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by liuhui on 2016/3/3.
 */
public class ActiveDetailActivity extends AppCompatActivity
{
    private Toolbar toolbar;
    private VolunteerActive active;
    private CircleImageView circleImageView;
    private TextView tvTitle;
    private Button btnJoin;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_detail);
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        circleImageView= (CircleImageView) findViewById(R.id.user_img);
        tvTitle= (TextView) findViewById(R.id.active_title);
        btnJoin= (Button) findViewById(R.id.btn_join);
        tabLayout= (TabLayout) findViewById(R.id.tab_layout);
        viewPager= (ViewPager) findViewById(R.id.viewpager);
        active= (VolunteerActive) getIntent().getSerializableExtra("data");
        initToolbar();
        initData();
        initViewPager();
    }

    private void initViewPager()
    {
        ActiveDetailViewpagerAdapter adapter=new ActiveDetailViewpagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initData()
    {
        tvTitle.setText(active.getTitle());
        Glide.with(this).load(active.getAuthor().getImg()).into(circleImageView);
    }

    private void initToolbar()
    {
        ToolbarUtils.initToolbar(toolbar,this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
    }
}
