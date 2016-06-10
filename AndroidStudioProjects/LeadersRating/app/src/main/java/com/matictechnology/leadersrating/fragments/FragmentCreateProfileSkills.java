package com.matictechnology.leadersrating.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.matictechnology.leadersrating.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FragmentCreateProfileSkills extends Fragment
{
    OnDataPassFour dataPasser;
    String Skills="";
    int f=-1;
    Button profile_edit_add_skill;
    EditText profile_edit_skills;
    GridLayout grid_skill;
    String skills[] = new String[10];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        LayoutInflater inflater1 = (LayoutInflater) getActivity().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View view=inflater1.inflate(R.layout.fragment_create_profile_skills, container, false);

        profile_edit_skills=(EditText)view.findViewById(R.id.profile_edit_skills);

        grid_skill=(GridLayout)view.findViewById(R.id.grid_skill);


        profile_edit_add_skill=(Button)view.findViewById(R.id.profile_edit_add_skill);
        profile_edit_add_skill.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                final LinearLayout l;
                final TextView t;
                TextView t2;
                Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
                Matcher m = p.matcher(profile_edit_skills.getText().toString());
                boolean b = m.find();
                //Toast.makeText(Registration_User.this,""+user_skills.getText().toString(),Toast.LENGTH_LONG).show();
                if (profile_edit_skills.getText().toString().equals("") || profile_edit_skills.getText().toString().equals(null)||b)
                {
                    Toast.makeText(getActivity(), "Enter the valid Skills you possess!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    f++;
                    //Toast.makeText(Registration_User.this,"counter="+f.counter,Toast.LENGTH_LONG).show();
                    if (f> 9)
                    {
                        f--;
                        Toast.makeText(getActivity(), "Only 10 skills are allowed here!!", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {

                        if(Skills.equals(""))
                        {

                        }
                        else
                        {
                            Skills=Skills+", ";
                        }
                        l = new LinearLayout(getActivity());
                        l.setBackgroundResource(R.drawable.skill_back1);
                        l.setOrientation(LinearLayout.HORIZONTAL);
                        l.setClickable(true);

                        t = new TextView(getActivity());
                        Skills=Skills+profile_edit_skills.getText().toString();
                        String skill = " " + profile_edit_skills.getText().toString() + " ";
                        skills[f] = profile_edit_skills.getText().toString();
                        skill = skill.toUpperCase();
                        t.setText(skill);
                        t.setBackgroundResource(R.drawable.skill_back);


                        //t1 = new TextView(Registration_User.this);
                        //t1.setText(" ");


                        t2 = new TextView(getActivity());
                        t2.setText(" X ");

                        t.setTextColor(Color.BLACK);
                        t2.setTextColor(Color.BLACK);
                        t2.setClickable(true);

                        l.addView(t);
                        l.addView(t2);

                        grid_skill.addView(l);
                        //lay_exp3.addView(t1);

                        t2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                f--;
                                grid_skill.removeView(l);
                            }
                        });
                        //Toast.makeText(Registration_User.this,""+f.counter,Toast.LENGTH_LONG).show();
                        profile_edit_skills.setText("");
                    }
                }
            }
        });

        return view;
    }


    public interface OnDataPassFour
    {
        public void onDataPassFour(String skills[]);
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        dataPasser = (OnDataPassFour) activity;
    }

    public boolean send_data()
    {
        passDataFour(skills);
        return true;

    }


    public void passDataFour(String skills[])
    {
        dataPasser.onDataPassFour(skills);
    }
}
