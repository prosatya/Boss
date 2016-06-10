package com.matictechnology.leadersrating.util;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.util.Log;

import com.matictechnology.leadersrating.classes.Contacts;
import com.matictechnology.leadersrating.classes.FBUser;
import com.matictechnology.leadersrating.classes.User;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by matic on 2/5/16.
 */
public class DBHelper extends SQLiteOpenHelper
{
    Context c;
    ArrayList<Contacts> alist;

    public DBHelper(Context context)
    {
        super(context, "LeadersRating", null, 1);
        c=context;
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        String create_user="create table USER(NAME text," +
                "PHONE text primary key not null," +
                "EMAIL text," +
                "USERNAME text," +
                "PASSWORD text," +
                "CATEGORY text," +
                "WORK_NAME text," +
                "ADDRESS text," +
                "COUNTRY text," +
                "PICTURE text," +
                "PICTURE_PATH text," +
                "COVER text," +
                "COVER_PATH text," +
                "JOIN_MONTH integer," +
                "JOIN_YEAR integer);";

        String create_education="create table EDUCATION(PHONE text," +
                "SCHOOL text," +
                "DEGREE text," +
                "SPECIALISATION text," +
                "PLACE text," +
                "FRM text," +
                "T text," +
                "EXTRA text)";

        String create_experience="create table EXPERIENCE(PHONE text," +
                "ORGANIZATION text," +
                "PLACE text," +
                "FRM text," +
                "T text," +
                "POST text)";

        String create_skill="create table SKILL(PHONE text," +
                "SKILL text)";

        String Create_fbuser="create table FBUSER(Email text, " +
                "Name text, " +
                "Gender text, " +
                "FB_Profile text, " +
                "Picture_URL text, " +
                "Birthday text, " +
                "FB_ID text);";

        String user_details="create table contact(id text, " +
                "name text, " +
                "email text)";

        String user_number="create table number(id text," +
                "number text)";

        String create_users="create table USERS(NAME text," +
                "PHONE text primary key not null," +
                "EMAIL text," +
                "USERNAME text," +
                "PASSWORD text," +
                "CATEGORY text," +
                "WORK_NAME text," +
                "ADDRESS text," +
                "COUNTRY text," +
                "PICTURE text," +
                "PICTURE_PATH text,"+
                "COVER text," +
                "COVER_PATH text," +
                "JOIN_MONTH integer," +
                "JOIN_YEAR integer);";

        String create_users_education="create table EDUCATION_USERS(PHONE text," +
                "SCHOOL text," +
                "DEGREE text," +
                "SPECIALISATION text," +
                "PLACE text," +
                "FRM text," +
                "T text," +
                "EXTRA text)";

        String create_users_experience="create table EXPERIENCE_USERS(PHONE text," +
                "ORGANIZATION text," +
                "PLACE text," +
                "FRM text," +
                "T text," +
                "POST text)";

        String create_users_skill="create table SKILL_USERS(PHONE text," +
                "SKILL text)";

        String create_user_rating="create table USER_RATING(PHONE text," +
                "RATE text)";

        String create_all_rating="create table ALL_RATING(PHONE text," +
                "RATE text,PHONE_OTHER text)";

        String create_all_comment="create table ALL_COMMENT(PHONE text," +
                "COMMENT text,PHONE_OTHER text)";

        //String create_team="create table TEAM(PHONE text," +
        //        "TEAM_NAME text,POST text)";

        try
        {
            sqLiteDatabase.execSQL(Create_fbuser);

            sqLiteDatabase.execSQL(user_details);

            sqLiteDatabase.execSQL(user_number);

            sqLiteDatabase.execSQL(create_user);
            sqLiteDatabase.execSQL(create_education);
            sqLiteDatabase.execSQL(create_experience);
            sqLiteDatabase.execSQL(create_skill);

            sqLiteDatabase.execSQL(create_users);
            sqLiteDatabase.execSQL(create_users_education);
            sqLiteDatabase.execSQL(create_users_experience);
            sqLiteDatabase.execSQL(create_users_skill);

            sqLiteDatabase.execSQL(create_user_rating);
            sqLiteDatabase.execSQL(create_all_rating);
            sqLiteDatabase.execSQL(create_all_comment);

          //  sqLiteDatabase.execSQL(create_team);
        }
        catch(SQLException e)
        {

            e.printStackTrace();

        }

        alist=new ArrayList<>();

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
                if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0)
                {
                    Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
                    int count = 0;
                    String phoneNo[] = new String[pCur.getCount()];

                    while (pCur.moveToNext())
                    {

                        phoneNo[count++] = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        sl = sl + "" + pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)) + "\n";
                    }
                    c.setNumber(phoneNo);
                    pCur.close();

                    Cursor emailCur = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,null,
                            ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",new String[]{id}, null);
                    while (emailCur.moveToNext())
                    {
                        // This would allow you get several email addresses
                        // if the email addresses were stored in an array
                        String email = emailCur.getString(
                                emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                        String emailType = emailCur.getString(
                                emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));

                        System.out.println("Email " + email + " Email Type : " + emailType);
                        c.setEmail(email );
                    }
                    emailCur.close();

                }


                //slist.add(sl);
                alist.add(c);
            }
        }
        Log.e("contacts","done getting");
        Log.e("contacts","inserting");
        cur.close();
        getAllPrint(alist);
        insert_details(sqLiteDatabase, alist);
        initialize_Users(sqLiteDatabase);
    }

    public boolean insert_fb_user(SQLiteDatabase db,String Email,String Name,String Gender ,String FB_Profile ,String Picture_URL ,String Birthday ,String FB_ID )
    {
        ContentValues initialValues = new ContentValues();

        initialValues.put("Email", Email);
        initialValues.put("Name", Name);
        initialValues.put("Gender", Gender);
        initialValues.put("FB_Profile", FB_Profile);
        initialValues.put("Picture_URL", Picture_URL);
        initialValues.put("FB_ID", FB_ID);
        initialValues.put("Birthday", Birthday);

        long stat=db.insert("FBUSER", null, initialValues);
        if(stat!=-1)
            return true;
        else
            return false;
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
                v.put("email",""+list.get(count).getName());
            else
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

    public ArrayList<FBUser> fetch_fbuser(SQLiteDatabase db)
    {
        String fetch_query="select * from FBUSER";
        Cursor cursor=db.rawQuery(fetch_query,null);

        cursor.moveToFirst();
        if(cursor.getCount()>0)
        {
            ArrayList<FBUser> al=new ArrayList<>();
            for(int count=0;count<cursor.getCount();count++)
            {
                FBUser u=new FBUser();
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

    public void getAllPrint(ArrayList<Contacts> alist1)
    {
        Log.e("contacts", "trying to print");
        //ArrayList<Contacts> temp = new ArrayList<Contacts>();
        //String select_user = "select * from contact";
        //Cursor c = db.rawQuery(select_user, null);
        //c.moveToFirst();
        if (alist1.size() > 0)
        {
            //temp = new ArrayList<>();
            for (int count = 0; count < alist1.size(); count++)
            {
                Contacts cn = new Contacts();
                Log.e("name "+count,""+alist1.get(count).getName());
                Log.e("name "+count,""+alist1.get(count).getId());
                if(alist1.get(count).getNumber()!=null)
                {
                    if(alist1.get(count).getNumber().length!=0)
                        Log.e("name "+count,""+alist1.get(count).getNumber()[0]);
                    Log.e("name "+count,""+alist1.get(count).getEmail());
                }
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {



    }

    public void insertUser(SQLiteDatabase db,String name,String phone,String email,String username,
                           String password,String category, String work_name,String address,
                           String country,String picture,String picture_path,String cover,String cover_path)
    {
        ContentValues v=new ContentValues();
        v.put("NAME", name);
        v.put("PHONE", ""+phone);
        v.put("EMAIL", email);
        v.put("USERNAME", username);
        v.put("PASSWORD", password);
        v.put("CATEGORY", category);
        v.put("WORK_NAME", work_name);
        v.put("ADDRESS", address);
        v.put("COUNTRY", country);
        v.put("PICTURE", picture);
        v.put("PICTURE_PATH", picture_path);
        v.put("COVER", cover);
        v.put("COVER_PATH", cover_path);
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        v.put("JOIN_MONTH", mMonth);
        v.put("JOIN_YEAR", mYear);
        db.insert("USER",null, v);
        show_all(db);
    }


    public ArrayList<User> getNewBasic(SQLiteDatabase db)
    {

        String query="select * from USERS";
        Cursor c=db.rawQuery(query,null);
        c.moveToFirst();
        ArrayList<User> user_list=new ArrayList<>();
        if(c.getCount()>0)
        {
            final Calendar cal = Calendar.getInstance();
            int mYear = cal.get(Calendar.YEAR);
            int mMonth = cal.get(Calendar.MONTH);
            int join_year=0,join_month=0;

            for(int count=0;count<c.getCount();count++)
            {
                User u=new User();
                Log.e("checking for phone",""+c.getString(0));
                Log.e("checking for name",""+c.getString(1));
                join_year = c.getInt(14);
                join_month = c.getInt(13);
                if (mYear - join_year == 0)
                {
                    if ((12 - join_month) - (12 - mMonth) <= 3)
                    {

                        u.setName(c.getString(0));
                        u.setPhone(c.getString(1));
                        u.setEmail(c.getString(2));
                        u.setUsername(c.getString(3));
                        u.setPassword(c.getString(4));
                        u.setCategory(c.getString(5));
                        u.setWork_name(c.getString(6));
                        u.setAddress(c.getString(7));
                        u.setCountry(c.getString(8));
                        u.setPicture(c.getString(9));
                        u.setPicture_path(c.getString(10));
                        u.setCover_img(c.getString(11));
                        u.setCover_path(c.getString(12));
                        u.setJoin_month(c.getInt(13));
                        u.setJoin_year(c.getInt(14));
                        user_list.add(u);
                        c.moveToNext();
                    }
                    else
                        c.moveToNext();
                }
                else if (mYear - join_year == 1)
                {
                    if ((12 - join_month) - (12 - mMonth) >= 9)
                    {
                        u.setName(c.getString(0));
                        u.setPhone(c.getString(1));
                        u.setEmail(c.getString(2));
                        u.setUsername(c.getString(3));
                        u.setPassword(c.getString(4));
                        u.setCategory(c.getString(5));
                        u.setWork_name(c.getString(6));
                        u.setAddress(c.getString(7));
                        u.setCountry(c.getString(8));
                        u.setPicture(c.getString(9));
                        u.setPicture_path(c.getString(10));
                        u.setCover_img(c.getString(11));
                        u.setCover_path(c.getString(12));
                        u.setJoin_month(c.getInt(13));
                        u.setJoin_year(c.getInt(14));
                        user_list.add(u);
                        c.moveToNext();
                    }
                    else
                        c.moveToNext();
                }
            }



            c.close();
        }
        return user_list;
    }


    public User getBasic(SQLiteDatabase db,User u)
    {
        String query="select * from USER";
        Cursor c=db.rawQuery(query,null);
        c.moveToFirst();
        if(c.getCount()>0)
        {
            for(int count=0;count<c.getCount();count++)
            {
                u.setName(c.getString(0));
                u.setPhone(c.getString(1));
                u.setEmail(c.getString(2));
                u.setUsername(c.getString(3));
                u.setPassword(c.getString(4));
                u.setCategory(c.getString(5));
                u.setWork_name(c.getString(6));
                u.setAddress(c.getString(7));
                u.setCountry(c.getString(8));
                u.setPicture(c.getString(9));
                u.setPicture_path(c.getString(10));
                u.setCover_img(c.getString(11));
                u.setCover_path(c.getString(12));
                u.setJoin_month(c.getInt(13));
                u.setJoin_year(c.getInt(14));
                c.moveToNext();
            }
            c.close();
        }
        return u;
    }

    public ArrayList<User> getAllUsers(SQLiteDatabase db,User u)
    {

        String query="select * from USERS";
        Cursor c=db.rawQuery(query,null);
        c.moveToFirst();
        ArrayList<User> user_list=null;
        if(c.getCount()>0)
        {
            user_list=new ArrayList<User>();
            for(int count=0;count<c.getCount();count++)
            {
                u.setName(c.getString(0));
                u.setPhone(c.getString(1));
                u.setEmail(c.getString(2));
                u.setUsername(c.getString(3));
                u.setPassword(c.getString(4));
                u.setCategory(c.getString(5));
                u.setWork_name(c.getString(6));
                u.setAddress(c.getString(7));
                u.setCountry(c.getString(8));
                u.setPicture(c.getString(9));
                u.setPicture_path(c.getString(10));
                u.setCover_img(c.getString(11));
                u.setCover_path(c.getString(12));
                u.setJoin_month(c.getInt(13));
                u.setJoin_year(c.getInt(14));
                c.moveToNext();
                user_list.add(u);
            }
            c.close();
        }
        return user_list;
    }

    public int getAllUsersCount(SQLiteDatabase db)
    {
        int user_count=0;
        String query="select * from USERS";
        Cursor c=db.rawQuery(query,null);
        c.moveToFirst();
        if(c.getCount()>0)
        {
            for(int count=0;count<c.getCount();count++)
            {
                user_count++;
                c.moveToNext();
            }
            c.close();
        }
        return user_count;
    }

    public void show_all_users(SQLiteDatabase db)
    {
        String query="select * from USERS";


        Cursor c=db.rawQuery(query,null);
        c.moveToFirst();
        if(c.getCount()>0)
        {
            for(int count=0;count<c.getCount();count++)
            {
                Log.e("columns=>",""+c.getColumnCount());

                Log.e("NAME",""+c.getString(0)+","+c.getString(1));
                Log.e("PHONE","->"+c.getString(1));
                Log.e("EMAIL",""+c.getString(2));
                Log.e("USERNAME",""+c.getString(3));
                Log.e("PASSWORD", ""+c.getString(4));
                Log.e("CATEGORY", ""+c.getString(5));
                Log.e("WORK_NAME", ""+c.getString(6));
                Log.e("ADDRESS", ""+c.getString(7));
                Log.e("COUNTRY", ""+c.getString(8));
                Log.e("PICTURE", ""+c.getString(9));
                Log.e("PICTURE_PATH", ""+c.getString(10));
                Log.e("COVER", ""+c.getString(11));
                Log.e("COVER_PATH", ""+c.getString(12));
                Log.e("JOIN_MONTH", ""+c.getString(13));
                Log.e("JOIN_YEAR", ""+c.getString(14));
                Log.e("......","......");
                c.moveToNext();
            }
            c.close();
        }
    }


    public ArrayList<User> search_users_name(SQLiteDatabase db,String name)
    {
        String query="select * from USERS";
        Cursor c=db.rawQuery(query,null);
        c.moveToFirst();
        ArrayList<User> alist=new ArrayList<User>();
        User u=new User();
        if(c.getCount()>0)
        {
            c.moveToFirst();
            for(int count=0;count<c.getCount();count++)
            {
                Log.e("columns=>",""+c.getColumnCount());

                String str_name=c.getString(0);
                Log.e("str_name"+str_name,"name:"+name);
                boolean res=str_name.toLowerCase().contains(name.toLowerCase());
                Log.e("search result",""+res);
                if(res)
                {
                    //Log.e("NAME",""+c.getString(0)+","+c.getString(1));
                    u.setName(c.getString(0));
                    u.setPhone(c.getString(1));
                    u.setEmail(c.getString(2));
                    u.setUsername(c.getString(3));
                    u.setPassword(c.getString(4));
                    u.setCategory(c.getString(5));
                    u.setWork_name(c.getString(6));
                    u.setAddress(c.getString(7));
                    u.setCountry(c.getString(8));
                    u.setPicture(c.getString(9));
                    u.setPicture_path(c.getString(10));
                    u.setCover_img(c.getString(11));
                    u.setCover_path(c.getString(12));
                    u.setJoin_month(c.getInt(13));
                    u.setJoin_year(c.getInt(14));
                    alist.add(u);
                }
                c.moveToNext();
            }

            c.close();
            //Log.e("name returning",""+alist.get(0).getName());
        }
        return alist;
    }

    public User search_users_phone(SQLiteDatabase db,String phone)
    {
        String query="select * from USERS";
        Cursor c=db.rawQuery(query,null);
        c.moveToFirst();
        User u=new User();
        if(c.getCount()>0)
        {
            c.moveToFirst();
            for(int count=0;count<c.getCount();count++)
            {
                //Log.e("columns=>",""+c.getColumnCount());

                String str_phone=c.getString(1);
                //Log.e("str_name"+str_name,"name:"+name);
                int res=str_phone.compareToIgnoreCase(phone);
                //Log.e("result of comapare",""+res);
                if(res>=0)
                {
                    //Log.e("NAME",""+c.getString(0)+","+c.getString(1));
                    u.setName(c.getString(0));
                    u.setPhone(c.getString(1));
                    u.setEmail(c.getString(2));
                    u.setUsername(c.getString(3));
                    u.setPassword(c.getString(4));
                    u.setCategory(c.getString(5));
                    u.setWork_name(c.getString(6));
                    u.setAddress(c.getString(7));
                    u.setCountry(c.getString(8));
                    u.setPicture(c.getString(9));
                    u.setPicture_path(c.getString(10));
                    u.setCover_img(c.getString(11));
                    u.setCover_path(c.getString(12));
                    u.setJoin_month(c.getInt(13));
                    u.setJoin_year(c.getInt(14));
                }
                c.moveToNext();
            }

            c.close();
            //Log.e("name returning",""+alist.get(0).getName());
        }
        return u;
    }


    public void updateUser(SQLiteDatabase db,String id,String name,String phone,String email,String username,
                           String password,String category, String work_name,String address,
                           String country,String picture,String picture_path)
    {
        String str="update USER set NAME='"+name+"', PHONE='"+phone+"', EMAIL='"+email+"', USERNAME='"+username+"', " +
                "PASSWORD='"+password+"', CATEGORY='"+category+"', WORK_NAME='"+work_name+"', ADDRESS='"+address+"'," +
                "COUNTRY='"+country+"', PICTURE='"+picture+"', PICTURE_PATH='"+picture_path+"' " +
                "where PHONE='"+id+"'; ";

        Cursor c = db.rawQuery(str,null);
        c.close();
    }

    public void show_all(SQLiteDatabase db)
    {
        String query="select * from USER";
        Cursor c=db.rawQuery(query,null);
        c.moveToFirst();
        if(c.getCount()>0)
        {
            for(int count=0;count<c.getCount();count++)
            {
                Log.e("columns=>",""+c.getColumnCount());

                Log.e("NAME",""+c.getString(0)+","+c.getString(1));
                Log.e("PHONE","->"+c.getString(1));
                Log.e("EMAIL",""+c.getString(2));
                Log.e("USERNAME",""+c.getString(3));
                Log.e("PASSWORD", ""+c.getString(4));
                Log.e("CATEGORY", ""+c.getString(5));
                Log.e("WORK_NAME", ""+c.getString(6));
                Log.e("ADDRESS", ""+c.getString(7));
                Log.e("COUNTRY", ""+c.getString(8));
                Log.e("PICTURE", ""+c.getString(9));
                Log.e("PICTURE_PATH", ""+c.getString(10));
                Log.e("COVER", ""+c.getString(11));
                Log.e("COVER_PATH", ""+c.getString(12));
                Log.e("JOIN_MONTH",""+c.getInt(13));
                Log.e("JOIN_YEAR",""+c.getInt(14));
                c.moveToNext();
            }
            c.close();
        }
    }

    public String get_id(SQLiteDatabase db)
    {

        Log.e("Printing id","id==>");
        String query="select * from USER";
        Cursor c=db.rawQuery(query,null);
        c.moveToFirst();
        if(c.getCount()>0)
        {
            String str=c.getString(1);
            c.moveToNext();
            c.close();
            return str;
        }
        return null;

    }

    public void insertEducation(SQLiteDatabase db,String PHONE,String SCHOOL,String DEGREE,String SPECIALISATION,
                           String PLACE,String FROM,String TO,String EXTRA)
    {
        ContentValues v=new ContentValues();
        v.put("PHONE", PHONE);
        v.put("SCHOOL", SCHOOL);
        v.put("DEGREE", DEGREE);
        v.put("SPECIALISATION", SPECIALISATION);
        v.put("PLACE", PLACE);
        v.put("FRM", FROM);
        v.put("T", TO);
        v.put("EXTRA", EXTRA);
        db.insert("EDUCATION",null, v);
    }

    public User getEdu(SQLiteDatabase db,User u)
    {
        String id=get_id(db);

        String query="select * from EDUCATION where PHONE='"+id+"'";
        Cursor c=db.rawQuery(query,null);
        c.moveToFirst();
        if(c.getCount()>0)
        {
            for(int count=0;count<c.getCount();count++)
            {
                u.addEdu_school(c.getString(1));
                u.addEdu_degree(c.getString(2));
                u.addEdu_specialzation(c.getString(3));
                u.addEdu_location(c.getString(4));
                u.addEdu_from(c.getString(5));
                u.addEdu_to(c.getString(6));
                u.addEdu_extra(c.getString(7));

                c.moveToNext();
            }
            c.close();
        }
        return u;
    }

    public ArrayList<User> getNewEdu(SQLiteDatabase db,ArrayList<User> al)
    {
        for(int count1=0;count1<al.size();count1++)
        {
            String id=al.get(count1).getPhone();

            String query="select * from EDUCATION where PHONE='"+id+"'";
            Cursor c=db.rawQuery(query,null);
            c.moveToFirst();
            if(c.getCount()>0)
            {
                for(int count=0;count<c.getCount();count++)
                {
                    al.get(count1).addEdu_school(c.getString(1));
                    al.get(count1).addEdu_degree(c.getString(2));
                    al.get(count1).addEdu_specialzation(c.getString(3));
                    al.get(count1).addEdu_location(c.getString(4));
                    al.get(count1).addEdu_from(c.getString(5));
                    al.get(count1).addEdu_to(c.getString(6));
                    al.get(count1).addEdu_extra(c.getString(7));

                    c.moveToNext();
                }
                c.close();
            }

        }
        return al;
    }


    public void insertExp(SQLiteDatabase db,String PHONE,String ORGANIZATION,String PLACE,String FROM,
                           String TO,String POST)
    {
        ContentValues v=new ContentValues();
        v.put("PHONE", PHONE);
        v.put("ORGANIZATION", ORGANIZATION);
        v.put("PLACE", PLACE);
        v.put("FRM", FROM);
        v.put("T", TO);
        v.put("POST", POST);
        db.insert("EXPERIENCE",null, v);
    }

    public User getExp(SQLiteDatabase db,User u)
    {
        String id=get_id(db);

        String query="select * from EXPERIENCE where PHONE='"+id+"'";
        Cursor c=db.rawQuery(query,null);
        c.moveToFirst();
        if(c.getCount()>0)
        {
            for(int count=0;count<c.getCount();count++)
            {
                u.addExp_company(c.getString(1));
                u.addExp_location(c.getString(2));
                u.addExp_from(c.getString(3));
                u.addExp_to(c.getString(4));
                u.addExp_post(c.getString(5));

                c.moveToNext();
            }
            c.close();
        }
        return u;
    }


    public ArrayList<User> getNewExp(SQLiteDatabase db,ArrayList<User> al)
    {
        for(int count1=0;count1<al.size();count1++)
        {
            String id=al.get(count1).getPhone();

            String query="select * from EXPERIENCE where PHONE='"+id+"'";
            Cursor c=db.rawQuery(query,null);
            c.moveToFirst();
            if(c.getCount()>0)
            {
                for(int count=0;count<c.getCount();count++)
                {
                    al.get(count1).addExp_company(c.getString(1));
                    al.get(count1).addExp_location(c.getString(2));
                    al.get(count1).addExp_from(c.getString(3));
                    al.get(count1).addExp_to(c.getString(4));
                    al.get(count1).addExp_post(c.getString(5));

                    c.moveToNext();
                }
                c.close();
            }

        }
        return al;
    }


    public void insertSkill(SQLiteDatabase db,String PHONE,String SKILL)
    {
        //"PHONE text" +
        //"SKILL
        ContentValues v=new ContentValues();
        v.put("PHONE", PHONE);
        v.put("SKILL", SKILL);
        db.insert("SKILL",null, v);
    }

    public User getskills(SQLiteDatabase db,User u)
    {
        String id=get_id(db);

        String query="select * from SKILL where PHONE='"+id+"'";
        Cursor c=db.rawQuery(query,null);
        c.moveToFirst();
        if(c.getCount()>0)
        {
            for(int count=0;count<c.getCount();count++)
            {
                u.addskills(c.getString(1));
                c.moveToNext();
            }
            c.close();
        }
        return u;
    }

    public ArrayList<User> getNewskills(SQLiteDatabase db,ArrayList<User> al)
    {
        for(int count1=0;count1<al.size();count1++)
        {
            String id=al.get(count1).getPhone();

            String query="select * from SKILL where PHONE='"+id+"'";
            Cursor c=db.rawQuery(query,null);
            c.moveToFirst();
            if(c.getCount()>0)
            {
                for(int count=0;count<c.getCount();count++)
                {
                    al.get(count1).addskills(c.getString(1));
                    c.moveToNext();
                }
                c.close();
            }
        }

        return al;
    }

    public User getAll(SQLiteDatabase db,User u)
    {
        u=getBasic(db,u);
        u=getEdu(db,u);
        u=getExp(db,u);
        u=getskills(db,u);
        return u;
    }

    public ArrayList<User> getAllNewest(SQLiteDatabase db)
    {
        ArrayList<User> user_list;//=new ArrayList<>();
        user_list=getNewBasic(db);
        user_list=getNewEdu(db,user_list);
        user_list=getNewExp(db,user_list);
        user_list=getNewskills(db,user_list);
        return user_list;
    }

    public ArrayList<Contacts> getAllContacts(SQLiteDatabase db)
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

    public void initialize_Users(SQLiteDatabase db)
    {
        ContentValues v=new ContentValues();
        v.put("NAME", "SatyaNarayan Verma");
        v.put("PHONE", "9990099900");
        v.put("EMAIL", "satyanarayanverma@gmail.com");
        v.put("USERNAME", "prosatya");
        v.put("PASSWORD", "abc1234567890");
        v.put("CATEGORY", "Business");
        v.put("WORK_NAME", "Satya");
        v.put("ADDRESS", "112, shree vardhan complex, RNT marg, Indore");
        v.put("COUNTRY", "India");
        v.put("PICTURE", "");
        v.put("PICTURE_PATH", "");
        v.put("COVER", "");
        v.put("COVER_PATH", "");
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        v.put("JOIN_MONTH", mMonth);
        v.put("JOIN_YEAR", mYear);
        db.insert("USERS",null, v);
        show_all_users(db);
    }

    public void insertUsers(SQLiteDatabase db,String name,String phone,String email,String username,
                            String password,String category, String work_name,String address,
                            String country,String picture,String picture_path,String cover,String cover_path)
    {
        ContentValues v=new ContentValues();
        v.put("NAME", name);
        v.put("PHONE", ""+phone);
        v.put("EMAIL", email);
        v.put("USERNAME", username);
        v.put("PASSWORD", password);
        v.put("CATEGORY", category);
        v.put("WORK_NAME", work_name);
        v.put("ADDRESS", address);
        v.put("COUNTRY", country);
        v.put("PICTURE", picture);
        v.put("PICTURE_PATH", picture_path);
        v.put("COVER", cover);
        v.put("COVER_PATH", cover_path);
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        v.put("JOIN_MONTH", mMonth);
        v.put("JOIN_YEAR", mYear);
        db.insert("USERS",null, v);
        show_all_users(db);
    }

    public User getUsersBasic(SQLiteDatabase db,User u,String phone)
    {
        String query="select * from USERS where PHONE='"+phone+"'";
        Log.e("basic phone=>",""+phone);
        Cursor c=db.rawQuery(query,null);
        c.moveToFirst();
        if(c.getCount()>0)
        {
            for(int count=0;count<c.getCount();count++)
            {
                u.setName(c.getString(0));
                Log.e("name=>",""+c.getString(0));
                u.setPhone(c.getString(1));
                Log.e("phone=>",""+c.getString(1));
                u.setEmail(c.getString(2));
                Log.e("Email=>",""+c.getString(2));
                u.setUsername(c.getString(3));
                Log.e("Username=>",""+c.getString(3));
                u.setPassword(c.getString(4));
                Log.e("Password=>",""+c.getString(4));
                u.setCategory(c.getString(5));
                Log.e("category=>",""+c.getString(5));
                u.setWork_name(c.getString(6));
                Log.e("work name=>",""+c.getString(6));
                u.setAddress(c.getString(7));
                Log.e("Address=>",""+c.getString(7));
                u.setCountry(c.getString(8));
                Log.e("Country=>",""+c.getString(8));
                u.setPicture(c.getString(9));
                Log.e("Picture=>",""+c.getString(9));
                u.setPicture_path(c.getString(10));
                Log.e("picture path=>",""+c.getString(10));
                u.setCover_img(c.getString(11));
                Log.e("Cover=>",""+c.getString(11));
                u.setCover_path(c.getString(12));
                Log.e("Cover path=>",""+c.getString(12));
                u.setJoin_month(c.getInt(13));
                Log.e("Join_month",""+c.getInt(13));
                u.setJoin_year(c.getInt(14));
                Log.e("Join_year",""+c.getInt(14));
                c.moveToNext();
            }
            c.close();
        }
        return u;
    }

    public ArrayList<String> getAllUserPhone(SQLiteDatabase db)
    {
        ArrayList<String> phone_list=new ArrayList<>();
        String query="select * from USERS;";
        Cursor c=db.rawQuery(query,null);
        c.moveToFirst();
        if(c.getCount()>0)
        {
            for(int count=0;count<c.getCount();count++)
            {
                phone_list.add(c.getString(1));
                c.moveToNext();
            }
            c.close();
        }
        return phone_list;
    }


    public void insertUsersEducation(SQLiteDatabase db,String PHONE,String SCHOOL,String DEGREE,String SPECIALISATION,
                                String PLACE,String FROM,String TO,String EXTRA)
    {
        ContentValues v=new ContentValues();
        v.put("PHONE", PHONE);
        v.put("SCHOOL", SCHOOL);
        v.put("DEGREE", DEGREE);
        v.put("SPECIALISATION", SPECIALISATION);
        v.put("PLACE", PLACE);
        v.put("FRM", FROM);
        v.put("T", TO);
        v.put("EXTRA", EXTRA);
        db.insert("EDUCATION_USERS",null, v);
    }

    public User getUsersEdu(SQLiteDatabase db,User u,String phone)
    {
        String query="select * from EDUCATION_USERS where PHONE='"+phone+"'";
        Log.e("Edu phone=>",""+phone);
        Cursor c=db.rawQuery(query,null);
        c.moveToFirst();
        if(c.getCount()>0)
        {
            for(int count=0;count<c.getCount();count++)
            {
                u.addEdu_school(c.getString(1));
                Log.e("school=>",""+c.getString(1));
                u.addEdu_degree(c.getString(2));
                Log.e("degree=>",""+c.getString(2));
                u.addEdu_specialzation(c.getString(3));
                Log.e("specialization=>",""+c.getString(3));
                u.addEdu_location(c.getString(4));
                Log.e("Location=>",""+c.getString(4));
                u.addEdu_from(c.getString(5));
                Log.e("From=>",""+c.getString(5));
                u.addEdu_to(c.getString(6));
                Log.e("To=>",""+c.getString(6));
                u.addEdu_extra(c.getString(7));
                Log.e("Extra=>",""+c.getString(7));

                c.moveToNext();
            }
            c.close();
        }
        return u;
    }


    public void insertUsersExp(SQLiteDatabase db,String PHONE,String ORGANIZATION,String PLACE,String FROM,
                          String TO,String POST)
    {
        ContentValues v=new ContentValues();
        v.put("PHONE", PHONE);
        v.put("ORGANIZATION", ORGANIZATION);
        v.put("PLACE", PLACE);
        v.put("FRM", FROM);
        v.put("T", TO);
        v.put("POST", POST);
        db.insert("EXPERIENCE_USERS",null, v);
    }

    public User getUsersExp(SQLiteDatabase db,User u,String phone)
    {
        String query="select * from EXPERIENCE_USERS where PHONE='"+phone+"'";
        Log.e("exp phone=>",""+phone);
        Cursor c=db.rawQuery(query,null);
        c.moveToFirst();
        if(c.getCount()>0)
        {
            for(int count=0;count<c.getCount();count++)
            {
                u.addExp_company(c.getString(1));
                Log.e("company=>",""+c.getString(1));
                u.addExp_location(c.getString(2));
                Log.e("location=>",""+c.getString(2));
                u.addExp_from(c.getString(3));
                Log.e("from=>",""+c.getString(3));
                u.addExp_to(c.getString(4));
                Log.e("to=>",""+c.getString(4));
                u.addExp_post(c.getString(5));
                Log.e("post=>",""+c.getString(5));

                c.moveToNext();
            }
            c.close();
        }
        return u;
    }

    public void insertUsersSkill(SQLiteDatabase db,String PHONE,String SKILL)
    {
        //"PHONE text" +
        //"SKILL
        ContentValues v=new ContentValues();
        v.put("PHONE", PHONE);
        v.put("SKILL", SKILL);
        db.insert("SKILL_USERS",null, v);
    }

    public User getUsersskills(SQLiteDatabase db,User u,String phone)
    {
        String query="select * from SKILL_USERS where PHONE='"+phone+"'";
        Log.e("basic phone=>",""+phone);
        Cursor c=db.rawQuery(query,null);
        c.moveToFirst();
        if(c.getCount()>0)
        {
            for(int count=0;count<c.getCount();count++)
            {
                u.addskills(c.getString(1));
                Log.e("skill=>",""+c.getString(1));
                c.moveToNext();
            }
            c.close();
        }
        return u;
    }

    public User getUsersAll(SQLiteDatabase db,String phone)
    {
        User u=new User();
        Log.e("getting all wid phone=>",""+phone);
        u=getUsersBasic(db,u,phone);
        u=getUsersEdu(db,u,phone);
        u=getUsersExp(db,u,phone);
        u=getUsersskills(db,u,phone);
        return u;
    }

    public void show_all_Users(SQLiteDatabase db)
    {
        String query="select * from USERS";
        String phone_number="";
        Cursor c=db.rawQuery(query,null);
        c.moveToFirst();
        if(c.getCount()>0)
        {
            for(int count=0;count<c.getCount();count++)
            {
                Log.e("columns=>",""+c.getColumnCount());

                Log.e("NAME",""+c.getString(0)+","+c.getString(1));
                Log.e("PHONE","->"+c.getString(1));
                phone_number=c.getString(1);
                Log.e("EMAIL",""+c.getString(2));
                Log.e("USERNAME",""+c.getString(3));
                Log.e("PASSWORD", ""+c.getString(4));
                Log.e("CATEGORY", ""+c.getString(5));
                Log.e("WORK_NAME", ""+c.getString(6));
                Log.e("ADDRESS", ""+c.getString(7));
                Log.e("COUNTRY", ""+c.getString(8));
                Log.e("PICTURE", ""+c.getString(9));
                Log.e("PICTURE_PATH", ""+c.getString(10));
                c.moveToNext();
            }
            c.close();
        }


    }

    public void showUsersskills(SQLiteDatabase db)
    {

        String query="select * from SKILL_USERS";
        Cursor c=db.rawQuery(query,null);
        c.moveToFirst();
        if(c.getCount()>0)
        {
            for(int count=0;count<c.getCount();count++)
            {
                Log.e("phone "+count,""+c.getString(0));
                Log.e("skill "+count,""+c.getString(1));
                c.moveToNext();
            }
            c.close();
        }
    }

    public void showUsersExp(SQLiteDatabase db)
    {

        String query="select * from EXPERIENCE_USERS";
        Cursor c=db.rawQuery(query,null);
        Log.e("Showing experience:","......count=>"+c.getColumnCount()+",row=>"+c.getCount());

        c.moveToFirst();
        if(c.getCount()>0)
        {
            for(int count=0;count<c.getCount();count++)
            {
                Log.e("phone "+count,""+c.getString(0));
                Log.e("Company "+count,""+c.getString(1));
                Log.e("location"+count,""+c.getString(2));
                Log.e("from"+count,""+c.getString(3));
                Log.e("to "+count,""+c.getString(4));
                Log.e("post"+count,""+c.getString(5));

                c.moveToNext();
            }
            c.close();
        }
    }


    public void showUsersEdu(SQLiteDatabase db)
    {
        String id=get_id(db);

        String query="select * from EDUCATION_USERS";
        Cursor c=db.rawQuery(query,null);
        c.moveToFirst();
        if(c.getCount()>0)
        {
            for(int count=0;count<c.getCount();count++)
            {
                Log.e("phone "+count,""+c.getString(0));
                Log.e("school "+count,""+c.getString(1));
                Log.e("degree"+count,""+c.getString(2));
                Log.e("specialization "+count,""+c.getString(3));
                Log.e("location "+count,""+c.getString(4));
                Log.e("from "+count,""+c.getString(5));
                Log.e("to "+count,""+c.getString(6));
                Log.e("extra "+count,""+c.getString(7));

                c.moveToNext();
            }
            c.close();
        }
    }

    public boolean insertRating(SQLiteDatabase db,String phone,String phone_other,String rate)
    {
        String check_query="select * from ALL_RATING where PHONE_OTHER='"+phone_other+"' and PHONE='"+phone+"';";

        Cursor check_cursor=db.rawQuery(check_query,null);
        check_cursor.moveToFirst();
        Log.e("all rating","before update");
        for(int count=0;count<check_cursor.getCount();count++)
        {
            Log.e("phone",""+check_cursor.getString(0));
            Log.e("Rate",""+check_cursor.getString(1));
            Log.e("phone_other",""+check_cursor.getString(2));
            check_cursor.moveToNext();
        }
        check_cursor.moveToFirst();
        if(check_cursor.getCount()>0)
        {
            check_cursor.close();

            //db.rawQuery("UPDATE ALL_RATING SET RATE= '"+rate+"' WHERE PHONE = '"+phone+"' AND PHONE_OTHER='"+phone_other+"';", null);

            ContentValues contentValues=new ContentValues();
            contentValues.put("RATE",rate);

            db.update("ALL_RATING",contentValues,"PHONE = ? AND PHONE_OTHER = ?",new String[]{phone, phone_other});

            String check_query1="select * from ALL_RATING where PHONE_OTHER='"+phone_other+"' and PHONE='"+phone+"';";

            Cursor check_cursor1=db.rawQuery(check_query1,null);
            check_cursor1.moveToFirst();
            Log.e("all rating","after update");
            for(int count=0;count<check_cursor1.getCount();count++)
            {
                Log.e("phone",""+check_cursor1.getString(0));
                Log.e("Rate",""+check_cursor1.getString(1));
                Log.e("phone_other",""+check_cursor1.getString(2));
                check_cursor1.moveToNext();
            }
            check_cursor1.close();

            String rate_calc_query="select * from ALL_RATING where PHONE='"+phone+"';";

            Cursor rate_calc_cursor=db.rawQuery(rate_calc_query,null);
            rate_calc_cursor.moveToFirst();
            int total_rating=0,total_no_ratings=0,overall_rating=0;
            if(rate_calc_cursor.getCount()>0)
            {
                total_no_ratings=rate_calc_cursor.getCount();

                for(int count=0;count<rate_calc_cursor.getCount();count++)
                {
                    int t=Integer.parseInt(rate_calc_cursor.getString(1));
                    //Log.e("adding",""+t);
                    total_rating=total_rating+t;
                    rate_calc_cursor.moveToNext();
                }
                //Log.e("after total of ratings",""+total_rating);
                overall_rating=total_rating/total_no_ratings;

            }
            rate_calc_cursor.close();

            String overall_rate_query="select * from USER_RATING where PHONE='"+phone+"'";
            Cursor overall_rate_cursor=db.rawQuery(overall_rate_query,null);
            overall_rate_cursor.moveToFirst();

            overall_rate_cursor.close();
            ContentValues overall_rate_update_value= new ContentValues();
            overall_rate_update_value.put("RATE",""+overall_rating);
            db.update("USER_RATING", overall_rate_update_value,"PHONE = ? ",new String[]{ String.valueOf(phone) });
            overall_rate_cursor.close();

            //db.update("USER_RATING", rating_update,"PHONE = ? ",new String[]{String.valueOf(phone)});
            return false;
        }
        else
        {
            check_cursor.close();
            ContentValues add_rate_value=new ContentValues();
            add_rate_value.put("PHONE", phone);
            add_rate_value.put("RATE", rate);
            add_rate_value.put("PHONE_OTHER", phone_other);
            db.insert("ALL_RATING",null, add_rate_value);

            String rate_calc_query="select * from ALL_RATING where PHONE='"+phone+"';";

            Cursor rate_calc_cursor=db.rawQuery(rate_calc_query,null);
            rate_calc_cursor.moveToFirst();
            int total_rating=0,total_no_ratings=0,overall_rating=0;
            if(rate_calc_cursor.getCount()>0)
            {
            /*for(int count=0;count<c.getCount();count++)
            {
                Log.e("phone",""+c.getString(0));
                Log.e("Rate",""+c.getString(1));
                Log.e("phone_other",""+c.getString(2));
            }*/
                total_no_ratings=rate_calc_cursor.getCount();
                //Log.e("before total of ratings",""+total_rating);
                for(int count=0;count<rate_calc_cursor.getCount();count++)
                {
                    int t=Integer.parseInt(rate_calc_cursor.getString(1));
                    //Log.e("adding",""+t);
                    total_rating=total_rating+t;
                    rate_calc_cursor.moveToNext();
                }
                //Log.e("after total of ratings",""+total_rating);
                overall_rating=total_rating/total_no_ratings;
                //Log.e("total of ratings",""+total_rating+"...total number of ratings=>"+total_no_ratings);
                //Log.e("overall ratings",""+overall_rating+"...");
            }
            rate_calc_cursor.close();

            String overall_rate_query="select * from USER_RATING where PHONE='"+phone+"'";
            Cursor overall_rate_cursor=db.rawQuery(overall_rate_query,null);
            overall_rate_cursor.moveToFirst();
            if(overall_rate_cursor.getCount()<=0)
            {
                overall_rate_cursor.close();
                ContentValues overall_rate_insert_value=new ContentValues();
                overall_rate_insert_value.put("PHONE", phone);
                overall_rate_insert_value.put("RATE", rate);
                db.insert("USER_RATING",null, overall_rate_insert_value);
            }
            else
            {
                overall_rate_cursor.close();
                ContentValues overall_rate_update_value= new ContentValues();
                overall_rate_update_value.put("RATE",""+overall_rating);
                db.update("USER_RATING", overall_rate_update_value,"PHONE = ? ",new String[]{ String.valueOf(phone) });
            }
            overall_rate_cursor.close();

            return true;
        }
    }

    public int getRating(SQLiteDatabase db,String phone_other)
    {

        String query="select * from USER_RATING where PHONE='"+phone_other+"';";

        Cursor c1=db.rawQuery(query,null);
        c1.moveToFirst();
        int current_rate=0;
        //Log.e("count of ratings",""+c1.getCount()+"..");
        if(c1.getCount()>0)
        {
            /*for(int count=0;count<c1.getCount();count++)
            {
                Log.e("phone",""+c1.getString(0));
                Log.e("Rate",""+c1.getString(1));
                c1.moveToNext();
            }*/
            String t=c1.getString(1);
            Log.e("rating from DB",""+c1.getString(1)+"..");
            current_rate=Integer.parseInt(t);
            Log.e("current rating of"+phone_other,""+current_rate+"..");

            c1.close();
        }


        return current_rate;

    }

    public int getRatingCount(SQLiteDatabase db,String phone_other)
    {
        int rate_count=0;
        String query="";

        if(phone_other.equals("all"))
        {
            query="select * from ALL_RATING;";
        }
        else
        {
            query="select * from ALL_RATING where PHONE_OTHER='"+phone_other+"';";
        }

        Cursor c1=db.rawQuery(query,null);
        c1.moveToFirst();
        //Log.e("count of ratings",""+c1.getCount()+"..");

        if(c1.getCount()>0)
        {
            for(int count=0;count<c1.getCount();count++)
            {
                rate_count++;
                c1.moveToNext();
            }
            c1.close();
        }


        return rate_count;
    }

    public int getRatedCount(SQLiteDatabase db,String phone)
    {

        String query="select * from ALL_RATING where PHONE='"+phone+"';";

        Cursor c1=db.rawQuery(query,null);
        c1.moveToFirst();
        int current_rate=0;
        //Log.e("count of ratings",""+c1.getCount()+"..");
        int rate_count=0;
        if(c1.getCount()>0)
        {
            for(int count=0;count<c1.getCount();count++)
            {
                rate_count++;
                c1.moveToNext();
            }
            c1.close();
        }
        return rate_count;
    }


    public ArrayList<User> getPopularList(SQLiteDatabase db)
    {

        ArrayList<String> phoneList=getAllUserPhone(db);

        ArrayList<User> user_list=new ArrayList<>();

        for(int count=0;count<phoneList.size();count++)
        {
            int comment_count=getCommentCount(db,phoneList.get(count));
            int rate_count=getRatedCount(db,phoneList.get(count));
            Log.e("rate,comment",""+comment_count+".."+rate_count+"!"+phoneList.get(count));
            if(comment_count>0&&rate_count>0)
            {
                user_list.add(getUsersAll(db,phoneList.get(count)));
                Log.e("user number",""+getUsersAll(db,phoneList.get(count)).getPhone());
            }
        }

        return user_list;
    }




    public boolean insertComment(SQLiteDatabase db,String phone,String phone_other,String comment)
    {
        String check_query="select * from ALL_COMMENT where PHONE_OTHER='"+phone_other+"' and PHONE='"+phone+"';";

        Cursor check_cursor=db.rawQuery(check_query,null);
        check_cursor.moveToFirst();

        if(check_cursor.getCount()>0)
        {
            check_cursor.close();
            return false;
        }
        else
        {
            check_cursor.close();
            ContentValues add_rate_value=new ContentValues();
            add_rate_value.put("PHONE", phone);
            add_rate_value.put("COMMENT", comment);
            add_rate_value.put("PHONE_OTHER", phone_other);
            db.insert("ALL_COMMENT",null, add_rate_value);
            return true;
        }
    }

    public ArrayList<String> getComments(SQLiteDatabase db,String phone_other)
    {
        ArrayList<String> comment_list=new ArrayList<String>();
        String query="select * from ALL_COMMENT where PHONE='"+phone_other+"';";

        Cursor c1=db.rawQuery(query,null);
        c1.moveToFirst();
        int current_rate=0;
        //Log.e("count of ratings",""+c1.getCount()+"..");
        if(c1.getCount()>0)
        {
            /*for(int count=0;count<c1.getCount();count++)
            {
                Log.e("phone",""+c1.getString(0));
                Log.e("Rate",""+c1.getString(1));
                c1.moveToNext();
            }*/
            String t=c1.getString(1);
            comment_list.add(t);
            Log.e("Comment from DB",""+c1.getString(1)+"..");

            c1.close();
        }
        return comment_list;
    }



    public int getCommentedCount(SQLiteDatabase db,String phone_other)
    {
        ArrayList<String> comment_list=new ArrayList<String>();
        String query="select * from ALL_COMMENT where PHONE_OTHER='"+phone_other+"';";

        Cursor c1=db.rawQuery(query,null);
        c1.moveToFirst();
        int comment_count=0;
        //Log.e("count of ratings",""+c1.getCount()+"..");
        if(c1.getCount()>0)
        {
            for(int count=0;count<c1.getCount();count++)
            {
                //Log.e("phone",""+c1.getString(0));
                //Log.e("Rate",""+c1.getString(1));
                comment_count++;
                c1.moveToNext();
            }
            /*String t=c1.getString(1);
            comment_list.add(t);
            Log.e("Comment from DB",""+c1.getString(1)+"..");*/


            c1.close();
        }
        return comment_count;
    }


    public int getCommentCount(SQLiteDatabase db,String phone)
    {
        //ArrayList<String> comment_list=new ArrayList<String>();

        int comment_count=0;

        if(phone.equals("all"))
        {
            String query="select * from ALL_COMMENT;";

            Cursor c1=db.rawQuery(query,null);
            c1.moveToFirst();

            //Log.e("count of ratings",""+c1.getCount()+"..");
            if(c1.getCount()>0)
            {
                for(int count=0;count<c1.getCount();count++)
                {
                    //Log.e("phone",""+c1.getString(0));
                    //Log.e("Rate",""+c1.getString(1));
                    comment_count++;
                    c1.moveToNext();
                }
            /*String t=c1.getString(1);
            comment_list.add(t);
            Log.e("Comment from DB",""+c1.getString(1)+"..");*/
                //count++;

                c1.close();
            }
        }
        else
        {
            String query="select * from ALL_COMMENT where PHONE='"+phone+"';";

            Cursor c1=db.rawQuery(query,null);
            c1.moveToFirst();

            //Log.e("count of ratings",""+c1.getCount()+"..");
            if(c1.getCount()>0)
            {
                for(int count=0;count<c1.getCount();count++)
                {
                    //Log.e("phone",""+c1.getString(0));
                    //Log.e("Rate",""+c1.getString(1));
                    comment_count++;
                    c1.moveToNext();
                }
            /*String t=c1.getString(1);
            comment_list.add(t);
            Log.e("Comment from DB",""+c1.getString(1)+"..");*/
                //count++;

                c1.close();
            }
        }

        return comment_count;
    }


}

