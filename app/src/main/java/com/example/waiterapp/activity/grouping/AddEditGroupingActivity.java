package com.example.waiterapp.activity.grouping;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.waiterapp.R;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

public class AddEditGroupingActivity extends AppCompatActivity {

    SlidrInterface slidrInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_grouping);

        slidrInterface = Slidr.attach(this);


    }
}