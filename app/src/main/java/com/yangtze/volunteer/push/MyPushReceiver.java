package com.yangtze.volunteer.push;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import cn.bmob.push.PushConstants;
import android.app.NotificationManager;
import android.app.Notification;
import com.yangtze.volunteer.R;

public class MyPushReceiver extends BroadcastReceiver
{

    @Override
    public void onReceive(Context p1, Intent p2)
    {
        System.out.println("push");
        //Toast.makeText(p1,p2.getStringExtra(PushConstants.EXTRA_PUSH_MESSAGE_STRING),Toast.LENGTH_SHORT).show();
        NotificationManager nm=(NotificationManager) p1.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder nb=new Notification.Builder(p1);
        nb.setContentTitle("提示");
        nb.setSmallIcon(R.drawable.ic_launcher);
        nb.setContentText(p2.getStringExtra(PushConstants.EXTRA_PUSH_MESSAGE_STRING));
        nm.notify(R.id.home,nb.build());
    }
    
}
