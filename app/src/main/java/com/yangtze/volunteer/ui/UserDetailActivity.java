package com.yangtze.volunteer.ui;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.yangtze.volunteer.R;
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
    private CircleImageView circleImageView;
    private Toolbar toolbar;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_user_detail);
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        ToolbarUtils.initToolbar(toolbar,this);
        userName= (TextView) findViewById(R.id.user_name);
        userXuehao= (TextView) findViewById(R.id.user_xuehao);
        circleImageView= (CircleImageView) findViewById(R.id.user_img);
        user= BmobUser.getCurrentUser(this,User.class);
        circleImageView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                changeUserImg();
            }
        });
        initData();
    }

    private void changeUserImg()
    {
        Intent i=new Intent();
        i.setAction(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/*");
        startActivityForResult(i, 0);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(resultCode!=RESULT_OK)
        {
            return;
        }
        Uri imgUri=data.getData();
        String res = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(imgUri, proj, null, null, null);
        if(cursor.moveToFirst()){;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        if(res!=null)
        {
            uploadImg(res);
        }
    }

    private void uploadImg(String res)
    {
        final BmobFile file=new BmobFile(new File(res));
        file.upload(this, new UploadFileListener()
        {
            @Override
            public void onSuccess()
            {
                User buser=new User();
                buser.setImg(file.getFileUrl(UserDetailActivity.this));
                buser.update(UserDetailActivity.this, user.getObjectId(), new UpdateListener()
                {
                    @Override
                    public void onSuccess()
                    {
                        initData();
                    }

                    @Override
                    public void onFailure(int i, String s)
                    {
                        Toast.makeText(UserDetailActivity.this,"上传失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(int i, String s)
            {
                Toast.makeText(UserDetailActivity.this,"上传失败",Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void initData()
    {
        if (user.getImg()!=null)
        {
            Glide.with(this).load(user.getImg()).into(circleImageView);
        }
        userName.setText(user.getEmail());
        userXuehao.setText(user.getXuehao());
    }
}
