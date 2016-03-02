package com.yangtze.volunteer.domain.event;
import com.yangtze.volunteer.model.bean.User;

public class UserEvent
{
    public static final int ONLINE=1;
    public static final int OFFLINE=2;
    public static final int UPDATE=3;
    
    public int status;
}
