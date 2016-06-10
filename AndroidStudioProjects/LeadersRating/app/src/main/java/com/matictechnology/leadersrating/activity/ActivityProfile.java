package com.matictechnology.leadersrating.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
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
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.LruCache;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;
import android.text.style.TypefaceSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.OvershootInterpolator;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.github.clans.fab.FloatingActionMenu;
import com.matictechnology.leadersrating.R;
import com.matictechnology.leadersrating.classes.User;
import com.matictechnology.leadersrating.fragments.ProfileFragmentManager;
import com.matictechnology.leadersrating.util.DBHelper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import me.drakeet.materialdialog.MaterialDialog;

public class ActivityProfile extends AppCompatActivity implements View.OnClickListener
{
    private PagerSlidingTabStrip tabs;  //tab strip for the pager slider
    private ViewPager pager;    //pager object for switching fragments in
    private MyPagerAdapter adapter;
    String mode;

    User u;

    //Toolbar toolbar;

    DBHelper helper;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile1);

        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        pager = (ViewPager) findViewById(R.id.pager);
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
        tabs.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        tabs.setDividerColorResource(R.color.white);
        tabs.setTextColorResource(R.color.white);
        tabs.setIndicatorColorResource(R.color.white);
        tabs.setIndicatorHeight(4);


        Intent in=getIntent();
        String phone=in.getStringExtra("phone");
        Log.e("from profile phone=>",""+phone);
        mode=in.getStringExtra("mode");
        Log.e("from profile mode=>",""+mode);


        helper=new DBHelper(ActivityProfile.this);
        db=helper.getWritableDatabase();

        if(phone!=null)
        {
            u=helper.getUsersAll(db,phone);
        }
        else
        {
            u=new User();
            u=helper.getAll(db,u);

        }

        //toolbar.setTitle("");
        //Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/angelina.TTF");

        //TextView toolbar_text=(TextView)toolbar.findViewById(R.id.toolbar_text);


        //SpannableString s = new SpannableString(u.getName());
        //s.setSpan(new TypefaceSpan(this, "kelvinchItalic.otf"), 0, u.getName().length(),
        //        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        //toolbar.setTitle(s);


        //toolbar_text.setTypeface(custom_font);
        //toolbar_text.setText(u.getName());
        //setSupportActionBar(toolbar);

        themeNavAndStatusBar(ActivityProfile.this);

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

    @Override
    public void onClick(View view)
    {

    }

    public class MyPagerAdapter extends FragmentPagerAdapter
    {
        //fragment pager adapter for the fragments on pager
        private final String[] TITLES =
        {
                //titles for the fragments
                "Basic", "Education","Work", "Skills"
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
            return ProfileFragmentManager.newInstance(position,u,mode);
        }

    }

}
