package com.example.waiterapp.activity.login;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.waiterapp.R;
import com.example.waiterapp.activity.homepage.HomeActivity;


public class login extends AppCompatActivity {

    ImageView imageView;
    ImageView imageView1;
    TextView buttonlogin;
    TextView Register_tv;
    Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView= findViewById(R.id.img_back);
        imageView1= findViewById(R.id.img_back_blur);
        Register_tv = findViewById(R.id.Register_tv);

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


        buttonlogin=findViewById(R.id.buttonlogin);

        buttonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent a = new Intent(login.this, HomeActivity.class);
                startActivity(a);
            }
        });

        register_dialog();


    }

    private void register_dialog(){
        Register_tv.setOnClickListener(view -> {

            dialog = new Dialog(this);
            dialog.setContentView(R.layout.regster_layout);
            dialog.show();

//            dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialog_desin));
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
//            dialog.getWindow().getAttributes().windowAnimations = R.style.animation;



        });
    }
}