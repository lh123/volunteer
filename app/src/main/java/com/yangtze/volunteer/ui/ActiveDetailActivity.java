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
import com.yangtze.volunteer.ui.fragment.ActiveFollowFragment;
import android.app.ProgressDialog;

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
    private User currentUser;
    private User author;

    private ActiveDetailViewpagerAdapter adapter;

    private ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_detail);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        circleImageView = (CircleImageView) findViewById(R.id.user_img);
        tvTitle = (TextView) findViewById(R.id.active_title);
        btnJoin = (Button) findViewById(R.id.btn_join);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        active = (VolunteerActive) getIntent().getSerializableExtra("data");
        initToolbar();
        initData();
        setBtnState();
        initViewPager();
    }

    private void initViewPager()
    {
        adapter=new ActiveDetailViewpagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initData()
    {
        currentUser = BmobUser.getCurrentUser(ActiveDetailActivity.this, User.class);
        tvTitle.setText(active.getTitle());
        author=active.getAuthor();
        Glide.with(this).load(active.getAuthor().getImg()).into(circleImageView);
        btnJoin.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (currentUser == null)
                    {
                        Toast.makeText(ActiveDetailActivity.this, "未登录", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else if(author==null)
                    {
                        return;
                    }
                    else if(currentUser.getObjectId()==null||author.getObjectId()==null)
                    {
                        return;
                    }
                    else if (currentUser.getObjectId().equals(author.getObjectId()))
                    {
                        delleteActive();
                    }
                    else if(!((ActiveFollowFragment)adapter.getItem(0)).isCanExit())
                    {
                        joinActive();
                    }
                    else if(((ActiveFollowFragment)adapter.getItem(0)).isCanExit())
                    {
                        exitActive();
                    }
                }
            });
    }


    private void setBtnState()
    {
        if(System.currentTimeMillis()>active.getTime())
        {
            btnJoin.setVisibility(View.INVISIBLE);
            btnJoin.setClickable(false);
            return;
        }
        if (BmobUser.getCurrentUser(ActiveDetailActivity.this, User.class) == null)
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
        showPb();
        VolunteerActive newActive=new VolunteerActive();
        newActive.setObjectId(active.getObjectId());
        newActive.delete(this, new DeleteListener()
            {
                @Override
                public void onSuccess()
                {
                    hidePb();
                    Toast.makeText(ActiveDetailActivity.this, "关闭成功", Toast.LENGTH_SHORT).show();
                    active = null;
                    finish();
                }

                @Override
                public void onFailure(int i, String s)
                {
                    hidePb();
                    Toast.makeText(ActiveDetailActivity.this, "关闭失败", Toast.LENGTH_SHORT).show();
                }
            });
    }

    private void joinActive()
    {
        showPb();
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
                    Toast.makeText(ActiveDetailActivity.this, "参加成功", Toast.LENGTH_SHORT).show();
                    active = newActive;
                    ((ActiveFollowFragment)adapter.getItem(0)).refreshData();
                }
                
                @Override
                public void onFailure(int i, String s)
                {
                    hidePb();
                    Toast.makeText(ActiveDetailActivity.this, "参加失败", Toast.LENGTH_SHORT).show();
                }
            });

    }
    
    private void exitActive()
    {
        showPb();
        BmobRelation relation=new BmobRelation();
        relation.remove(BmobUser.getCurrentUser(ActiveDetailActivity.this, User.class));
        final VolunteerActive newActive=new VolunteerActive();
        newActive.setAttendee(relation);
        newActive.setObjectId(active.getObjectId());
        newActive.update(ActiveDetailActivity.this, new UpdateListener()
            {
                @Override
                public void onSuccess()
                {
                    Toast.makeText(ActiveDetailActivity.this, "退出成功", Toast.LENGTH_SHORT).show();
                    ((ActiveFollowFragment)adapter.getItem(0)).refreshData();
                }

                @Override
                public void onFailure(int i, String s)
                {
                    hidePb();
                    Toast.makeText(ActiveDetailActivity.this, "退出失败", Toast.LENGTH_SHORT).show();
                }
            });
        
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
    
    public void setBtnText(String text)
    {
        btnJoin.setText(text);
    }
    
    public void showPb()
    {
        pd=new ProgressDialog(this);
        pd.setTitle("处理中");
        pd.setMessage("请稍候");
        pd.show();
    }
    
    public void hidePb()
    {
        pd.dismiss();
    }
}
