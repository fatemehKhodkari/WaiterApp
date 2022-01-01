package com.example.waiterapp.activity.submittedorderdetail;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.waiterapp.R;
import com.example.waiterapp.adapter.SubmittedOrderDetailAdapter;
import com.example.waiterapp.database.DatabaseHelper;
import com.example.waiterapp.database.dao.CustomerDao;
import com.example.waiterapp.database.dao.DetailOrderDao;
import com.example.waiterapp.database.dao.SubmitOrderDao;
import com.example.waiterapp.model.Customer;

public class SubmittedOrderDetailActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private DetailOrderDao detailOrderDao;
    private SubmitOrderDao submitOrderDao;
    private CustomerDao customerDao;
    private SubmittedOrderDetailAdapter submittedOrderDetailAdapter;
    private String
            code ,
            orderer_name ,
            total_detail ;
    private RecyclerView recyclerView;
    private int orderer_id;
    private TextView
            orderer_name_tv ,
            orderer_phone_tv ,
            total;
    private ImageView arrow_beck_ic;
    private Customer customer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submitted_order_detail);

        check_intent();
        initID();
        set_recycler();
        set_orderer_info();
        set_total();

    }

    private void check_intent(){
        if(getIntent().getExtras() != null){
            code = getIntent().getStringExtra("code");
            orderer_id = getIntent().getIntExtra("orderer_id" , 0);
            orderer_name = getIntent().getStringExtra("orderer_name");
            total_detail = getIntent().getStringExtra("total");
        }
    }

    private void initID(){
        orderer_name_tv = findViewById(R.id.orderer_name_detail_tv);
        orderer_phone_tv = findViewById(R.id.ordere_phone_detail_tv);
        arrow_beck_ic = findViewById(R.id.back_submitted);
        total = findViewById(R.id.total_detail);
        recyclerView = findViewById(R.id.detail_recycler);

    }

    private void set_recycler(){
        recyclerView.setHasFixedSize(true);
        submittedOrderDetailAdapter = new SubmittedOrderDetailAdapter(this,detailOrderDao.getSpecificOrder(code));
        recyclerView.setAdapter(submittedOrderDetailAdapter);
    }

    private void set_orderer_info(){
        orderer_name_tv.setText(orderer_name);
        orderer_phone_tv.setText(customerDao.getID(orderer_id).phone);
        arrow_beck_ic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void set_total(){
        total.setText(total_detail);
    }
}