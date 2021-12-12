package com.example.waiterapp.activity.addordering;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
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
import com.example.waiterapp.helper.Tools;
import com.example.waiterapp.model.Customer;
import com.example.waiterapp.model.Product;
import com.google.gson.Gson;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

import java.util.List;

public class AddOrderingActivity extends AppCompatActivity {

    private LottieAnimationView lottie;
    private RecyclerView recyclerView;
    private LinearLayout customer_orderer ;
    private TextView
            orderer_name_tv ,
            total_price_tv ,
            counting_orders_tv ,
            submit_ordering_tv ,
            not_ordering_tv ;
    private RelativeLayout bttnvigtion_submit_order;
    private CardView counting_orders , add_ordering;
    private SlidrInterface slidrInterface;
    private OrderingAdapter orderingAdapter;
    private DatabaseHelper databaseHelper;
    private DetailOrderDao detailOrderDao;
    private SubmitOrderDao submitOrderDao;
    private Customer customer;
    private String CODE = String.valueOf(System.currentTimeMillis());
    private List<Product> orderDetailList;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ordering);

        slidrInterface = Slidr.attach(this);

        callDatabase();
        init();
        click_customer();
        set_Lottie();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_OK){
            switch (resultCode){

                case 100:
                    String json_customer = data.getExtras().getString("json_customer");
                    customer = new Gson().fromJson(json_customer , Customer.class);
                    orderer_name_tv.setText(customer.name);
                    break;

                case 200:
                    not_ordering_tv.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    bttnvigtion_submit_order.setVisibility(View.VISIBLE);
                    String json_product = data.getExtras().getString("json_product");
                    Product product = new Gson().fromJson(json_product,Product.class);
                    insertToOrderList(product);
                    break;


            }
        }
    }

    private void insertToOrderList(Product product) {
        for (int i = 0; i < orderDetailList.size(); i++) {
            if (orderDetailList.get(i).id == product.id) {
                orderDetailList.get(i).amount = orderDetailList.get(i).amount + 1;
                orderingAdapter.notifyDataSetChanged();
                initCounter();
                return;
            }
        }
    }


    private void initCounter(){
        if(orderDetailList.size() > 0){
            counting_orders.setVisibility(View.VISIBLE);
            counting_orders_tv.setText(orderDetailList.size()+"");

        }else {
            counting_orders.setVisibility(View.GONE);
        }
        total_price_tv.setText(Tools.getForamtPrice(getTotalPrice()+""));
    }

    private Integer getTotalPrice(){
        int p = 0;
        for (int i = 0; i < orderDetailList.size(); i++) {
            p = p + (Tools.convertToPrice(orderDetailList.get(i).price) * orderDetailList.get(i).amount);
        }
        return p;
    }

    private void set_Lottie(){
        lottie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(orderDetailList.size() != 0 ){
                    lottie.setRepeatCount(0);
                    lottie.playAnimation();
                    orderDetailList.clear();
                    orderingAdapter.notifyDataSetChanged();
                    bttnvigtion_submit_order.setVisibility(View.GONE);
                }else {
                    Toast.makeText(AddOrderingActivity.this, "لیست خالی است", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
        not_ordering_tv = findViewById(R.id.not_ordering_tv);
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