package com.example.waiterapp.activity.submittedorder;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.waiterapp.R;
import com.example.waiterapp.adapter.ListSubmittedAdapter;

public class SubmittedOrderActivity extends AppCompatActivity {

    ListSubmittedAdapter listSubmittedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submitted_order);
    }
}