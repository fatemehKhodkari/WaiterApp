package com.example.waiterapp.activity.addordering;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.waiterapp.R;
import com.example.waiterapp.activity.customer.CustomerActivity;
import com.example.waiterapp.adapter.OrderingAdapter;
import com.example.waiterapp.database.DatabaseHelper;
import com.example.waiterapp.database.dao.DetailOrderDao;
import com.example.waiterapp.database.dao.SubmitOrderDao;
import com.example.waiterapp.model.Customer;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

public class AddOrderingActivity extends AppCompatActivity {

    private LottieAnimationView lottie;
    private RecyclerView recyclerView;
    private LinearLayout customer_orderer ;
    private TextView
            orderer_name_tv ,
            total_price_tv ,
            counting_orders_tv ,
            submit_ordering_tv;
    private RelativeLayout bttnvigtion_submit_order;
    private CardView counting_orders , add_ordering;
    private SlidrInterface slidrInterface;
    private OrderingAdapter orderingAdapter;
    private DatabaseHelper databaseHelper;
    private DetailOrderDao detailOrderDao;
    private SubmitOrderDao submitOrderDao;
    private Customer customer;
    private String CODE = String.valueOf(System.currentTimeMillis());







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ordering);

        slidrInterface = Slidr.attach(this);

        callDatabase();
        init();
        click_customer();


    }

    private void init(){
        lottie = findViewById(R.id.clear_submit_ordering_ic_lottie);
        recyclerView = findViewById(R.id.ordering_recycler);
        customer_orderer = findViewById(R.id.customer_orderer);
        orderer_name_tv = findViewById(R.id.orderer_name_tv);
        bttnvigtion_submit_order = findViewById(R.id.bttnvigtion_submit_order);
        total_price_tv = findViewById(R.id.total_price_tv);
        counting_orders = findViewById(R.id.counting_orders);
        counting_orders_tv = findViewById(R.id.counting_orders_tv);
        submit_ordering_tv = findViewById(R.id.submit_ordering_tv);
        add_ordering = findViewById(R.id.add_ordering);
    }

    private void callDatabase(){
        databaseHelper = DatabaseHelper.getInstance(getApplicationContext());
        submitOrderDao = databaseHelper.submitOrderDao();
        detailOrderDao = databaseHelper.detailOrderDao();
    }

    private void click_customer(){
        customer_orderer.setOnClickListener(v -> {
            Intent intent = new Intent(this, CustomerActivity.class);
            intent.putExtra("for_order" , true);
            startActivityForResult(intent,100);
        });
    }
}