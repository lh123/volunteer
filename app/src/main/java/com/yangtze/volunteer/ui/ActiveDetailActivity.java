package com.yangtze.volunteer.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.yangtze.volunteer.R;
import com.yangtze.volunteer.model.bean.User;
import com.yangtze.volunteer.model.bean.VolunteerActive;
import com.yangtze.volunteer.ui.adapter.ActiveDetailViewpagerAdapter;
import com.yangtze.volunteer.utils.ToolbarUtils;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.UpdateListener;
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

    private boolean isJoinning=false;

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
        setBtnState();
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
        btnJoin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (BmobUser.getCurrentUser(ActiveDetailActivity.this, User.class) == null)
                {
                    Toast.makeText(ActiveDetailActivity.this, "未登录", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (BmobUser.getCurrentUser(ActiveDetailActivity.this, User.class).getObjectId().equals(active.getAuthor().getObjectId()))
                {
                    delleteActive();
                }
                else
                {
                    joinActive();
                }
            }
        });
    }


    private void setBtnState()
    {
        if(BmobUser.getCurrentUser(ActiveDetailActivity.this, User.class)==null)
        {
            return;
        }
        if (BmobUser.getCurrentUser(ActiveDetailActivity.this, User.class).getObjectId().equals(active.getAuthor().getObjectId()))
        {
            btnJoin.setText("关闭活动");
        }
    }

    private void delleteActive()
    {
        VolunteerActive newActive=new VolunteerActive();
        newActive.setObjectId(active.getObjectId());
        newActive.delete(this, new DeleteListener()
        {
            @Override
            public void onSuccess()
            {
                Toast.makeText(ActiveDetailActivity.this, "关闭成功", Toast.LENGTH_SHORT).show();
                active = null;
                finish();
            }

            @Override
            public void onFailure(int i, String s)
            {
                Toast.makeText(ActiveDetailActivity.this, "关闭失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void joinActive()
    {
        if(!isJoinning)
        {
            isJoinning=true;
            BmobRelation relation=new BmobRelation();
            relation.add(BmobUser.getCurrentUser(ActiveDetailActivity.this, User.class));
            final VolunteerActive newActive=new VolunteerActive();
            newActive.setAttendee(relation);
            newActive.setObjectId(active.getObjectId());
            newActive.update(ActiveDetailActivity.this, new UpdateListener()
            {
                @Override
                public void onSuccess()
                {
                    Toast.makeText(ActiveDetailActivity.this,"参加成功",Toast.LENGTH_SHORT).show();
                    active=newActive;
                    isJoinning=false;
                }

                @Override
                public void onFailure(int i, String s)
                {
                    Toast.makeText(ActiveDetailActivity.this,"参加失败",Toast.LENGTH_SHORT).show();
                    isJoinning=false;
                }
            });
        }
    }

    private void initToolbar()
    {
        ToolbarUtils.initToolbar(toolbar, this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
    }

    public VolunteerActive getActive()
    {
        return active;
    }
}
