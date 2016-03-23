package com.yangtze.volunteer.ui;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.yangtze.volunteer.R;
import com.yangtze.volunteer.mvp.presenter.impl.MainPresenter;
import com.yangtze.volunteer.mvp.views.MainView;
import com.yangtze.volunteer.utils.ToolbarUtils;
import de.hdodenhof.circleimageview.CircleImageView;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import com.yangtze.volunteer.ui.fragment.ViewPagerFragment;

public class MainActivity extends AppCompatActivity implements MainView
{
    private Drawer drawerLayout;
    private Toolbar toolbar;
    private MainPresenter presenter;
    private CircleImageView circleImageView;
    private TextView userName;
    private ImageView userSign;


    private IDrawerItem[] items;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        initToolbar();
        initDrawer();
        presenter = new MainPresenter(this);
        presenter.onCreate(savedInstanceState);
    }

    private void initToolbar()
    {
        ToolbarUtils.initToolbar(toolbar, this);
    }

    private void initDrawer()
    {
        DrawerBuilder db=new DrawerBuilder();
        db.withActivity(this);
        db.withActionBarDrawerToggle(true);
        db.withToolbar(toolbar);
        db.withHeader(R.layout.navigation_header);
        db.withHeaderDivider(true);
        db.withStickyFooterDivider(true);
        db.withSliderBackgroundColorRes(R.color.gray_light);
        db.withTranslucentStatusBar(true);
        items = new IDrawerItem[]{new PrimaryDrawerItem().withName(R.string.home).withIcon(R.drawable.ic_home).withIconTinted(true),
            new PrimaryDrawerItem().withName(R.string.active_predict).withIcon(R.drawable.ic_active_predict).withIconTinted(true),
            new PrimaryDrawerItem().withName(R.string.introduce).withIcon(R.drawable.ic_jp).withIconTinted(true),
            new PrimaryDrawerItem().withName(R.string.luntan).withIcon(R.drawable.ic_luntan).withIconTinted(true),
            new PrimaryDrawerItem().withName(R.string.rank).withIcon(R.drawable.ic_rank).withIconTinted(true),
            new PrimaryDrawerItem().withName(R.string.jointed).withIcon(R.drawable.ic_joined).withIconTinted(true),
            new DividerDrawerItem(),
            new PrimaryDrawerItem().withName(R.string.attention).withIcon(R.drawable.ic_attention).withIconTinted(true),
            new PrimaryDrawerItem().withName(R.string.feedback).withIcon(R.drawable.ic_feedback).withIconTinted(true),
            new PrimaryDrawerItem().withName(R.string.software_introduce).withIcon(R.drawable.ic_introduce).withIconTinted(true)};
        db.addDrawerItems(items);
        db.withCloseOnClick(true);
        db.withDelayDrawerClickEvent(500);
        db.withStickyFooter(R.layout.navigation_foot);
        db.withStickyFooterShadow(false);
        drawerLayout = db.build();
        drawerLayout.setOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener(){

                @Override
                public boolean onItemClick(View p1, int p2, IDrawerItem p3)
                {
                    switch (p2)
                    {
                        case 1:
                            presenter.onHomeItemClick();
                            break;
                        case 2:
                            presenter.onActiveItemClick();
                            break;
                        case 3:
                            presenter.onIntroduceItemClick();
                            break;
                        case 5:
                            presenter.onRankItemClick();
                            break;
                        case 6:
                            presenter.onJoinedItemClick();
                            break;
                        default:
                            Toast.makeText(MainActivity.this, "未完成", Toast.LENGTH_SHORT).show();
                    }
                   // drawerLayout.closeDrawer();
                    return true;
                }
            });
        View head=drawerLayout.getHeader();
        View foot=drawerLayout.getStickyFooter();
        circleImageView = (CircleImageView) head.findViewById(R.id.user_img);
        userName = (TextView) head.findViewById(R.id.user_name);
        userSign = (ImageView) head.findViewById(R.id.user_sign);
        foot.findViewById(R.id.btn_exit).setOnClickListener(new OnClickListener(){

                @Override
                public void onClick(View p1)
                {
                    finish();
                }
            });
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
        if (path == null)
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
        if (state == null)
        {
            userSign.setVisibility(View.INVISIBLE);
        }
        else
        {
            userSign.setVisibility(View.VISIBLE);
            userSign.setImageResource(state == true ?R.drawable.ic_already_sign: R.drawable.ic_sign);
        }
    }

    @Override
    public void showToast(String msg)
    {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void closeDrawer()
    {
        drawerLayout.closeDrawer();
    }

    @Override
    public void setTitle(String title)
    {
        toolbar.setTitle(title);
    }

    @Override
    public void onBackPressed()
    {
        if(drawerLayout.isDrawerOpen())
        {
            drawerLayout.closeDrawer();
            return;
        }
        FragmentTransaction tr=getSupportFragmentManager().beginTransaction();
        Fragment homeFragment = getSupportFragmentManager().findFragmentByTag(ViewPagerFragment.TAG);
        if (homeFragment!=null&&!homeFragment.isVisible())
        {
            drawerLayout.setSelectionAtPosition(1,true);
            for(Fragment f:getSupportFragmentManager().getFragments())
            {
                tr.hide(f);
            }
            tr.show(homeFragment);
            tr.commit();
            return;
        }
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
