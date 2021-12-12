package com.example.waiterapp.activity.addordering;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.waiterapp.R;

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
    private CardView counting_orders;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ordering);

        init();
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
    }
}