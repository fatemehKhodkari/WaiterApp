package com.example.waiterapp.activity.customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.waiterapp.R;
import com.example.waiterapp.adapter.CustomerAdapter;
import com.example.waiterapp.database.DatabaseHelper;
import com.example.waiterapp.database.dao.CustomerDao;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class CustomerActivity extends AppCompatActivity {

    FloatingActionButton floatingActionButton;
    RecyclerView recyclerView;
    DatabaseHelper databaseHelper;
    CustomerDao customerDao;
    CustomerAdapter customerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        init();
        set_floatingActtionButton();
        hide_floatingActionButton();
        databaseHelper = DatabaseHelper.getInstance(getApplicationContext());
        customerDao = databaseHelper.customerDao();
        set_recyclerView();
    }

    public void init(){
        recyclerView = findViewById(R.id.customer_recycler);
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

    public void hide_floatingActionButton(){
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if(dy > 0){
                    floatingActionButton.hide();
                }else{
                    floatingActionButton.show();
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    public void set_recyclerView(){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        customerAdapter = new CustomerAdapter(new ArrayList<>() , this);
        recyclerView.setAdapter(customerAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(databaseHelper != null)
            databaseHelper.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(customerAdapter != null)
            customerAdapter.addList(customerDao.getCustomerList());
    }
}