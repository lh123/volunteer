package com.yangtze.volunteer.ui;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.yangtze.volunteer.R;
import com.yangtze.volunteer.mvp.views.MainView;
import android.support.v7.widget.Toolbar;
import android.support.v4.widget.DrawerLayout;
import com.yangtze.volunteer.utils.ToolbarUtils;
import android.support.design.widget.NavigationView;
import android.view.View.OnClickListener;
import android.view.View;
import android.content.Intent;
import android.support.v7.app.ActionBarDrawerToggle;
import com.yangtze.volunteer.mvp.presenter.impl.MainPresenter;
import android.widget.Toast;
import com.yangtze.volunteer.model.NewsModel;

public class MainActivity extends AppCompatActivity implements MainView
{
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private MainPresenter presenter;
    
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
//        new Thread(new Runnable(){
//
//                @Override
//                public void run()
//                {
//                    NewsModel m=new NewsModel();
//                    m.getFocus();
//                }
//            }).start();
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
        navigationView.getHeaderView(0).findViewById(R.id.btn_login).setOnClickListener(new OnClickListener(){

                @Override
                public void onClick(View p1)
                {
                    Intent i=new Intent();
                    i.setClass(MainActivity.this,LoginActivity.class);
                    startActivity(i);
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
    public void showToast(String msg)
    {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
    
}
