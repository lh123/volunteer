package com.yangtze.volunteer.model.bean;
import cn.bmob.v3.BmobUser;

public class User extends BmobUser
{
    private String xuehao;
    private String sex;
    private String img;

    public void setXuehao(String xuehao)
    {
        this.xuehao = xuehao;
    }

    public String getXuehao()
    {
        return xuehao;
    }

    public void setSex(String sex)
    {
        this.sex = sex;
    }

    public String getSex()
    {
        return sex;
    }
    
    public void setImg(String img)
    {
        this.img = img;
    }

    public String getImg()
    {
        return img;
    }
}
