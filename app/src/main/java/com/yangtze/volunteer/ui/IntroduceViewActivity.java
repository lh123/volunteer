package com.yangtze.volunteer.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;

import com.tencent.bugly.crashreport.CrashReport;
import com.yangtze.volunteer.R;
import com.yangtze.volunteer.utils.ToolbarUtils;

/**
 * Created by liuhui on 2016/3/12.
 */
public class IntroduceViewActivity extends AppCompatActivity
{
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        initToolbar();
        webView = (WebView) findViewById(R.id.webview);
        findViewById(R.id.progressbar).setVisibility(View.GONE);
        webView.getSettings().setDefaultTextEncodingName("UTF-8");
        webView.loadUrl("file:///android_asset/active/active.htm");

    }

    private void initToolbar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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
}
