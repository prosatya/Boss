package com.matictechnology.leadersrating.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.matictechnology.leadersrating.R;
import com.matictechnology.leadersrating.classes.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matic on 7/6/16.
 */
public class UserAdapter extends ArrayAdapter<User>
{
    User u;
    ArrayList<User> user;
    Context context;

    public UserAdapter(Context context, int resource)
    {

        super(context, resource);
    }

    public UserAdapter(Context context1, int resource, ArrayList<User> objects)
    {
        super(context1, resource, objects);
        user=objects;
        context=context1;
    }

    //override getViewMethod
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        u=user.get(position);

        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView=inflater.inflate(R.layout.item_user, null);
        //View itemView=convertView;
        TextView item_user_name=(TextView)itemView.findViewById(R.id.item_user_name);
        TextView item_user_workname=(TextView)itemView.findViewById(R.id.item_user_workname);
        ImageView star1,star2,star3,star4,star5;

        star1=(ImageView)itemView.findViewById(R.id.star1);
        star2=(ImageView)itemView.findViewById(R.id.star2);
        star3=(ImageView)itemView.findViewById(R.id.star3);
        star4=(ImageView)itemView.findViewById(R.id.star4);
        star5=(ImageView)itemView.findViewById(R.id.star5);

        DBHelper helper=new DBHelper(getContext());
        SQLiteDatabase db=helper.getWritableDatabase();

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
            star4.setImageResource(R.drawable.star);
            star3.setImageResource(R.drawable.star);
            star2.setImageResource(R.drawable.star);
            star1.setImageResource(R.drawable.star);
        }
        else if(rate==3)
        {
            star3.setImageResource(R.drawable.star);
            star2.setImageResource(R.drawable.star);
            star1.setImageResource(R.drawable.star);
        }
        else if(rate==2)
        {
            star2.setImageResource(R.drawable.star);
            star1.setImageResource(R.drawable.star);
        }
        else if(rate==1)
        {
            star1.setImageResource(R.drawable.star);
        }

        item_user_name.setText(u.getName());
        item_user_workname.setText(u.getWork_name());

        return itemView;
    }
}
