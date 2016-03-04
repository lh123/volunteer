package com.yangtze.volunteer.ui;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.TextView;
import com.yangtze.volunteer.App;
import com.yangtze.volunteer.R;
import com.yangtze.volunteer.model.bean.NewsItem;
import java.io.IOException;
import java.net.URL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import android.graphics.Bitmap;
import java.net.MalformedURLException;
import com.yangtze.volunteer.utils.StreamUtils;
import android.graphics.BitmapFactory;
import android.widget.ScrollView;

public class NewsDetailActivity extends AppCompatActivity
{

    private TextView tvContent;
    private ScrollView scrollView;
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO: Implement this method
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        tvContent = (TextView) findViewById(R.id.tv_content);
        scrollView=(ScrollView) findViewById(R.id.scrollview);
        path=getIntent().getStringExtra("data");
        initData();
    }

    private void initData()
    {
        new Thread(new Runnable(){

                @Override
                public void run()
                {
                    try
                    {
                        Document d=Jsoup.parse(new URL(path), 3000);
                        final Element dd=d.getElementsByClass("zhengwen").get(0);
                        //final Element e=dd.getElementsByTag("dd").get(0);
                        App.getApp().getMainHandler().post(new Runnable(){

                                @Override
                                public void run()
                                {
                                    tvContent.setText(Html.fromHtml(dd.toString(),new ImageGet(),null));
                                }
                            });
                    }
                    catch (IOException e)
                    {}
                }
            }).start();

    }

    class ImageGet implements Html.ImageGetter
    {

        @Override
        public Drawable getDrawable(String p1)
        {
            final UrlDrawable d=new UrlDrawable();
            final String path=p1.replace("../../../","http://zgzyz.cyol.com/");
            new Thread(new Runnable(){

                    @Override
                    public void run()
                    {
                        try
                        {
                            URL url=new URL(path);
                            Bitmap bit=BitmapFactory.decodeStream(url.openConnection().getInputStream());
                            d.bitmap=bit;
                            d.setBounds(0,0,bit.getWidth(),bit.getHeight());
                            tvContent.setText(tvContent.getText());
                            tvContent.requestLayout();
                            tvContent.invalidate();
                            scrollView.requestLayout();
                            scrollView.invalidate();
                        }
                        catch (Exception e)
                        {}
                    }
                }).start();
            return d;
        }  

    }

    class UrlDrawable extends BitmapDrawable
    {
        protected Bitmap bitmap;

        @Override
        public void draw(Canvas canvas)
        {
            if (bitmap != null)
            {
                canvas.drawBitmap(bitmap, 0, 0, getPaint());
            }
        }
    }
}
