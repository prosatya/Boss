package com.matictechnology.leadersrating.activity;

import android.app.SearchManager;
import android.content.AsyncQueryHandler;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.matictechnology.leadersrating.R;
import com.matictechnology.leadersrating.classes.User;
import com.matictechnology.leadersrating.util.DBHelper;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class ActivitySearch extends AppCompatActivity
{
    LinearLayout no_record2;
    ListView search_list;
    ArrayList<User> resList;
    ArrayList<String> name_list;
    DBHelper helper;
    SQLiteDatabase db;
    TextView no_record;
    String id="";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        no_record=(TextView)findViewById(R.id.no_record);

        search_list=(ListView)findViewById(R.id.search_list);

        no_record2=(LinearLayout)findViewById(R.id.no_record2);

        name_list=new ArrayList<String>();

        helper=new DBHelper(ActivitySearch.this);
        db=helper.getWritableDatabase();

        Intent in=getIntent();
        id=in.getStringExtra("id");
        String restr=in.getStringExtra("query");
        resList=helper.search_users_name(db,restr);

        for(int count=0;count<resList.size();count++)
        {
            name_list.add(resList.get(count).getName());
            //Log.e("name=>",""+resList.get(count).getName());
            //Log.e("phone=>",""+resList.get(count).getPhone());

        }
        if(name_list.size()>0)
        {
            ArrayAdapter<String> adapter=new ArrayAdapter<>(ActivitySearch.this,android.R.layout.simple_list_item_1,name_list);
            search_list.setAdapter(adapter);
            search_list.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
                {
                    Intent in =new Intent(ActivitySearch.this,ActivityProfile.class);
                    in.putExtra("phone",resList.get(i).getPhone());
                    //Log.e("phone=>",""+resList.get(i).getPhone());
                    in.putExtra("mode","other");
                    startActivity(in);
                    finish();
                }
            });
        }
        else
        {
            no_record.setVisibility(View.VISIBLE);
            no_record2.setVisibility(View.VISIBLE);
            search_list.setVisibility(View.GONE);
            no_record2.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Intent in1 =new Intent(ActivitySearch.this,ActivityCreateProfile.class);
                    in1.putExtra("mode","other");
                    startActivity(in1);
                }
            });

        }

    }
}
