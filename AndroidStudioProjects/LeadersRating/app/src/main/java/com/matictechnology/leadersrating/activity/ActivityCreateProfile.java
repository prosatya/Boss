package com.matictechnology.leadersrating.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.matictechnology.leadersrating.R;
import com.matictechnology.leadersrating.classes.User;
import com.matictechnology.leadersrating.fragments.FragmentCreateProfileBasic;
import com.matictechnology.leadersrating.fragments.FragmentCreateProfileEdu;
import com.matictechnology.leadersrating.fragments.FragmentCreateProfileExp;
import com.matictechnology.leadersrating.fragments.FragmentCreateProfileSkills;
import com.matictechnology.leadersrating.util.DBHelper;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class ActivityCreateProfile extends AppCompatActivity implements FragmentCreateProfileBasic.OnDataPass,
        FragmentCreateProfileEdu.OnDataPassTwo1,FragmentCreateProfileEdu.OnDataPassTwo2,FragmentCreateProfileEdu.OnDataPassTwo3,
        FragmentCreateProfileExp.OnDataPassThree1,FragmentCreateProfileExp.OnDataPassThree2,FragmentCreateProfileExp.OnDataPassThree3,
        FragmentCreateProfileSkills.OnDataPassFour
{
    public final static String APP_PATH_SD_CARD = "/LeadersRating";

    int frag_flag=0;

    String cover_img_path, cover_img_name,login;

    String mode,other_phone;

    FragmentCreateProfileBasic one;
    FragmentCreateProfileEdu two;
    FragmentCreateProfileExp three;
    FragmentCreateProfileSkills four;

    User user=new User();

    DBHelper helper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        helper=new DBHelper(ActivityCreateProfile.this);
        db=helper.getWritableDatabase();

        Intent in=getIntent();
        login=in.getStringExtra("login");
        mode=in.getStringExtra("mode");

        Log.e("login frm create",""+login);

        if(mode.equals("user"))
        {
            FragmentManager fm = getFragmentManager();
            one= new FragmentCreateProfileBasic();
            fm.beginTransaction().replace(R.id.fragment_place, one, null).commit();
            if(login.equals("manual"))
                one.getData(in.getStringExtra("name"),in.getStringExtra("phone"),in.getStringExtra("email"),in.getStringExtra("username"),in.getStringExtra("password"),in.getStringExtra("work_name"),in.getStringExtra("address"),in.getStringExtra("country"),in.getIntExtra("category",0));
            else if(login.equals("fb"))
                one.getData(in.getStringExtra("name"),in.getStringExtra("phone"),in.getStringExtra("email"),in.getStringExtra("username"),in.getStringExtra("password"),in.getStringExtra("work_name"),in.getStringExtra("address"),in.getStringExtra("country"),in.getIntExtra("category",0),in.getStringExtra("picture_url"),in.getStringExtra("fb_id"),in.getStringExtra("cover_url"));//
            frag_flag++;
        }
        else if(mode.equals("other"))
        {
            FragmentManager fm = getFragmentManager();
            one= new FragmentCreateProfileBasic();
            fm.beginTransaction().replace(R.id.fragment_place, one, null).commit();
            frag_flag++;
        }

        themeNavAndStatusBar(ActivityCreateProfile.this);

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
    public void onBackPressed()
    {

    }

    public void selectFrag(View view)
    {
        if(frag_flag==0)
        {
            Log.e("","");
        }
        else if(frag_flag==1)
        {
            boolean stat=one.send_data();
            if(stat)
            {
                FragmentManager fm = getFragmentManager();
                two= new FragmentCreateProfileEdu();
                fm.beginTransaction().replace(R.id.fragment_place, two, null)         //.addToBackStack(null);  // uncomment this line if you want to be able to return to the prev. fragment with "back" button
                        .commit();
                frag_flag++;
            }

        }
        else if(frag_flag==2)
        {
            boolean stat=two.send_data();
            if(stat)
            {
                FragmentManager fm = getFragmentManager();
                three= new FragmentCreateProfileExp();
                //FragmentBoxOffice f = (FragmentBoxOffice) fm.findFragmentByTag(FragmentBoxOffice.TAG);
                fm.beginTransaction().replace(R.id.fragment_place, three, null)
                        //.addToBackStack(null);  // uncomment this line if you want to be able to return to the prev. fragment with "back" button
                        .commit();
                frag_flag++;
            }
        }
        else if(frag_flag==3)
        {
            boolean stat=three.send_data();
            FragmentManager fm = getFragmentManager();
            four= new FragmentCreateProfileSkills();
            fm.beginTransaction().replace(R.id.fragment_place, four, null)
                    .commit();
            frag_flag++;

        }
        else if(frag_flag==4)
        {
            boolean stat=four.send_data();
            if(stat)
            {
                if(mode.equals("user"))
                {
                    Intent in=new Intent(ActivityCreateProfile.this,ActivityHome.class);
                    in.putExtra("user",user);
                    //Log.e("name, profile pic,email",""+user.getName()+","+user.getPicture()+","+user.getPicture_path()+"..");
                    startActivity(in);
                    finish();
                }
                else if(mode.equals("other"))
                {
                    Intent in =new Intent(ActivityCreateProfile.this,ActivityProfile.class);
                    in.putExtra("phone",user.getPhone());
                    //Log.e("phone=>",""+resList.get(i).getPhone());
                    in.putExtra("mode","other");
                    startActivity(in);
                    finish();
                }

            }

        }
    }

    @Override
    public void onDataPass(String name, String phone, String email, String username,
                           String password, String work_name, String address,
                           String country, String category,String picture,String picture_path,
                           String cover_img_name,String cover_img_path)
    {
        user.setName(name);
        user.setPhone(phone);
        other_phone=phone;
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(password);
        user.setWork_name(work_name);
        user.setAddress(address);
        user.setCountry(country);
        user.setCategory(category);
        user.setPicture(picture);
        user.setPicture_path(picture_path);
        user.setCover_img(cover_img_name);
        user.setCover_path(cover_img_path);

        if(mode.equals("user"))
        {
            helper.insertUser(db,name,phone,email,username,password,category,work_name,
                    address,country,picture,picture_path, cover_img_name, cover_img_path);
            helper.insertUsers(db,name,phone,email,username,password,category,work_name,
                    address,country,picture,picture_path, cover_img_name, cover_img_path);
        }
        else if(mode.equals("other"))
        {

            helper.insertUsers(db,name,phone,email,username,password,category,work_name,
                    address,country,picture,picture_path, cover_img_name, cover_img_path);
        }

    }

    @Override
    public void onDataPassTwo1(String school, String degree, String specialization, String location, String extra, String from, String to)
    {
        user.addEdu_school(school);
        user.addEdu_school(degree);
        user.addEdu_school(specialization);
        user.addEdu_school(location);
        user.addEdu_school(extra);
        user.addEdu_school(from);
        user.addEdu_school(to);


        if(mode.equals("user"))
        {
            helper.insertEducation(db,other_phone,school,degree,specialization,
                    location,from,to,extra);
            helper.insertUsersEducation(db,other_phone,school,degree,specialization,
                    location,from,to,extra);
        }
        else if(mode.equals("other"))
        {
            Log.e("phone insert=>","null or not null="+other_phone+"....");
            helper.insertUsersEducation(db,other_phone,school,degree,specialization,
                    location,from,to,extra);
        }
    }

    @Override
    public void onDataPassTwo2(String school, String degree, String specialization, String location, String extra, String from, String to)
    {
        user.addEdu_school(school);
        user.addEdu_school(degree);
        user.addEdu_school(specialization);
        user.addEdu_school(location);
        user.addEdu_school(extra);
        user.addEdu_school(from);
        user.addEdu_school(to);
        if(mode.equals("user"))
        {
            helper.insertEducation(db,other_phone,school,degree,specialization,
                    location,from,to,extra);
            helper.insertUsersEducation(db,other_phone,school,degree,specialization,
                    location,from,to,extra);
        }
        else if(mode.equals("other"))
        {
            Log.e("phone insert=>","null or not null="+other_phone+"....");
            helper.insertUsersEducation(db,other_phone,school,degree,specialization,
                    location,from,to,extra);
        }
    }

    @Override
    public void onDataPassTwo3(String school, String degree, String specialization, String location, String extra, String from, String to)
    {
        user.addEdu_school(school);
        user.addEdu_school(degree);
        user.addEdu_school(specialization);
        user.addEdu_school(location);
        user.addEdu_school(extra);
        user.addEdu_school(from);
        user.addEdu_school(to);

        if(mode.equals("user"))
        {
            helper.insertEducation(db,other_phone,school,degree,specialization,
                    location,from,to,extra);
            helper.insertUsersEducation(db,other_phone,school,degree,specialization,
                    location,from,to,extra);
        }
        else if(mode.equals("other"))
        {
            Log.e("phone insert=>","null or not null="+other_phone+"....");
            helper.insertUsersEducation(db,other_phone,school,degree,specialization,
                    location,from,to,extra);
        }

    }

    @Override
    public void onDataPassThree1(String org, String loc, String post, String from, String to)
    {
        user.addExp_company(org);
        user.addExp_location(loc);
        user.addExp_post(post);
        user.addExp_from(from);
        user.addExp_to(to);

        if(mode.equals("user"))
        {
            helper.insertExp(db,other_phone,org,loc,from,to,post);
            helper.insertUsersExp(db,other_phone,org,loc,from,to,post);
        }
        else if(mode.equals("other"))
        {
            Log.e("phone insert=>","null or not null="+other_phone+"....");
            helper.insertUsersExp(db,other_phone,org,loc,from,to,post);
        }


    }

    @Override
    public void onDataPassThree2(String org, String loc, String post, String from, String to)
    {
        user.addExp_company(org);
        user.addExp_location(loc);
        user.addExp_post(post);
        user.addExp_from(from);
        user.addExp_to(to);

        if(mode.equals("user"))
        {
            helper.insertExp(db,other_phone,org,loc,from,to,post);
            helper.insertUsersExp(db,other_phone,org,loc,from,to,post);
        }
        else if(mode.equals("other"))
        {
            Log.e("phone insert=>","null or not null="+other_phone+"....");
            helper.insertUsersExp(db,other_phone,org,loc,from,to,post);
        }


    }

    @Override
    public void onDataPassThree3(String org, String loc, String post, String from, String to)
    {
        user.addExp_company(org);
        user.addExp_location(loc);
        user.addExp_post(post);
        user.addExp_from(from);
        user.addExp_to(to);

        if(mode.equals("user"))
        {
            helper.insertExp(db,other_phone,org,loc,from,to,post);
            helper.insertUsersExp(db,other_phone,org,loc,from,to,post);
        }
        else if(mode.equals("other"))
        {
            Log.e("phone insert=>","null or not null="+other_phone+"....");
            helper.insertUsersExp(db,other_phone,org,loc,from,to,post);
        }


    }

    @Override
    public void onDataPassFour(String[] skills)
    {
        for(int count=0;count<skills.length;count++)
            user.addskills(skills[count]);

        for(int count=0;count<skills.length;count++)
        {
            if(skills[count]!=null)
            {
                if(mode.equals("user"))
                {
                    helper.insertSkill(db,other_phone,skills[count]);
                    helper.insertUsersSkill(db,other_phone,skills[count]);
                }
                else if(mode.equals("other"))
                {
                    Log.e("phone insert=>","null or not null="+other_phone+"....");
                    helper.insertUsersSkill(db,other_phone,skills[count]);
                }

            }
        }

        Task task=new Task();

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

                nameValuePairs.add(new BasicNameValuePair("name", user.getName()));
                nameValuePairs.add(new BasicNameValuePair("phone", user.getPhone()));
                nameValuePairs.add(new BasicNameValuePair("email", user.getEmail()));
                nameValuePairs.add(new BasicNameValuePair("username", user.getUsername()));
                nameValuePairs.add(new BasicNameValuePair("password", user.getPassword()));
                nameValuePairs.add(new BasicNameValuePair("work_name", user.getWork_name()));
                nameValuePairs.add(new BasicNameValuePair("address", user.getAddress()));
                nameValuePairs.add(new BasicNameValuePair("country", user.getCountry()));
                nameValuePairs.add(new BasicNameValuePair("category", user.getCategory()));
                nameValuePairs.add(new BasicNameValuePair("picture", user.getPicture()));


                nameValuePairs.add(new BasicNameValuePair("edu_size", user.getEdu_school().size()+""));

                for(int count=0;count<user.getEdu_school().size();count++)
                {
                    nameValuePairs.add(new BasicNameValuePair("school"+(count+1), user.getEdu_school().get(count)));
                    nameValuePairs.add(new BasicNameValuePair("degree"+(count+1), user.getEdu_degree().get(count)));
                    nameValuePairs.add(new BasicNameValuePair("specialization"+(count+1), user.getEdu_specialization().get(count)));
                    nameValuePairs.add(new BasicNameValuePair("location"+(count+1), user.getEdu_location().get(count)));
                    nameValuePairs.add(new BasicNameValuePair("extra"+(count+1), user.getEdu_extra().get(count)));
                    nameValuePairs.add(new BasicNameValuePair("from"+(count+1), user.getEdu_from().get(count)));
                    nameValuePairs.add(new BasicNameValuePair("to"+(count+1), user.getEdu_to().get(count)));
                }

                nameValuePairs.add(new BasicNameValuePair("exp_size", user.getExp_company().size()+""));

                for(int count=0;count<user.getExp_company().size();count++)
                {
                    nameValuePairs.add(new BasicNameValuePair("company"+(count+1), user.getExp_company().get(count)));
                    nameValuePairs.add(new BasicNameValuePair("location"+(count+1), user.getExp_location().get(count)));
                    nameValuePairs.add(new BasicNameValuePair("post"+(count+1), user.getExp_post().get(count)));
                    nameValuePairs.add(new BasicNameValuePair("from"+(count+1), user.getExp_from().get(count)));
                    nameValuePairs.add(new BasicNameValuePair("to"+(count+1), user.getExp_to().get(count)));
                }

                nameValuePairs.add(new BasicNameValuePair("skill_size", user.getSkills().size()+""));

                for(int count=0;count<user.getSkills().size();count++)
                {
                    nameValuePairs.add(new BasicNameValuePair("skill"+(count+1), user.getSkills().get(count)));
                }

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



    public class image_download extends AsyncTask<String, Void, Bitmap>
    {
        @Override
        protected Bitmap doInBackground(String... strings)
        {
            final String file_url = strings[0];

            Bitmap bitmap = null;
            System.out.println("image " + file_url);

            try
            {
                URL url = new URL(file_url);
                URLConnection conn = url.openConnection();
                bitmap = BitmapFactory.decodeStream(conn.getInputStream());
            }
            catch (Exception ex)
            {
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bm)
        {
            ImageStorage imstrg = new ImageStorage();
            //bm = getRoundedBitmap(bm);
            //profile_edit_profile_pic.setImageBitmap(bm);
            imstrg.saveToSdCard(bm, user.getPhone()+"_cover"+ ".jpg");
            //pd.dismiss();
            //saveImageToExternalStorage(bm);
            super.onPostExecute(bm);
        }
    }

    public class ImageStorage
    {
        public String saveToSdCard(Bitmap bitmap, String filename)
        {

            String stored = null;

            File sdcard = Environment.getExternalStorageDirectory();

            File folder = new File(sdcard.getAbsoluteFile(), APP_PATH_SD_CARD);//the dot makes this directory hidden to the user
            cover_img_name = filename;
            cover_img_path = sdcard.getAbsoluteFile() + APP_PATH_SD_CARD + "/" + cover_img_name;
            folder.mkdir();
            File file = new File(folder.getAbsoluteFile(), filename);

            if (file.exists())
                return stored;

            try
            {
                FileOutputStream out = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                out.flush();
                out.close();
                stored = "success";
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return stored;
        }

        public File getImage(String imagename)
        {

            File mediaImage = null;
            try
            {
                String root = Environment.getExternalStorageDirectory().toString();
                File myDir = new File(root);
                if (!myDir.exists())
                    return null;

                mediaImage = new File(myDir.getPath() + "/.your_specific_directory/" + imagename);
            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return mediaImage;
        }

        public boolean checkifImageExists(String imagename)
        {
            /*Bitmap b = null;
            File file = imstrg.getImage("/" + imagename + ".jpg");
            String path = file.getAbsolutePath();

            if (path != null)
                b = BitmapFactory.decodeFile(path);

            if (b == null || b.equals("")) {
                return false;
            }*/
            return true;
        }
    }

}
