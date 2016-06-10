package com.matictechnology.leadersrating.activity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import com.matictechnology.leadersrating.R;
import com.matictechnology.leadersrating.classes.Contacts;
import com.matictechnology.leadersrating.util.DBHelper;

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

public class ActivityFriendList extends AppCompatActivity
{

    ListView listView;

    MyCustomAdapter dataAdapter = null;

    ArrayList<Contacts> countryList;

    //list view initialisation for containing the list of all friends
    ListView list;
    //array list that contains the Contacts object which will be fetched from the DBHelper class and Database
    ArrayList<Contacts> alist;
    //String array list which contains the name and id to show name alone o user and ID to trace the selected friend by user
    ArrayList<String> name,id;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        displayListView();

        checkButtonClick();
    }


    private void displayListView()
    {

        DBHelper helper=new DBHelper(ActivityFriendList.this);
        SQLiteDatabase db=helper.getWritableDatabase();

        //getting the generic contacts type array list from database helper class

        //Array list of countries
        countryList = new ArrayList<Contacts>();
        countryList =helper.getAllContacts(db);

        //create an ArrayAdaptar from the String Array
        dataAdapter = new MyCustomAdapter(ActivityFriendList.this,R.layout.friend_list_item, countryList);
        listView = (ListView) findViewById(R.id.listView1);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> parent, View view,int position, long id)
            {
                // When clicked, show a toast with the TextView text
                Contacts country = (Contacts) parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(),
                        "Clicked on Row: " + country.getName(),
                        Toast.LENGTH_LONG).show();
            }
        });

    }


    private class MyCustomAdapter extends ArrayAdapter<Contacts>
    {

        private ArrayList<Contacts> countryList;

        public MyCustomAdapter(Context context, int textViewResourceId, ArrayList<Contacts> countryList)
        {
            super(context, textViewResourceId, countryList);
            this.countryList = new ArrayList<Contacts>();
            this.countryList.addAll(countryList);
        }

        private class ViewHolder
        {
            CheckBox name;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {

            ViewHolder holder = null;

            if (convertView == null)
            {
                LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.friend_list_item, null);

                holder = new ViewHolder();
                holder.name = (CheckBox) convertView.findViewById(R.id.checkBox1);
                convertView.setTag(holder);

                holder.name.setOnClickListener( new View.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        CheckBox cb = (CheckBox) v ;
                        Contacts country = (Contacts) cb.getTag();
                        Toast.makeText(getApplicationContext(),"Clicked on Checkbox: "+cb.getText()+" is "+cb.isChecked(),
                                Toast.LENGTH_LONG).show();
                        country.setSelected(cb.isChecked());
                    }
                });
            }
            else
            {
                holder = (ViewHolder) convertView.getTag();
            }

            Contacts country = countryList.get(position);
            holder.name.setText(country.getName());
            holder.name.setChecked(country.isSelected());
            holder.name.setTag(country);

            return convertView;

        }

    }

    private void checkButtonClick()
    {
        Button myButton = (Button) findViewById(R.id.findSelected);
        myButton.setOnClickListener(new View.OnClickListener()
        {
            String email_invite_result="",mobile_invite_result="";
            @Override
            public void onClick(View v)
            {
                StringBuffer responseText = new StringBuffer();
                responseText.append("The following were selected...\n");

                ArrayList<Contacts> countryList = dataAdapter.countryList;
                for(int i=0;i<countryList.size();i++)
                {
                    Contacts country = countryList.get(i);
                    String[] number_invite;//=new String[](country.getNumber().length);

                    if(country.isSelected())
                    {
                        responseText.append("\n" + country.getName());
                        email_invite_result=email_invite_result+country.getEmail();
                        number_invite=country.getNumber();
                        for(int count=0;count<country.getNumber().length;count++)
                            mobile_invite_result=mobile_invite_result+number_invite[count];
                    }
                }
                Toast.makeText(getApplicationContext(),responseText, Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(),"result="+email_invite_result, Toast.LENGTH_LONG).show();
                Task task=new Task();
                task.execute(email_invite_result,mobile_invite_result);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        new MenuInflater(this).inflate(R.menu.friend_list_menu, menu);
        return (super.onCreateOptionsMenu(menu));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        if (item.getItemId() == R.id.select_all)
        {
            //countryList
            for(int count=0;count<countryList.size();count++)
            {
                countryList.get(count).setSelected(true);
                dataAdapter.notifyDataSetChanged();
            }


        }
        else if (item.getItemId()==R.id.clear_all)
        {
            for(int count=0;count<countryList.size();count++)
            {
                countryList.get(count).setSelected(false);
                dataAdapter.notifyDataSetChanged();
            }
            return(true);
        }
        return (super.onOptionsItemSelected(item));
    }


    public class Task extends AsyncTask<String,Void,Void>
    {

        @Override
        protected Void doInBackground(String... strings)
        {
            String result="";
            try
            {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("");

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);


                nameValuePairs.add(new BasicNameValuePair("email_result", strings[0]));
                nameValuePairs.add(new BasicNameValuePair("mobile_result", strings[1]));

                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

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
            return null;
        }
    }

}
