package com.matictechnology.leadersrating.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.matictechnology.leadersrating.R;
import com.matictechnology.leadersrating.activity.FilePicker;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by matic on 25/5/16.
 */
public class FragmentCreateProfileBasic extends Fragment
{
    String full_path;
    Bitmap picture_bitmap = null;
    String login;
    int image_flag=-1;
    Bitmap cover_bitmap = null;

    String fullPath;
    ImageStorage cover_imstrg,picture_imstrg;
    private static final int REQUEST_PATH = 1;
    ImageView profile_edit_profile_pic,profile_edit_cover_pic;
    private static final int REQUEST_PICK_FILE = 1;
    public final static String APP_PATH_SD_CARD = "/LeadersRating";
    OnDataPass dataPasser;
    private Button Browse;
    private File selectedFile, got_file;
    ArrayList<String> cat_list;
    ProgressDialog pd;

    EditText profile_edit_name, profile_edit_phone, profile_edit_email, profile_edit_username, profile_edit_password;
    EditText profile_edit_work_name, profile_edit_address, profile_edit_country;
    Spinner profile_edit_category;

    String reg_name="", reg_phone="", reg_email="", reg_username="", reg_password="", reg_work_name="", reg_address="", reg_country="", pictuer_img_url ="", picture_img_name ="", picture_img_path ="",fb_id="",cover_img_url ="", cover_img_name ="", cover_img_path ="";
    int reg_category;

    public void getData(String reg_name1, String reg_phone1, String reg_email1, String reg_username1, String reg_password1, String reg_work_name1, String reg_address1, String reg_country1, int reg_category1)
    {
        reg_name = reg_name1;
        reg_phone = reg_phone1;
        reg_email = reg_email1;
        reg_username = reg_username1;
        reg_password = reg_password1;
        reg_work_name = reg_work_name1;
        reg_address = reg_address1;
        reg_country = reg_country1;
        reg_category = reg_category1;
        login="manual";
    }

    public void getData(String reg_name1, String reg_phone1, String reg_email1, String reg_username1, String reg_password1, String reg_work_name1, String reg_address1, String reg_country1, int reg_category1, String img_url1,String fb_id1,String cover_url1)
    {
        reg_name = reg_name1;
        reg_phone = reg_phone1;
        reg_email = reg_email1;
        reg_username = reg_username1;
        reg_password = reg_password1;
        reg_work_name = reg_work_name1;
        reg_address = reg_address1;
        reg_country = reg_country1;
        reg_category = reg_category1;
        pictuer_img_url = img_url1;
        cover_img_url=cover_url1;
        fb_id=fb_id1;
        login="fb";
        picture_img_name = reg_phone + "_profile.jpg";
        cover_img_name = reg_phone + "_cover.jpg";

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        LayoutInflater inflater1 = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater1.inflate(R.layout.fragment_create_profile_basic, container, false);

        profile_edit_name = (EditText) view.findViewById(R.id.profile_edit_name);
        profile_edit_phone = (EditText) view.findViewById(R.id.profile_edit_phone);
        profile_edit_email = (EditText) view.findViewById(R.id.profile_edit_email);
        profile_edit_username = (EditText) view.findViewById(R.id.profile_edit_username);
        profile_edit_password = (EditText) view.findViewById(R.id.profile_edit_password);
        profile_edit_work_name = (EditText) view.findViewById(R.id.profile_edit_work_name);
        profile_edit_address = (EditText) view.findViewById(R.id.profile_edit_address);
        profile_edit_country = (EditText) view.findViewById(R.id.profile_edit_country);
        profile_edit_category = (Spinner) view.findViewById(R.id.profile_edit_category);

        cat_list = new ArrayList<>();
        cat_list.add("Select Category");
        cat_list.add("Business");
        cat_list.add("Politics");
        cat_list.add("Doctor");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, cat_list);

        profile_edit_category.setAdapter(adapter);

        profile_edit_name.setText(reg_name);
        profile_edit_phone.setText(reg_phone);
        profile_edit_email.setText(reg_email);
        profile_edit_username.setText(reg_username);
        profile_edit_password.setText(reg_password);
        profile_edit_work_name.setText(reg_work_name);
        profile_edit_address.setText(reg_address);
        profile_edit_country.setText(reg_country);
        profile_edit_category.setSelection(reg_category);

        profile_edit_profile_pic = (ImageView) view.findViewById(R.id.profile_edit_profile_pic);
        profile_edit_cover_pic = (ImageView) view.findViewById(R.id.profile_edit_cover_pic );

        Log.e("login",""+ login +",");

        if(login!=null)
        {
            if(login.equals("manual"))
            {
                Log.e("login","in manual");
                profile_edit_profile_pic.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        image_flag=1;
                        Intent intent = new Intent(getActivity(), FilePicker.class);
                        startActivityForResult(intent, REQUEST_PICK_FILE);
                    }
                });

                profile_edit_cover_pic.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        image_flag=2;
                        Intent intent = new Intent(getActivity(), FilePicker.class);
                        startActivityForResult(intent, REQUEST_PICK_FILE);
                    }
                });
            }
            else if(login.equals("fb"))
            {
                Log.e("login","in fb");
                pd = new ProgressDialog(getActivity());
                pd.setCancelable(false);
                pd.show();
                image_download task = new image_download();
                task.execute("https://graph.facebook.com/" + fb_id + "/picture?type=large",cover_img_url);
            }
        }




        //Inflate the layout for this fragment
        return view;
    }

    public class image_download extends AsyncTask<String, Void, Void>
    {
        @Override
        protected Void doInBackground(String... strings)
        {
            final String picture_file_url = strings[0];


            System.out.println("image " + picture_file_url);
            //new DownloadFileFromURL().execute(file_url);

            //downloadFile(file_url, adds.get(count).getImage().toString());

            try
            {
                URL url = new URL(picture_file_url);
                URLConnection conn = url.openConnection();
                picture_bitmap = BitmapFactory.decodeStream(conn.getInputStream());
            }
            catch (Exception ex)
            {
            }

            final String cover_file_url = strings[1];


            System.out.println("image " + cover_file_url);

            try
            {
                URL url = new URL(cover_file_url);
                URLConnection conn = url.openConnection();
                cover_bitmap = BitmapFactory.decodeStream(conn.getInputStream());
            }
            catch (Exception ex)
            {
            }

            picture_imstrg = new ImageStorage();

            picture_bitmap= getRoundedBitmap(picture_bitmap);
            Log.e("frome fragment create","..");

            Log.e("picture=>",""+picture_img_name);


            image_flag=1;
            picture_imstrg .saveToSdCard(picture_bitmap, picture_img_name);
            Log.e("picture=>",""+picture_img_path);

            cover_imstrg = new ImageStorage();

            Log.e("cover=>",""+cover_img_name);

            image_flag=2;
            cover_imstrg.saveToSdCard(cover_bitmap, cover_img_name);
            Log.e("cover=>",""+cover_img_path);
            return null;
        }

        @Override
        protected void onPostExecute(Void v)
        {

            profile_edit_profile_pic.setImageBitmap(picture_bitmap);
            profile_edit_cover_pic.setImageBitmap(cover_bitmap);
            pd.dismiss();
            //saveImageToExternalStorage(bm);
            super.onPostExecute(v);
        }
    }

    public class ImageStorage
     {
        public String saveToSdCard(Bitmap bitmap, String filename)
        {

            String stored = null;

            File sdcard = Environment.getExternalStorageDirectory();

            File folder = new File(sdcard.getAbsoluteFile(), APP_PATH_SD_CARD);//the dot makes this directory hidden to the user
            if(image_flag==1)
            {

                picture_img_name = filename;
                picture_img_path = sdcard.getAbsoluteFile() + APP_PATH_SD_CARD + "/" + picture_img_name;
                folder.mkdir();
                File file = new File(folder.getAbsoluteFile(), filename);

                //if (file.exists())
                  //  return stored;

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
            }
            else if(image_flag==2)
            {
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
            }

            return stored;
        }

    }

    public boolean send_data()
    {
        Pattern simple_string = Pattern.compile("[^A-Za-z' ']");
        Pattern simple_number = Pattern.compile("[^0-9]");

        String EMAIL_REGEX = "[a-zA-Z0-9\\.]+@[a-zA-Z0-9\\-\\_\\.]+\\.[a-zA-Z0-9]{3}";

        String emailid;
        boolean email_check = false;
        emailid = profile_edit_email.getText().toString();

        email_check = emailid.matches(EMAIL_REGEX);


        String name_check;
        String phone_check;

        boolean name_flag = true, phone_flag = true;

        Matcher name_matcher, phone_matcher;

        name_matcher = simple_string.matcher(profile_edit_name.getText().toString());
        name_flag = name_matcher.find();

        phone_matcher = simple_number.matcher(profile_edit_phone.getText().toString());
        phone_flag = phone_matcher.find();


        if (profile_edit_name.getText().toString().equals(""))
        {
            profile_edit_name.requestFocus();
            //Snackbar.make(rel,"Enter a Valid Name",Snackbar.LENGTH_LONG).show();
            Toast.makeText(getActivity(), "Enter a Valid Name", Toast.LENGTH_SHORT).show();
        }
        else if (name_flag)
        {
            profile_edit_name.requestFocus();
            //Snackbar.make(rel,"Enter a Valid Name",Snackbar.LENGTH_LONG).show();
            Toast.makeText(getActivity(), "Enter a Valid Name", Toast.LENGTH_SHORT).show();
        }
        else if (profile_edit_phone.getText().toString().equals("") || profile_edit_phone == null)
        {
            profile_edit_phone.requestFocus();
            //Snackbar.make(rel,"Enter a Valid Number..",Snackbar.LENGTH_LONG).show();
            Toast.makeText(getActivity(), "Enter a Valid Number..", Toast.LENGTH_SHORT).show();
        }
        else if (phone_flag)
        {
            profile_edit_phone.requestFocus();
            //Snackbar.make(rel,"Enter a Valid Number..",Snackbar.LENGTH_LONG).show();
            Toast.makeText(getActivity(), "Enter a Valid Number..", Toast.LENGTH_SHORT).show();
        }
        else if (profile_edit_email.getText().toString().equals("") || profile_edit_email == null)
        {
            profile_edit_email.requestFocus();
            //Snackbar.make(rel,"Enter a Valid Email ID..",Snackbar.LENGTH_LONG).show();
            Toast.makeText(getActivity(), "Enter a Valid Email ID..", Toast.LENGTH_SHORT).show();
        }
        else if (!email_check)
        {
            profile_edit_email.requestFocus();
            //Snackbar.make(rel,"Enter a Valid Email ID..",Snackbar.LENGTH_LONG).show();
            Toast.makeText(getActivity(), "Enter a Valid Email ID..", Toast.LENGTH_SHORT).show();
        }//profile_edit_name,profile_edit_phone,profile_edit_email,profile_edit_username,profile_edit_password;
        else if (profile_edit_password.getText().toString().equals("") || profile_edit_password == null)
        {
            profile_edit_password.requestFocus();
            //Snackbar.make(rel,"Enter a Valid Password!!",Snackbar.LENGTH_LONG).show();
            Toast.makeText(getActivity(), "Enter a Valid Password!!", Toast.LENGTH_SHORT).show();
        }
        else if (profile_edit_work_name.getText().toString().equals("") || profile_edit_work_name == null)
        {
            profile_edit_work_name.requestFocus();
            //Snackbar.make(rel,"Enter Valid Work Name..",Snackbar.LENGTH_LONG).show();
            Toast.makeText(getActivity(), "Enter Valid Work Name..", Toast.LENGTH_SHORT).show();
        }
        else if (profile_edit_address.getText().toString().equals("") || profile_edit_address == null)
        {
            profile_edit_address.requestFocus();
            //Snackbar.make(rel,"Enter Valid Address..",Snackbar.LENGTH_LONG).show();
            Toast.makeText(getActivity(), "Enter Valid Address..", Toast.LENGTH_SHORT).show();
        }
        else if (profile_edit_country.getText().toString().equals("") || profile_edit_country == null)
        {
            profile_edit_country.requestFocus();
            //Snackbar.make(rel,"Enter Valid Country..",Snackbar.LENGTH_LONG).show();
            Toast.makeText(getActivity(), "Enter Valid Country..", Toast.LENGTH_SHORT).show();
        }
        else if (profile_edit_category.getSelectedItem().toString().equals("Select Category"))
        {
            //Snackbar.make(rel,"Select a Category..",Snackbar.LENGTH_LONG).show();
            Toast.makeText(getActivity(), "Select a Category..", Toast.LENGTH_SHORT).show();
        }
        else
        {
            passData(profile_edit_name.getText().toString(), profile_edit_phone.getText().toString(),
                        profile_edit_email.getText().toString(), profile_edit_username.getText().toString(),
                        profile_edit_password.getText().toString(), profile_edit_work_name.getText().toString(),
                        profile_edit_address.getText().toString(), profile_edit_country.getText().toString(),
                        profile_edit_category.getSelectedItem().toString(),picture_img_name, picture_img_path,
                        cover_img_name,cover_img_path);


            return true;
        }


        return false;
    }

    public void passData(String name, String phone, String email, String username, String password,
                         String work_name, String address, String country, String category, String picture, String picture_path
                        ,String cover, String cover_path)
    {
        dataPasser.onDataPass(name, phone, email, username, password,
                work_name, address, country, category, picture, picture_path,cover,cover_path);
    }

    public interface OnDataPass
    {
        public void onDataPass(String name, String phone, String email, String username, String password,
                               String work_name, String address, String country, String category, String picture, String picture_path
                                ,String cover,String cover_path);
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        dataPasser = (OnDataPass) activity;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {

        //if(resultCode == RESULT_OK) {

        switch (requestCode)
        {

            case REQUEST_PICK_FILE:
                Log.e("FilPickr.EXTRAFILEPATH",""+FilePicker.EXTRA_FILE_PATH);
                if (data.hasExtra(FilePicker.EXTRA_FILE_PATH))
                {
                    selectedFile = new File(data.getStringExtra(FilePicker.EXTRA_FILE_PATH));
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                    Bitmap bitmap = BitmapFactory.decodeFile(selectedFile.getPath(), options);

                    if(image_flag==1)
                    {
                        picture_img_name = reg_phone + "_profile.jpg";
                        picture_imstrg = new ImageStorage();
                        picture_bitmap= getRoundedBitmap(bitmap);
                        profile_edit_profile_pic.setImageBitmap(picture_bitmap);
                        //picture_imstrg .saveToSdCard(picture_bitmap, picture_img_name);
                        saveImageToExternalStorage(picture_bitmap,picture_img_name);
                        Log.e("picture:",""+picture_img_name);
                        Log.e("picture:",""+picture_img_path);
                    }
                    else if(image_flag==2)
                    {

                        cover_img_name = reg_phone + "_cover.jpg";
                        cover_imstrg = new ImageStorage();
                        cover_bitmap= bitmap;
                        profile_edit_cover_pic.setImageBitmap(cover_bitmap);
                        //cover_imstrg.saveToSdCard(cover_bitmap, cover_img_name);
                        saveImageToExternalStorage(cover_bitmap,cover_img_name);
                        Log.e("cover:",""+cover_img_name);
                        Log.e("cover:",""+cover_img_path);
                    }

                    /*Bitmap bitmap1 = getRoundedBitmap(bitmap);




                    saveImageToExternalStorage(bitmap);*/


                }
                break;
        }
    }

    public boolean saveImageToExternalStorage(Bitmap image,String name)
    {
        fullPath = Environment.getExternalStorageDirectory().getAbsolutePath() + APP_PATH_SD_CARD ;
        Log.e("full_path",""+fullPath);
        try
        {
            File dir = new File(fullPath);
            if (!dir.exists())
            {
                dir.mkdirs();
            }

            OutputStream fOut = null;

            File file = new File(fullPath, name);
            file.createNewFile();
            fOut = new FileOutputStream(file);

            // 100 means no compression, the lower you go, the stronger the compression
            image.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
            MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), file.getAbsolutePath(), file.getName(), file.getName());
            if(image_flag==1)
            {
                picture_img_path=fullPath+"/"+name;
                Log.e("picture_path:",""+picture_img_path);
            }
            else if(image_flag==2)
            {
                cover_img_path=fullPath+"/"+name;

                Log.e("cover_path:",""+cover_img_path);

            }


            /*if (selectedFile.getName().toString() == null || selectedFile.getName().equals(""))
            {


            }
            else
            {
                File file = new File(fullPath, selectedFile.getName());
                file.createNewFile();
                fOut = new FileOutputStream(file);

                // 100 means no compression, the lower you go, the stronger the compression
                image.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                fOut.flush();
                fOut.close();
                MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), file.getAbsolutePath(), file.getName(), file.getName());
            }*/


            return true;

        }
        catch (Exception e)
        {
            Log.e("saveToExternalStorage()", e.getMessage());
            return false;
        }
    }

    public static Bitmap getRoundedBitmap(Bitmap bitmap)
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
}