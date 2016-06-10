package com.matictechnology.boss.Acivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.afollestad.materialdialogs.AlertDialogWrapper;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.android.Facebook;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.model.AppInviteContent;
import com.facebook.share.widget.AppInviteDialog;
import com.matictechnology.boss.Classes.User;
import com.matictechnology.boss.R;
import com.matictechnology.boss.Utility.DBHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import bolts.AppLinks;

/*
* Written By Aditya Gupta on 20-04-2016
* for the android application project named as Boss
* */

public class ActivityLogin extends AppCompatActivity
{
    //Facebook Button initialized

    /*

    * */

    private LoginButton fbloginButton;
    String appLinkUrl, previewImageUrl;

    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_LOCATION = 2;
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 3;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_CONTACTS= 4;

    //Button fbloginButton;
    private static String APP_ID = "668042990000730";
    private Facebook facebook = new Facebook(APP_ID);
    CallbackManager callbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.activity_login);



        fbloginButton=(LoginButton) findViewById(R.id.fblogin_button);

        fbloginButton.setReadPermissions(Arrays.asList("public_profile", "email", "user_birthday", "user_friends"));
        //Log.e("login status",""+fbloginButton.getLoginBehavior());
        callbackManager = CallbackManager.Factory.create();
        if (android.os.Build.VERSION.SDK_INT >19) {
            /*

             */
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        //LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "user_friends", "email", "user_birthday"));
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>()
                {
                    @Override
                    public void onSuccess(LoginResult loginResult)
                    {
                        getProfileInformation();
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });
    }



    @Override
    protected void onStart()
    {
        super.onStart();

        if (android.os.Build.VERSION.SDK_INT >19)
        {
            if (ActivityCompat.checkSelfPermission(ActivityLogin.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ActivityLogin.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(ActivityLogin.this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ActivityLogin.this, Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(ActivityLogin.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

            }
        }//java.io.FileNotFoundException: /storage/emulated/0/musicfiles/ringtones/LeanOnRingtone1.mp3: open failed: EACCES (Permission denied)

        if(isLoggedIn())
        {
            Log.e("login status","Logged in");
            getProfileInformation();
        }
        else
            Log.e("login status","Logged out");

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.e("permission",""+requestCode+","+permissions+""+ grantResults.toString());
        if(requestCode==1)
        {
            ActivityCompat.requestPermissions(ActivityLogin.this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_LOCATION);
        }
        if(requestCode==2)
        {
            ActivityCompat.requestPermissions(ActivityLogin.this,new String[]{Manifest.permission.READ_CONTACTS},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);
        }
        if(requestCode==3)
        {
            ActivityCompat.requestPermissions(ActivityLogin.this,new String[]{Manifest.permission.WRITE_CONTACTS},
                    MY_PERMISSIONS_REQUEST_WRITE_CONTACTS);
        }
        if (requestCode==4)
        {
            Log.e("permissions","done with all permissions");
        }
    }

    public boolean isLoggedIn()
    {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }

    public void getProfileInformation()
    {

        com.facebook.AccessToken accessToken = com.facebook.AccessToken.getCurrentAccessToken();
        GraphRequest request = GraphRequest.newMeRequest(accessToken,new GraphRequest.GraphJSONObjectCallback()
        {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response)
            {
                Log.e("Profile response",""+response);
                Parser parser_task=new Parser();
                parser_task.execute(""+response);
                //Toast.makeText(ActivityLogin.this, ""+response, Toast.LENGTH_SHORT).show();
                try
                {
                    String email = object.getString("email");
                    String birthday = object.getString("birthday");
                    Log.e("email,birthday",""+response);
                    //Toast.makeText(ActivityLogin.this, ""+email+","+birthday, Toast.LENGTH_SHORT).show();
                    new GraphRequest(AccessToken.getCurrentAccessToken(),"/me/friends",null, HttpMethod.GET,
                            new GraphRequest.Callback()
                            {
                                public void onCompleted(GraphResponse response)
                                {
                                    Log.e("special response",""+response);
                                    //Toast.makeText(ActivityLogin.this, ""+response, Toast.LENGTH_SHORT).show();
                                    new AlertDialogWrapper.Builder(ActivityLogin.this)
                                            .setTitle("Invite")
                                            .setMessage("Invite Facebook Friends to install this Application")
                                            .setPositiveButton("Invite", new DialogInterface.OnClickListener()
                                            {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which)
                                                {
                                                    appLinkUrl = "https://l.facebook.com/l.php?u=https%3A%2F%2Ffb.me%2F670500059755023&h=FAQF0TUbF";
                                                    previewImageUrl = "https://lh3.googleusercontent.com/6mLDecgCtVTKDjVFBd29d9L4aZAkKsiqinqAvUXLWTz4bu6eJ2h4B8wFxB_nRVKrCvqz=h900";

                                                    Uri targetUrl = AppLinks.getTargetUrlFromInboundIntent(ActivityLogin.this, getIntent());
                                                    if (targetUrl != null)
                                                    {
                                                        Log.i("Activity", "App Link Target URL: " + targetUrl.toString());
                                                    }

                                                    if (AppInviteDialog.canShow())
                                                    {
                                                        AppInviteContent content = new AppInviteContent.Builder()
                                                                .setApplinkUrl(appLinkUrl)
                                                                .setPreviewImageUrl(previewImageUrl)
                                                                .build();
                                                        AppInviteDialog appInviteDialog = new AppInviteDialog(ActivityLogin.this);
                                                        appInviteDialog.registerCallback(callbackManager, new FacebookCallback<AppInviteDialog.Result>()
                                                        {
                                                            @Override
                                                            public void onSuccess(AppInviteDialog.Result result)
                                                            {
                                                                Log.e("App invite","on success");
                                                                Intent in=new Intent(ActivityLogin.this,ActivityMain.class);
                                                                startActivity(in);
                                                            }

                                                            @Override
                                                            public void onCancel()
                                                            {
                                                                Log.e("App invite","on cancel");
                                                                Intent in=new Intent(ActivityLogin.this,ActivityMain.class);
                                                                startActivity(in);
                                                            }

                                                            @Override
                                                            public void onError(FacebookException e)
                                                            {
                                                                Log.e("App invite","on Error");
                                                            }
                                                        });

                                                        appInviteDialog.show(content);
                                                        //AppInviteDialog.show(ActivityLogin.this, content);

                                                    }
                                                    dialog.dismiss();
                                                }
                                            })
                                            .setNegativeButton("Skip", new DialogInterface.OnClickListener()
                                            {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which)
                                                {
                                                    Intent in=new Intent(ActivityLogin.this,ActivityMain.class);
                                                    startActivity(in);
                                                    dialog.dismiss();
                                                }
                                            }).show();
                                }
                            }
                    ).executeAsync();
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }

            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,birthday,picture,education,email,gender,hometown,languages,locale,location,name,link,cover,relationship_status,religion,website,work");
        request.setParameters(parameters);
        request.executeAsync();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public class Parser extends AsyncTask<String ,Void, String >
    {

        @Override
        protected String doInBackground(String... params)
        {
            try
            {
                DBHelper helper=new DBHelper(ActivityLogin.this);
                SQLiteDatabase db=helper.getReadableDatabase();

                ArrayList<User> al=helper.fetch_user(db);
                if(al!=null)
                    return null;
                params[0]=convertStandardJSONString(params[0]);
                Log.e("corrected Json=>",""+params[0]);
                JSONObject obj=new JSONObject(params[0]);
                if(obj!=null)
                {
                    User u=new User();
                    String Email , Name , Gender , FB_Profile , Picture_URL ="", Birthday="" , FB_ID;
                    JSONObject graph_obj=obj.getJSONObject("graphObject");
                    FB_ID=graph_obj.getString("id");
                    if (graph_obj.getString("birthday")!=null)
                    {
                        Birthday=graph_obj.getString("birthday");
                        //u.setBirthday(graph_obj.getString("birthday"));
                    }
                    JSONObject picture_obj=graph_obj.getJSONObject("picture");
                    if(picture_obj!=null)
                    {
                        JSONObject data_obj=picture_obj.getJSONObject("data");
                        if(data_obj!=null)
                        {
                            Picture_URL=data_obj.getString("url");
                            //u.setPicture_url(data_obj.getString("url"));
                        }
                    }
                    Email=graph_obj.getString("email");
                    //u.setEmail(graph_obj.getString("email"));
                    Gender=graph_obj.getString("gender");
                    //u.setGender(graph_obj.getString("gender"));
                    Name=graph_obj.getString("name");
                    //u.setName(graph_obj.getString("name"));
                    FB_Profile=graph_obj.getString("link");
                    //u.setFB_Profile(graph_obj.getString("link"));
                    //Email , Name , Gender , FB_Profile , Picture_URL , Birthday , FB_ID
                    if (Email==null)
                        Email="";
                    if (Name==null)
                        Name="";
                    if (Gender==null)
                        Gender="";
                    if (FB_Profile==null)
                        FB_Profile="";
                    if (Picture_URL==null)
                        Picture_URL="";
                    if (Birthday==null)
                        Birthday="";
                    if (FB_ID==null)
                        FB_ID="";
                    helper.insert_user(db, Email,Name,Gender,FB_Profile,Picture_URL,Birthday,FB_ID);

                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s)
        {
            DBHelper helper=new DBHelper(ActivityLogin.this);
            SQLiteDatabase db=helper.getReadableDatabase();

            ArrayList<User> al=helper.fetch_user(db);
            for(int count=0;count<al.size();count++)
            {
                Log.e("Email=>",""+al.get(count).getEmail());
                Log.e("Name=>",""+al.get(count).getName());
                Log.e("Gender=>",""+al.get(count).getGender());
                Log.e("Birthday=>",""+al.get(count).getBirthday());
                Log.e("Profile=>",""+al.get(count).getFB_Profile());
                Log.e("FB ID=>",""+al.get(count).getFB_ID());
                Log.e("Picture Url=>",""+al.get(count).getPicture_url());
            }
            super.onPostExecute(s);
        }
    }

    public String convertStandardJSONString(String data_json){
        data_json = data_json.replace("Response:", "\"Response\":\"\",");
        data_json = data_json.replace("responseCode: 200", "\"responseCode\": \"200\"");
        data_json = data_json.replace("graphObject", "\"graphObject\"");
        data_json = data_json.replace("error", "\"error\"");
        return data_json;
    }
}
