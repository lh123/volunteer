package com.yangtze.volunteer.domain;
import com.squareup.otto.Bus;

public class BusProvide
{
    private static final Bus bus=new Bus();
    
    public static Bus getBus()
    {
        return bus;
    }
}
