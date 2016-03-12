package com.yangtze.volunteer.model.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by liuhui on 2016/3/3.
 */
public class VolunteerActive extends BmobObject
{
    private User author;
    private String title;
    private String startTime;
    private Long time;
    private Integer status;
    private BmobRelation attendee;
    private String desc;
    private Integer maxNumber;

    public User getAuthor()
    {
        return author;
    }

    public void setAuthor(User author)
    {
        this.author = author;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getStartTime()
    {
        return startTime;
    }

    public void setStartTime(String startTime)
    {
        this.startTime = startTime;
    }

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public BmobRelation getAttendee()
    {
        return attendee;
    }

    public void setAttendee(BmobRelation attendee)
    {
        this.attendee = attendee;
    }

    public String getDesc()
    {
        return desc;
    }

    public void setDesc(String desc)
    {
        this.desc = desc;
    }

    public Integer getMaxNumber()
    {
        return maxNumber;
    }

    public void setMaxNumber(Integer maxNumber)
    {
        this.maxNumber = maxNumber;
    }

    public Long getTime()
    {
        return time;
    }

    public void setTime(Long time)
    {
        this.time = time;
    }
}
