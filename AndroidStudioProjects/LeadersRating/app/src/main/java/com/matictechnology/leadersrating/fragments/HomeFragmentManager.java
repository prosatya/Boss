package com.matictechnology.leadersrating.fragments;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.matictechnology.leadersrating.R;
import com.matictechnology.leadersrating.activity.ActivityProfile;
import com.matictechnology.leadersrating.classes.User;
import com.matictechnology.leadersrating.util.DBHelper;
import com.matictechnology.leadersrating.util.UserAdapter;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import me.drakeet.materialdialog.MaterialDialog;

/**
 * Created by matic on 3/6/16.
 */
public class HomeFragmentManager extends Fragment
{
    User u;

    DBHelper helper;
    SQLiteDatabase db;

    //fragment class to show fragments as per user selection
    private static final String ARG_POSITION = "position";

    private int position;   //position flag to get the count of current fragment selection

    public static HomeFragmentManager newInstance(int position, User u1)
    {
        HomeFragmentManager f = new HomeFragmentManager();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        b.putSerializable("user",u1);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //getting current fragment count
        position = getArguments().getInt(ARG_POSITION);
        u=(User)getArguments().getSerializable("user");

        helper=new DBHelper(getActivity());
        db=helper.getWritableDatabase();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        //getting layout inflater service
        LayoutInflater inflater1 = (LayoutInflater) getContext().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View itemView1=null;
        if(position==0)
        {
            itemView1=inflater1.inflate(R.layout.fragment_home_newest, null);

            ListView home_newest_list=(ListView)itemView1.findViewById(R.id.home_newest_list);

            final ArrayList<User> newuserlist=helper.getAllNewest(db);

            UserAdapter adapter=new UserAdapter(getActivity(),R.layout.item_user,newuserlist);

            home_newest_list.setAdapter(adapter);
            /*Log.e("printing","newest names=>");
            for(int count=0;count<newuserlist.size();count++)
            {
                Log.e("name",""+newuserlist.get(count).getName());
                Log.e("phone",""+newuserlist.get(count).getPhone());
                Log.e("size",""+newuserlist.size());
            }*/

            home_newest_list.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
                {
                    Intent in=new Intent(getActivity(), ActivityProfile.class);
                    in.putExtra("phone",newuserlist.get(i).getPhone());
                    if(newuserlist.get(i).getPhone().equals(u.getPhone()))
                        in.putExtra("mode","user");
                    else
                        in.putExtra("mode","other");
                    startActivity(in);
                }
            });

        }
        else if(position==1)
        {
            //deciding the Active member by checking rate and comment count per member to be >0
            itemView1=inflater1.inflate(R.layout.fragment_home_active, null);

            ListView home_active_list=(ListView)itemView1.findViewById(R.id.home_active_list);

            ArrayList<String> phoneList=helper.getAllUserPhone(db);
            ArrayList<Integer> comment_count_list=new ArrayList<>();
            ArrayList<String> comment_user=new ArrayList<>();
            for(int count=0;count<phoneList.size();count++)
            {
                int comment_count=helper.getCommentedCount(db,phoneList.get(count));
                int rate_count=helper.getRatingCount(db,phoneList.get(count));
                if(comment_count>0 && rate_count>0)
                {
                    comment_count_list.add(comment_count);
                    comment_user.add(phoneList.get(count));
                }
            }
            //User u=new User();
            final ArrayList<User> user_list=new ArrayList<>();
            for(int count=0;count<comment_count_list.size();count++)
                user_list.add(helper.getUsersAll(db,comment_user.get(count)));

            UserAdapter adapter=new UserAdapter(getActivity(),R.layout.item_user,user_list);

            home_active_list.setAdapter(adapter);

            home_active_list.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
                {
                    Intent in=new Intent(getActivity(), ActivityProfile.class);
                    in.putExtra("phone",user_list.get(i).getPhone());
                    if(user_list.get(i).getPhone().equals(u.getPhone()))
                        in.putExtra("mode","user");
                    else
                        in.putExtra("mode","other");
                    startActivity(in);
                }
            });

        }
        else if(position==2)
        {
            //deciding about popular member by checking personal rating to be 4/5, and non-zero comment count
            itemView1=inflater1.inflate(R.layout.fragment_home_popular, null);

            ListView home_popular_list=(ListView)itemView1.findViewById(R.id.home_popular_list);

            final ArrayList<User> userlist=helper.getPopularList(db);

            UserAdapter adapter=new UserAdapter(getActivity(),R.layout.item_user,userlist);

            home_popular_list.setAdapter(adapter);

            home_popular_list.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
                {
                    Intent in=new Intent(getActivity(), ActivityProfile.class);
                    in.putExtra("phone",userlist.get(i).getPhone());
                    if(userlist.get(i).getPhone().equals(u.getPhone()))
                        in.putExtra("mode","user");
                    else
                        in.putExtra("mode","other");
                    startActivity(in);
                }
            });

        }
        return itemView1;
    }

}
