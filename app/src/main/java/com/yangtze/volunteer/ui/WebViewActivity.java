package com.yangtze.volunteer.ui;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.yangtze.volunteer.R;

import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.support.v7.widget.Toolbar;
import android.widget.ProgressBar;

import com.yangtze.volunteer.utils.ToolbarUtils;

public class WebViewActivity extends AppCompatActivity
{
    private WebView webView;
    private Toolbar toolbar;
    private ProgressBar progressBar;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        initToolbar();
        webView = (WebView) findViewById(R.id.webview);
        progressBar= (ProgressBar) findViewById(R.id.progressBar);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new MyWebViewClient());
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                progressBar.setVisibility(View.VISIBLE);
                view.loadUrl(url);
                return true;
            }
        });
        String path=getIntent().getStringExtra("url");
        webView.loadUrl(path);
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

    class MyWebViewClient extends WebChromeClient
    {
        @Override
        public void onProgressChanged(WebView view, int newProgress)
        {
            super.onProgressChanged(view, newProgress);
            progressBar.setProgress(newProgress);
            if(newProgress==100)
            {
                progressBar.setVisibility(View.GONE);
            }
        }
    }
}
