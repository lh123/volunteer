package com.yangtze.volunteer.ui;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import com.bumptech.glide.Glide;
import com.yangtze.volunteer.App;
import com.yangtze.volunteer.R;
import com.yangtze.volunteer.utils.ToolbarUtils;
import java.io.IOException;
import java.net.URL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import uk.co.senab.photoview.PhotoView;

public class NewsDetailActivity extends AppCompatActivity
{
    private WebView webView;
    private ProgressBar progressBar;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_ACTION_MODE_OVERLAY);
        setContentView(R.layout.activity_news_detail);
        initToolbar();
        webView = (WebView) findViewById(R.id.webview);
        progressBar= (ProgressBar) findViewById(R.id.progressbar);
        String url=getIntent().getStringExtra("data");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new ImgClick(),"Android");
        initData(url);
    }

    private void initData(final String path)
    {
        progressBar.setVisibility(View.VISIBLE);
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                Document d= null;
                try
                {
                    d = Jsoup.parse(new URL(path), 3000);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                if(d==null)
                {
                    return;
                }
                final Element dd=d.getElementsByClass("bod2").get(0);
                Elements imgs=dd.getElementsByTag("img");
                Elements titles=dd.getElementsByClass("bttitle");
                titles.get(0).attr("style", "color: #323232;font-size: 24px;font-style: normal;font-weight: bold;line-height: 35px;text-align: center;");
                for(Element e:imgs)
                {
                    e.attr("width","100%");
                    e.attr("onclick","Android.imgClick(this.src)");
                    e.removeAttr("height");
                }
                final String content=dd.html().replaceAll("../../../", "http://zgzyz.cyol.com/");
                App.getApp().getMainHandler().post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        progressBar.setVisibility(View.INVISIBLE);
                        webView.loadData(content,"text/html;charset=UTF-8",null);
                    }
                });
            }
        }).start();
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
    
    class ImgClick
    {
        @JavascriptInterface
        public void imgClick(String path)
        {
            showPopupWindows(path);
        }
    }
    
    public void showPopupWindows(String url)
    {
        // TODO: 2016/3/12  
    }
}
