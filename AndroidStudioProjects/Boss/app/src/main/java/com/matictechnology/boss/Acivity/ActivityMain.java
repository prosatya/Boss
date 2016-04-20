package com.matictechnology.boss.Acivity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.login.LoginManager;
import com.matictechnology.boss.Classes.Contacts;
import com.matictechnology.boss.R;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ActivityMain extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{
    ArrayList<Contacts> alist;
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    android.support.v7.widget.CardView home_find_near_by,home_update_my_location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        home_find_near_by=(CardView)findViewById(R.id.home_find_near_by);
        home_update_my_location=(CardView)findViewById(R.id.home_update_my_location);

        home_update_my_location.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent in=new Intent(ActivityMain.this,ActivityMaps.class);
                startActivity(in);
            }
        });

        home_find_near_by.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

            }
        });

        if (ActivityCompat.checkSelfPermission(ActivityMain.this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ActivityMain.this, Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(ActivityMain.this,new String[]
                    {
                            Manifest.permission.READ_CONTACTS},
                            MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
        else
        {
            ContentResolver cr = getContentResolver();
            Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
            if(cur!=null)
            {
                cur.moveToFirst();
                for(int count1=0;count1<cur.getCount();count1++)
                {
                    //Log.e(count1+"",""+cur.getColumnName(count1));
                    /*for(int count=0;count<cur.getColumnCount();count++)
                    {
                        Log.e(count1+","+count,""+cur.getColumnName(count));
                    }*/
                    cur.moveToNext();
                }
            }
            alist=new ArrayList<>();
            if (cur.getCount() > 0)
            {
                cur.moveToFirst();
                while (cur.moveToNext())
                {
                    String sl = "";
                    Contacts c = new Contacts();
                    String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                    c.setId(id);
                    sl = sl + "" + id + "\n";
                    String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    c.setName(name);

                    String EMAIL_REGEX = "[a-zA-Z0-9\\.]+@[a-zA-Z0-9\\-\\_\\.]+\\.[a-zA-Z0-9]{3}";
                    Boolean b = name.matches(EMAIL_REGEX);
                    if(b)
                        c.setEmail(name);

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
                        c.setNumber(phoneNo);// = ;
                        pCur.close();
                    }
                    //slist.add(sl);
                    alist.add(c);
                }
            }
            cur.close();
        }

        Log.e("array size=>",""+alist.size());
        for(int count=0;count<alist.size();count++)
        {
            Log.e(count+",name",""+alist.get(count).getName());
            Log.e(count+",id",""+alist.get(count).getId());
            Log.e(count+",Email",""+alist.get(count).getEmail());
            if(alist.get(count).getNumber()!=null)
            {
                for(int count1=0;count1<alist.get(count).getNumber().length;count1++)
                    Log.e(count+",number",""+alist.get(count).getNumber()[count1]);
            }

        }




        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.setDrawerListener(toggle);


        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if(cur!=null)
        {
            cur.moveToFirst();
            for(int count1=0;count1<cur.getCount();count1++)
            {
                for(int count=0;count<cur.getColumnCount();count++)
                {
                    Log.e(count1+","+count,""+cur.getColumnName(count));
                }
                cur.moveToNext();
            }
        }

        cur.close();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        LoginManager mLoginManager = LoginManager.getInstance();
        mLoginManager.logOut();
    }
}
