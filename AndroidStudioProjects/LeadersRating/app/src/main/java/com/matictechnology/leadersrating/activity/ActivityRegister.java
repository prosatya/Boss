package com.matictechnology.leadersrating.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.matictechnology.leadersrating.R;
import com.matictechnology.leadersrating.util.DBHelper;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ActivityRegister extends AppCompatActivity implements View.OnClickListener
{
    EditText reg_name,reg_phone,reg_email,reg_username,reg_password,reg_cpassword,reg_work_name,reg_address,reg_country;
    Spinner reg_category;
    Button register;
    ArrayList<String> cat_list;
    String email,name,image_url,fb_id,cover_url,login;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        reg_name=(EditText)findViewById(R.id.reg_name);
        reg_phone=(EditText)findViewById(R.id.reg_phone);
        reg_email=(EditText)findViewById(R.id.reg_email);
        reg_username=(EditText)findViewById(R.id.reg_username);
        reg_password=(EditText)findViewById(R.id.reg_password);
        reg_cpassword=(EditText)findViewById(R.id.reg_cpassword);
        reg_work_name=(EditText)findViewById(R.id.reg_work_name);
        reg_address=(EditText)findViewById(R.id.reg_address);
        reg_country=(EditText)findViewById(R.id.reg_country);

        reg_category=(Spinner)findViewById(R.id.reg_category);

        register=(Button)findViewById(R.id.register);

        cat_list=new ArrayList<>();
        cat_list.add("Select Category");
        cat_list.add("Business");
        cat_list.add("Politics");
        cat_list.add("Doctor");

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(ActivityRegister.this,android.R.layout.simple_spinner_dropdown_item,cat_list);

        reg_category.setAdapter(adapter);

        register.setOnClickListener(this);

        Intent in =getIntent();
        login=in.getStringExtra("login");
        Log.e("login from register=>",""+login);
        if(login.equals("fb"))
        {
            email=in.getStringExtra("email");
            name=in.getStringExtra("name");
            image_url =in.getStringExtra("picture");
            fb_id =in.getStringExtra("fb_id");
            cover_url=in.getStringExtra("cover_url");

            Log.e("from register","..");
            Log.e("email",""+email);
            Log.e("name",""+name);
            Log.e("profile",""+image_url);
            Log.e("id",""+fb_id);
            Log.e("cover",""+cover_url);
        }

        //Log.e("now checking","for variable equals");
        if(login.equals("fb"))
        {
            reg_name.setText(name);
            reg_email.setText(email);
        }
        else
        {
            reg_name.setText("Aditya Gupta");
            reg_email.setText("aditya@gmail.com");
        }
        enterData();
        themeNavAndStatusBar(ActivityRegister.this);
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

    public void enterData()
    {
        reg_phone.setText("9425902749");
        reg_username.setText("aditya");
        reg_password.setText("abc1234567890");
        reg_cpassword.setText("abc1234567890");
        reg_work_name.setText("aditya");
        reg_address.setText("sdkfn sdhn sdf;jk");
        reg_country.setText("india");
        reg_category.setSelection(1);
    }


    @Override
    public void onClick(View view)
    {
        if(view.getId()==R.id.register)
        {

            //ProgressDialog progressDialog;
            //progressDialog = MyCustomProgressDialog.ctor(ActivityRegister.this);

            //progressDialog.show();
            RelativeLayout rel=(RelativeLayout)findViewById(R.id.rel);

            Pattern simple_string = Pattern.compile("[^A-Za-z' ']");
            Pattern simple_number = Pattern.compile("[^0-9]");

            String EMAIL_REGEX = "[a-zA-Z0-9\\.]+@[a-zA-Z0-9\\-\\_\\.]+\\.[a-zA-Z0-9]{3}";
            String emailid=reg_email.getText().toString();

            boolean email_check = emailid.matches(EMAIL_REGEX);


            Matcher name_matcher = simple_string.matcher(reg_name.getText().toString());
            boolean name_flag = name_matcher.find();

            Matcher phone_matcher = simple_number.matcher(reg_phone.getText().toString());
            boolean phone_flag = phone_matcher.find();

            if (reg_name.getText().toString().equals("") || reg_name == null)
            {

                reg_name.requestFocus();
                Snackbar.make(rel,"Enter a Valid Name",Snackbar.LENGTH_LONG).show();
                //Toast.makeText(ActivityRegister.this, "Enter a Valid Name", Toast.LENGTH_SHORT).show();
            }
            else if (name_flag)
            {
                reg_name.requestFocus();
                Snackbar.make(rel,"Enter a Valid Name",Snackbar.LENGTH_LONG).show();
                //Toast.makeText(ActivityRegister.this, "Enter a Valid Name", Toast.LENGTH_SHORT).show();
            }
            else if (reg_phone.getText().toString().equals("") || reg_phone == null)
            {
                reg_phone.requestFocus();
                Snackbar.make(rel,"Enter a Valid Number..",Snackbar.LENGTH_LONG).show();
                //Toast.makeText(ActivityRegister.this, "Enter a Valid Number..", Toast.LENGTH_SHORT).show();
            }
            else if(phone_flag)
            {
                reg_phone.requestFocus();
                Snackbar.make(rel,"Enter a Valid Number..",Snackbar.LENGTH_LONG).show();
                //Toast.makeText(ActivityRegister.this, "Enter a Valid Number..", Toast.LENGTH_SHORT).show();
            }
            else if(reg_email.getText().toString().equals("")||reg_email==null)
            {
                reg_email.requestFocus();
                Snackbar.make(rel,"Enter a Valid Email ID..",Snackbar.LENGTH_LONG).show();
                //Toast.makeText(ActivityRegister.this, "Enter a Valid Email ID..", Toast.LENGTH_SHORT).show();
            }
            else if(!email_check)
            {
                reg_email.requestFocus();
                Snackbar.make(rel,"Enter a Valid Email ID..",Snackbar.LENGTH_LONG).show();
                //Toast.makeText(ActivityRegister.this, "Enter a Valid Email ID..", Toast.LENGTH_SHORT).show();
            }
            else if(reg_password.getText().toString().equals("")||reg_password==null)
            {
                reg_password.requestFocus();
                Snackbar.make(rel,"Enter a Valid Password!!",Snackbar.LENGTH_LONG).show();
                //Toast.makeText(ActivityRegister.this, "Enter a Valid Password!!", Toast.LENGTH_SHORT).show();
            }
            else if(!reg_password.getText().toString().equals(reg_cpassword.getText().toString()))
            {
                reg_cpassword.requestFocus();
                Snackbar.make(rel,"Both Password Should Match!!",Snackbar.LENGTH_LONG).show();
                //Toast.makeText(ActivityRegister.this, "Both Password Should Match!!", Toast.LENGTH_SHORT).show();
            }
            else if(reg_work_name.getText().toString().equals("")||reg_work_name==null)
            {
                reg_work_name.requestFocus();
                Snackbar.make(rel,"Enter Valid Work Name..",Snackbar.LENGTH_LONG).show();
            }
            else if(reg_address.getText().toString().equals("")||reg_address==null)
            {
                reg_address.requestFocus();
                Snackbar.make(rel,"Enter Valid Address..",Snackbar.LENGTH_LONG).show();
            }
            else if(reg_country.getText().toString().equals("")||reg_country==null)
            {
                reg_country.requestFocus();
                Snackbar.make(rel,"Enter Valid Country..",Snackbar.LENGTH_LONG).show();
            }
            else if(reg_category.getSelectedItem().toString().equals("Select Category"))
            {
                Snackbar.make(rel,"Select a Category..",Snackbar.LENGTH_LONG).show();
            }
            else
            {
                DBHelper helper=new DBHelper(ActivityRegister.this);
                SQLiteDatabase db=helper.getWritableDatabase();
                if(helper.get_id(db)!=null)
                {
                    Toast.makeText(ActivityRegister.this, "User already registered!!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent in=new Intent(ActivityRegister.this,ActivityCreateProfile.class);
                    in.putExtra("mode","user");
                    in.putExtra("name",""+reg_name.getText().toString());
                    in.putExtra("email",""+reg_email.getText().toString());
                    in.putExtra("username",""+reg_username.getText().toString());
                    in.putExtra("password",""+reg_password.getText().toString());
                    in.putExtra("category",reg_category.getSelectedItemPosition());
                    in.putExtra("work_name",""+reg_work_name.getText().toString());
                    in.putExtra("address",""+reg_address.getText().toString());
                    in.putExtra("country",""+reg_country.getText().toString());
                    in.putExtra("phone",""+reg_phone.getText().toString());
                    if(login.equals("fb"))
                    {
                        Log.e("login sending regfb=>",""+login);
                        in.putExtra("picture_url",""+image_url);//picture,fb_id,cover_url
                        in.putExtra("fb_id",fb_id);
                        in.putExtra("cover_url",""+cover_url);
                    }
                    else if(login.equals("manual"))
                    {
                        Log.e("login sending regmn=>",""+login);


                    }

                    in.putExtra("login",login);
                    startActivity(in);
                    finish();
                }

                //progressDialog.dismiss();

            }
        }
    }

    @Override
    protected void onStart()
    {
        super.onStart();

    }
}
