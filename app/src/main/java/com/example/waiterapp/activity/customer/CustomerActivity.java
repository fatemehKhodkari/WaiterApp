package com.example.waiterapp.activity.customer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.waiterapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CustomerActivity extends AppCompatActivity {

    FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        init();
        set_floatingActtionButton();
    }
    public void init(){
        floatingActionButton = findViewById(R.id.customer_flabttn);
    }

    public void set_floatingActtionButton(){
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerActivity.this, AddEditCostomerActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in , android.R.anim.fade_out);
            }
        });
    }
}