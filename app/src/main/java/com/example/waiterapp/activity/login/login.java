package com.example.waiterapp.activity.login;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.waiterapp.R;
import com.example.waiterapp.activity.homepage.HomeActivity;
import com.example.waiterapp.database.DatabaseHelper;
import com.example.waiterapp.database.dao.UserDao;
import com.example.waiterapp.helper.App;
import com.example.waiterapp.model.User;
import com.google.android.material.textfield.TextInputEditText;


public class login extends AppCompatActivity {

    private ImageView imageView;
    private ImageView imageView1;
    private TextView buttonlogin;
    private TextView Register_tv;
    private TextInputEditText input_user_tv , input_pass_tv ;
    private Dialog dialog;
    private UserDao userDao;
    private CheckBox checkBox;
    private String userN , passW ;
    private DatabaseHelper databaseHelper;
    private SharedPreferences sharedPreferences;
    public static final String CafePref = "CafePrefernce";
    public static final String Name = "nameKey";
    public static final String Pass = "passKey";
    private static final int STORAGE_PERMISSION_CODE = 101;
    public static final int CALL_PERMISSION_CODE = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(CafePref , MODE_PRIVATE);

        init_database();
        initID();
        set_anim();
        set_bttnlogin();
        register_dialog();
        set_checkBox();

    }

    public void init_database(){
        databaseHelper = App.getDatabase();
        userDao = databaseHelper.userDao();
    }

    private void initID(){

        imageView= findViewById(R.id.img_back);
        imageView1= findViewById(R.id.img_back_blur);
        Register_tv = findViewById(R.id.Register_tv);
        buttonlogin = findViewById(R.id.buttonlogin);
        input_user_tv = findViewById(R.id.input_user_tv);
        input_pass_tv = findViewById(R.id.input_pass_tv);
        checkBox = findViewById(R.id.remember);
    }
    private void set_anim(){

        final Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setDuration(1500);
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {imageView.setVisibility(View.GONE);}

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        imageView.startAnimation(fadeOut);
    }

    private void set_checkBox(){
        checkBox.setOnClickListener(view -> {
            String getName = input_user_tv.getText().toString();
            String getPass = input_pass_tv.getText().toString();

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(Name , getName);
            editor.putString(Pass , getPass);
            editor.apply();
        });
        if(sharedPreferences.contains(Name) && sharedPreferences.contains(Pass)){
            input_user_tv.setText(sharedPreferences.getString(Name , null));
            input_pass_tv.setText(sharedPreferences.getString(Pass , null));
            checkBox.setChecked(true);
        }
    }

    private void set_bttnlogin(){
        buttonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userN = input_user_tv.getText().toString();
                passW = input_pass_tv.getText().toString();

                if(TextUtils.isEmpty(userN) || TextUtils.isEmpty(passW)){
                    Toast.makeText(getApplicationContext(), "لطفا فیلد ها را تکمیل کنید!", Toast.LENGTH_SHORT).show();
                }else if(userDao.getUser(userN,passW) == null){
                    Toast.makeText(getApplicationContext(), "همچین کافیشاپی وجود ندارد!", Toast.LENGTH_SHORT).show();
                }else{
                    if(checkPermission()) {
                        Intent login = new Intent(login.this, HomeActivity.class);
                        startActivity(login);
                        finish();
                    }
                }
            }
        });
    }
    private void register_dialog(){
        Register_tv.setOnClickListener(view -> {

            dialog = new Dialog(this);
            dialog.setContentView(R.layout.regster_layout);

            TextInputEditText USERname = (TextInputEditText)dialog.findViewById(R.id.register_user_name_ed);
            TextInputEditText passWord = (TextInputEditText)dialog.findViewById(R.id.register_user_pass_ed);
            TextView register_new_user = (TextView)dialog.findViewById(R.id.register_new_user);

            register_new_user.setOnClickListener(view1 -> {

                String getName = USERname.getText().toString();
                String getPass = passWord.getText().toString();

                if(TextUtils.isEmpty(getName) || TextUtils.isEmpty(getPass)){
                    Toast.makeText(getApplicationContext(), "لطفا فیلد ها را تکمیل کنید!", Toast.LENGTH_SHORT).show();
                }else {
                    userDao.insertUser(new User(getName , getPass));
                    Toast.makeText(getApplicationContext(), "ثبت نام شما با موفقیت انجام شد", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }

            });

            dialog.show();

            dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        });
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == STORAGE_PERMISSION_CODE ) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Storage Permission Granted", Toast.LENGTH_SHORT).show();

            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("desc_need_permission");
                builder.setPositiveButton("ok_permission", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(intent, STORAGE_PERMISSION_CODE);
                    }
                });
                builder.setNegativeButton("exit_app", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        finish();
                    }
                });
                builder.show();
            }
        }
    }

    public Boolean checkPermission() {
        String[] permission = {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CALL_PHONE};
        int requestCode = STORAGE_PERMISSION_CODE;
        int requestCode_call = CALL_PERMISSION_CODE;
        if (ContextCompat.checkSelfPermission(this, permission[0]) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[] { permission[0] }, requestCode);
        }else if (ContextCompat.checkSelfPermission(this, permission[1]) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{permission[1]}, requestCode);
        }else if (ContextCompat.checkSelfPermission(this, permission[2]) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{permission[2]}, requestCode_call);
        }
        else {
            return true;
        }
        return false;
    }
}