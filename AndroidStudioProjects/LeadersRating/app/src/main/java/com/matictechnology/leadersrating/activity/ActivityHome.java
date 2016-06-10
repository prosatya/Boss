package com.matictechnology.leadersrating.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.SearchManager;
import android.content.ComponentName;
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
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.matictechnology.leadersrating.R;
import com.matictechnology.leadersrating.classes.User;
import com.matictechnology.leadersrating.fragments.HomeFragmentManager;
import com.matictechnology.leadersrating.fragments.ProfileFragmentManager;
import com.matictechnology.leadersrating.util.DBHelper;
import com.pkmmte.view.CircularImageView;

import java.util.ArrayList;
import java.util.Calendar;

import me.drakeet.materialdialog.MaterialDialog;

public class ActivityHome extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,SearchView.OnQueryTextListener
{
    private PagerSlidingTabStrip tabs;  //tab strip for the pager slider
    private ViewPager pager;    //pager object for switching fragments in
    private MyPagerAdapter adapter;

    TextView home_rate_counter,home_user_counter,home_comment_counter;

    MaterialDialog materialdialog_adv_search,materialdialog_adv_search1,materialdialog_adv_search2;

    User u;
    DBHelper helper;
    SQLiteDatabase db;
    CircularImageView header_image;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        home_rate_counter=(TextView)findViewById(R.id.home_rate_counter);
        home_user_counter=(TextView)findViewById(R.id.home_user_counter);
        home_comment_counter=(TextView)findViewById(R.id.home_comment_counter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimary));
        setSupportActionBar(toolbar);



        Intent in=getIntent();
        u=(User)in.getSerializableExtra("user");

        tabs = (PagerSlidingTabStrip) findViewById(R.id.home_tabs);
        pager = (ViewPager) findViewById(R.id.home_pager);
        adapter = new MyPagerAdapter(getSupportFragmentManager());

        pager.setAdapter(adapter);
        //setting adapter to the pager

        //setting margin to the pager in the activity
        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                .getDisplayMetrics());
        pager.setPageMargin(pageMargin);

        pager.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                pager.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        //setting different attributes to the tabs
        tabs.setViewPager(pager);
        tabs.setIndicatorHeight(3);
        tabs.setTextColorResource(R.color.colorPrimary);
        tabs.setDividerColorResource(R.color.colorPrimary);
        tabs.setTextColorResource(R.color.colorPrimary);
        tabs.setIndicatorColorResource(R.color.colorPrimary);
        tabs.setDividerPadding(20);
        //tabs.setTextSize(20);
        //tabs.setTabPaddingLeftRight(500);
        //tabs.setPadding(10,10,10,10);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View hView =  navigationView.getHeaderView(0);
        TextView nav_user = (TextView)hView.findViewById(R.id.header_text);
        TextView nav_user1 = (TextView)hView.findViewById(R.id.textView);


        nav_user.setText(u.getName());
        nav_user1.setText(u.getEmail());

        header_image=(CircularImageView)hView.findViewById(R.id.header_image);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile(u.getPicture_path(), options);

        //Bitmap bitmap1=getRoundedBitmap(bitmap);

        if(bitmap==null)
            header_image.setImageResource(R.drawable.blank_user);
        else
            header_image.setImageBitmap(bitmap);



        helper=new DBHelper(ActivityHome.this);
        db=helper.getWritableDatabase();

        int comment_counter=helper.getCommentCount(db,"all");

        home_comment_counter.setText(""+comment_counter);

        int rate_counter=helper.getRatingCount(db,"all");

        home_rate_counter.setText(""+rate_counter);

        int user_counter=helper.getAllUsersCount(db);

        home_user_counter.setText(""+user_counter);



        /*Log.e("printing","Usersss");
        Log.e("===================","============================================================================");
        helper.show_all_Users(db);
        helper.showUsersskills(db);
        helper.showUsersExp(db);
        helper.showUsersEdu(db);*/

        themeNavAndStatusBar(ActivityHome.this);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        adapter = null;
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        adapter = new MyPagerAdapter(getSupportFragmentManager());

        pager.setAdapter(adapter);
        //setting adapter to the pager

        //setting margin to the pager in the activity
        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                .getDisplayMetrics());
        pager.setPageMargin(pageMargin);

        pager.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                pager.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        //setting different attributes to the tabs
        tabs.setViewPager(pager);
        tabs.setIndicatorHeight(3);
        tabs.setTextColorResource(R.color.colorPrimary);
        tabs.setDividerColorResource(R.color.colorPrimary);
        tabs.setTextColorResource(R.color.colorPrimary);
        tabs.setIndicatorColorResource(R.color.colorPrimary);
        tabs.setDividerPadding(20);

        int comment_counter=helper.getCommentCount(db,"all");

        home_comment_counter.setText(""+comment_counter);

        int rate_counter=helper.getRatingCount(db,"all");

        home_rate_counter.setText(""+rate_counter);

        int user_counter=helper.getAllUsersCount(db);

        home_user_counter.setText(""+user_counter);
    }

    public void themeNavAndStatusBar(Activity activity)
    {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
            return;

        Window w = activity.getWindow();
        w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        w.setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        w.setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        w.setNavigationBarColor(activity.getResources().getColor(android.R.color.transparent));

        w.setStatusBarColor(activity.getResources().getColor(android.R.color.transparent));
    }

    public Bitmap getRoundedBitmap(Bitmap bitmap)
    {
        if(bitmap!=null)
        {
            final Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            final Canvas canvas = new Canvas(output);

            final int color = Color.RED;
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            final RectF rectF = new RectF(rect);

            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);
            canvas.drawOval(rectF, paint);

            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);

            bitmap.recycle();

            return output;
        }
        return null;
    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);
        //SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        //searchView.setSearchableInfo(searchManager.getSearchableInfo(new ComponentName(this, ActivitySearch.class)));
        //searchView.setIconifiedByDefault(false);

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query)
    {
        Intent in=new Intent(ActivityHome.this,ActivitySearch.class);

        in.putExtra("query",query);

        startActivity(in);
        // User pressed the search button
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText)
    {
        // User changed the text
        return false;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.add_user)
        {
            Intent in1 =new Intent(ActivityHome.this,ActivityCreateProfile.class);
            in1.putExtra("mode","other");
            startActivity(in1);
        }
        else if (id == R.id.advance_search)
        {
            LayoutInflater inflater = (LayoutInflater) ActivityHome.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View itemView_select = inflater.inflate(R.layout.dialog_advance_serach, null);

            final Spinner advance_search_spinner1=(Spinner)itemView_select.findViewById(R.id.advance_search_spinner);

            final ArrayList<String> cat_list=new ArrayList<>();
            cat_list.add("Select Category");
            cat_list.add("Business");
            cat_list.add("Politics");
            cat_list.add("Doctor");

            ArrayAdapter<String> adapter=new ArrayAdapter<String>(ActivityHome.this,android.R.layout.simple_spinner_dropdown_item,cat_list);

            advance_search_spinner1.setAdapter(adapter);


            final ArrayList<String> state_list=new ArrayList<>();

            state_list.add("Andhra Pradesh");
            state_list.add("Arunachal Pradesh");
            state_list.add("Assam");
            state_list.add("Bihar");
            state_list.add("Chhattisgarh");
            state_list.add("Goa");
            state_list.add("Gujarat");
            state_list.add("Haryana");
            state_list.add("Himachal Pradesh");
            state_list.add("Jammu & Kashmir");
            state_list.add("Jharkhand");
            state_list.add("Karnataka");
            state_list.add("Kerala");
            state_list.add("Madhya Pradesh");
            state_list.add("Maharashtra");
            state_list.add("Manipur");
            state_list.add("Meghalaya");
            state_list.add("Mizoram");
            state_list.add("Nagaland");
            state_list.add("Odisha (Orissa)");
            state_list.add("Punjab");
            state_list.add("Rajasthan");
            state_list.add("Sikkim");
            state_list.add("Tamil Nadu");
            state_list.add("Telangana");
            state_list.add("Tripura");
            state_list.add("Uttar Pradesh");
            state_list.add("Uttarakhand");
            state_list.add("West Bengal");
            state_list.add("Andaman and Nicobar Islands");
            state_list.add("Chandigarh");
            state_list.add("Dadra and Nagar");
            state_list.add("Daman and Diu");
            state_list.add("Lakshadweep");
            state_list.add("Delhi");
            state_list.add("Puducherry (Pondicherry)");


            final ArrayList<String> andhra_district_list=new ArrayList<>();

            andhra_district_list.add("Anantapur");
            andhra_district_list.add("Chittoor");
            andhra_district_list.add("East Godavari Kakinada");
            andhra_district_list.add("Guntur Guntur");
            andhra_district_list.add("Kadapa Kadapa");
            andhra_district_list.add("Krishna Machilipatnam");
            andhra_district_list.add("Kurnool Kurnool");
            andhra_district_list.add("Nellore Nellore");
            andhra_district_list.add("Prakasam Ongole");
            andhra_district_list.add("Srikakulam Srikakulam");
            andhra_district_list.add("Visakhapatnam Visakhapatnam");
            andhra_district_list.add("Vizianagaram Vizianagaram");
            andhra_district_list.add("West Godavari");


            materialdialog_adv_search=new MaterialDialog(ActivityHome.this)
                    .setTitle("Advance Search")
                    .setView(itemView_select)
                    .setMessage("Select Category")
                    .setNegativeButton("Cancel", new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View view)
                        {

                            materialdialog_adv_search.dismiss();

                        }
                    })
                    .setPositiveButton("Search", new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View view)
                        {
                            Log.e("selected",""+advance_search_spinner1.getSelectedItem());
                            materialdialog_adv_search.dismiss();


                            if(advance_search_spinner1.getSelectedItem().equals("Politics"))
                            {
                                LayoutInflater inflater = (LayoutInflater) ActivityHome.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                View itemView_select = inflater.inflate(R.layout.dialog_advance_serach, null);

                                final Spinner advance_search_spinner2=(Spinner)itemView_select.findViewById(R.id.advance_search_spinner);

                                ArrayAdapter<String> adapter=new ArrayAdapter<String>(ActivityHome.this,android.R.layout.simple_spinner_dropdown_item,state_list);

                                advance_search_spinner2.setAdapter(adapter);

                                materialdialog_adv_search1=new MaterialDialog(ActivityHome.this)
                                        .setTitle("Advance Search")
                                        .setView(itemView_select)
                                        .setMessage("Select State")
                                        .setNegativeButton("Cancel", new View.OnClickListener()
                                        {
                                            @Override
                                            public void onClick(View view)
                                            {
                                                materialdialog_adv_search1.dismiss();
                                            }
                                        })
                                        .setPositiveButton("Search", new View.OnClickListener()
                                        {
                                            @Override
                                            public void onClick(View view)
                                            {
                                                Log.e("selected",""+advance_search_spinner2.getSelectedItem());

                                                materialdialog_adv_search1.dismiss();

                                                if(advance_search_spinner2.getSelectedItem().equals("Andhra Pradesh"))
                                                {
                                                    LayoutInflater inflater = (LayoutInflater) ActivityHome.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                                    View itemView_select = inflater.inflate(R.layout.dialog_advance_serach, null);

                                                    final Spinner advance_search_spinner3=(Spinner)itemView_select.findViewById(R.id.advance_search_spinner);

                                                    ArrayAdapter<String> adapter=new ArrayAdapter<String>(ActivityHome.this,android.R.layout.simple_spinner_dropdown_item,andhra_district_list);

                                                    advance_search_spinner3.setAdapter(adapter);

                                                    materialdialog_adv_search2=new MaterialDialog(ActivityHome.this)
                                                            .setTitle("Advance Search")
                                                            .setView(itemView_select)
                                                            .setMessage("Select Category")
                                                            .setNegativeButton("Cancel", new View.OnClickListener()
                                                            {
                                                                @Override
                                                                public void onClick(View view)
                                                                {
                                                                    materialdialog_adv_search2.dismiss();
                                                                }
                                                            })
                                                            .setPositiveButton("Search", new View.OnClickListener()
                                                            {
                                                                @Override
                                                                public void onClick(View view)
                                                                {
                                                                    Log.e("selected",""+advance_search_spinner3.getSelectedItem());

                                                                    materialdialog_adv_search2.dismiss();
                                                                }
                                                            });
                                                    materialdialog_adv_search2.show();
                                                }
                                            }
                                        });
                                materialdialog_adv_search1.show();
                            }
                        }
                    });
            materialdialog_adv_search.show();
        }
        else if (id == R.id.nav_slideshow)
        {

        }
        else if (id == R.id.nav_manage)
        {

        }
        else if (id == R.id.nav_share)
        {

        }
        else if (id == R.id.nav_send)
        {

        }
        else if (id == R.id.nav_profile)
        {
            Intent in=new Intent(ActivityHome.this,ActivityProfile.class);
            in.putExtra("mode","user");
            startActivity(in);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public class MyPagerAdapter extends FragmentPagerAdapter
    {
        //fragment pager adapter for the fragments on pager
        private final String[] TITLES =
        {
                //titles for the fragments
                "Newest", "Active","Popular"
        };

        public MyPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            return TITLES[position];
        }

        @Override
        public int getCount()
        {
            return TITLES.length;
        }

        @Override
        public Fragment getItem(int position)
        {
            return HomeFragmentManager.newInstance(position,u);
        }
    }

}
