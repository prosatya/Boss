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
import android.widget.Toast;

import com.matictechnology.leadersrating.R;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by matic on 25/5/16.
 */
public class FragmentCreateProfileEdu extends Fragment
{
    LinearLayout lin_add_edu,lin_edu1,lin_edu2,lin_edu3,lin_del_edu;

    int lin_flag=-1;

    OnDataPassTwo1 dataPasser;
    OnDataPassTwo2 dataPasser2;
    OnDataPassTwo3 dataPasser3;

    EditText profile_edit_school,profile_edit_degree,profile_edit_specialization,profile_edit_school_place,profile_edit_extra;
    EditText profile_edit_school2,profile_edit_degree2,profile_edit_specialization2,profile_edit_school_place2,profile_edit_extra2;
    EditText profile_edit_school3,profile_edit_degree3,profile_edit_specialization3,profile_edit_school_place3,profile_edit_extra3;

    ImageView edit_profile_edu_from_add,edit_profile_edu_to_add,edit_profile_edu_from_add2,edit_profile_edu_to_add2,edit_profile_edu_from_add3,edit_profile_edu_to_add3;
    EditText profile_edit_edu_from,profile_edit_edu_to,profile_edit_edu_from2,profile_edit_edu_to2,profile_edit_edu_from3,profile_edit_edu_to3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        LayoutInflater inflater1 = (LayoutInflater) getActivity().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View view=inflater1.inflate(R.layout.fragment_create_profile_edu, container, false);

        lin_edu1=(LinearLayout)view.findViewById(R.id.lin_edu1);
        lin_edu2=(LinearLayout)view.findViewById(R.id.lin_edu2);
        lin_edu3=(LinearLayout)view.findViewById(R.id.lin_edu3);

        profile_edit_school=(EditText)view.findViewById(R.id.profile_edit_school);
        profile_edit_degree=(EditText)view.findViewById(R.id.profile_edit_degree);
        profile_edit_specialization=(EditText)view.findViewById(R.id.profile_edit_specialization);
        profile_edit_school_place=(EditText)view.findViewById(R.id.profile_edit_school_place);
        profile_edit_extra=(EditText)view.findViewById(R.id.profile_edit_extra);

        edit_profile_edu_from_add=(ImageView)view.findViewById(R.id.edit_profile_edu_from_add);
        edit_profile_edu_to_add=(ImageView)view.findViewById(R.id.edit_profile_edu_to_add);
        profile_edit_edu_from=(EditText) view.findViewById(R.id.profile_edit_edu_from);
        profile_edit_edu_to=(EditText) view.findViewById(R.id.profile_edit_edu_to);

        profile_edit_school2=(EditText)view.findViewById(R.id.profile_edit_school2);
        profile_edit_degree2=(EditText)view.findViewById(R.id.profile_edit_degree2);
        profile_edit_specialization2=(EditText)view.findViewById(R.id.profile_edit_specialization2);
        profile_edit_school_place2=(EditText)view.findViewById(R.id.profile_edit_school_place2);
        profile_edit_extra2=(EditText)view.findViewById(R.id.profile_edit_extra2);

        edit_profile_edu_from_add2=(ImageView)view.findViewById(R.id.edit_profile_edu_from_add2);
        edit_profile_edu_to_add2=(ImageView)view.findViewById(R.id.edit_profile_edu_to_add2);
        profile_edit_edu_from2=(EditText) view.findViewById(R.id.profile_edit_edu_from2);
        profile_edit_edu_to2=(EditText) view.findViewById(R.id.profile_edit_edu_to2);

        profile_edit_school3=(EditText)view.findViewById(R.id.profile_edit_school3);
        profile_edit_degree3=(EditText)view.findViewById(R.id.profile_edit_degree3);
        profile_edit_specialization3=(EditText)view.findViewById(R.id.profile_edit_specialization3);
        profile_edit_school_place3=(EditText)view.findViewById(R.id.profile_edit_school_place3);
        profile_edit_extra3=(EditText)view.findViewById(R.id.profile_edit_extra3);

        edit_profile_edu_from_add3=(ImageView)view.findViewById(R.id.edit_profile_edu_from_add3);
        edit_profile_edu_to_add3=(ImageView)view.findViewById(R.id.edit_profile_edu_to_add3);
        profile_edit_edu_from3=(EditText) view.findViewById(R.id.profile_edit_edu_from3);
        profile_edit_edu_to3=(EditText) view.findViewById(R.id.profile_edit_edu_to3);

        profile_edit_edu_from=(EditText)view.findViewById(R.id.profile_edit_edu_from);
        profile_edit_edu_to=(EditText)view.findViewById(R.id.profile_edit_edu_to);

        edit_profile_edu_from_add=(ImageView)view.findViewById(R.id.edit_profile_edu_from_add);
        edit_profile_edu_to_add=(ImageView)view.findViewById(R.id.edit_profile_edu_to_add);

        profile_edit_edu_from2=(EditText)view.findViewById(R.id.profile_edit_edu_from2);
        profile_edit_edu_to2=(EditText)view.findViewById(R.id.profile_edit_edu_to2);

        edit_profile_edu_from_add2=(ImageView)view.findViewById(R.id.edit_profile_edu_from_add2);
        edit_profile_edu_to_add2=(ImageView)view.findViewById(R.id.edit_profile_edu_to_add2);

        profile_edit_edu_from3=(EditText)view.findViewById(R.id.profile_edit_edu_from3);
        profile_edit_edu_to3=(EditText)view.findViewById(R.id.profile_edit_edu_to3);

        edit_profile_edu_from_add3=(ImageView)view.findViewById(R.id.edit_profile_edu_from_add3);
        edit_profile_edu_to_add3=(ImageView)view.findViewById(R.id.edit_profile_edu_to_add3);

        lin_del_edu=(LinearLayout)view.findViewById(R.id.lin_del_edu);
        lin_add_edu=(LinearLayout)view.findViewById(R.id.lin_add_edu);


        lin_del_edu.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(lin_flag==0)
                {
                    lin_edu1.setVisibility(View.GONE);
                    lin_del_edu.setVisibility(View.GONE);
                    lin_flag--;
                }
                if(lin_flag==1)
                {
                    lin_edu2.setVisibility(View.GONE);
                    profile_edit_school.requestFocus();
                    lin_flag--;
                }
                if(lin_flag>=2)
                {
                    lin_edu3.setVisibility(View.GONE);
                    profile_edit_school2.requestFocus();
                    lin_flag=1;
                }
            }
        });

        lin_add_edu.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                lin_del_edu.setVisibility(View.VISIBLE);
                lin_flag++;
                if (lin_flag==0)
                {
                    lin_edu1.setVisibility(View.VISIBLE);
                    profile_edit_school.requestFocus();

                }
                if (lin_flag==1)
                {
                    lin_edu2.setVisibility(View.VISIBLE);
                    profile_edit_school2.requestFocus();
                }
                if (lin_flag==2)
                {
                    lin_edu3.setVisibility(View.VISIBLE);//✓
                    profile_edit_school3.requestFocus();
                }
                else
                    Log.e("clicked","after on click "+lin_flag);
            }
        });

        edit_profile_edu_from_add.setOnClickListener(new View.OnClickListener()
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
                        profile_edit_edu_from.setText(from);
                    }
                }, mYear, mMonth, mDay);
                dpd.show();
            }
        });


        edit_profile_edu_to_add.setOnClickListener(new View.OnClickListener()
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
                        profile_edit_edu_to.setText(from);
                    }
                }, mYear, mMonth, mDay);
                dpd.show();
            }
        });

        edit_profile_edu_from_add2.setOnClickListener(new View.OnClickListener()
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
                        profile_edit_edu_from2.setText(from);
                    }
                }, mYear, mMonth, mDay);
                dpd.show();
            }
        });


        edit_profile_edu_to_add2.setOnClickListener(new View.OnClickListener()
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
                        profile_edit_edu_to2.setText(from);
                    }
                }, mYear, mMonth, mDay);
                dpd.show();
            }
        });

        edit_profile_edu_from_add3.setOnClickListener(new View.OnClickListener()
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
                        profile_edit_edu_from3.setText(from);
                    }
                }, mYear, mMonth, mDay);
                dpd.show();
            }
        });


        edit_profile_edu_to_add3.setOnClickListener(new View.OnClickListener()
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
                        profile_edit_edu_to3.setText(from);
                    }
                }, mYear, mMonth, mDay);
                dpd.show();
            }
        });

        insertData();

        return view;
    }

    public void insertData()
    {
        profile_edit_school.setText("Anna University");
        profile_edit_degree.setText("MCA");
        profile_edit_specialization.setText("IT");
        profile_edit_school_place.setText("Chennai");
        profile_edit_extra.setText("Falana Dhimkana");
        profile_edit_edu_from.setText("Aug,2012");
        profile_edit_edu_to.setText("Jun,2015");

        profile_edit_school2.setText("DAVV University");
        profile_edit_degree2.setText("BCA");
        profile_edit_specialization2.setText("E-commerce");
        profile_edit_school_place2.setText("Indore");
        profile_edit_extra2.setText("Falana Dhimkana");
        profile_edit_edu_from2.setText("Aug,2008");
        profile_edit_edu_to2.setText("Jun,2011");

        profile_edit_school3.setText("Sica S. S. School");
        profile_edit_degree3.setText("12th");
        profile_edit_specialization3.setText("Commerce with Maths");
        profile_edit_school_place3.setText("Indore");
        profile_edit_extra3.setText("Falana Dhimkana");
        profile_edit_edu_from3.setText("Aug,2007");
        profile_edit_edu_to3.setText("Jun,2008");
    }

    public interface OnDataPassTwo1
    {
        public void onDataPassTwo1(String school, String degree, String specialization, String location,
                                   String extra, String from, String to);
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        dataPasser = (OnDataPassTwo1) activity;
        dataPasser2 = (OnDataPassTwo2) activity;
        dataPasser3 = (OnDataPassTwo3) activity;
    }

    public interface OnDataPassTwo2
    {
        public void onDataPassTwo2(String school, String degree, String specialization, String location,
                                   String extra, String from, String to);
    }

    public interface OnDataPassTwo3
    {
        public void onDataPassTwo3(String school, String degree, String specialization, String location,
                                   String extra, String from, String to);
    }

    public boolean send_data()
    {

        if(lin_edu3.getVisibility()==View.VISIBLE)
        {
            Pattern simple_string = Pattern.compile("[A-Za-z' ']");

            Matcher school_matcher3,degree_matcher3,spec_matcher3,place_matcher3,extra_matcher3;

            boolean school_flag3,degree_flag3,spec_flag3,place_flag3,extra_flag3;

            school_matcher3 = simple_string.matcher(profile_edit_school3.getText().toString());
            degree_matcher3= simple_string.matcher(profile_edit_degree3.getText().toString());
            spec_matcher3= simple_string.matcher(profile_edit_specialization3.getText().toString());
            place_matcher3= simple_string.matcher(profile_edit_school_place3.getText().toString());
            extra_matcher3= simple_string.matcher(profile_edit_extra3.getText().toString());

            school_flag3=school_matcher3.find();
            degree_flag3=degree_matcher3.find();
            spec_flag3=spec_matcher3.find();
            place_flag3=place_matcher3.find();
            extra_flag3=extra_matcher3.find();

            Matcher school_matcher2,degree_matcher2,spec_matcher2,place_matcher2,extra_matcher2;

            boolean school_flag2,degree_flag2,spec_flag2,place_flag2,extra_flag2;

            school_matcher2 = simple_string.matcher(profile_edit_school.getText().toString());
            degree_matcher2= simple_string.matcher(profile_edit_degree.getText().toString());
            spec_matcher2= simple_string.matcher(profile_edit_specialization.getText().toString());
            place_matcher2= simple_string.matcher(profile_edit_school_place.getText().toString());
            extra_matcher2= simple_string.matcher(profile_edit_extra.getText().toString());

            school_flag2=school_matcher2.find();
            degree_flag2=degree_matcher2.find();
            spec_flag2=spec_matcher2.find();
            place_flag2=place_matcher2.find();
            extra_flag2=extra_matcher2.find();

            Matcher school_matcher1,degree_matcher1,spec_matcher1,place_matcher1,extra_matcher1;

            boolean school_flag1,degree_flag1,spec_flag1,place_flag1,extra_flag1;

            school_matcher1 = simple_string.matcher(profile_edit_school.getText().toString());
            degree_matcher1= simple_string.matcher(profile_edit_degree.getText().toString());
            spec_matcher1= simple_string.matcher(profile_edit_specialization.getText().toString());
            place_matcher1= simple_string.matcher(profile_edit_school_place.getText().toString());
            extra_matcher1= simple_string.matcher(profile_edit_extra.getText().toString());

            school_flag1=school_matcher1.find();
            degree_flag1=degree_matcher1.find();
            spec_flag1=spec_matcher1.find();
            place_flag1=place_matcher1.find();
            extra_flag1=extra_matcher1.find();

            if(profile_edit_school3.getText().toString().equals(""))
            {
                Toast.makeText(getActivity(),"School/College name is required field...",Toast.LENGTH_LONG).show();
            }
            else if(!school_flag3)
            {
                Toast.makeText(getActivity(),"Enter valid school/college name...",Toast.LENGTH_LONG).show();
            }
            else if(profile_edit_degree3.getText().toString().equals(""))
            {
                Toast.makeText(getActivity(),"Degree name is required field...",Toast.LENGTH_LONG).show();
            }
            else if(!degree_flag3)
            {
                Toast.makeText(getActivity(),"Enter valid Degree name...",Toast.LENGTH_LONG).show();
            }
            else if(profile_edit_specialization3.getText().toString().equals(""))
            {
                Toast.makeText(getActivity(),"Specialization name is required field...",Toast.LENGTH_LONG).show();
            }
            else if(!spec_flag3)
            {
                Toast.makeText(getActivity(),"Enter valid Specialization name...",Toast.LENGTH_LONG).show();
            }
            else if(profile_edit_school_place3.getText().toString().equals(""))
            {
                Toast.makeText(getActivity(),"Location name is required field...",Toast.LENGTH_LONG).show();
            }
            else if(!place_flag3)
            {
                Toast.makeText(getActivity(),"Enter valid Location name...",Toast.LENGTH_LONG).show();
            }
            else if(!extra_flag3)
            {
                Toast.makeText(getActivity(),"Enter valid Extra activity name...",Toast.LENGTH_LONG).show();
            }
            else if(profile_edit_school2.getText().toString().equals(""))
            {
                Toast.makeText(getActivity(),"School/College name is required field...",Toast.LENGTH_LONG).show();
            }
            else if(!school_flag2)
            {
                Toast.makeText(getActivity(),"Enter valid school/college name...",Toast.LENGTH_LONG).show();
            }
            else if(profile_edit_degree2.getText().toString().equals(""))
            {
                Toast.makeText(getActivity(),"Degree name is required field...",Toast.LENGTH_LONG).show();
            }
            else if(!degree_flag2)
            {
                Toast.makeText(getActivity(),"Enter valid Degree name...",Toast.LENGTH_LONG).show();
            }
            else if(profile_edit_specialization2.getText().toString().equals(""))
            {
                Toast.makeText(getActivity(),"Specialization name is required field...",Toast.LENGTH_LONG).show();
            }
            else if(!spec_flag2)
            {
                Toast.makeText(getActivity(),"Enter valid Specialization name...",Toast.LENGTH_LONG).show();
            }
            else if(profile_edit_school_place2.getText().toString().equals(""))
            {
                Toast.makeText(getActivity(),"Location name is required field...",Toast.LENGTH_LONG).show();
            }
            else if(!place_flag2)
            {
                Toast.makeText(getActivity(),"Enter valid Location name...",Toast.LENGTH_LONG).show();
            }
            else if(!extra_flag2)
            {
                Toast.makeText(getActivity(),"Enter valid Extra activity name...",Toast.LENGTH_LONG).show();
            }
            else if(profile_edit_school.getText().toString().equals(""))
            {
                Toast.makeText(getActivity(),"School/College name is required field...",Toast.LENGTH_LONG).show();
            }
            else if(!school_flag1)
            {
                Toast.makeText(getActivity(),"Enter valid school/college name...",Toast.LENGTH_LONG).show();
            }
            else if(profile_edit_degree.getText().toString().equals(""))
            {
                Toast.makeText(getActivity(),"Degree name is required field...",Toast.LENGTH_LONG).show();
            }
            else if(!degree_flag1)
            {
                Toast.makeText(getActivity(),"Enter valid Degree name...",Toast.LENGTH_LONG).show();
            }
            else if(profile_edit_specialization.getText().toString().equals(""))
            {
                Toast.makeText(getActivity(),"Specialization name is required field...",Toast.LENGTH_LONG).show();
            }
            else if(!spec_flag1)
            {
                Toast.makeText(getActivity(),"Enter valid Specialization name...",Toast.LENGTH_LONG).show();
            }
            else if(profile_edit_school_place.getText().toString().equals(""))
            {
                Toast.makeText(getActivity(),"Location name is required field...",Toast.LENGTH_LONG).show();
            }
            else if(!place_flag1)
            {
                Toast.makeText(getActivity(),"Enter valid Location name...",Toast.LENGTH_LONG).show();
            }
            else if(!extra_flag1)
            {
                Toast.makeText(getActivity(),"Enter valid Extra activity name...",Toast.LENGTH_LONG).show();
            }
            else
            {
                passDataTwo3(profile_edit_school3.getText().toString(),profile_edit_degree3.getText().toString(),
                        profile_edit_specialization3.getText().toString(),profile_edit_school_place3.getText().toString(),
                        profile_edit_extra3.getText().toString(),profile_edit_edu_from3.getText().toString(),
                        profile_edit_edu_to3.getText().toString());
                passDataTwo2(profile_edit_school2.getText().toString(),profile_edit_degree2.getText().toString(),
                        profile_edit_specialization2.getText().toString(),profile_edit_school_place2.getText().toString(),
                        profile_edit_extra2.getText().toString(),profile_edit_edu_from2.getText().toString(),
                        profile_edit_edu_to2.getText().toString());
                passDataTwo1(profile_edit_school.getText().toString(),profile_edit_degree.getText().toString(),
                        profile_edit_specialization.getText().toString(),profile_edit_school_place.getText().toString(),
                        profile_edit_extra.getText().toString(),profile_edit_edu_from.getText().toString(),
                        profile_edit_edu_to.getText().toString());
                return true;
            }
        }
        else if(lin_edu2.getVisibility()==View.VISIBLE)
        {
            Pattern simple_string = Pattern.compile("[A-Za-z' ']");

            Matcher school_matcher2,degree_matcher2,spec_matcher2,place_matcher2,extra_matcher2;

            boolean school_flag2,degree_flag2,spec_flag2,place_flag2,extra_flag2;

            school_matcher2 = simple_string.matcher(profile_edit_school.getText().toString());
            degree_matcher2= simple_string.matcher(profile_edit_degree.getText().toString());
            spec_matcher2= simple_string.matcher(profile_edit_specialization.getText().toString());
            place_matcher2= simple_string.matcher(profile_edit_school_place.getText().toString());
            extra_matcher2= simple_string.matcher(profile_edit_extra.getText().toString());

            school_flag2=school_matcher2.find();
            degree_flag2=degree_matcher2.find();
            spec_flag2=spec_matcher2.find();
            place_flag2=place_matcher2.find();
            extra_flag2=extra_matcher2.find();

            Matcher school_matcher1,degree_matcher1,spec_matcher1,place_matcher1,extra_matcher1;

            boolean school_flag1,degree_flag1,spec_flag1,place_flag1,extra_flag1;

            school_matcher1 = simple_string.matcher(profile_edit_school.getText().toString());
            degree_matcher1= simple_string.matcher(profile_edit_degree.getText().toString());
            spec_matcher1= simple_string.matcher(profile_edit_specialization.getText().toString());
            place_matcher1= simple_string.matcher(profile_edit_school_place.getText().toString());
            extra_matcher1= simple_string.matcher(profile_edit_extra.getText().toString());

            school_flag1=school_matcher1.find();
            degree_flag1=degree_matcher1.find();
            spec_flag1=spec_matcher1.find();
            place_flag1=place_matcher1.find();
            extra_flag1=extra_matcher1.find();

            if(profile_edit_school2.getText().toString().equals(""))
            {
                Toast.makeText(getActivity(),"School/College name is required field...",Toast.LENGTH_LONG).show();
            }
            else if(!school_flag2)
            {
                Toast.makeText(getActivity(),"Enter valid school/college name...",Toast.LENGTH_LONG).show();
            }
            else if(profile_edit_degree2.getText().toString().equals(""))
            {
                Toast.makeText(getActivity(),"Degree name is required field...",Toast.LENGTH_LONG).show();
            }
            else if(!degree_flag2)
            {
                Toast.makeText(getActivity(),"Enter valid Degree name...",Toast.LENGTH_LONG).show();
            }
            else if(profile_edit_specialization2.getText().toString().equals(""))
            {
                Toast.makeText(getActivity(),"Specialization name is required field...",Toast.LENGTH_LONG).show();
            }
            else if(!spec_flag2)
            {
                Toast.makeText(getActivity(),"Enter valid Specialization name...",Toast.LENGTH_LONG).show();
            }
            else if(profile_edit_school_place2.getText().toString().equals(""))
            {
                Toast.makeText(getActivity(),"Location name is required field...",Toast.LENGTH_LONG).show();
            }
            else if(!place_flag2)
            {
                Toast.makeText(getActivity(),"Enter valid Location name...",Toast.LENGTH_LONG).show();
            }
            else if(!extra_flag2)
            {
                Toast.makeText(getActivity(),"Enter valid Extra activity name...",Toast.LENGTH_LONG).show();
            }
            else if(profile_edit_school.getText().toString().equals(""))
            {
                Toast.makeText(getActivity(),"School/College name is required field...",Toast.LENGTH_LONG).show();
            }
            else if(!school_flag1)
            {
                Toast.makeText(getActivity(),"Enter valid school/college name...",Toast.LENGTH_LONG).show();
            }
            else if(profile_edit_degree.getText().toString().equals(""))
            {
                Toast.makeText(getActivity(),"Degree name is required field...",Toast.LENGTH_LONG).show();
            }
            else if(!degree_flag1)
            {
                Toast.makeText(getActivity(),"Enter valid Degree name...",Toast.LENGTH_LONG).show();
            }
            else if(profile_edit_specialization.getText().toString().equals(""))
            {
                Toast.makeText(getActivity(),"Specialization name is required field...",Toast.LENGTH_LONG).show();
            }
            else if(!spec_flag1)
            {
                Toast.makeText(getActivity(),"Enter valid Specialization name...",Toast.LENGTH_LONG).show();
            }
            else if(profile_edit_school_place.getText().toString().equals(""))
            {
                Toast.makeText(getActivity(),"Location name is required field...",Toast.LENGTH_LONG).show();
            }
            else if(!place_flag1)
            {
                Toast.makeText(getActivity(),"Enter valid Location name...",Toast.LENGTH_LONG).show();
            }
            else if(!extra_flag1)
            {
                Toast.makeText(getActivity(),"Enter valid Extra activity name...",Toast.LENGTH_LONG).show();
            }
            else
            {
                passDataTwo2(profile_edit_school2.getText().toString(),profile_edit_degree2.getText().toString(),
                        profile_edit_specialization2.getText().toString(),profile_edit_school_place2.getText().toString(),
                        profile_edit_extra2.getText().toString(),profile_edit_edu_from2.getText().toString(),
                        profile_edit_edu_to2.getText().toString());
                passDataTwo1(profile_edit_school.getText().toString(),profile_edit_degree.getText().toString(),
                        profile_edit_specialization.getText().toString(),profile_edit_school_place.getText().toString(),
                        profile_edit_extra.getText().toString(),profile_edit_edu_from.getText().toString(),
                        profile_edit_edu_to.getText().toString());
                return true;
            }
        }
        else if(lin_edu1.getVisibility()==View.VISIBLE)
        {
            Pattern simple_string = Pattern.compile("[A-Za-z' ']");

            Matcher school_matcher1,degree_matcher1,spec_matcher1,place_matcher1,extra_matcher1;

            boolean school_flag1,degree_flag1,spec_flag1,place_flag1,extra_flag1;

            school_matcher1 = simple_string.matcher(profile_edit_school.getText().toString());
            degree_matcher1= simple_string.matcher(profile_edit_degree.getText().toString());
            spec_matcher1= simple_string.matcher(profile_edit_specialization.getText().toString());
            place_matcher1= simple_string.matcher(profile_edit_school_place.getText().toString());
            extra_matcher1= simple_string.matcher(profile_edit_extra.getText().toString());

            school_flag1=school_matcher1.find();
            degree_flag1=degree_matcher1.find();
            spec_flag1=spec_matcher1.find();
            place_flag1=place_matcher1.find();
            extra_flag1=extra_matcher1.find();

            if(profile_edit_school.getText().toString().equals(""))
            {
                Toast.makeText(getActivity(),"School/College name is required field...",Toast.LENGTH_LONG).show();
            }
            else if(!school_flag1)
            {
                Toast.makeText(getActivity(),"Enter valid school/college name...",Toast.LENGTH_LONG).show();
            }
            else if(profile_edit_degree.getText().toString().equals(""))
            {
                Toast.makeText(getActivity(),"Degree name is required field...",Toast.LENGTH_LONG).show();
            }
            else if(!degree_flag1)
            {
                Toast.makeText(getActivity(),"Enter valid Degree name...",Toast.LENGTH_LONG).show();
            }
            else if(profile_edit_specialization.getText().toString().equals(""))
            {
                Toast.makeText(getActivity(),"Specialization name is required field...",Toast.LENGTH_LONG).show();
            }
            else if(!spec_flag1)
            {
                Toast.makeText(getActivity(),"Enter valid Specialization name...",Toast.LENGTH_LONG).show();
            }
            else if(profile_edit_school_place.getText().toString().equals(""))
            {
                Toast.makeText(getActivity(),"Location name is required field...",Toast.LENGTH_LONG).show();
            }
            else if(!place_flag1)
            {
                Toast.makeText(getActivity(),"Enter valid Location name...",Toast.LENGTH_LONG).show();
            }
            else if(!extra_flag1)
            {
                Toast.makeText(getActivity(),"Enter valid Extra activity name...",Toast.LENGTH_LONG).show();
            }
            else
            {
                passDataTwo1(profile_edit_school.getText().toString(),profile_edit_degree.getText().toString(),
                        profile_edit_specialization.getText().toString(),profile_edit_school_place.getText().toString(),
                        profile_edit_extra.getText().toString(),profile_edit_edu_from.getText().toString(),
                        profile_edit_edu_to.getText().toString());
                return true;
            }
        }
        else
        {
            Toast.makeText(getActivity(), "Add atleast one Education!", Toast.LENGTH_SHORT).show();
            return false;

        }
        return false;

    }

    public void passDataTwo1(String school,String degree,String specialization,String location,
                             String extra,String from,String to)
    {
        dataPasser.onDataPassTwo1(school,degree,specialization,location,extra,from,to);
    }

    public void passDataTwo2(String school,String degree,String specialization,String location,
                             String extra,String from,String to)
    {
        dataPasser.onDataPassTwo1(school,degree,specialization,location,extra,from,to);
    }

    public void passDataTwo3(String school,String degree,String specialization,String location,
                             String extra,String from,String to)
    {
        dataPasser.onDataPassTwo1(school,degree,specialization,location,extra,from,to);
    }
}
