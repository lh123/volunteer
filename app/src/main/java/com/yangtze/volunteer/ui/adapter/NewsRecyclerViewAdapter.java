package com.yangtze.volunteer.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.yangtze.volunteer.R;
import com.yangtze.volunteer.model.bean.NewsItem;
import java.util.ArrayList;
import android.view.View.OnClickListener;
import android.content.Intent;
import com.yangtze.volunteer.ui.WebViewActivity;

public class NewsRecyclerViewAdapter extends RecyclerView.Adapter
{
    private ArrayList<NewsItem> list;
    private Context context;

    public NewsRecyclerViewAdapter(Context context)
    {
        this.context = context;
    }
    
    public void setData(ArrayList<NewsItem> list)
    {
        this.list=list;
    }
    
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup p1, int p2)
    {
        View view=LayoutInflater.from(context).inflate(R.layout.news_item,p1,false);
        return new NewsHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder p1, int p2)
    {
        NewsHolder holder=(NewsRecyclerViewAdapter.NewsHolder) p1;
        holder.title.setText(list.get(p2).getTitle());
        holder.time.setText(list.get(p2).getTime());
    }

    @Override
    public int getItemCount()
    {
        if(list==null)
            return 0;
        return list.size();
    }
    
    class NewsHolder extends RecyclerView.ViewHolder
    {
        private TextView title,time;
        public NewsHolder(View view)
        {
            super(view);
            title=(TextView) itemView.findViewById(R.id.news_title);
            time=(TextView) itemView.findViewById(R.id.news_time);
            itemView.setOnClickListener(new OnClickListener(){

                    @Override
                    public void onClick(View p1)
                    {
                        Intent i=new Intent();
                        i.setClass(context,WebViewActivity.class);
                        i.putExtra("url",list.get(getAdapterPosition()).getUrl());
                        context.startActivity(i);
                    }
                });
        }
    }
    
}
