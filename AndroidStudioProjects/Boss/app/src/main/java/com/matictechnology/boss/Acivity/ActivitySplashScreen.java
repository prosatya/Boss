package com.matictechnology.boss.Acivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

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
                    Intent intent = new Intent(ActivitySplashScreen.this,ActivityLogin.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    ActivitySplashScreen.this.finish();
                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    ActivitySplashScreen.this.finish();
                }

            }
        };
        splashTread.start();

    }

    @Override
    protected void onStart()
    {
        super.onStart();

    }
}
