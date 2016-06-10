package com.matictechnology.leadersrating.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.matictechnology.leadersrating.R;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by matic on 25/5/16.
 */
public class FragmentCreateProfileExp extends Fragment
{
    OnDataPassThree1 dataPasser;
    OnDataPassThree2 dataPasser2;
    OnDataPassThree3 dataPasser3;

    LinearLayout lin_del_exp,lin_add_exp;
    LinearLayout lin_exp1,lin_exp2,lin_exp3;
    LinearLayout lin_exp_to1,lin_exp_current1,lin_exp_to2,lin_exp_current2,lin_exp_to3,lin_exp_current3;

    RadioGroup radioGroup_current1,radioGroup_current2,radioGroup_current3;

    ImageView edit_profile_exp_from_add1,edit_profile_exp_to_add1,edit_profile_exp_from_add2,edit_profile_exp_to_add2,edit_profile_exp_from_add3,edit_profile_exp_to_add3;

    EditText profile_edit_exp_from1,profile_edit_exp_to1,profile_edit_exp_from2,profile_edit_exp_to2,profile_edit_exp_from3,profile_edit_exp_to3;

    EditText profile_edit_exp_org1,profile_edit_exp_loc1,profile_edit_exp_post1;
    EditText profile_edit_exp_org2,profile_edit_exp_loc2,profile_edit_exp_post2;
    EditText profile_edit_exp_org3,profile_edit_exp_loc3,profile_edit_exp_post3;

    int lin_flag=-1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        LayoutInflater inflater1 = (LayoutInflater) getActivity().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View view=inflater1.inflate(R.layout.fragment_create_profile_exp, container, false);

        lin_exp1=(LinearLayout)view.findViewById(R.id.lin_exp1);
        lin_exp2=(LinearLayout)view.findViewById(R.id.lin_exp2);
        lin_exp3=(LinearLayout)view.findViewById(R.id.lin_exp3);

        profile_edit_exp_org1=(EditText)view.findViewById(R.id.profile_edit_exp_org1);
        profile_edit_exp_loc1=(EditText)view.findViewById(R.id.profile_edit_exp_loc1);
        profile_edit_exp_post1=(EditText)view.findViewById(R.id.profile_edit_exp_post1);

        profile_edit_exp_org2=(EditText)view.findViewById(R.id.profile_edit_exp_org2);
        profile_edit_exp_loc2=(EditText)view.findViewById(R.id.profile_edit_exp_loc2);
        profile_edit_exp_post2=(EditText)view.findViewById(R.id.profile_edit_exp_post2);

        profile_edit_exp_org3=(EditText)view.findViewById(R.id.profile_edit_exp_org3);
        profile_edit_exp_loc3=(EditText)view.findViewById(R.id.profile_edit_exp_loc3);
        profile_edit_exp_post3=(EditText)view.findViewById(R.id.profile_edit_exp_post3);

        profile_edit_exp_from1=(EditText)view.findViewById(R.id.profile_edit_exp_from1);
        profile_edit_exp_to1=(EditText)view.findViewById(R.id.profile_edit_exp_to1);

        edit_profile_exp_from_add1=(ImageView)view.findViewById(R.id.edit_profile_exp_from_add1);
        edit_profile_exp_to_add1=(ImageView)view.findViewById(R.id.edit_profile_exp_to_add1);

        profile_edit_exp_from2=(EditText)view.findViewById(R.id.profile_edit_exp_from2);
        profile_edit_exp_to2=(EditText)view.findViewById(R.id.profile_edit_exp_to2);

        edit_profile_exp_from_add2=(ImageView)view.findViewById(R.id.edit_profile_exp_from_add2);
        edit_profile_exp_to_add2=(ImageView)view.findViewById(R.id.edit_profile_exp_to_add2);

        profile_edit_exp_from3=(EditText)view.findViewById(R.id.profile_edit_exp_from3);
        profile_edit_exp_to3=(EditText)view.findViewById(R.id.profile_edit_exp_to3);

        edit_profile_exp_from_add3=(ImageView)view.findViewById(R.id.edit_profile_exp_from_add3);
        edit_profile_exp_to_add3=(ImageView)view.findViewById(R.id.edit_profile_exp_to_add3);

        lin_del_exp=(LinearLayout)view.findViewById(R.id.lin_del_exp);
        lin_add_exp=(LinearLayout)view.findViewById(R.id.lin_add_exp);

        lin_exp_to1=(LinearLayout)view.findViewById(R.id.lin_exp_to1);
        lin_exp_current1=(LinearLayout)view.findViewById(R.id.lin_exp_current1);

        radioGroup_current1=(RadioGroup)view.findViewById(R.id.profile_edit_exp_current1);

        lin_exp_to2=(LinearLayout)view.findViewById(R.id.lin_exp_to2);
        lin_exp_current2=(LinearLayout)view.findViewById(R.id.lin_exp_current2);

        radioGroup_current2=(RadioGroup)view.findViewById(R.id.profile_edit_exp_current2);

        lin_exp_to3=(LinearLayout)view.findViewById(R.id.lin_exp_to3);
        lin_exp_current3=(LinearLayout)view.findViewById(R.id.lin_exp_current3);

        radioGroup_current3=(RadioGroup)view.findViewById(R.id.profile_edit_exp_current3);

        lin_del_exp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(lin_flag==0)
                {
                    lin_exp1.setVisibility(View.GONE);

                    lin_del_exp.setVisibility(View.GONE);
                    lin_flag--;
                }
                if(lin_flag==1)
                {
                    lin_exp2.setVisibility(View.GONE);
                    lin_flag--;
                }
                if(lin_flag>=2)
                {
                    lin_exp3.setVisibility(View.GONE);
                    lin_flag=1;
                }
            }
        });

        lin_add_exp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                lin_del_exp.setVisibility(View.VISIBLE);
                lin_flag++;
                if (lin_flag==0)
                {
                    lin_exp1.setVisibility(View.VISIBLE);
                }
                if (lin_flag==1)
                {
                    lin_exp2.setVisibility(View.VISIBLE);
                }
                if (lin_flag==2)
                {
                    lin_exp3.setVisibility(View.VISIBLE);
                }
                else
                    Log.e("clicked","after on click "+lin_flag);
            }
        });

        lin_exp_to1.setVisibility(View.GONE);
        lin_exp_to2.setVisibility(View.GONE);
        lin_exp_to3.setVisibility(View.GONE);

        radioGroup_current1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i)
            {
                if(i==R.id.profile_edit_exp_current_no1)
                {
                    lin_exp_to1.setVisibility(View.VISIBLE);
                    lin_exp_current1.setVisibility(View.GONE);
                }
            }
        });

        radioGroup_current2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i)
            {
                if(i==R.id.profile_edit_exp_current_no2)
                {
                    lin_exp_to2.setVisibility(View.VISIBLE);
                    lin_exp_current2.setVisibility(View.GONE);
                }
            }
        });

        radioGroup_current3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i)
            {
                if(i==R.id.profile_edit_exp_current_no3)
                {
                    lin_exp_to3.setVisibility(View.VISIBLE);
                    lin_exp_current3.setVisibility(View.GONE);
                }
            }
        });

        edit_profile_exp_from_add1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dpd = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener()
                {
                    String exp_from_month="";
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
                    {
                        // Display Selected date in textbox

                        switch (monthOfYear + 1)
                        {
                            case 1: {
                                exp_from_month = "Jan";
                                break;
                            }
                            case 2: {
                                exp_from_month = "Feb";
                                break;
                            }
                            case 3: {
                                exp_from_month = "Mar";
                                break;
                            }
                            case 4: {
                                exp_from_month = "Apr";
                                break;
                            }
                            case 5: {
                                exp_from_month = "May";
                                break;
                            }
                            case 6: {
                                exp_from_month = "Jun";
                                break;
                            }
                            case 7: {
                                exp_from_month = "Jul";
                                break;
                            }
                            case 8: {
                                exp_from_month = "Aug";
                                break;
                            }
                            case 9: {
                                exp_from_month = "Sept";
                                break;
                            }
                            case 10: {
                                exp_from_month = "Oct";
                                break;
                            }
                            case 11: {
                                exp_from_month = "Nov";
                                break;
                            }
                            case 12: {
                                exp_from_month = "Dec";
                                break;
                            }
                        }
                        String from=exp_from_month+","+year;
                        profile_edit_exp_from1.setText(from);
                    }
                }, mYear, mMonth, mDay);
                dpd.show();
            }
        });


        edit_profile_exp_to_add1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                // Launch Date Picker Dialog


                DatePickerDialog dpd = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener()
                {
                    String exp_from_month="";
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
                    {
                        switch (monthOfYear + 1)
                        {
                            case 1: {
                                exp_from_month = "Jan";
                                break;
                            }
                            case 2: {
                                exp_from_month = "Feb";
                                break;
                            }
                            case 3: {
                                exp_from_month = "Mar";
                                break;
                            }
                            case 4: {
                                exp_from_month = "Apr";
                                break;
                            }
                            case 5: {
                                exp_from_month = "May";
                                break;
                            }
                            case 6: {
                                exp_from_month = "Jun";
                                break;
                            }
                            case 7: {
                                exp_from_month = "Jul";
                                break;
                            }
                            case 8: {
                                exp_from_month = "Aug";
                                break;
                            }
                            case 9: {
                                exp_from_month = "Sept";
                                break;
                            }
                            case 10: {
                                exp_from_month = "Oct";
                                break;
                            }
                            case 11: {
                                exp_from_month = "Nov";
                                break;
                            }
                            case 12: {
                                exp_from_month = "Dec";
                                break;
                            }
                        }
                        String from=exp_from_month+","+year;
                        profile_edit_exp_to1.setText(from);
                    }
                }, mYear, mMonth, mDay);
                dpd.show();
            }
        });

        edit_profile_exp_from_add2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dpd = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener()
                {
                    String exp_from_month="";
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
                    {
                        switch (monthOfYear + 1)
                        {
                            case 1: {
                                exp_from_month = "Jan";
                                break;
                            }
                            case 2: {
                                exp_from_month = "Feb";
                                break;
                            }
                            case 3: {
                                exp_from_month = "Mar";
                                break;
                            }
                            case 4: {
                                exp_from_month = "Apr";
                                break;
                            }
                            case 5: {
                                exp_from_month = "May";
                                break;
                            }
                            case 6: {
                                exp_from_month = "Jun";
                                break;
                            }
                            case 7: {
                                exp_from_month = "Jul";
                                break;
                            }
                            case 8: {
                                exp_from_month = "Aug";
                                break;
                            }
                            case 9: {
                                exp_from_month = "Sept";
                                break;
                            }
                            case 10: {
                                exp_from_month = "Oct";
                                break;
                            }
                            case 11: {
                                exp_from_month = "Nov";
                                break;
                            }
                            case 12: {
                                exp_from_month = "Dec";
                                break;
                            }
                        }
                        String from=exp_from_month+","+year;
                        profile_edit_exp_from2.setText(from);
                    }
                }, mYear, mMonth, mDay);
                dpd.show();
            }
        });


        edit_profile_exp_to_add2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                // Launch Date Picker Dialog


                DatePickerDialog dpd = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener()
                {
                    String exp_from_month="";
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
                    {
                        switch (monthOfYear + 1)
                        {
                            case 1: {
                                exp_from_month = "Jan";
                                break;
                            }
                            case 2: {
                                exp_from_month = "Feb";
                                break;
                            }
                            case 3: {
                                exp_from_month = "Mar";
                                break;
                            }
                            case 4: {
                                exp_from_month = "Apr";
                                break;
                            }
                            case 5: {
                                exp_from_month = "May";
                                break;
                            }
                            case 6: {
                                exp_from_month = "Jun";
                                break;
                            }
                            case 7: {
                                exp_from_month = "Jul";
                                break;
                            }
                            case 8: {
                                exp_from_month = "Aug";
                                break;
                            }
                            case 9: {
                                exp_from_month = "Sept";
                                break;
                            }
                            case 10: {
                                exp_from_month = "Oct";
                                break;
                            }
                            case 11: {
                                exp_from_month = "Nov";
                                break;
                            }
                            case 12: {
                                exp_from_month = "Dec";
                                break;
                            }
                        }
                        String from=exp_from_month+","+year;
                        profile_edit_exp_to2.setText(from);
                    }
                }, mYear, mMonth, mDay);
                dpd.show();
            }
        });

        edit_profile_exp_from_add3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dpd = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener()
                {
                    String exp_from_month="";
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
                    {
                        switch (monthOfYear + 1)
                        {
                            case 1: {
                                exp_from_month = "Jan";
                                break;
                            }
                            case 2: {
                                exp_from_month = "Feb";
                                break;
                            }
                            case 3: {
                                exp_from_month = "Mar";
                                break;
                            }
                            case 4: {
                                exp_from_month = "Apr";
                                break;
                            }
                            case 5: {
                                exp_from_month = "May";
                                break;
                            }
                            case 6: {
                                exp_from_month = "Jun";
                                break;
                            }
                            case 7: {
                                exp_from_month = "Jul";
                                break;
                            }
                            case 8: {
                                exp_from_month = "Aug";
                                break;
                            }
                            case 9: {
                                exp_from_month = "Sept";
                                break;
                            }
                            case 10: {
                                exp_from_month = "Oct";
                                break;
                            }
                            case 11: {
                                exp_from_month = "Nov";
                                break;
                            }
                            case 12: {
                                exp_from_month = "Dec";
                                break;
                            }
                        }
                        String from=exp_from_month+","+year;
                        profile_edit_exp_from3.setText(from);
                    }
                }, mYear, mMonth, mDay);
                dpd.show();
            }
        });


        edit_profile_exp_to_add3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                // Launch Date Picker Dialog


                DatePickerDialog dpd = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener()
                {
                    String exp_from_month="";
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
                    {
                        switch (monthOfYear + 1)
                        {
                            case 1: {
                                exp_from_month = "Jan";
                                break;
                            }
                            case 2: {
                                exp_from_month = "Feb";
                                break;
                            }
                            case 3: {
                                exp_from_month = "Mar";
                                break;
                            }
                            case 4: {
                                exp_from_month = "Apr";
                                break;
                            }
                            case 5: {
                                exp_from_month = "May";
                                break;
                            }
                            case 6: {
                                exp_from_month = "Jun";
                                break;
                            }
                            case 7: {
                                exp_from_month = "Jul";
                                break;
                            }
                            case 8: {
                                exp_from_month = "Aug";
                                break;
                            }
                            case 9: {
                                exp_from_month = "Sept";
                                break;
                            }
                            case 10: {
                                exp_from_month = "Oct";
                                break;
                            }
                            case 11: {
                                exp_from_month = "Nov";
                                break;
                            }
                            case 12: {
                                exp_from_month = "Dec";
                                break;
                            }
                        }
                        String from=exp_from_month+","+year;
                        profile_edit_exp_to3.setText(from);
                    }
                }, mYear, mMonth, mDay);
                dpd.show();
            }
        });

        enterData();

        return view;
    }

    public void enterData()
    {
        profile_edit_exp_org1.setText("Avalanche Infotech");
        profile_edit_exp_loc1.setText("Indore");
        profile_edit_exp_post1.setText("Android Developer");

        profile_edit_exp_org2.setText("Matic Technology");
        profile_edit_exp_loc2.setText("indore");
        profile_edit_exp_post2.setText("Android Developer");

        profile_edit_exp_org3.setText("Info beans");
        profile_edit_exp_loc3.setText("Indore");
        profile_edit_exp_post3.setText("Android Developer");

        profile_edit_exp_from1.setText("Nov,2015");
        profile_edit_exp_to1.setText("Mar,2016");

        radioGroup_current1.check(R.id.profile_edit_exp_current_no1);

        profile_edit_exp_from2.setText("Mar,2016");
        profile_edit_exp_to2.setText("Mar,2017");

        radioGroup_current2.check(R.id.profile_edit_exp_current_no2);

        profile_edit_exp_from3.setText("Mar,2017");
        profile_edit_exp_to3.setText("Mar,2018");

        radioGroup_current3.check(R.id.profile_edit_exp_current_yes3);

    }


    public interface OnDataPassThree1
    {
        public void onDataPassThree1(String org, String loc, String post, String from, String to);
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        dataPasser = (OnDataPassThree1) activity;
        dataPasser2 = (OnDataPassThree2) activity;
        dataPasser3 = (OnDataPassThree3) activity;
    }

    public interface OnDataPassThree2
    {
        public void onDataPassThree2(String org, String loc, String post, String from, String to);
    }

    public interface OnDataPassThree3
    {
        public void onDataPassThree3(String org, String loc, String post, String from, String to);
    }


    public boolean send_data()
    {
        if(lin_exp3.getVisibility()==View.VISIBLE)
        {
            Pattern simple_string = Pattern.compile("[A-Za-z' ']");

            Matcher org_matcher3,loc_matcher3,post_matcher3;

            boolean org_flag3,loc_flag3,post_flag3;

            org_matcher3 = simple_string.matcher(profile_edit_exp_org3.getText().toString());
            loc_matcher3= simple_string.matcher(profile_edit_exp_loc3.getText().toString());
            post_matcher3= simple_string.matcher(profile_edit_exp_post3.getText().toString());

            org_flag3=org_matcher3.find();
            loc_flag3=loc_matcher3.find();
            post_flag3=post_matcher3.find();

            Matcher org_matcher2,loc_matcher2,post_matcher2;

            boolean org_flag2,loc_flag2,post_flag2;

            org_matcher2 = simple_string.matcher(profile_edit_exp_org2.getText().toString());
            loc_matcher2= simple_string.matcher(profile_edit_exp_loc2.getText().toString());
            post_matcher2= simple_string.matcher(profile_edit_exp_post2.getText().toString());

            org_flag2=org_matcher2.find();
            loc_flag2=loc_matcher2.find();
            post_flag2=post_matcher2.find();

            Matcher org_matcher1,loc_matcher1,post_matcher1;

            boolean org_flag1,loc_flag1,post_flag1;

            org_matcher1 = simple_string.matcher(profile_edit_exp_org1.getText().toString());
            loc_matcher1= simple_string.matcher(profile_edit_exp_loc1.getText().toString());
            post_matcher1= simple_string.matcher(profile_edit_exp_post1.getText().toString());

            org_flag1=org_matcher1.find();
            loc_flag1=loc_matcher1.find();
            post_flag1=post_matcher1.find();

            if(profile_edit_exp_org3.getText().toString().equals(""))
            {
                Toast.makeText(getActivity(),"School/College name is required field...",Toast.LENGTH_LONG).show();
            }
            else if(!org_flag3)
            {
                Toast.makeText(getActivity(),"Enter valid school/college name...",Toast.LENGTH_LONG).show();
            }
            else if(profile_edit_exp_loc3.getText().toString().equals(""))
            {
                Toast.makeText(getActivity(),"Degree name is required field...",Toast.LENGTH_LONG).show();
            }
            else if(!loc_flag3)
            {
                Toast.makeText(getActivity(),"Enter valid Degree name...",Toast.LENGTH_LONG).show();
            }
            else if(profile_edit_exp_post3.getText().toString().equals(""))
            {
                Toast.makeText(getActivity(),"Specialization name is required field...",Toast.LENGTH_LONG).show();
            }
            else if(!post_flag3)
            {
                Toast.makeText(getActivity(),"Enter valid Specialization name...",Toast.LENGTH_LONG).show();
            }
            else if(profile_edit_exp_org2.getText().toString().equals(""))
            {
                Toast.makeText(getActivity(),"School/College name is required field...",Toast.LENGTH_LONG).show();
            }
            else if(!org_flag2)
            {
                Toast.makeText(getActivity(),"Enter valid school/college name...",Toast.LENGTH_LONG).show();
            }
            else if(profile_edit_exp_loc2.getText().toString().equals(""))
            {
                Toast.makeText(getActivity(),"Degree name is required field...",Toast.LENGTH_LONG).show();
            }
            else if(!loc_flag2)
            {
                Toast.makeText(getActivity(),"Enter valid Degree name...",Toast.LENGTH_LONG).show();
            }
            else if(profile_edit_exp_post2.getText().toString().equals(""))
            {
                Toast.makeText(getActivity(),"Specialization name is required field...",Toast.LENGTH_LONG).show();
            }
            else if(!post_flag2)
            {
                Toast.makeText(getActivity(),"Enter valid Specialization name...",Toast.LENGTH_LONG).show();
            }
            else if(profile_edit_exp_org1.getText().toString().equals(""))
            {
                Toast.makeText(getActivity(),"School/College name is required field...",Toast.LENGTH_LONG).show();
            }
            else if(!org_flag1)
            {
                Toast.makeText(getActivity(),"Enter valid school/college name...",Toast.LENGTH_LONG).show();
            }
            else if(profile_edit_exp_loc1.getText().toString().equals(""))
            {
                Toast.makeText(getActivity(),"Degree name is required field...",Toast.LENGTH_LONG).show();
            }
            else if(!loc_flag1)
            {
                Toast.makeText(getActivity(),"Enter valid Degree name...",Toast.LENGTH_LONG).show();
            }
            else if(profile_edit_exp_post1.getText().toString().equals(""))
            {
                Toast.makeText(getActivity(),"Specialization name is required field...",Toast.LENGTH_LONG).show();
            }
            else if(!post_flag1)
            {
                Toast.makeText(getActivity(),"Enter valid Specialization name...",Toast.LENGTH_LONG).show();
            }
            else
            {
                //lin_exp_to1,lin_exp_current1,lin_exp_to2,lin_exp_current2,lin_exp_to3,lin_exp_current3
                if(lin_exp_to3.getVisibility()==View.VISIBLE)
                {
                    passDataThree3(profile_edit_exp_org3.getText().toString(),profile_edit_exp_loc3.getText().toString(),
                            profile_edit_exp_post3.getText().toString(),profile_edit_exp_from3.getText().toString(),
                            profile_edit_exp_to3.getText().toString());
                }
                else
                {
                    passDataThree3(profile_edit_exp_org3.getText().toString(),profile_edit_exp_loc3.getText().toString(),
                            profile_edit_exp_post3.getText().toString(),profile_edit_exp_from3.getText().toString(),
                            "Current Work");
                }
                if(lin_exp_to2.getVisibility()==View.VISIBLE)
                {
                    passDataThree2(profile_edit_exp_org2.getText().toString(),profile_edit_exp_loc2.getText().toString(),
                            profile_edit_exp_post2.getText().toString(),profile_edit_exp_from2.getText().toString(),
                            profile_edit_exp_to2.getText().toString());
                }
                else
                {
                    passDataThree2(profile_edit_exp_org2.getText().toString(),profile_edit_exp_loc2.getText().toString(),
                            profile_edit_exp_post2.getText().toString(),profile_edit_exp_from2.getText().toString(),
                            "Current Work");
                }

                if(lin_exp_to1.getVisibility()==View.VISIBLE)
                {
                    passDataThree1(profile_edit_exp_org1.getText().toString(),profile_edit_exp_loc1.getText().toString(),
                            profile_edit_exp_post1.getText().toString(),profile_edit_exp_from1.getText().toString(),
                            profile_edit_exp_to1.getText().toString());
                }
                else
                {
                    passDataThree1(profile_edit_exp_org1.getText().toString(),profile_edit_exp_loc1.getText().toString(),
                            profile_edit_exp_post1.getText().toString(),profile_edit_exp_from1.getText().toString(),
                            "Current Work");
                }
                return true;
            }
        }
        else if(lin_exp2.getVisibility()==View.VISIBLE)
        {
            Pattern simple_string = Pattern.compile("[A-Za-z' ']");

            Matcher org_matcher2,loc_matcher2,post_matcher2;

            boolean org_flag2,loc_flag2,post_flag2;

            org_matcher2 = simple_string.matcher(profile_edit_exp_org2.getText().toString());
            loc_matcher2= simple_string.matcher(profile_edit_exp_loc2.getText().toString());
            post_matcher2= simple_string.matcher(profile_edit_exp_post2.getText().toString());

            org_flag2=org_matcher2.find();
            loc_flag2=loc_matcher2.find();
            post_flag2=post_matcher2.find();

            Matcher org_matcher1,loc_matcher1,post_matcher1;

            boolean org_flag1,loc_flag1,post_flag1;

            org_matcher1 = simple_string.matcher(profile_edit_exp_org1.getText().toString());
            loc_matcher1= simple_string.matcher(profile_edit_exp_loc1.getText().toString());
            post_matcher1= simple_string.matcher(profile_edit_exp_post1.getText().toString());

            org_flag1=org_matcher1.find();
            loc_flag1=loc_matcher1.find();
            post_flag1=post_matcher1.find();

            if(profile_edit_exp_org2.getText().toString().equals(""))
            {
                Toast.makeText(getActivity(),"School/College name is required field...",Toast.LENGTH_LONG).show();
            }
            else if(!org_flag2)
            {
                Toast.makeText(getActivity(),"Enter valid school/college name...",Toast.LENGTH_LONG).show();
            }
            else if(profile_edit_exp_loc2.getText().toString().equals(""))
            {
                Toast.makeText(getActivity(),"Degree name is required field...",Toast.LENGTH_LONG).show();
            }
            else if(!loc_flag2)
            {
                Toast.makeText(getActivity(),"Enter valid Degree name...",Toast.LENGTH_LONG).show();
            }
            else if(profile_edit_exp_post2.getText().toString().equals(""))
            {
                Toast.makeText(getActivity(),"Specialization name is required field...",Toast.LENGTH_LONG).show();
            }
            else if(!post_flag2)
            {
                Toast.makeText(getActivity(),"Enter valid Specialization name...",Toast.LENGTH_LONG).show();
            }
            else if(profile_edit_exp_org1.getText().toString().equals(""))
            {
                Toast.makeText(getActivity(),"School/College name is required field...",Toast.LENGTH_LONG).show();
            }
            else if(!org_flag1)
            {
                Toast.makeText(getActivity(),"Enter valid school/college name...",Toast.LENGTH_LONG).show();
            }
            else if(profile_edit_exp_loc1.getText().toString().equals(""))
            {
                Toast.makeText(getActivity(),"Degree name is required field...",Toast.LENGTH_LONG).show();
            }
            else if(!loc_flag1)
            {
                Toast.makeText(getActivity(),"Enter valid Degree name...",Toast.LENGTH_LONG).show();
            }
            else if(profile_edit_exp_post1.getText().toString().equals(""))
            {
                Toast.makeText(getActivity(),"Specialization name is required field...",Toast.LENGTH_LONG).show();
            }
            else if(!post_flag1)
            {
                Toast.makeText(getActivity(),"Enter valid Specialization name...",Toast.LENGTH_LONG).show();
            }
            else
            {
                if(lin_exp_to2.getVisibility()==View.VISIBLE)
                {
                    passDataThree2(profile_edit_exp_org2.getText().toString(),profile_edit_exp_loc2.getText().toString(),
                            profile_edit_exp_post2.getText().toString(),profile_edit_exp_from2.getText().toString(),
                            profile_edit_exp_to2.getText().toString());
                }
                else
                {
                    passDataThree2(profile_edit_exp_org2.getText().toString(),profile_edit_exp_loc2.getText().toString(),
                            profile_edit_exp_post2.getText().toString(),profile_edit_exp_from2.getText().toString(),
                            "Current Work");
                }

                if(lin_exp_to1.getVisibility()==View.VISIBLE)
                {
                    passDataThree1(profile_edit_exp_org1.getText().toString(),profile_edit_exp_loc1.getText().toString(),
                            profile_edit_exp_post1.getText().toString(),profile_edit_exp_from1.getText().toString(),
                            profile_edit_exp_to1.getText().toString());
                }
                else
                {
                    passDataThree1(profile_edit_exp_org1.getText().toString(),profile_edit_exp_loc1.getText().toString(),
                            profile_edit_exp_post1.getText().toString(),profile_edit_exp_from1.getText().toString(),
                            "Current Work");
                }
                return true;
            }
        }
        else if(lin_exp1.getVisibility()==View.VISIBLE)
        {
            Pattern simple_string = Pattern.compile("[A-Za-z' ']");

            Matcher org_matcher1,loc_matcher1,post_matcher1;

            boolean org_flag1,loc_flag1,post_flag1;

            org_matcher1 = simple_string.matcher(profile_edit_exp_org1.getText().toString());
            loc_matcher1= simple_string.matcher(profile_edit_exp_loc1.getText().toString());
            post_matcher1= simple_string.matcher(profile_edit_exp_post1.getText().toString());

            org_flag1=org_matcher1.find();
            loc_flag1=loc_matcher1.find();
            post_flag1=post_matcher1.find();

            if(profile_edit_exp_org1.getText().toString().equals(""))
            {
                Toast.makeText(getActivity(),"School/College name is required field...",Toast.LENGTH_LONG).show();
            }
            else if(!org_flag1)
            {
                Toast.makeText(getActivity(),"Enter valid school/college name...",Toast.LENGTH_LONG).show();
            }
            else if(profile_edit_exp_loc1.getText().toString().equals(""))
            {
                Toast.makeText(getActivity(),"Degree name is required field...",Toast.LENGTH_LONG).show();
            }
            else if(!loc_flag1)
            {
                Toast.makeText(getActivity(),"Enter valid Degree name...",Toast.LENGTH_LONG).show();
            }
            else if(profile_edit_exp_post1.getText().toString().equals(""))
            {
                Toast.makeText(getActivity(),"Specialization name is required field...",Toast.LENGTH_LONG).show();
            }
            else if(!post_flag1)
            {
                Toast.makeText(getActivity(),"Enter valid Specialization name...",Toast.LENGTH_LONG).show();
            }
            else
            {
                if(lin_exp_to1.getVisibility()==View.VISIBLE)
                {
                    passDataThree1(profile_edit_exp_org1.getText().toString(),profile_edit_exp_loc1.getText().toString(),
                            profile_edit_exp_post1.getText().toString(),profile_edit_exp_from1.getText().toString(),
                            profile_edit_exp_to1.getText().toString());
                }
                else
                {
                    passDataThree1(profile_edit_exp_org1.getText().toString(),profile_edit_exp_loc1.getText().toString(),
                            profile_edit_exp_post1.getText().toString(),profile_edit_exp_from1.getText().toString(),
                            "Current Work");
                }
                return true;
            }
        }
        return false;

    }

    public void passDataThree1(String org,String loc, String post,String from,String to)
    {
        dataPasser.onDataPassThree1(org,loc, post,from,to);
    }

    public void passDataThree2(String org,String loc, String post,String from,String to)
    {
        dataPasser.onDataPassThree1(org,loc, post,from,to);
    }

    public void passDataThree3(String org,String loc, String post,String from,String to)
    {
        dataPasser.onDataPassThree1(org,loc, post,from,to);
    }
}
