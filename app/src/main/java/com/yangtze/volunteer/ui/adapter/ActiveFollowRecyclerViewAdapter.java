package com.yangtze.volunteer.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.yangtze.volunteer.model.bean.User;

import java.util.List;

/**
 * Created by liuhui on 2016/3/3.
 */
public class ActiveFollowRecyclerViewAdapter extends RecyclerView.Adapter
{
    private List<User> list;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {

    }

    @Override
    public int getItemCount()
    {
        if (list==null)
        {
            return 0;
        }
        return list.size();
    }

    public List<User> getList()
    {
        return list;
    }

    public void setList(List<User> list)
    {
        this.list = list;
    }
}
