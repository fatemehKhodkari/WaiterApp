package com.example.waiterapp.activity.grouping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.waiterapp.R;
import com.example.waiterapp.activity.customer.AddEditCostomerActivity;
import com.example.waiterapp.activity.customer.CustomerActivity;
import com.example.waiterapp.database.DatabaseHelper;
import com.example.waiterapp.database.dao.GroupingDao;
import com.example.waiterapp.model.Grouping;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

public class GroupingActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    GroupingDao groupingDao;
    FloatingActionButton floatingActionButton;
    SlidrInterface slidrInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grouping);

        slidrInterface = Slidr.attach(this);

        init();
        set_floatingActionBttn();
    }


    public void init(){
        floatingActionButton = findViewById(R.id.grouping_flabttn);
    }


    public void set_floatingActionBttn(){
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GroupingActivity.this, AddEditGroupingActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in , android.R.anim.fade_out);
            }
        });
    }
}