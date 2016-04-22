package com.matictechnology.boss.Utility;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.util.Log;
import com.matictechnology.boss.Classes.Contacts;
import com.matictechnology.boss.Classes.User;

import java.util.ArrayList;

/**
 * Created by matic on 20/4/16.
 */
public class DBHelper extends SQLiteOpenHelper
{
    ArrayList<Contacts> alist;
    ArrayList<String> slist;
    Context c;
    public DBHelper(Context context)
    {
        super(context, "Boss", null, 1);
        c=context;
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {

        String Create_user="create table USER(Email text, Name text, Gender text, FB_Profile text, Picture_URL text, Birthday text, FB_ID text);";
        String user_details="create table contact(id text, name text, email text)";
        String user_number="create table number(id text,number text)";
        try
        {
            db.execSQL(Create_user);
            db.execSQL(user_details);
            db.execSQL(user_number);
        }
        catch(SQLException e)
        {

            e.printStackTrace();

        }



        alist=new ArrayList<>();
        slist=new ArrayList<>();

        Log.e("contacts","getting");
        ContentResolver cr = c.getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);


        if (cur.getCount() > 0)
        {
            Log.e("contacts","started getting");
            while (cur.moveToNext())
            {
                String sl = "";
                Contacts c = new Contacts();
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                c.setId(id);
                sl = sl + "" + id + "\n";
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                c.setName(name);
                sl = sl + "" + name + "\n";
                if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
                    int count = 0;
                    String phoneNo[] = new String[pCur.getCount()];

                    while (pCur.moveToNext()) {

                        phoneNo[count++] = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        sl = sl + "" + pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)) + "\n";
                    }
                    c.setNumber(phoneNo);
                    pCur.close();
                }
                //slist.add(sl);
                alist.add(c);
            }
        }
        Log.e("contacts","done getting");
        Log.e("contacts","inserting");
        cur.close();
        insert_details(db, alist);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }

    public ArrayList<Contacts> getAll(SQLiteDatabase db)
    {
        ArrayList<Contacts> temp = new ArrayList<Contacts>();
        String select_user = "select * from contact";
        Cursor c = db.rawQuery(select_user, null);
        c.moveToFirst();
        if (c.getCount() > 0)
        {
            temp = new ArrayList<>();
            for(int count=0;count<c.getCount();count++)
            {
                //Log.e(""+count,""+c.getString(1));
                Contacts cn=new Contacts();
                cn.setId(c.getString(0));
                cn.setName(c.getString(1));
                //Log.e(""+c.getString(0),c.getString(1)+""+c.getString(2));
                if(c.getString(2)!=null)
                    cn.setEmail(c.getString(2));
                temp.add(cn);
                c.moveToNext();
            }
            c.close();
        }
        for(int count=0;count<temp.size();count++)
        {
            Contacts cn=new Contacts();
            cn=temp.get(count);
            //Log.e("in for numbers get","before if"+cn.getId()+",");
            if(cn.getId()!=null)
            {
                String select_number= "select * from number where id='"+cn.getId()+"'";
                Cursor c1 = db.rawQuery(select_number, null);
                c1.moveToFirst();
                String str[]=new String[c1.getCount()];
                if(c1.getCount()>0)
                {
                    //temp.get(count).size=c1.getCount();
                    for(int count1=0;count1<c1.getCount();count1++)
                    {
                        str[count1]=c1.getString(1);
                        c1.moveToNext();
                    }
                    temp.get(count).setNumber(str);

                    c1.close();
                }
            }
        }
        return temp;
    }

    public void insert_details(SQLiteDatabase db,ArrayList<Contacts> list)
    {

        Log.e("contacts","Actually inserting");
        for(int count=0;count<list.size();count++)
        {

            ContentValues v=new ContentValues();
            v.put("id", "" + list.get(count).getId());
            //Log.e("" + list.get(count).getId(), "" + list.get(count).getName());
            v.put("name", "" + list.get(count).getName());
            if(list.get(count).getName().contains("@")&&list.get(count).getName().contains(".com"))
                v.put("email",""+list.get(count).getEmail());

            db.insert("contact",null, v);

            if(list.get(count).getNumber()!=null)
            {
                ContentValues v1=new ContentValues();
                for(int count1=0;count1<list.get(count).getNumber().length;count1++)
                {
                    //Log.e("" + list.get(count).getId(), "" + list.get(count).number[count1]);
                    v1.put("id",""+list.get(count).getId());
                    v1.put("number",""+list.get(count).getNumber()[count1]);
                    db.insert("number", null, v1);
                }

            }
        }
        Log.e("contacts","done inserting");
        getAllPrint(db);
    }

    public void getAllPrint(SQLiteDatabase db)
    {
        Log.e("contacts","trying to print");
        ArrayList<Contacts> temp = new ArrayList<Contacts>();
        String select_user = "select * from contact";
        Cursor c = db.rawQuery(select_user, null);
        c.moveToFirst();
        if (c.getCount() > 0)
        {
            //temp = new ArrayList<>();
            for(int count=0;count<c.getCount();count++)
            {
                Contacts cn=new Contacts();
                cn.setId(c.getString(0));
                cn.setName(c.getString(1));
                Log.e(""+c.getString(0),c.getString(1)+""+c.getString(2));
                temp.add(cn);
                c.moveToNext();
            }
            c.close();
        }



            /*String select_number= "select * from user_number";
            SQLiteDatabase db1=getReadableDatabase();
            Cursor c1 = db1.rawQuery(select_number, null);
            c1.moveToFirst();
            Log.e("query", "" + select_number);
            //String str[]=new String[c1.getCount()];
            //Log.e("in for numbers get","in if"+cn.getId()+","+c1.getCount());
            if(c1.getCount()>0)
            {
                //temp.get(count).size=c1.getCount();
                for(int count1=0;count1<c1.getCount();count1++)
                {
                    Log.e(""+c1.getString(0),""+c1.getString(1));
                    //str[count1]=c1.getString(1);
                    c1.moveToNext();
                }
                //temp.get(count).number=str;
            }
            c1.close();*/

        //c.close();
        Log.e("contacts","done printing");
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
