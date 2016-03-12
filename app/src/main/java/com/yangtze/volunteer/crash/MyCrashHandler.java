package com.yangtze.volunteer.crash;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class MyCrashHandler implements Thread.UncaughtExceptionHandler
{
    private static final MyCrashHandler handler = new MyCrashHandler();

    private Context mContext;

    private MyCrashHandler()
    {
    }

    public static MyCrashHandler getInstance()
    {
        return handler;
    }

    public void init(Context context)
    {
        mContext = context;
    }

    @Override
    public void uncaughtException(Thread p1, Throwable p2)
    {
        p2.printStackTrace();
        Intent i = new Intent();
        i.setClass(mContext, CrashActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("error", p2);
        PendingIntent pi = PendingIntent.getActivity(mContext, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        am.cancel(pi);
        am.set(AlarmManager.RTC, System.currentTimeMillis() + 1000, pi);
        System.exit(0);
    }
}
