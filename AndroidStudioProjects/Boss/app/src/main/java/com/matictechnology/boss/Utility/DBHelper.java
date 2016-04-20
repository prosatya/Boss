package com.matictechnology.boss.Utility;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.matictechnology.boss.Classes.User;

import java.util.ArrayList;

/**
 * Created by matic on 20/4/16.
 */
public class DBHelper extends SQLiteOpenHelper
{
    public DBHelper(Context context)
    {
        super(context, "Boss", null, 1);
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String Create_user="create table USER(Email text, Name text, Gender text, FB_Profile text, Picture_URL text, Birthday text, FB_ID text);";
        db.execSQL(Create_user);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }

    public boolean insert_user(SQLiteDatabase db,String Email,String Name,String Gender ,String FB_Profile ,String Picture_URL ,String Birthday ,String FB_ID )
    {
        ContentValues initialValues = new ContentValues();

        initialValues.put("Email", Email);
        initialValues.put("Name", Name);
        initialValues.put("Gender", Gender);
        initialValues.put("FB_Profile", FB_Profile);
        initialValues.put("Picture_URL", Picture_URL);
        initialValues.put("FB_ID", FB_ID);
        initialValues.put("Birthday", Birthday);

        long stat=db.insert("USER", null, initialValues);
        if(stat!=-1)
            return true;
        else
            return false;
    }

    public ArrayList<User> fetch_user(SQLiteDatabase db)
    {
        String fetch_query="select * from USER";
        Cursor cursor=db.rawQuery(fetch_query,null);

        cursor.moveToFirst();
        if(cursor.getCount()>0)
        {
            ArrayList<User> al=new ArrayList<>();
            for(int count=0;count<cursor.getCount();count++)
            {
                User u=new User();
                //Email , Name , Gender , FB_Profile , Picture_URL , Birthday , FB_ID
                u.setEmail(cursor.getString(0));
                u.setName(cursor.getString(1));
                u.setGender(cursor.getString(2));
                u.setFB_Profile(cursor.getString(3));
                u.setPicture_url(cursor.getString(4));
                u.setBirthday(cursor.getString(5));
                u.setFB_ID(cursor.getString(6));
                al.add(u);
            }
            cursor.close();
            return al;
        }
        else
            return null;


    }
}
