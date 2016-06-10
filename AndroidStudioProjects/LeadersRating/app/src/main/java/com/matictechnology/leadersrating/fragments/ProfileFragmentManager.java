package com.matictechnology.leadersrating.fragments;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.matictechnology.leadersrating.R;
import com.matictechnology.leadersrating.classes.User;
import com.matictechnology.leadersrating.util.DBHelper;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import me.drakeet.materialdialog.MaterialDialog;

/**
 * Created by matic on 3/6/16.
 */
public class ProfileFragmentManager extends Fragment
{
    User u;
    String mode;
    MaterialDialog materialdialog_select;
    ImageView star11,star22,star33,star44,star55;
    ImageView star1,star2,star3,star4,star5,comment;
    int star1_flag=0,star2_flag=0,star3_flag=0,star4_flag=0,star5_flag=0;
    DBHelper helper;
    SQLiteDatabase db;
    //fragment class to show fragments as per user selection
    private static final String ARG_POSITION = "position";

    private int position;   //position flag to get the count of current fragment selection

    public static ProfileFragmentManager newInstance(int position,User u1,String mode)
    {
        ProfileFragmentManager f = new ProfileFragmentManager();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        b.putSerializable("user",u1);
        b.putString("mode",mode);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //getting current fragment count
        position = getArguments().getInt(ARG_POSITION);
        u=(User)getArguments().getSerializable("user");
        mode=getArguments().getString("mode");



        helper=new DBHelper(getActivity());
        db=helper.getWritableDatabase();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        //getting layout inflater service
        LayoutInflater inflater1 = (LayoutInflater) getContext().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View itemView1=null;
        if(position==0)
        {
            star1_flag=-1;
            star2_flag=-1;
            star3_flag=-1;
            star4_flag=-1;
            star5_flag=-1;
            itemView1=inflater1.inflate(R.layout.fragment_profile_basic, null);
            final TextView profile_phone,profile_username,profile_workname,profile_category,profile_address,profile_country,profile_rating_text;

            LinearLayout profile_rating_lin=(LinearLayout)itemView1.findViewById(R.id.profile_rating_lin);
            LinearLayout profile_rate_lin=(LinearLayout)itemView1.findViewById(R.id.profile_rate_lin);
            Button profile_rate,profile_comment;
            com.pkmmte.view.CircularImageView user_pic;
            TextView profile_name,profile_email;
            profile_rating_text=(TextView)itemView1.findViewById(R.id.profile_rating_text);
            user_pic=(com.pkmmte.view.CircularImageView)itemView1.findViewById(R.id.profile_picture);
            profile_name=(TextView)itemView1.findViewById(R.id.profile_name);
            profile_email=(TextView)itemView1.findViewById(R.id.profile_email);
            ImageView profile_cover=(ImageView)itemView1.findViewById(R.id.profile_cover);

            if(mode.equals("user"))
            {
                profile_rate_lin.setVisibility(View.GONE);
            }

            star1=(ImageView)itemView1.findViewById(R.id.star1);
            star2=(ImageView)itemView1.findViewById(R.id.star2);
            star3=(ImageView)itemView1.findViewById(R.id.star3);
            star4=(ImageView)itemView1.findViewById(R.id.star4);
            star5=(ImageView)itemView1.findViewById(R.id.star5);

            profile_rating_text.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    view_rating_click();
                }
            });
            star5.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    view_rating_click();
                }
            });

            star4.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    view_rating_click();
                }
            });
            star3.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    view_rating_click();
                }
            });
            star2.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    view_rating_click();
                }
            });
            star1.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    view_rating_click();
                }
            });

            String rt=refersh_ratings();

            profile_rating_text.setText(rt+"/5");

            profile_rate=(Button)itemView1.findViewById(R.id.profile_rate);
            profile_comment=(Button)itemView1.findViewById(R.id.profile_comment);

            profile_rating_lin.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    view_rating_click();
                }
            });

            profile_rate.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View itemView_select = inflater.inflate(R.layout.dialog_star_rating, null);

                    int rate_old=helper.getRating(db,u.getPhone());

                    star11=(ImageView)itemView_select.findViewById(R.id.star1);
                    star22=(ImageView)itemView_select.findViewById(R.id.star2);
                    star33=(ImageView)itemView_select.findViewById(R.id.star3);
                    star44=(ImageView)itemView_select.findViewById(R.id.star4);
                    star55=(ImageView)itemView_select.findViewById(R.id.star5);



                    if(rate_old==5)
                    {
                        star5_flag=1;
                        star55.setImageResource(R.drawable.star);
                        star44.setImageResource(R.drawable.star);
                        star33.setImageResource(R.drawable.star);
                        star22.setImageResource(R.drawable.star);
                        star11.setImageResource(R.drawable.star);
                    }
                    else if(rate_old==4)
                    {
                        star4_flag=1;
                        star44.setImageResource(R.drawable.star);
                        star33.setImageResource(R.drawable.star);
                        star22.setImageResource(R.drawable.star);
                        star11.setImageResource(R.drawable.star);
                    }
                    else if(rate_old==3)
                    {
                        star3_flag=1;
                        star33.setImageResource(R.drawable.star);
                        star22.setImageResource(R.drawable.star);
                        star11.setImageResource(R.drawable.star);
                    }
                    else if(rate_old==2)
                    {
                        star2_flag=1;
                        star22.setImageResource(R.drawable.star);
                        star11.setImageResource(R.drawable.star);
                    }
                    else if(rate_old==1)
                    {
                        star1_flag=1;
                        star11.setImageResource(R.drawable.star);
                    }


                    star11.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View view)
                        {
                            if(star1_flag==-1||star1_flag==0)
                            {
                                star11.setImageResource(R.drawable.star);
                                star1_flag=1;
                            }
                            else if(star1_flag==1)
                            {
                                star11.setImageResource(R.drawable.holo_star1);
                                star1_flag=0;
                            }
                            star22.setImageResource(R.drawable.holo_star1);
                            star2_flag=0;
                            star33.setImageResource(R.drawable.holo_star1);
                            star3_flag=0;
                            star44.setImageResource(R.drawable.holo_star1);
                            star4_flag=0;
                            star55.setImageResource(R.drawable.holo_star1);
                            star5_flag=0;

                        }
                    });
                    star22.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View view)
                        {
                            star33.setImageResource(R.drawable.holo_star1);
                            star3_flag=0;
                            star44.setImageResource(R.drawable.holo_star1);
                            star4_flag=0;
                            star55.setImageResource(R.drawable.holo_star1);
                            star5_flag=0;
                            if(star2_flag==-1||star2_flag==0)
                            {
                                star22.setImageResource(R.drawable.star);
                                star2_flag=1;
                                star11.setImageResource(R.drawable.star);
                                star1_flag=1;
                            }
                            else if(star2_flag==1)
                            {
                                star22.setImageResource(R.drawable.holo_star1);
                                star2_flag=0;
                            }
                        }
                    });
                    star33.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View view)
                        {
                            star44.setImageResource(R.drawable.holo_star1);
                            star4_flag=0;
                            star55.setImageResource(R.drawable.holo_star1);
                            star5_flag=0;
                            if(star3_flag==-1||star3_flag==0)
                            {
                                star33.setImageResource(R.drawable.star);
                                star3_flag=1;
                                star22.setImageResource(R.drawable.star);
                                star2_flag=1;
                                star11.setImageResource(R.drawable.star);
                                star1_flag=1;
                            }
                            else if(star3_flag==1)
                            {
                                star33.setImageResource(R.drawable.holo_star1);
                                star3_flag=0;
                            }
                        }
                    });
                    star44.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View view)
                        {
                            star55.setImageResource(R.drawable.holo_star1);
                            star5_flag=0;
                            if(star4_flag==-1||star4_flag==0)
                            {
                                star44.setImageResource(R.drawable.star);
                                star4_flag=1;
                                star33.setImageResource(R.drawable.star);
                                star3_flag=1;
                                star22.setImageResource(R.drawable.star);
                                star2_flag=1;
                                star11.setImageResource(R.drawable.star);
                                star1_flag=1;
                            }
                            else if(star4_flag==1)
                            {
                                star44.setImageResource(R.drawable.holo_star1);
                                star4_flag=0;
                            }
                        }
                    });
                    star55.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View view)
                        {
                            if(star5_flag==-1||star5_flag==0)
                            {
                                star55.setImageResource(R.drawable.star);
                                star5_flag=1;
                                star44.setImageResource(R.drawable.star);
                                star4_flag=1;
                                star33.setImageResource(R.drawable.star);
                                star3_flag=1;
                                star22.setImageResource(R.drawable.star);
                                star2_flag=1;
                                star11.setImageResource(R.drawable.star);
                                star1_flag=1;
                            }
                            else if(star5_flag==1)
                            {
                                star55.setImageResource(R.drawable.holo_star1);
                                star5_flag=0;
                            }
                        }
                    });


                    //Toast.makeText(ActivityProfile.this, "clicked star user", Toast.LENGTH_SHORT).show();
                    materialdialog_select=new MaterialDialog(getActivity())
                            .setTitle("RATE")
                            .setView(itemView_select)
                            .setMessage("Rate User as per your opinion!")
                            .setPositiveButton("Rate", new View.OnClickListener()
                            {
                                @Override
                                public void onClick(View view)
                                {
                                    int rate=0;
                                    if(star5_flag==1)
                                    {
                                        rate=5;
                                    }
                                    else if(star4_flag==1)
                                    {
                                        rate=4;
                                    }
                                    else if(star3_flag==1)
                                    {
                                        rate=3;
                                    }
                                    else if(star2_flag==1)
                                    {
                                        rate=2;
                                    }
                                    else if(star1_flag==1)
                                    {
                                        rate=1;
                                    }
                                    Log.e("rate=>",""+rate);
                                    String id=helper.get_id(db);
                                    String rt=""+rate;
                                    Log.e("rate from activity",""+rt);
                                    if(!helper.insertRating(db,u.getPhone(),id,rt))
                                    {
                                        Toast.makeText(getActivity(), "User Rating Updated", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        Rating_Upload_Task rate_task=new Rating_Upload_Task();
                                        //rate_task.execute(u.getPhone(),rt,id);
                                    }
                                    String rt1=refersh_ratings();

                                    profile_rating_text.setText(rt1+"/5");
                                    materialdialog_select.dismiss();
                                }
                            })
                            .setNegativeButton("Cancel",new View.OnClickListener()
                            {
                                @Override
                                public void onClick(View view)
                                {
                                    materialdialog_select.dismiss();
                                }
                            });

                    materialdialog_select.show();
                }
            });

            profile_comment.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View itemView_select = inflater.inflate(R.layout.dialog_comment_rating, null);

                    final EditText comment_user=(EditText)itemView_select.findViewById(R.id.comment_user);
                    //Toast.makeText(ActivityProfile.this, "clicked star user", Toast.LENGTH_SHORT).show();
                    materialdialog_select=new MaterialDialog(getActivity())
                            .setTitle("Comment")
                            .setView(itemView_select)
                            .setMessage("Comment about User as per your opinion!")
                            .setPositiveButton("Comment", new View.OnClickListener()
                            {
                                @Override
                                public void onClick(View view)
                                {
                                    //Log.e("comment:",""+comment_user.getText().toString());
                                    String id=helper.get_id(db);
                                    if(!helper.insertComment(db,u.getPhone(),id,comment_user.getText().toString()))
                                    {
                                        Toast.makeText(getActivity(), "Already Commented for User", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        Comment_Upload_Task comment_task=new Comment_Upload_Task();
                                        //comment_task.execute(u.getPhone(),comment_user.getText().toString(),id);
                                    }
                                    materialdialog_select.dismiss();
                                }
                            })
                            .setNegativeButton("Cancel",new View.OnClickListener()
                            {
                                @Override
                                public void onClick(View view)
                                {
                                    materialdialog_select.dismiss();
                                }
                            });

                    materialdialog_select.show();
                }
            });


            /*Log.e("picture=>",""+u.getPicture()+"..");
            Log.e("picture=>",""+u.getPicture_path()+"..");
            Log.e("cover=>",""+u.getCover_img()+"..");
            Log.e("cover=>",""+u.getCover_path()+"..");*/

            if(u.getPicture().equals(""))
            {
                user_pic.setBackgroundResource(R.drawable.user);
            }
            else
            {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                Bitmap bitmap = BitmapFactory.decodeFile(u.getPicture_path(), options);

                Bitmap bitmap1=getRoundedBitmap(bitmap);
                user_pic.setImageBitmap(bitmap1);
            }
            Log.e("cover=>",""+u.getCover_img());
            if(u.getCover_img()==null||u.getCover_img().equals(""))
            {
                profile_cover.setBackgroundResource(R.drawable.nav_bakground);
            }
            else
            {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                Bitmap bitmap = BitmapFactory.decodeFile(u.getCover_path(), options);

                //Bitmap bitmap1=getRoundedBitmap(bitmap);
                BitmapDrawable ob = new BitmapDrawable(getResources(), bitmap);

                profile_cover.setBackgroundDrawable(ob);
            }


                //android.support.design.widget.CollapsingToolbarLayout toolbar_layout=(android.support.design.widget.CollapsingToolbarLayout)findViewById(R.id.toolbar_layout);
                BitmapFactory.Options options_cover = new BitmapFactory.Options();
                options_cover.inPreferredConfig = Bitmap.Config.ARGB_8888;




            //Log.e("cover image url..",""+u.getCover_path()+"..");
            /*if(u.getCover_path()!=null)
            {
                Bitmap bitmap_cover = BitmapFactory.decodeFile(u.getCover_path(), options);
                BitmapDrawable ob = new BitmapDrawable(getResources(), bitmap_cover);
                //toolbar_layout.setBackgroundDrawable(ob);
                profile_cover.setBackgroundDrawable(ob);
            }*/



            profile_name.setText(u.getName());

            profile_email.setText(u.getEmail());


            profile_phone=(TextView)itemView1.findViewById(R.id.profile_phone);
            profile_username=(TextView)itemView1.findViewById(R.id.profile_username);
            profile_workname=(TextView)itemView1.findViewById(R.id.profile_workname);
            profile_category=(TextView)itemView1.findViewById(R.id.profile_category);
            profile_address=(TextView)itemView1.findViewById(R.id.profile_address);
            profile_country=(TextView)itemView1.findViewById(R.id.profile_country);

            profile_phone.setText(u.getPhone());
            profile_username.setText(u.getUsername());
            profile_workname.setText(u.getWork_name());
            profile_category.setText(u.getCategory());
            profile_address.setText(u.getAddress());
            profile_country.setText(u.getCountry());

            if(mode.equals("user"))
            {


            }
            else if(mode.equals("other"))
            {

            }
        }
        else if(position==1)
        {
            itemView1=inflater1.inflate(R.layout.fragment_profile_education, null);

            TextView profile_school,profile_degree,profile_special,profile_location,profile_edu_from,profile_edu_to,profile_extra;

            TextView profile_school2,profile_degree2,profile_special2,profile_location2,profile_edu_from2,profile_edu_to2,profile_extra2;

            TextView profile_school3,profile_degree3,profile_special3,profile_location3,profile_edu_from3,profile_edu_to3,profile_extra3;
            TextView no_edu;

            LinearLayout edu_lin1,edu_lin2,edu_lin3;

            profile_school=(TextView)itemView1.findViewById(R.id.profile_school);
            profile_degree=(TextView)itemView1.findViewById(R.id.profile_degree);
            profile_special=(TextView)itemView1.findViewById(R.id.profile_special);
            profile_location=(TextView)itemView1.findViewById(R.id.profile_location);
            profile_edu_from=(TextView)itemView1.findViewById(R.id.profile_edu_from);
            profile_edu_to=(TextView)itemView1.findViewById(R.id.profile_edu_to);
            profile_extra=(TextView)itemView1.findViewById(R.id.profile_extra);

            edu_lin1=(LinearLayout)itemView1.findViewById(R.id.lin_edu1);

            edu_lin2=(LinearLayout)itemView1.findViewById(R.id.lin_edu2);

            profile_school2=(TextView)itemView1.findViewById(R.id.profile_school2);
            profile_degree2=(TextView)itemView1.findViewById(R.id.profile_degree2);
            profile_special2=(TextView)itemView1.findViewById(R.id.profile_special2);
            profile_location2=(TextView)itemView1.findViewById(R.id.profile_location2);
            profile_edu_from2=(TextView)itemView1.findViewById(R.id.profile_edu_from2);
            profile_edu_to2=(TextView)itemView1.findViewById(R.id.profile_edu_to2);
            profile_extra2=(TextView)itemView1.findViewById(R.id.profile_extra2);

            edu_lin3=(LinearLayout)itemView1.findViewById(R.id.lin_edu3);

            profile_school3=(TextView)itemView1.findViewById(R.id.profile_school3);
            profile_degree3=(TextView)itemView1.findViewById(R.id.profile_degree3);
            profile_special3=(TextView)itemView1.findViewById(R.id.profile_special3);
            profile_location3=(TextView)itemView1.findViewById(R.id.profile_location3);
            profile_edu_from3=(TextView)itemView1.findViewById(R.id.profile_edu_from3);
            profile_edu_to3=(TextView)itemView1.findViewById(R.id.profile_edu_to3);
            profile_extra3=(TextView)itemView1.findViewById(R.id.profile_extra3);
            no_edu=(TextView)itemView1.findViewById(R.id.no_edu);


            for(int count=0;count<u.getEdu_school().size();count++)
            {
                if(count==0)
                {
                    profile_school.setText(u.getEdu_school().get(count));
                    profile_degree.setText(u.getEdu_degree().get(count));
                    profile_special.setText(u.getEdu_specialization().get(count));
                    profile_location.setText(u.getEdu_location().get(count));
                    profile_edu_from.setText(u.getEdu_from().get(count));
                    profile_edu_to.setText(u.getEdu_to().get(count));
                    profile_extra.setText(u.getEdu_extra().get(count));
                }
                if(count==1)
                {
                    edu_lin2.setVisibility(View.VISIBLE);
                    profile_school2.setText(u.getEdu_school().get(count));
                    profile_degree2.setText(u.getEdu_degree().get(count));
                    profile_special2.setText(u.getEdu_specialization().get(count));
                    profile_location2.setText(u.getEdu_location().get(count));
                    profile_edu_from2.setText(u.getEdu_from().get(count));
                    profile_edu_to2.setText(u.getEdu_to().get(count));
                    profile_extra2.setText(u.getEdu_extra().get(count));
                }
                if(count==2)
                {
                    edu_lin3.setVisibility(View.VISIBLE);

                    profile_school3.setText(u.getEdu_school().get(count));
                    profile_degree3.setText(u.getEdu_degree().get(count));
                    profile_special3.setText(u.getEdu_specialization().get(count));
                    profile_location3.setText(u.getEdu_location().get(count));
                    profile_edu_from3.setText(u.getEdu_from().get(count));
                    profile_edu_to3.setText(u.getEdu_to().get(count));
                    profile_extra3.setText(u.getEdu_extra().get(count));
                }
            }

            if(u.getEdu_school().size()==0)
            {
                no_edu.setVisibility(View.VISIBLE);
                edu_lin1.setVisibility(View.GONE);
            }

        }
        else if(position==2)
        {
            itemView1=inflater1.inflate(R.layout.fragment_profile_exp, null);

            TextView profile_company,profile_exp_location,profile_exp_from,profile_exp_to,profile_post;

            TextView profile_company2,profile_exp_location2,profile_exp_from2,profile_exp_to2,profile_post2;

            TextView profile_company3,profile_exp_location3,profile_exp_from3,profile_exp_to3,profile_post3;

            TextView no_exp;

            LinearLayout exp_lin1,exp_lin2,exp_lin3;

            profile_company=(TextView)itemView1.findViewById(R.id.profile_company);
            profile_exp_location=(TextView)itemView1.findViewById(R.id.profile_exp_location);
            profile_exp_from=(TextView)itemView1.findViewById(R.id.profile_exp_from);
            profile_exp_to=(TextView)itemView1.findViewById(R.id.profile_exp_to);
            profile_post=(TextView)itemView1.findViewById(R.id.profile_post);

            exp_lin1=(LinearLayout)itemView1.findViewById(R.id.lin_exp1);

            profile_company2=(TextView)itemView1.findViewById(R.id.profile_company2);
            profile_exp_location2=(TextView)itemView1.findViewById(R.id.profile_exp_location2);
            profile_exp_from2=(TextView)itemView1.findViewById(R.id.profile_exp_from2);
            profile_exp_to2=(TextView)itemView1.findViewById(R.id.profile_exp_to2);
            profile_post2=(TextView)itemView1.findViewById(R.id.profile_post2);

            exp_lin2=(LinearLayout)itemView1.findViewById(R.id.lin_exp2);

            profile_company3=(TextView)itemView1.findViewById(R.id.profile_company3);
            profile_exp_location3=(TextView)itemView1.findViewById(R.id.profile_exp_location3);
            profile_exp_from3=(TextView)itemView1.findViewById(R.id.profile_exp_from3);
            profile_exp_to3=(TextView)itemView1.findViewById(R.id.profile_exp_to3);
            profile_post3=(TextView)itemView1.findViewById(R.id.profile_post3);

            exp_lin3=(LinearLayout)itemView1.findViewById(R.id.lin_exp3);

            no_exp=(TextView)itemView1.findViewById(R.id.no_exp);

            if(u.getExp_company().size()==0)
            {
                no_exp.setVisibility(View.VISIBLE);
            }
            else
            {
                for(int count=0;count<u.getExp_company().size();count++)
                {
                    if(count==0)
                    {
                        exp_lin1.setVisibility(View.VISIBLE);


                        profile_company.setText(u.getExp_company().get(count));
                        profile_exp_location.setText(u.getExp_location().get(count));
                        profile_exp_from.setText(u.getExp_from().get(count));
                        profile_exp_to.setText(u.getExp_to().get(count));
                        profile_post.setText(u.getExp_post().get(count));
                    }
                    if(count==1)
                    {
                        exp_lin2.setVisibility(View.VISIBLE);

                        profile_company2.setText(u.getExp_company().get(count));
                        profile_exp_location2.setText(u.getExp_location().get(count));
                        profile_exp_from2.setText(u.getExp_from().get(count));
                        profile_exp_to2.setText(u.getExp_to().get(count));
                        profile_post2.setText(u.getExp_post().get(count));
                    }
                    if(count==2)
                    {
                        exp_lin3.setVisibility(View.VISIBLE);

                        profile_company3.setText(u.getExp_company().get(count));
                        profile_exp_location3.setText(u.getExp_location().get(count));
                        profile_exp_from3.setText(u.getExp_from().get(count));
                        profile_exp_to3.setText(u.getExp_to().get(count));
                        profile_post3.setText(u.getExp_post().get(count));
                    }
                }

            }

        }
        else if(position==3)
        {
            itemView1=inflater1.inflate(R.layout.fragment_profile_skill, null);

            TextView profile_no_skills;

            TextView skills;

            skills=(TextView)itemView1.findViewById(R.id.profile_skills);
            profile_no_skills=(TextView)itemView1.findViewById(R.id.profile_no_skills);

            String skill="-";
            Log.e("skill size=>",""+u.getSkills().size());
            if(u.getSkills().size()!=0)
            {
                skill=skill+u.getSkills().get(0);
                for(int count=1;count<u.getSkills().size();count++)
                    skill=skill+",\n-"+u.getSkills().get(count);
                skills.setText(skill);
            }
            else
            {

                skills.setVisibility(View.GONE);
                profile_no_skills.setVisibility(View.VISIBLE);

            }
        }
        return itemView1;
    }


    public Bitmap getRoundedBitmap(Bitmap bitmap)
    {
        if(bitmap!=null)
        {
            final Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            final Canvas canvas = new Canvas(output);

            final int color = Color.RED;
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            final RectF rectF = new RectF(rect);

            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);
            canvas.drawOval(rectF, paint);

            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);

            bitmap.recycle();

            return output;
        }
        return null;
    }

    public String refersh_ratings()
    {
        int rate=helper.getRating(db,u.getPhone());

        if(rate==5)
        {
            star5.setImageResource(R.drawable.star);
            star4.setImageResource(R.drawable.star);
            star3.setImageResource(R.drawable.star);
            star2.setImageResource(R.drawable.star);
            star1.setImageResource(R.drawable.star);
        }
        else if(rate==4)
        {
            star5.setImageResource(R.drawable.holo_star1);
            star4.setImageResource(R.drawable.star);
            star3.setImageResource(R.drawable.star);
            star2.setImageResource(R.drawable.star);
            star1.setImageResource(R.drawable.star);

        }
        else if(rate==3)
        {
            star5.setImageResource(R.drawable.holo_star1);
            star4.setImageResource(R.drawable.holo_star1);
            star3.setImageResource(R.drawable.star);
            star2.setImageResource(R.drawable.star);
            star1.setImageResource(R.drawable.star);
        }
        else if(rate==2)
        {
            star5.setImageResource(R.drawable.holo_star1);
            star4.setImageResource(R.drawable.holo_star1);
            star3.setImageResource(R.drawable.holo_star1);
            star2.setImageResource(R.drawable.star);
            star1.setImageResource(R.drawable.star);
        }
        else if(rate==1)
        {
            star5.setImageResource(R.drawable.holo_star1);
            star4.setImageResource(R.drawable.holo_star1);
            star3.setImageResource(R.drawable.holo_star1);
            star2.setImageResource(R.drawable.holo_star1);
            star1.setImageResource(R.drawable.star);
        }
        else if(rate==0)
        {
            star5.setImageResource(R.drawable.holo_star1);
            star4.setImageResource(R.drawable.holo_star1);
            star3.setImageResource(R.drawable.holo_star1);
            star2.setImageResource(R.drawable.holo_star1);
            star1.setImageResource(R.drawable.holo_star1);
        }

        return rate+"";
    }

    public class Rating_Upload_Task extends AsyncTask<String,Void,Void>
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

                nameValuePairs.add(new BasicNameValuePair("phone",strings[0] ));
                nameValuePairs.add(new BasicNameValuePair("rate",strings[1] ));
                nameValuePairs.add(new BasicNameValuePair("phone_other",strings[2] ));

                /*
                create table ALL_RATING(PHONE text," +
                "RATE text,PHONE_OTHER text)

                create table ALL_COMMENT(PHONE text," +
                "COMMENT text,PHONE_OTHER text)*/

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

    public class Comment_Upload_Task extends AsyncTask<String,Void,Void>
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

                nameValuePairs.add(new BasicNameValuePair("phone",strings[0] ));
                nameValuePairs.add(new BasicNameValuePair("comment",strings[1] ));
                nameValuePairs.add(new BasicNameValuePair("phone_other",strings[2] ));

                /*
                create table ALL_COMMENT(PHONE text," +
                "COMMENT text,PHONE_OTHER text)*/

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

    public void view_rating_click()
    {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView_select = inflater.inflate(R.layout.dialog_view_ratings, null);
        ListView comments_user=(ListView)itemView_select.findViewById(R.id.comments_user);

        ImageView star111=(ImageView)itemView_select.findViewById(R.id.star1);
        ImageView star222=(ImageView)itemView_select.findViewById(R.id.star2);
        ImageView star333=(ImageView)itemView_select.findViewById(R.id.star3);
        ImageView star444=(ImageView)itemView_select.findViewById(R.id.star4);
        ImageView star555=(ImageView)itemView_select.findViewById(R.id.star5);

        int rate=helper.getRating(db,u.getPhone());

        if(rate==5)
        {
            star555.setImageResource(R.drawable.star);
            star444.setImageResource(R.drawable.star);
            star333.setImageResource(R.drawable.star);
            star222.setImageResource(R.drawable.star);
            star111.setImageResource(R.drawable.star);
        }
        else if(rate==4)
        {
            star444.setImageResource(R.drawable.star);
            star333.setImageResource(R.drawable.star);
            star222.setImageResource(R.drawable.star);
            star111.setImageResource(R.drawable.star);
        }
        else if(rate==3)
        {
            star333.setImageResource(R.drawable.star);
            star222.setImageResource(R.drawable.star);
            star111.setImageResource(R.drawable.star);
        }
        else if(rate==2)
        {
            star222.setImageResource(R.drawable.star);
            star111.setImageResource(R.drawable.star);
        }
        else if(rate==1)
        {
            star111.setImageResource(R.drawable.star);
        }

        ArrayList<String> comment_List=new ArrayList<String>();
        comment_List=helper.getComments(db,u.getPhone());

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,comment_List);

        comments_user.setAdapter(adapter);


        //Toast.makeText(ActivityProfile.this, "clicked star user", Toast.LENGTH_SHORT).show();
        materialdialog_select=new MaterialDialog(getActivity())
                .setTitle("Comment")
                .setView(itemView_select)
                .setMessage("Comment about User as per your opinion!")
                .setPositiveButton("Done", new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        materialdialog_select.dismiss();
                    }
                });

        materialdialog_select.show();
    }

}
