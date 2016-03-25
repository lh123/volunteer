package com.yangtze.volunteer.ui;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import com.yangtze.volunteer.R;
import com.yangtze.volunteer.mvp.presenter.impl.PostActivePresenter;
import com.yangtze.volunteer.mvp.views.PostActiveView;
import com.yangtze.volunteer.utils.ToolbarUtils;
import java.util.Calendar;
import android.os.Build;

/**
 * Created by liuhui on 2016/3/3.
 */
public class PostActiveActivity extends AppCompatActivity implements PostActiveView
{
    private EditText edTitle;
    private EditText edDes;
    private EditText edNum;
    private Button btnPost;
    private Button btnTime;
    private PostActivePresenter presenter;

    private Toolbar toolbar;

    private String time;
    private Long lTime;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //supportRequestWindowFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);  
        getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_post_active);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP)
        {
            getWindow().setEnterTransition(new Slide());
        }
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        initToolbar();
        presenter=new PostActivePresenter(this);
        edTitle= (EditText) findViewById(R.id.ed_title);
        edDes= (EditText) findViewById(R.id.ed_des);
        edNum= (EditText) findViewById(R.id.ed_num);
        btnPost= (Button) findViewById(R.id.btn_post);
        btnTime= (Button) findViewById(R.id.btn_time);
        btnTime.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showTimeDialog();
            }
        });
        btnPost.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                presenter.postActive();
            }
        });
        presenter.onCreate(savedInstanceState);
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

    @Override
    protected void onDestroy()
    {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public String getActiveTitle()
    {
        return edTitle.getText().toString();
    }

    @Override
    public String getActiveDes()
    {
        return edDes.getText().toString();
    }

    @Override
    public String getActiveNum()
    {
        return edNum.getText().toString();
    }

    @Override
    public String getActiveTime()
    {
        return time;
    }

    @Override
    public Long getTime()
    {
        return lTime;
    }

    @Override
    public void showTimeDialog()
    {
        Calendar ca=Calendar.getInstance();
        ca.setTimeInMillis(System.currentTimeMillis());
        DatePickerDialog dp=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                time=year+"年"+(monthOfYear+1)+"月"+dayOfMonth+"日";
                Calendar c=Calendar.getInstance();
                c.set(year,monthOfYear,dayOfMonth);
                lTime=c.getTimeInMillis();
            }
        },ca.get(Calendar.YEAR),ca.get(Calendar.MONTH),ca.get(Calendar.DAY_OF_MONTH));
        dp.setCancelable(false);
        dp.show();
    }

    @Override
    public void showToast(String msg)
    {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressDialog()
    {
        pd=new ProgressDialog(this);
        pd.setTitle("请稍后!");
        pd.setMessage("正在发布");
        pd.show();
    }

    @Override
    public void dismissProgressDialog()
    {
        pd.dismiss();
    }
}
