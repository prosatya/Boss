package com.matictechnology.calculater;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    EditText num1,num2;
    TextView res;
    Button add,sub;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        add=(Button)findViewById(R.id.add);
        sub=(Button)findViewById(R.id.sub);

        res=(TextView)findViewById(R.id.result);

        num1=(EditText)findViewById(R.id.num1);
        num2=(EditText)findViewById(R.id.num2);

        add.setOnClickListener(this);
        sub.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        if(view.getId()==R.id.add)
        {
            int n1=Integer.parseInt(num1.getText().toString());
            int n2=Integer.parseInt(num2.getText().toString());
            int result=n1+n2;
            res.setText(result);
        }
        else if (view.getId()==R.id.sub)
        {
            int n1=Integer.parseInt(num1.getText().toString());
            int n2=Integer.parseInt(num2.getText().toString());
            int result=n1-n2;
            res.setText(result);
        }
    }
}
