package com.yangtze.volunteer.ui;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.yangtze.volunteer.R;
import android.widget.EditText;
import android.widget.Button;
import com.yangtze.volunteer.mvp.views.RegisterView;
import android.widget.Toast;
import com.yangtze.volunteer.mvp.presenter.impl.RegisterPresenter;
import android.view.View.OnClickListener;
import android.view.View;

public class RegisterActivity extends AppCompatActivity implements RegisterView
{
    private EditText etAccount;
    private EditText etPassword;
    private EditText etPhone;
    private EditText etXuehao;
    private Button btnRegister;

    private RegisterPresenter presenter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etAccount = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);
        etPhone = (EditText) findViewById(R.id.et_phone);
        etXuehao = (EditText) findViewById(R.id.et_xuehao);
        btnRegister = (Button) findViewById(R.id.btn_register);
        presenter=new RegisterPresenter(this);
        presenter.onCreate(savedInstanceState);
        btnRegister.setOnClickListener(new OnClickListener(){

                @Override
                public void onClick(View p1)
                {
                    presenter.register();
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
    public void setXuehao(String xuehao)
    {
        etXuehao.setText(xuehao);
    }

    @Override
    public String getXuehao()
    {
        return etXuehao.getText().toString();
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
    public void showToast(String msg)
    {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void finishActivity()
    {
        finish();
    }
}
