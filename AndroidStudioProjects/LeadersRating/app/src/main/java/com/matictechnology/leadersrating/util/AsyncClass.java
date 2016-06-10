package com.matictechnology.leadersrating.util;

import android.os.AsyncTask;
import android.util.Log;

import com.matictechnology.leadersrating.classes.User;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by matic on 8/6/16.
 */
public class AsyncClass extends AsyncTask<String, Void, String>
{
    public FetchDataListener listener;
    String result;
    int flag;

    @Override
    protected String doInBackground(String... strings)
    {

        if(strings[0].equals("all_users"))
        {
            result=all_users();
            flag=1;
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s)
    {
        if(flag==1)
        {
            getAllUsers(s);
        }

    }

    public String all_users()
    {
        result="";
        try
        {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("");

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);


            HttpResponse response = httpclient.execute(httppost);

            InputStream is = response.getEntity().getContent();
            InputStreamReader reader = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(reader);
            while (true)
            {
                String str = br.readLine();
                if (str == null)
                    break;
                result = result + str;
            }
            br.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();

        }
        return result;
    }

    public void getAllUsers(String result)
    {
        ArrayList<User> user=new ArrayList<>();

        Log.e("all user result=>",""+result);
        try
        {
            JSONObject main_obj=new JSONObject(result);
            JSONArray main_arr=main_obj.getJSONArray("user");

            for(int count=0;count<main_arr.length();count++)
            {
                JSONObject user_obj=main_arr.getJSONObject(count);
                JSONObject basic_obj=user_obj.getJSONObject("basic");

                User u=new User();

                u.setName(basic_obj.getString("name"));
                u.setPhone(basic_obj.getString("phone"));
                u.setEmail(basic_obj.getString("email"));
                u.setUsername(basic_obj.getString("username"));
                u.setCategory(basic_obj.getString("category"));
                u.setWork_name(basic_obj.getString("work_name"));
                u.setAddress(basic_obj.getString("address"));
                u.setCountry(basic_obj.getString("country"));
                u.setPicture(basic_obj.getString("picture"));
                u.setPicture_path(basic_obj.getString("picture_path"));
                u.setCover_img(basic_obj.getString("cover"));
                u.setCover_path(basic_obj.getString("cover_path"));

                JSONObject edu_obj=user_obj.getJSONObject("Education");

                for(int count1=0;count1<edu_obj.length();count1++)
                {
                    JSONObject edu_object=new JSONObject(""+(count1+1));
                    ArrayList<String> edu_school=new ArrayList<>();
                    ArrayList<String> edu_degree=new ArrayList<>();
                    ArrayList<String> edu_specialization=new ArrayList<>();
                    ArrayList<String> edu_from=new ArrayList<>();
                    ArrayList<String> edu_to=new ArrayList<>();
                    ArrayList<String> edu_extra=new ArrayList<>();
                    edu_school.add(edu_object.getString("school"));
                    edu_degree.add(edu_object.getString("degree"));
                    edu_specialization.add(edu_object.getString("specilisation"));
                    edu_from.add(edu_object.getString("from"));
                    edu_to.add(edu_object.getString("to"));
                    edu_extra.add(edu_object.getString("extra"));
                }


            }

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }
}
