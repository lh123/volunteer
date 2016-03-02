package com.yangtze.volunteer.ui;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.yangtze.volunteer.R;

import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import com.yangtze.volunteer.mvp.views.LoginView;
import android.widget.Toast;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.view.View;
import android.content.Intent;
import com.yangtze.volunteer.mvp.presenter.impl.LoginPresenter;
import com.yangtze.volunteer.utils.ToolbarUtils;

public class LoginActivity extends AppCompatActivity implements LoginView
{
    private Button btnLogin;
    private EditText etAccount;
    private EditText etPassword;
    private TextView tvRegister;
    private Toolbar toolbar;
    
    private LoginPresenter presenter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initToolbar();
        etAccount=(EditText) findViewById(R.id.et_username);
        etPassword=(EditText) findViewById(R.id.et_password);
        btnLogin=(Button) findViewById(R.id.btn_login);
        tvRegister=(TextView) findViewById(R.id.btn_register);
        btnLogin.setOnClickListener(new OnClickListener(){

                @Override
                public void onClick(View p1)
                {
                    presenter.login();
                }
            });
        tvRegister.setOnClickListener(new OnClickListener(){

                @Override
                public void onClick(View p1)
                {
                    Intent i=new Intent();
                    i.setClass(LoginActivity.this,RegisterActivity.class);
                    startActivity(i);
                }
            });
         presenter=new LoginPresenter(this);
         presenter.onCreate(savedInstanceState);
    }

    private void initToolbar()
    {
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        ToolbarUtils.initToolbar(toolbar, this);
        toolbar.setNavigationOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finishActivity();
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
    public void setAccount(String account)
    {
        etAccount.setText(account);
    }

    @Override
    public String getAccount()
    {
        return etAccount.getText().toString();
    }

    @Override
    public void setPassword(String password)
    {
        etPassword.setText(password);
    }

    @Override
    public String getPassword()
    {
        return etPassword.getText().toString();
    }

    @Override
    public void finishActivity()
    {
        finish();
    }

    @Override
    public void showToast(String msg)
    {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
}
