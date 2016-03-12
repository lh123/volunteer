package com.yangtze.volunteer.ui.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yangtze.volunteer.R;
import com.yangtze.volunteer.model.bean.User;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by liuhui on 2016/3/12.
 */
public class RankRecyclerViewAdapter extends RecyclerView.Adapter
{
    private List<User> list;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.rank_item,parent,false);
        return new FollowHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        FollowHolder h= (FollowHolder) holder;
        User user = list.get(position);
        Glide.with(holder.itemView.getContext()).load(user.getImg()).into(h.img);
        h.name.setText(user.getUsername());
        h.xuehao.setText(user.getXuehao());
        if(position<3)
        {
            h.rank.setTextColor(Color.RED);
        }
        else
        {
            h.rank.setTextColor(Color.BLACK);
        }
        h.rank.setText(position+1+"");
        h.coint.setText("积分:"+(user.getCoint()==null?"0":user.getCoint()));
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

    class FollowHolder extends RecyclerView.ViewHolder
    {
        private TextView name;
        private TextView xuehao;
        private CircleImageView img;
        private TextView rank;
        private TextView coint;
        public FollowHolder(View itemView)
        {
            super(itemView);
            name=(TextView) itemView.findViewById(R.id.name);
            xuehao=(TextView) itemView.findViewById(R.id.user_xuehao);
            img=(CircleImageView) itemView.findViewById(R.id.user_img);
            rank= (TextView) itemView.findViewById(R.id.tv_rank);
            coint= (TextView) itemView.findViewById(R.id.tv_coint);
            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    // TODO: 2016/3/3
                }
            });
        }
    }
}
