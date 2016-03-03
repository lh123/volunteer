package com.yangtze.volunteer.ui;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.yangtze.volunteer.R;
import com.yangtze.volunteer.mvp.views.MainView;
import android.support.v7.widget.Toolbar;
import android.support.v4.widget.DrawerLayout;
import com.yangtze.volunteer.utils.ToolbarUtils;
import android.support.design.widget.NavigationView;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.view.View;
import android.support.v7.app.ActionBarDrawerToggle;
import com.yangtze.volunteer.mvp.presenter.impl.MainPresenter;

import android.widget.TextView;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements MainView
{
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private MainPresenter presenter;
    private CircleImageView circleImageView;
    private TextView userName;
    private TextView userSign;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        drawerLayout=(DrawerLayout) findViewById(R.id.drawerlayout);
        toolbar=(Toolbar) findViewById(R.id.toolbar);
        navigationView=(NavigationView) findViewById(R.id.navigation_view);
        initToolbar();
        initDrawer();
        presenter=new MainPresenter(this);
        presenter.onCreate(savedInstanceState);
    }

    private void initToolbar()
    {
        ToolbarUtils.initToolbar(toolbar,this);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
    }

    private void initDrawer()
    {
        circleImageView= (CircleImageView) navigationView.getHeaderView(0).findViewById(R.id.user_img);
        userName= (TextView) navigationView.getHeaderView(0).findViewById(R.id.user_name);
        userSign= (TextView) navigationView.getHeaderView(0).findViewById(R.id.user_sign);
        circleImageView.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                presenter.onUserImgClick();
            }
        });
        userSign.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                presenter.onUserSignClick();
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(MenuItem item)
            {
                switch (item.getItemId())
                {
                    case R.id.active_predict:
                        presenter.onActiveItemClick();
                        return true;
                    case R.id.home:
                        presenter.onHoneIttemClick();
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void onDestroy()
    {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void setUserImg(String path)
    {
        if(path==null)
        {
            circleImageView.setImageResource(R.drawable.myface);
        }
        else
        {
            Glide.with(this).load(path).into(circleImageView);
        }
    }

    @Override
    public void setUserName(String name)
    {
        userName.setText(name);
    }

    @Override
    public void setSignState(boolean state)
    {
        userSign.setText(state==true?"已签到":"签到！");
    }

    @Override
    public void showToast(String msg)
    {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void closeDrawer()
    {
        drawerLayout.closeDrawers();
    }

    @Override
    public void setTitle(String title)
    {
        toolbar.setTitle(title);
    }


}
