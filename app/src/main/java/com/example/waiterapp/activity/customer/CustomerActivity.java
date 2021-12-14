package com.example.waiterapp.activity.customer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.waiterapp.R;
import com.example.waiterapp.adapter.CustomerAdapter;
import com.example.waiterapp.database.DatabaseHelper;
import com.example.waiterapp.database.dao.CustomerDao;
import com.example.waiterapp.helper.App;
import com.example.waiterapp.model.Customer;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

import java.util.ArrayList;

public class CustomerActivity extends AppCompatActivity {

    FloatingActionButton floatingActionButton;
    RecyclerView recyclerView;
    DatabaseHelper databaseHelper;
    CustomerDao customerDao;
    Customer customerr;
    private int poss;
    CustomerAdapter customerAdapter;
    private boolean for_order = false;
    private SlidrInterface slidrInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        slidrInterface = Slidr.attach(this);

        databaseHelper = App.getDatabase();
        customerDao = databaseHelper.customerDao();

        check_intent();
        init();
        set_floatingActtionButton();
        hide_floatingActionButton();

        set_recyclerView();
    }

    public void check_intent(){
        if (getIntent() != null) {
            for_order = getIntent().getBooleanExtra("for_order", false);
        }
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
        customerAdapter = new CustomerAdapter(new ArrayList<>() , this,new CustomerAdapter.Listener(){
            @Override
            public void onClickListener(Customer customer, int pos) {
//                customerr = customer;
//                poss = pos;
                if (for_order) {
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("json_customer", new Gson().toJson(customer));
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                } else {
                    customerAdapter.showDialogBSheet(pos);
                }
            }
        });
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