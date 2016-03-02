package com.yangtze.volunteer.ui;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.yangtze.volunteer.R;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.support.v7.widget.Toolbar;
import com.yangtze.volunteer.utils.ToolbarUtils;

public class WebViewActivity extends AppCompatActivity
{
    private WebView webView;
    private Toolbar toolbar;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        toolbar=(Toolbar) findViewById(R.id.toolbar);
        ToolbarUtils.initToolbar(toolbar,this);
        webView = (WebView) findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(){
                public boolean shouldOverrideUrlLoading(WebView view, String url)
                { 
                    view.loadUrl(url); 
                    return true;
                }
            });
        String path=getIntent().getStringExtra("url");
        webView.loadUrl(path);
    }
}
