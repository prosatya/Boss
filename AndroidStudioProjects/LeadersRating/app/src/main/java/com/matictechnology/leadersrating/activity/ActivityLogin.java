package com.matictechnology.leadersrating.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.model.AppInviteContent;
import com.facebook.share.widget.AppInviteDialog;
import com.matictechnology.leadersrating.R;
import com.matictechnology.leadersrating.classes.FBUser;
import com.matictechnology.leadersrating.classes.User;
import com.matictechnology.leadersrating.util.AsyncClass;
import com.matictechnology.leadersrating.util.DBHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bolts.AppLinks;
import me.drakeet.materialdialog.MaterialDialog;

public class ActivityLogin extends AppCompatActivity implements View.OnClickListener
{
    View itemView_select;

    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;

    MaterialDialog materialdialog_invite;//,
    MaterialDialog materialdialog_select;
    RadioGroup.OnCheckedChangeListener listen_check_change;

    private LoginButton fbloginButton;
    CallbackManager callbackManager;
    Button register;
    String final_response="";

    String appLinkUrl, previewImageUrl;

    private static final int MY_PERMISSIONS_REQUEST_ACCESS_READ_CONTACTS= 1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.activity_login);

        callbackManager = CallbackManager.Factory.create();

        //AppEventsLogger.activateApp(this);

        register=(Button)findViewById(R.id.register);

        register.setOnClickListener(this);

        fbloginButton=(LoginButton) findViewById(R.id.fblogin_button);

        fbloginButton.setReadPermissions(Arrays.asList("public_profile", "email", "user_birthday", "user_friends"));

        LoginManager.getInstance().registerCallback(callbackManager,new FacebookCallback<LoginResult>()
        {
            @Override
            public void onSuccess(LoginResult loginResult)
            {
                // App code
                Log.e("success",""+loginResult.toString());
                getProfileInformation();
            }

            @Override
            public void onCancel()
            {
                // App code
                Log.e("Canceled","not possible");
            }

            @Override
            public void onError(FacebookException exception)
            {
                // App code
                Log.e("Error","not possible");
            }
        });

        Uri targetUrl = AppLinks.getTargetUrlFromInboundIntent(this, getIntent());
        if (targetUrl != null)
        {
            Log.e("Activity", "App Link Target URL: " + targetUrl.toString());
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimary));
        //toolbar.setLogo(R.drawable.leader1);
        setSupportActionBar(toolbar);

        themeNavAndStatusBar(ActivityLogin.this);
    }

    public void getProfileInformation()
    {

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        GraphRequest request = GraphRequest.newMeRequest(accessToken,new GraphRequest.GraphJSONObjectCallback()
        {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response)
            {
                Log.e("Profile response",""+response);
                final_response=""+response;
                //Toast.makeText(ActivityLogin.this, ""+response, Toast.LENGTH_SHORT).show();
                try
                {
                    String email = object.getString("email");
//                    String birthday = object.getString("birthday");
                    Log.e("email,birthday",""+response);
                    //Toast.makeText(ActivityLogin.this, ""+email+","+birthday, Toast.LENGTH_SHORT).show();
                    new GraphRequest(AccessToken.getCurrentAccessToken(),"/me/friends",null, HttpMethod.GET,
                            new GraphRequest.Callback()
                            {
                                public void onCompleted(GraphResponse response)
                                {
                                    Log.e("special response",""+response);
                                    //Toast.makeText(ActivityLogin.this, ""+response, Toast.LENGTH_SHORT).show();
                                    materialdialog_invite=new MaterialDialog(ActivityLogin.this)
                                            .setTitle("Invite")
                                            .setMessage("Invite Friends to install this Application")
                                            .setPositiveButton("Invite", new View.OnClickListener()
                                            {
                                                @Override
                                                public void onClick(View view)
                                                {
                                                    LayoutInflater inflater = (LayoutInflater) ActivityLogin.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                                    itemView_select = inflater.inflate(R.layout.dialog_invite_select_, null);
                                                    RadioGroup rg=(RadioGroup)itemView_select.findViewById(R.id.radiogroup);
                                                    rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
                                                    {
                                                        @Override
                                                        public void onCheckedChanged(RadioGroup radioGroup, int checkedId)
                                                        {
                                                            switch (checkedId)
                                                            {
                                                                case R.id.friends:
                                                                {
                                                                    Intent registration_intent=new Intent(ActivityLogin.this,ActivityFriendList.class);
                                                                    startActivity(registration_intent);
                                                                    break;
                                                                }
                                                                case R.id.fb_friends:
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
                                                                        appInviteDialog.registerCallback(callbackManager, new FacebookCallback<AppInviteDialog.Result>() {
                                                                            @Override
                                                                            public void onSuccess(AppInviteDialog.Result result)
                                                                            {
                                                                                Log.e("App invite", "on success");
                                                                                Intent in = new Intent(ActivityLogin.this, ActivityRegister.class);
                                                                                in.putExtra("login","fb");
                                                                                Log.e("login from login1","fb");
                                                                                startActivity(in);
                                                                                materialdialog_select.dismiss();
                                                                                materialdialog_invite.dismiss();
                                                                            }

                                                                            @Override
                                                                            public void onCancel() {
                                                                                Log.e("App invite", "on cancel");
                                                                                Intent in=new Intent(ActivityLogin.this,ActivityRegister.class);
                                                                                in.putExtra("login","fb");
                                                                                Log.e("login from login2","fb");
                                                                                startActivity(in);
                                                                                materialdialog_select.dismiss();
                                                                                materialdialog_invite.dismiss();
                                                                            }

                                                                            @Override
                                                                            public void onError(FacebookException e)
                                                                            {
                                                                                Intent in=new Intent(ActivityLogin.this,ActivityRegister.class);
                                                                                in.putExtra("login","fb");
                                                                                Log.e("login from login3","fb");
                                                                                startActivity(in);
                                                                                Log.e("App invite", "on Error");
                                                                            }
                                                                        });

                                                                        appInviteDialog.show(content);
                                                                        //AppInviteDialog.show(ActivityLogin.this, content);
                                                                    }
                                                                    break;
                                                                }
                                                            }
                                                        }
                                                    });



                                                    //materialdialog_invite.dismiss();

                                                    materialdialog_select=new MaterialDialog(ActivityLogin.this)
                                                            .setTitle("Invite")
                                                            .setView(itemView_select)
                                                            .setMessage("Invite Friends to install this Application")
                                                            .setPositiveButton("Cancel", new View.OnClickListener()
                                                            {
                                                                @Override
                                                                public void onClick(View view)
                                                                {
                                                                    //dialog.dismiss();
                                                                    Parser parser_task=new Parser();
                                                                    parser_task.execute(final_response);

                                                                    materialdialog_select.dismiss();
                                                                    materialdialog_invite.dismiss();

                                                                }
                                                            });
                                                    materialdialog_select.show();

                                                }
                                            })
                                            .setNegativeButton("Skip", new View.OnClickListener()
                                            {
                                                @Override
                                                public void onClick(View view)
                                                {
                                                    Parser parser_task=new Parser();
                                                    parser_task.execute(final_response);

                                                    materialdialog_invite.dismiss();
                                                }
                                            });
                                    materialdialog_invite.show();
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

    public class Parser extends AsyncTask<String ,Void, String >
    {

        @Override
        protected String doInBackground(String... params)
        {
            try
            {
                DBHelper helper=new DBHelper(ActivityLogin.this);
                SQLiteDatabase db=helper.getReadableDatabase();

                ArrayList<FBUser> al=helper.fetch_fbuser(db);
                if(al!=null)
                    return null;
                params[0]=convertStandardJSONString(params[0]);
                Log.e("corrected Json=>",""+params[0]);
                JSONObject obj=new JSONObject(params[0]);
                if(obj!=null)
                {
                    FBUser u=new FBUser();
                    String Email , Name , Gender , FB_Profile , Picture_URL ="",FB_ID,cover_url = null;
                    JSONObject graph_obj=obj.getJSONObject("graphObject");
                    FB_ID=graph_obj.getString("id");
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
                    JSONObject cover_obj=graph_obj.getJSONObject("cover");
                    if(cover_obj!=null)
                    {
                        cover_url=cover_obj.getString("source");
                    }



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
                    if (FB_ID==null)
                        FB_ID="";
                    if (cover_url==null)
                        cover_url="";

                    Intent in=new Intent(ActivityLogin.this,ActivityRegister.class);
                    in.putExtra("login","fb");
                    Log.e("login from login4","fb");
                    in.putExtra("email",""+Email);
                    in.putExtra("name",""+Name);
                    in.putExtra("picture",""+Picture_URL);
                    in.putExtra("fb_id",""+FB_ID);
                    in.putExtra("cover_url",""+cover_url);
                    startActivity(in);
                    finish();
                    //helper.insert_user(db, Email,Name,Gender,FB_Profile,Picture_URL,"",FB_ID);

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
            /*DBHelper helper=new DBHelper(ActivityLogin.this);
            SQLiteDatabase db=helper.getReadableDatabase();

            ArrayList<FBUser> al=helper.fetch_fbuser(db);
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
            */
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



    @Override
    public void onClick(View view)
    {
        if(view.getId()==R.id.register)
        {


            Intent in=new Intent(ActivityLogin.this,ActivityRegister.class);
            in.putExtra("login","manual");
            Log.e("login from login5","fb");
            startActivity(in);
            finish();
        }
    }

    public boolean isLoggedIn()
    {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }


    @Override
    protected void onStart()
    {
        Log.e("in start","starting app");

        if (android.os.Build.VERSION.SDK_INT >19)
        {
            Log.e("in start","above sdk 19");
            if (ActivityCompat.checkSelfPermission(ActivityLogin.this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.checkSelfPermission(ActivityLogin.this, Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.checkSelfPermission(ActivityLogin.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.checkSelfPermission(ActivityLogin.this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED)
            // && ActivityCompat.checkSelfPermission(ActivityLogin.this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ActivityLogin.this, Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED)
            {
                Log.e("in start","no permission");
                ActivityCompat.requestPermissions(ActivityLogin.this,new String[]{Manifest.permission.READ_CONTACTS,
                        Manifest.permission.WRITE_CONTACTS,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.INTERNET},
                        MY_PERMISSIONS_REQUEST_ACCESS_READ_CONTACTS);

                Log.e("in start","asked for read contacts permission");
            }
            else
            {
                DBHelper helper=new DBHelper(ActivityLogin.this);
                SQLiteDatabase db=helper.getWritableDatabase();
                String str=helper.get_id(db);
                Log.e("str=>",""+str+"....");
                if(str!=null)
                {
                    User u;//=new User();
                    u=helper.search_users_phone(db,str);
                    Log.e("string check=>",""+str+"....");
                    Intent in=new Intent(ActivityLogin.this,ActivityHome.class);
                    in.putExtra("user",u);
                    startActivity(in);
                    finish();
                }
            }
        }
        else
        {
            Log.e("in start","below sdk 19");
            DBHelper helper=new DBHelper(ActivityLogin.this);
            SQLiteDatabase db=helper.getWritableDatabase();
            String str=helper.get_id(db);
            if(str!=null)
            {
                User u;//=new User();
                u=helper.search_users_phone(db,str);
                Log.e("string check=>",""+str+"....");
                Intent in=new Intent(ActivityLogin.this,ActivityHome.class);
                in.putExtra("user",u);
                startActivity(in);
                finish();
            }
        }

        

        super.onStart();

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.e("permission","request code=>"+requestCode+" ,\npermission=>"+permissions[0]+" \n,grant result=>"+ grantResults[0]);

        if(requestCode==1)
        {
                Log.e("in start","All granted");
                DBHelper helper=new DBHelper(ActivityLogin.this);
                SQLiteDatabase db=helper.getWritableDatabase();
                String str=helper.get_id(db);
                if(str!=null)
                {
                    User u;//=new User();
                    u=helper.search_users_phone(db,str);
                    Log.e("string check=>",""+str+"....");
                    Intent in=new Intent(ActivityLogin.this,ActivityHome.class);
                    in.putExtra("user",u);
                    startActivity(in);
                    finish();
                }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        Log.e("result=>",requestCode+","+resultCode+","+data);
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
}
