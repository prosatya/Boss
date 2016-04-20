package com.matictechnology.boss.Acivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

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

public class ActivityLogin extends AppCompatActivity
{
    private LoginButton fbloginButton;
    String appLinkUrl, previewImageUrl;

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
        callbackManager = CallbackManager.Factory.create();
        if (android.os.Build.VERSION.SDK_INT >19) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "user_friends", "email", "user_birthday"));
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>()
                {
                    @Override
                    public void onSuccess(LoginResult loginResult)
                    {
                        /*GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {
                                        Log.e("LoginActivity", response.toString());

                                        // Application code
                                        try {
                                            String email = object.getString("email");
                                            String birthday = object.getString("birthday"); // 01/31/1980 format
                                            Toast.makeText(ActivityLogin.this, ""+email+","+birthday, Toast.LENGTH_SHORT).show();

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,gender,birthday");
                        request.setParameters(parameters);
                        request.executeAsync();*/
                        // App code
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

        setContentView(R.layout.activity_login);

        appLinkUrl = "https://l.facebook.com/l.php?u=https%3A%2F%2Ffb.me%2F670500059755023&h=FAQF0TUbF";
        previewImageUrl = "https://lh3.googleusercontent.com/6mLDecgCtVTKDjVFBd29d9L4aZAkKsiqinqAvUXLWTz4bu6eJ2h4B8wFxB_nRVKrCvqz=h900";

        Uri targetUrl = AppLinks.getTargetUrlFromInboundIntent(this, getIntent());
        if (targetUrl != null)
        {
            Log.i("Activity", "App Link Target URL: " + targetUrl.toString());
        }

        if (AppInviteDialog.canShow()) {
            AppInviteContent content = new AppInviteContent.Builder()
                    .setApplinkUrl(appLinkUrl)
                    .setPreviewImageUrl(previewImageUrl)
                    .build();
            AppInviteDialog.show(this, content);
        }
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
                                    Intent in=new Intent(ActivityLogin.this,ActivityMain.class);
                                    startActivity(in);
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
                    DBHelper helper=new DBHelper(ActivityLogin.this);
                    SQLiteDatabase db=helper.getWritableDatabase();
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
