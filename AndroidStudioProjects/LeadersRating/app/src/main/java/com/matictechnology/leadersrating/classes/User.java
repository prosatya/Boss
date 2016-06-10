package com.matictechnology.leadersrating.classes;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by matic on 12/5/16.
 */
public class User implements Serializable
{
    String picture;
    String picture_path;
    String name;
    String phone;
    String email;
    String username;
    String password;
    String work_name;
    String category;
    String address;
    String country;
    String cover_img;
    String cover_path;
    int join_month;

    public int getJoin_month() {
        return join_month;
    }

    public void setJoin_month(int join_month) {
        this.join_month = join_month;
    }

    public int getJoin_year() {
        return join_year;
    }

    public void setJoin_year(int join_year) {
        this.join_year = join_year;
    }

    int join_year;
    ArrayList<String> edu_school,edu_degree,edu_location,edu_specialization,edu_from,edu_to,edu_extra;
    ArrayList<String> exp_company,exp_location,exp_from,exp_to,exp_post;
    ArrayList<String> skills;


    public String getCover_path()
    {
        return cover_path;
    }

    public void setCover_path(String cover_path)
    {
        this.cover_path = cover_path;
    }

    public String getCover_img()
    {
        return cover_img;
    }

    public void setCover_img(String cover_img)
    {
        this.cover_img = cover_img;
    }


    public void addskills(String str)
    {
        skills.add(str);
    }

    public void addExp_company(String str)
    {
        exp_company.add(str);
    }

    public void addExp_location(String str)
    {
        exp_location.add(str);
    }

    public void addExp_from(String str)
    {
        exp_from.add(str);
    }

    public void addExp_to(String str)
    {
        exp_to.add(str);
    }

    public void addExp_post(String str)
    {
        exp_post.add(str);
    }

    public void addEdu_school(String str)
    {
        edu_school.add(str);
    }

    public void addEdu_degree(String str)
    {
        edu_degree.add(str);
    }

    public void addEdu_location(String str)
    {
        edu_location.add(str);
    }

    public void addEdu_specialzation(String str)
    {
        edu_specialization.add(str);
    }

    public void addEdu_from(String str)
    {
        edu_from.add(str);
    }

    public void addEdu_to(String str)
    {
        edu_to.add(str);
    }

    public void addEdu_extra(String str)
    {
        edu_extra.add(str);
    }


    public User()
    {
        edu_school=new ArrayList<>();
        edu_degree=new ArrayList<>();
        edu_location=new ArrayList<>();
        edu_specialization=new ArrayList<>();
        edu_from=new ArrayList<>();
        edu_to=new ArrayList<>();
        edu_extra=new ArrayList<>();
        exp_company=new ArrayList<>();
        exp_location=new ArrayList<>();
        exp_from=new ArrayList<>();
        exp_to=new ArrayList<>();
        exp_post=new ArrayList<>();
        skills=new ArrayList<>();
    }

    public ArrayList<String> getEdu_school()
    {
        return edu_school;
    }

    public void setEdu_school(ArrayList<String> edu_school)
    {
        this.edu_school = edu_school;
    }

    public ArrayList<String> getEdu_degree()
    {
        return edu_degree;
    }

    public void setEdu_degree(ArrayList<String> edu_degree)
    {
        this.edu_degree = edu_degree;
    }

    public ArrayList<String> getEdu_location()
    {
        return edu_location;
    }

    public void setEdu_location(ArrayList<String> edu_location)
    {
        this.edu_location = edu_location;
    }

    public ArrayList<String> getEdu_specialization()
    {
        return edu_specialization;
    }

    public void setEdu_specialization(ArrayList<String> edu_specialization)
    {
        this.edu_specialization = edu_specialization;
    }

    public ArrayList<String> getEdu_from()
    {
        return edu_from;
    }

    public void setEdu_from(ArrayList<String> edu_from)
    {
        this.edu_from = edu_from;
    }

    public ArrayList<String> getEdu_to()
    {
        return edu_to;
    }

    public void setEdu_to(ArrayList<String> edu_to)
    {
        this.edu_to = edu_to;
    }

    public ArrayList<String> getEdu_extra()
    {
        return edu_extra;
    }

    public void setEdu_extra(ArrayList<String> edu_extra)
    {
        this.edu_extra = edu_extra;
    }

    public ArrayList<String> getExp_company()
    {
        return exp_company;
    }

    public void setExp_company(ArrayList exp_company)
    {
        this.exp_company = exp_company;
    }

    public ArrayList<String> getExp_location()
    {
        return exp_location;
    }

    public void setExp_location(ArrayList exp_location)
    {
        this.exp_location = exp_location;
    }

    public ArrayList<String> getExp_from()
    {
        return exp_from;
    }

    public void setExp_from(ArrayList exp_from)
    {
        this.exp_from = exp_from;
    }

    public ArrayList<String> getExp_to()
    {
        return exp_to;
    }

    public void setExp_to(ArrayList exp_to)
    {
        this.exp_to = exp_to;
    }

    public ArrayList<String> getExp_post()
    {
        return exp_post;
    }

    public void setExp_post(ArrayList exp_post)
    {
        this.exp_post = exp_post;
    }

    public ArrayList<String> getSkills()
    {
        return skills;
    }

    public void setSkills(ArrayList skills)
    {
        this.skills = skills;
    }

    public String getPicture()
    {
        return picture;
    }

    public void setPicture(String picture)
    {
        this.picture = picture;
    }

    public String getPicture_path()
    {
        return picture_path;
    }

    public void setPicture_path(String picture_path)
    {
        this.picture_path = picture_path;
    }

    public String getName()
    {
        return name;

    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getWork_name()
    {
        return work_name;
    }

    public void setWork_name(String work_name)
    {
        this.work_name = work_name;
    }

    public String getCategory()
    {
        return category;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }
}
