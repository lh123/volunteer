package com.yangtze.volunteer.model;
import android.util.Log;

import java.util.ArrayList;
import com.yangtze.volunteer.model.bean.NewsItem;
import java.net.URL;
import org.jsoup.nodes.Document;
import org.jsoup.Jsoup;
import java.net.MalformedURLException;
import java.io.IOException;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

public class NewsModel
{
    public static final String FOCUS="http://www.zgzyz.org.cn/node_54667.htm";
    public static final String LOCATION="http://www.zgzyz.org.cn/node_24742.htm";
    
    public ArrayList<NewsItem> getFocus(String path)
    {
        ArrayList<NewsItem> list=new ArrayList<>();
        try
        {
            URL url=new URL(path);
            Document docu=Jsoup.parse(url, 3000);
            Elements es= docu.getElementsByClass("listX_X");
            Elements ess=es.get(0).getElementsByTag("li");
            for(Element e:ess)
            {
                NewsItem item=new NewsItem();
                item.setTitle(e.child(0).text());
                item.setUrl(e.child(0).attr("href"));
                item.setTime(e.child(2).text().replaceAll("\\[|\\]", ""));
                item.setImg(getImgUrl(item.getUrl()));
                list.add(item);
            }
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return list;
    }

    public String getImgUrl(String path)
    {
        String imgUrl=null;
        try
        {
            URL url=new URL(path);
            Document docu=Jsoup.parse(url, 3000);
            Elements es=docu.getElementsByClass("zhengwen");
            Elements img = es.get(0).getElementsByTag("img");
            if(img!=null&&img.size()>0)
            {
                imgUrl=img.get(0).attr("src");
                imgUrl=imgUrl.replace("../../../","http://zgzyz.cyol.com/");
                Log.e("img",imgUrl);
            }
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return imgUrl;
    }
}
