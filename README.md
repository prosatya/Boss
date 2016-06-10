# test

fragment two

<!--
<ScrollView android:layout_width="match_parent"
    android:layout_height="match_parent"
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto">
       <LinearLayout
           android:layout_width="match_parent"
           android:layout_height="wrap_content"
           android:orientation="vertical">

              <TextView
                  android:layout_marginTop="10dp"
                  android:textAlignment="center"
                  android:textColor="#000000"
                  android:textSize="22sp"
                  android:layout_width="match_parent"
                  android:layout_height="wrap_content"
                  android:text="@string/education_details"
                  android:textStyle="bold" />

              <LinearLayout
                  android:id="@+id/lin_edu1"
                  android:visibility="gone"
                  android:orientation="vertical"
                  android:layout_width="match_parent"
                  android:layout_height="wrap_content">

                     <com.rengwuxian.materialedittext.MaterialEditText
                         android:id="@+id/profile_edit_school"
                         android:layout_marginStart="20dp"
                         android:layout_marginEnd="10dp"
                         android:layout_width="match_parent"
                         android:layout_height="wrap_content"
                         android:hint="@string/school"
                         android:textCursorDrawable="@drawable/text_cursor_cyan"
                         app:met_floatingLabel="highlight"
                         app:met_maxCharacters="20"
                         app:met_primaryColor="?colorAccent"
                         app:met_singleLineEllipsis="true" />

                     <com.rengwuxian.materialedittext.MaterialEditText
                         android:id="@+id/profile_edit_degree"
                         android:layout_marginStart="20dp"
                         android:layout_marginEnd="10dp"
                         android:layout_width="match_parent"
                         android:layout_height="wrap_content"
                         android:hint="@string/degree"
                         android:textCursorDrawable="@drawable/text_cursor_cyan"
                         app:met_floatingLabel="highlight"
                         app:met_maxCharacters="20"
                         app:met_primaryColor="?colorAccent"
                         app:met_singleLineEllipsis="true" />

                     <com.rengwuxian.materialedittext.MaterialEditText
                         android:id="@+id/profile_edit_specialization"
                         android:layout_marginStart="20dp"
                         android:layout_marginEnd="10dp"
                         android:layout_width="match_parent"
                         android:layout_height="wrap_content"
                         android:hint="@string/specialization"
                         android:textCursorDrawable="@drawable/text_cursor_cyan"
                         app:met_floatingLabel="highlight"
                         app:met_maxCharacters="20"
                         app:met_primaryColor="?colorAccent"
                         app:met_singleLineEllipsis="true" />

                     <com.rengwuxian.materialedittext.MaterialEditText
                         android:id="@+id/profile_edit_school_place"
                         android:layout_marginStart="20dp"
                         android:layout_marginEnd="10dp"
                         android:layout_width="match_parent"
                         android:layout_height="wrap_content"
                         android:hint="@string/place"
                         android:textCursorDrawable="@drawable/text_cursor_cyan"
                         app:met_floatingLabel="highlight"
                         app:met_maxCharacters="20"
                         app:met_primaryColor="?colorAccent"
                         app:met_singleLineEllipsis="true" />

                     <com.rengwuxian.materialedittext.MaterialEditText
                         android:id="@+id/profile_edit_extra"
                         android:layout_marginStart="20dp"
                         android:layout_marginEnd="10dp"
                         android:layout_width="match_parent"
                         android:layout_height="wrap_content"
                         android:hint="@string/extra"
                         android:textCursorDrawable="@drawable/text_cursor_cyan"
                         app:met_floatingLabel="highlight"
                         app:met_maxCharacters="20"
                         app:met_primaryColor="?colorAccent"
                         app:met_singleLineEllipsis="true" />

              </LinearLayout>

              <LinearLayout
                  android:id="@+id/lin_edu2"
                  android:visibility="gone"
                  android:orientation="vertical"
                  android:layout_width="match_parent"
                  android:layout_height="wrap_content">

                     <com.rengwuxian.materialedittext.MaterialEditText
                         android:id="@+id/profile_edit_school2"
                         android:layout_marginStart="20dp"
                         android:layout_marginEnd="10dp"
                         android:layout_width="match_parent"
                         android:layout_height="wrap_content"
                         android:hint="@string/school"
                         android:textCursorDrawable="@drawable/text_cursor_cyan"
                         app:met_floatingLabel="highlight"
                         app:met_maxCharacters="20"
                         app:met_primaryColor="?colorAccent"
                         app:met_singleLineEllipsis="true" />

                     <com.rengwuxian.materialedittext.MaterialEditText
                         android:id="@+id/profile_edit_degree2"
                         android:layout_marginStart="20dp"
                         android:layout_marginEnd="10dp"
                         android:layout_width="match_parent"
                         android:layout_height="wrap_content"
                         android:hint="@string/degree"
                         android:textCursorDrawable="@drawable/text_cursor_cyan"
                         app:met_floatingLabel="highlight"
                         app:met_maxCharacters="20"
                         app:met_primaryColor="?colorAccent"
                         app:met_singleLineEllipsis="true" />

                     <com.rengwuxian.materialedittext.MaterialEditText
                         android:id="@+id/profile_edit_specialization2"
                         android:layout_marginStart="20dp"
                         android:layout_marginEnd="10dp"
                         android:layout_width="match_parent"
                         android:layout_height="wrap_content"
                         android:hint="@string/specialization"
                         android:textCursorDrawable="@drawable/text_cursor_cyan"
                         app:met_floatingLabel="highlight"
                         app:met_maxCharacters="20"
                         app:met_primaryColor="?colorAccent"
                         app:met_singleLineEllipsis="true" />

                     <com.rengwuxian.materialedittext.MaterialEditText
                         android:id="@+id/profile_edit_school_place2"
                         android:layout_marginStart="20dp"
                         android:layout_marginEnd="10dp"
                         android:layout_width="match_parent"
                         android:layout_height="wrap_content"
                         android:hint="@string/place"
                         android:textCursorDrawable="@drawable/text_cursor_cyan"
                         app:met_floatingLabel="highlight"
                         app:met_maxCharacters="20"
                         app:met_primaryColor="?colorAccent"
                         app:met_singleLineEllipsis="true" />

                     <com.rengwuxian.materialedittext.MaterialEditText
                         android:id="@+id/profile_edit_extra2"
                         android:layout_marginStart="20dp"
                         android:layout_marginEnd="10dp"
                         android:layout_width="match_parent"
                         android:layout_height="wrap_content"
                         android:hint="@string/extra"
                         android:textCursorDrawable="@drawable/text_cursor_cyan"
                         app:met_floatingLabel="highlight"
                         app:met_maxCharacters="20"
                         app:met_primaryColor="?colorAccent"
                         app:met_singleLineEllipsis="true" />

              </LinearLayout>

              <LinearLayout
                  android:id="@+id/lin_edu3"
                  android:visibility="gone"
                  android:orientation="vertical"
                  android:layout_width="match_parent"
                  android:layout_height="wrap_content">

                     <com.rengwuxian.materialedittext.MaterialEditText
                         android:id="@+id/profile_edit_school3"
                         android:layout_marginStart="20dp"
                         android:layout_marginEnd="10dp"
                         android:layout_width="match_parent"
                         android:layout_height="wrap_content"
                         android:hint="@string/school"
                         android:textCursorDrawable="@drawable/text_cursor_cyan"
                         app:met_floatingLabel="highlight"
                         app:met_maxCharacters="20"
                         app:met_primaryColor="?colorAccent"
                         app:met_singleLineEllipsis="true" />

                     <com.rengwuxian.materialedittext.MaterialEditText
                         android:id="@+id/profile_edit_degree3"
                         android:layout_marginStart="20dp"
                         android:layout_marginEnd="10dp"
                         android:layout_width="match_parent"
                         android:layout_height="wrap_content"
                         android:hint="@string/degree"
                         android:textCursorDrawable="@drawable/text_cursor_cyan"
                         app:met_floatingLabel="highlight"
                         app:met_maxCharacters="20"
                         app:met_primaryColor="?colorAccent"
                         app:met_singleLineEllipsis="true" />

                     <com.rengwuxian.materialedittext.MaterialEditText
                         android:id="@+id/profile_edit_specialization3"
                         android:layout_marginStart="20dp"
                         android:layout_marginEnd="10dp"
                         android:layout_width="match_parent"
                         android:layout_height="wrap_content"
                         android:hint="@string/specialization"
                         android:textCursorDrawable="@drawable/text_cursor_cyan"
                         app:met_floatingLabel="highlight"
                         app:met_maxCharacters="20"
                         app:met_primaryColor="?colorAccent"
                         app:met_singleLineEllipsis="true" />

                     <com.rengwuxian.materialedittext.MaterialEditText
                         android:id="@+id/profile_edit_school_place3"
                         android:layout_marginStart="20dp"
                         android:layout_marginEnd="10dp"
                         android:layout_width="match_parent"
                         android:layout_height="wrap_content"
                         android:hint="@string/place"
                         android:textCursorDrawable="@drawable/text_cursor_cyan"
                         app:met_floatingLabel="highlight"
                         app:met_maxCharacters="20"
                         app:met_primaryColor="?colorAccent"
                         app:met_singleLineEllipsis="true" />

                     <com.rengwuxian.materialedittext.MaterialEditText
                         android:id="@+id/profile_edit_extra3"
                         android:layout_marginStart="20dp"
                         android:layout_marginEnd="10dp"
                         android:layout_width="match_parent"
                         android:layout_height="wrap_content"
                         android:hint="@string/extra"
                         android:textCursorDrawable="@drawable/text_cursor_cyan"
                         app:met_floatingLabel="highlight"
                         app:met_maxCharacters="20"
                         app:met_primaryColor="?colorAccent"
                         app:met_singleLineEllipsis="true" />

              </LinearLayout>

              <LinearLayout
                  android:id="@+id/lin_add_edu"
                  android:clickable="true"
                  android:gravity="center"
                  android:layout_marginTop="20dp"
                  android:layout_width="match_parent"
                  android:layout_height="wrap_content"
                  android:orientation="horizontal">

                     <TextView
                         android:textSize="20sp"
                         android:textColor="#000000"
                         android:text="@string/add"
                         android:layout_width="wrap_content"
                         android:layout_height="wrap_content" />

                     <ImageView
                         android:layout_marginStart="15dp"
                         android:src="@drawable/add"
                         android:layout_width="30dp"
                         android:layout_height="30dp" />

           </LinearLayout>
    </LinearLayout>
</ScrollView>-->

fragment one

<ScrollView
       xmlns:android="http://schemas.android.com/apk/res/android"
       android:layout_width="match_parent"
       android:layout_height="match_parent">
       <LinearLayout
           xmlns:android="http://schemas.android.com/apk/res/android"
           android:layout_width="match_parent"
           android:layout_height="match_parent"
           xmlns:app="http://schemas.android.com/apk/res-auto"
           android:orientation="vertical">

              <TextView
                  android:layout_marginTop="10dp"
                  android:textAlignment="center"
                  android:textColor="#000000"
                  android:textSize="22sp"
                  android:layout_width="match_parent"
                  android:layout_height="wrap_content"
                  android:text="@string/basic_details"
                  android:textStyle="bold" />

              <LinearLayout
                  android:layout_gravity="center"
                  android:layout_marginStart="20dp"
                  android:layout_width="match_parent"
                  android:layout_height="wrap_content"
                  android:orientation="horizontal">

                     <ImageView
                         android:src="@drawable/blank_user"
                         android:layout_width="70dp"
                         android:layout_height="70dp" />

                     <com.rengwuxian.materialedittext.MaterialEditText
                         android:id="@+id/profile_edit_name"
                         android:layout_marginStart="20dp"
                         android:layout_marginEnd="10dp"
                         android:layout_width="match_parent"
                         android:layout_height="wrap_content"
                         android:hint="@string/name"
                         android:textCursorDrawable="@drawable/text_cursor_cyan"
                         app:met_floatingLabel="highlight"
                         app:met_maxCharacters="20"
                         app:met_primaryColor="?colorAccent"
                         app:met_singleLineEllipsis="true" />

              </LinearLayout>

              <com.rengwuxian.materialedittext.MaterialEditText
                  android:id="@+id/profile_edit_phone"
                  android:layout_marginStart="20dp"
                  android:layout_marginEnd="20dp"
                  android:layout_width="match_parent"
                  android:layout_height="wrap_content"
                  android:hint="@string/phone_number"
                  android:textCursorDrawable="@drawable/text_cursor_cyan"
                  app:met_floatingLabel="highlight"
                  app:met_maxCharacters="10"
                  app:met_primaryColor="?colorAccent"
                  app:met_singleLineEllipsis="true" />

              <com.rengwuxian.materialedittext.MaterialEditText
                  android:id="@+id/profile_edit_email"
                  android:layout_marginStart="20dp"
                  android:layout_marginEnd="20dp"
                  android:layout_width="match_parent"
                  android:layout_height="wrap_content"
                  android:hint="@string/email_id"
                  android:textCursorDrawable="@drawable/text_cursor_cyan"
                  app:met_floatingLabel="highlight"
                  app:met_maxCharacters="15"
                  app:met_primaryColor="?colorAccent"
                  app:met_singleLineEllipsis="true" />

              <com.rengwuxian.materialedittext.MaterialEditText
                  android:id="@+id/profile_edit_username"
                  android:layout_marginStart="20dp"
                  android:layout_marginEnd="20dp"
                  android:layout_width="match_parent"
                  android:layout_height="wrap_content"
                  android:hint="@string/username"
                  android:textCursorDrawable="@drawable/text_cursor_cyan"
                  app:met_floatingLabel="highlight"
                  app:met_maxCharacters="10"
                  app:met_primaryColor="?colorAccent"
                  app:met_singleLineEllipsis="true" />

              <com.rengwuxian.materialedittext.MaterialEditText
                  android:id="@+id/profile_edit_password"
                  android:inputType="textPassword"
                  android:layout_marginStart="20dp"
                  android:layout_marginEnd="20dp"
                  android:layout_width="match_parent"
                  android:layout_height="wrap_content"
                  android:hint="@string/password"
                  android:textCursorDrawable="@drawable/text_cursor_cyan"
                  app:met_floatingLabel="highlight"
                  app:met_maxCharacters="20"
                  app:met_primaryColor="?colorAccent"
                  app:met_singleLineEllipsis="true" />

              <com.rengwuxian.materialedittext.MaterialEditText
                  android:id="@+id/profile_edit_work_name"
                  android:layout_marginStart="20dp"
                  android:layout_marginEnd="20dp"
                  android:layout_width="match_parent"
                  android:layout_height="wrap_content"
                  android:hint="@string/work_name"
                  android:textCursorDrawable="@drawable/text_cursor_cyan"
                  app:met_floatingLabel="highlight"
                  app:met_maxCharacters="10"
                  app:met_primaryColor="?colorAccent"
                  app:met_singleLineEllipsis="true" />

              <Spinner
                  android:layout_marginEnd="20dp"
                  android:layout_marginStart="20dp"
                  android:id="@+id/profile_edit_category"
                  android:layout_width="match_parent"
                  android:layout_height="wrap_content"/>

              <com.rengwuxian.materialedittext.MaterialEditText
                  android:id="@+id/profile_edit_address"
                  android:layout_marginStart="20dp"
                  android:layout_marginEnd="20dp"
                  android:layout_width="match_parent"
                  android:layout_height="wrap_content"
                  android:hint="@string/address"
                  android:textCursorDrawable="@drawable/text_cursor_cyan"
                  app:met_floatingLabel="highlight"
                  app:met_maxCharacters="10"
                  app:met_primaryColor="?colorAccent"
                  app:met_singleLineEllipsis="true" />

              <com.rengwuxian.materialedittext.MaterialEditText
                  android:id="@+id/profile_edit_country"
                  android:layout_marginStart="20dp"
                  android:layout_marginEnd="20dp"
                  android:layout_width="match_parent"
                  android:layout_height="wrap_content"
                  android:hint="@string/country"
                  android:textCursorDrawable="@drawable/text_cursor_cyan"
                  app:met_floatingLabel="highlight"
                  app:met_maxCharacters="10"
                  app:met_primaryColor="?colorAccent"
                  app:met_singleLineEllipsis="true" />

       </LinearLayout>
</ScrollView>
# leaders-rating
