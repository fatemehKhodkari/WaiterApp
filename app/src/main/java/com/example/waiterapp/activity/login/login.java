package com.example.waiterapp.activity.login;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
    private String userN , passW ;
    private DatabaseHelper databaseHelper;
    private SharedPreferences sharedPreferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init_database();
        initID();
        set_anim();
        set_bttnlogin();
        register_dialog();
        sharedPreferences = getSharedPreferences("shpr" , MODE_PRIVATE);

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
                    Intent login = new Intent(login.this, HomeActivity.class);
                    startActivity(login);
                    finish();
                }


            }
        });
    }
    private void register_dialog(){
        Register_tv.setOnClickListener(view -> {

            dialog = new Dialog(this);
            dialog.setContentView(R.layout.regster_layout);

            TextInputEditText USERname = (TextInputEditText)dialog.findViewById(R.id.register_user_name_ed);
            TextInputEditText passWord = (TextInputEditText)dialog.findViewById(R.id.register_user_name_ed);
            TextView register_new_user = (TextView)dialog.findViewById(R.id.register_new_user);

            register_new_user.setOnClickListener(view1 -> {

                String getName = USERname.getText().toString();
                String getPass = passWord.getText().toString();

                if(TextUtils.isEmpty(getName) || TextUtils.isEmpty(getPass)){
                    Toast.makeText(getApplicationContext(), "لطفا فیلد ها را تکمیل کنید!", Toast.LENGTH_SHORT).show();
                }else {
//                    System.out.print("---------------------------------------------/n/npass and name:"+getName+"   "+getPass);
                    userDao.insertUser(new User(getName , getPass));
                    Toast.makeText(getApplicationContext(), "ثبت نام شما با موفقیت انجام شد", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }

            });

            dialog.show();

            dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        });
    }
}