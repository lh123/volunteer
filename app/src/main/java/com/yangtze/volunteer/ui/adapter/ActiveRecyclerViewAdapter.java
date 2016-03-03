package com.yangtze.volunteer.ui.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yangtze.volunteer.R;
import com.yangtze.volunteer.model.bean.VolunteerActive;
import com.yangtze.volunteer.ui.ActiveDetailActivity;

import java.util.List;

/**
 * Created by liuhui on 2016/3/3.
 */
public class ActiveRecyclerViewAdapter extends RecyclerView.Adapter
{
    private List<VolunteerActive> list;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.active_item,parent,false);
        return new ActiveHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        ActiveHolder h= (ActiveHolder) holder;
        VolunteerActive active = list.get(position);
        h.activeTitle.setText(active.getTitle());
        h.activeNum.setText(active.getMaxNumber()+"人");
        h.activeTime.setText(active.getStartTime());
    }

    @Override
    public int getItemCount()
    {
        if (list!=null)
        {
            return list.size();
        }
        return 0;
    }

    public List<VolunteerActive> getList()
    {
        return list;
    }

    public void setList(List<VolunteerActive> list)
    {
        this.list = list;
    }

    class ActiveHolder extends RecyclerView.ViewHolder
    {
        private TextView activeTitle;
        private TextView activeState;
        private TextView activeNum;
        private TextView activeTime;

        public ActiveHolder(final View itemView)
        {
            super(itemView);
            activeTitle= (TextView) itemView.findViewById(R.id.active_title);
            activeState= (TextView) itemView.findViewById(R.id.active_state);
            activeNum= (TextView) itemView.findViewById(R.id.active_num);
            activeTime= (TextView) itemView.findViewById(R.id.active_time);
            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent i=new Intent();
                    i.setClass(itemView.getContext(), ActiveDetailActivity.class);
                    i.putExtra("data",list.get(getAdapterPosition()));
                    itemView.getContext().startActivity(i);
                }
            });
        }
    }
}
