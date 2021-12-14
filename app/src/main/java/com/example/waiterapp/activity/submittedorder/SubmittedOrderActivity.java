package com.example.waiterapp.activity.submittedorder;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.waiterapp.R;
import com.example.waiterapp.adapter.ListSubmittedAdapter;
import com.example.waiterapp.database.DatabaseHelper;
import com.example.waiterapp.database.dao.SubmitOrderDao;
import com.example.waiterapp.helper.App;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

public class SubmittedOrderActivity extends AppCompatActivity {

    private ListSubmittedAdapter listSubmittedAdapter;
    private RecyclerView recyclerView;
    private SlidrInterface slidrInterface;
    private DatabaseHelper databaseHelper;
    private SubmitOrderDao submitOrderDao;
    private TextView not_submitted_order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submitted_order);
        
        slidrInterface = Slidr.attach(this);

        call_db();
        initID();
        initRecycler();

    }

    private void call_db(){
        databaseHelper = App.getDatabase();
        submitOrderDao = databaseHelper.submitOrderDao();
    }
    private void initID(){
        recyclerView = findViewById(R.id.submited_ordering_recycler);
        not_submitted_order = findViewById(R.id.not_submitted_order);
    }

    private void initRecycler(){
        recyclerView.setHasFixedSize(true);
        listSubmittedAdapter = new ListSubmittedAdapter(this,submitOrderDao.getOrderList());
        recyclerView.setAdapter(listSubmittedAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(submitOrderDao.getOrderList().size() != 0){
            not_submitted_order.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }
}