package com.yangtze.volunteer.domain.event;
import com.yangtze.volunteer.model.bean.User;

public class LoginEvent
{
    public static final int SUCCESS=1;
    public static final int FAIL=2;
    
    public int status;
    public User user;
}
