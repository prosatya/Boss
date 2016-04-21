package com.matictechnology.boss.Acivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.matictechnology.boss.R;


public class ActivitySplashScreen extends Activity
{
    /*public void onAttachedToWindow()
    {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }*/

    /** Called when the activity is first created. */
    Thread splashTread;




    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.activity_splash_screen);
        overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);

        //setContentView()

        StartAnimations();
    }



    private void StartAnimations()
    {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        LinearLayout l=(LinearLayout) findViewById(R.id.lin_lay);
        l.clearAnimation();
        l.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        ImageView iv = (ImageView) findViewById(R.id.splash);
        iv.clearAnimation();
        iv.startAnimation(anim);

        splashTread = new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    int waited = 0;
                    // Splash screen pause time
                    while (waited < 5000)
                    {
                        sleep(100);
                        waited += 100;
                    }
                    if(isLoggedIn())
                    {
                        Intent intent = new Intent(ActivitySplashScreen.this,ActivityMain.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                        ActivitySplashScreen.this.finish();
                    }
                    else
                    {
                        Intent intent = new Intent(ActivitySplashScreen.this,ActivityLogin.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                        ActivitySplashScreen.this.finish();
                    }

                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    ActivitySplashScreen.this.finish();
                }

            }
        };
        splashTread.start();

    }
    public boolean isLoggedIn()
    {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }

    @Override
    protected void onStart()
    {
        super.onStart();

    }


}
