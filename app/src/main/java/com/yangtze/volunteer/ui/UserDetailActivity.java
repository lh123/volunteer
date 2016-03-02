package com.yangtze.volunteer.ui;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.yangtze.volunteer.R;
import com.yangtze.volunteer.domain.BusProvide;
import com.yangtze.volunteer.domain.event.UserEvent;
import com.yangtze.volunteer.model.bean.User;
import com.yangtze.volunteer.utils.ToolbarUtils;

import java.io.File;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by liuhui on 2016/3/2.
 */
public class UserDetailActivity extends AppCompatActivity
{
    private TextView userName;
    private TextView userXuehao;
    private TextView userCoint;
    private CircleImageView circleImageView;
    private Toolbar toolbar;
    private Button btnLoginOut;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_user_detail);
        initToolbar();
        userName = (TextView) findViewById(R.id.user_name);
        userXuehao = (TextView) findViewById(R.id.user_xuehao);
        userCoint = (TextView) findViewById(R.id.user_conit);
        btnLoginOut = (Button) findViewById(R.id.btn_loginout);
        circleImageView = (CircleImageView) findViewById(R.id.user_img);
        user = BmobUser.getCurrentUser(this, User.class);
        circleImageView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                changeUserImg();
            }
        });
        btnLoginOut.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                user.logOut(UserDetailActivity.this);
                Toast.makeText(UserDetailActivity.this, "已退出登录", Toast.LENGTH_SHORT).show();
                UserEvent e=new UserEvent();
                e.status= UserEvent.OFFLINE;
                BusProvide.getBus().post(e);
                finish();
            }
        });
        initData();
    }

    private void initToolbar()
    {
        toolbar= (Toolbar) findViewById(R.id.toolbar);
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

    private void changeUserImg()
    {
        Intent i = new Intent();
        i.setAction(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/*");
        startActivityForResult(i, 0);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode != RESULT_OK)
        {
            return;
        }
        Uri imgUri = data.getData();
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(imgUri, proj, null, null, null);
        if (cursor.moveToFirst())
        {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        if (res != null)
        {
            uploadImg(res);
        }
    }

    private void uploadImg(String res)
    {
        final BmobFile file = new BmobFile(new File(res));
        file.upload(this, new UploadFileListener()
        {
            @Override
            public void onSuccess()
            {
                User buser = new User();
                buser.setImg(file.getFileUrl(UserDetailActivity.this));
                buser.update(UserDetailActivity.this, user.getObjectId(), new UpdateListener()
                {
                    @Override
                    public void onSuccess()
                    {
                        initData();
                        UserEvent e=new UserEvent();
                        e.status=UserEvent.UPDATE;
                        BusProvide.getBus().post(e);
                    }

                    @Override
                    public void onFailure(int i, String s)
                    {
                        Toast.makeText(UserDetailActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(int i, String s)
            {
                Toast.makeText(UserDetailActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void initData()
    {
        user=BmobUser.getCurrentUser(this,User.class);
        if (user.getImg() != null)
        {
            Glide.with(this).load(user.getImg()).into(circleImageView);
        }
        userName.setText(user.getUsername());
        userXuehao.setText(user.getXuehao());
        userCoint.setText(user.getCoint() + "分");
    }
}
