package com.matictechnology.leadersrating.classes;

/**
 * Created by maticd1 on 6/4/16.
 */
public class Contacts
{
    String id;
    String name;
    boolean selected = false;
    String number[];//size];

    public boolean isSelected()
    {
        return selected;
    }
    public void setSelected(boolean selected)
    {
        this.selected = selected;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    String email;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String[] getNumber()
    {
        return number;
    }

    public void setNumber(String[] number)
    {
        this.number = number;
    }
}
