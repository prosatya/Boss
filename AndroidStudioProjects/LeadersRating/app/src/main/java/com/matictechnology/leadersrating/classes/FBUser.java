package com.matictechnology.leadersrating.classes;

/**
 * Created by matic on 20/4/16.
 */
public class FBUser
{
    String Name;
    String Email;
    String Gender;
    String FB_Profile;
    String Picture_url;
    String Birthday;
    String FB_ID;

    public String getCover_url() {
        return Cover_url;
    }

    public void setCover_url(String cover_url) {
        Cover_url = cover_url;
    }

    String Cover_url;

    public String getName()
    {

        return Name;
    }

    public void setName(String name)
    {
        Name = name;
    }

    public String getEmail()
    {
        return Email;
    }

    public void setEmail(String email)
    {
        Email = email;
    }

    public String getGender()
    {
        return Gender;
    }

    public void setGender(String gender)
    {
        Gender = gender;
    }

    public String getFB_Profile()
    {
        return FB_Profile;
    }

    public void setFB_Profile(String FB_Profile)
    {
        this.FB_Profile = FB_Profile;
    }

    public String getPicture_url()
    {
        return Picture_url;
    }

    public void setPicture_url(String picture_url)
    {
        Picture_url = picture_url;
    }

    public String getBirthday()
    {
        return Birthday;
    }

    public void setBirthday(String birthday)
    {
        Birthday = birthday;
    }

    public String getFB_ID()
    {
        return FB_ID;
    }

    public void setFB_ID(String FB_ID)
    {
        this.FB_ID = FB_ID;
    }
}
