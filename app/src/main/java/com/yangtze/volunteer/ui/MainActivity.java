package com.yangtze.volunteer.ui;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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

import android.widget.ImageView;
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
    private ImageView userSign;
    
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
        userSign= (ImageView) navigationView.getHeaderView(0).findViewById(R.id.user_sign);
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
                        presenter.onHomeItemClick();
                        return true;
                    case R.id.jointed:
                        presenter.onJoinedItemClick();
                        return true;
                    case R.id.introduce:
                        presenter.onIntroduceItemClick();
                        return true;
                    case R.id.rank:
                        presenter.onRankItemClick();
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
    public void setSignState(Boolean state)
    {
        if (state==null)
        {
            userSign.setVisibility(View.INVISIBLE);
        }
        else
        {
            userSign.setVisibility(View.VISIBLE);
            userSign.setImageResource(state==true?R.drawable.ic_already_sign:R.drawable.ic_sign);
        }
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

    @Override
    public void onBackPressed()
    {
        final AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("确认退出?");
        builder.setPositiveButton("退出", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                MainActivity.super.onBackPressed();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
