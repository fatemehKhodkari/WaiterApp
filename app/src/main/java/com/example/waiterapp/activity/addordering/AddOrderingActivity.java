package com.example.waiterapp.activity.addordering;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.waiterapp.R;
import com.example.waiterapp.activity.customer.CustomerActivity;
import com.example.waiterapp.activity.product.ProductActivity;
import com.example.waiterapp.adapter.OrderingAdapter;
import com.example.waiterapp.database.DatabaseHelper;
import com.example.waiterapp.database.dao.CustomerDao;
import com.example.waiterapp.database.dao.DetailOrderDao;
import com.example.waiterapp.database.dao.SubmitOrderDao;
import com.example.waiterapp.helper.App;
import com.example.waiterapp.helper.Tools;
import com.example.waiterapp.model.Customer;
import com.example.waiterapp.model.DetailOrder;
import com.example.waiterapp.model.Order;
import com.example.waiterapp.model.Product;
import com.google.gson.Gson;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.api.PersianPickerDate;
import ir.hamsaa.persiandatepicker.api.PersianPickerListener;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;
import ir.hamsaa.persiandatepicker.util.PersianCalendarUtils;
//import saman.zamani.persiandate.PersianDate;
//import saman.zamani.persiandate.PersianDateFormat;

public class AddOrderingActivity extends AppCompatActivity {

    private LottieAnimationView lottie;
    private RecyclerView recyclerView;
    private LinearLayout customer_orderer ;
    private TextView
            total_price_tv ,
            counting_orders_tv ,
            submit_ordering_tv ,
            not_ordering_tv ;

    private TextView orderer_name_tv ;
    private RelativeLayout bttnvigtion_submit_order;
    private CardView counting_orders , add_ordering;
    private SlidrInterface slidrInterface;
    private OrderingAdapter orderingAdapter;
    private DatabaseHelper databaseHelper;
    private DetailOrderDao detailOrderDao;
    private SubmitOrderDao submitOrderDao;
    private Customer customer;
    private CustomerDao customerDao;
    private String CODE = String.valueOf(System.currentTimeMillis());
    private List<Product> orderDetailList;
    private TextView time_tv_bttn,date_tv_bttn;
    private PersianDatePickerDialog picker;

private static final String TAG = "AddOrdringActivity";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ordering);

        slidrInterface = Slidr.attach(this);

        callDatabase();
        init();
        click_customer();
        set_Lottie();
        click_add_product();
        set_recycler();
        set_submit_order();
        set_date_onClick();
        getCurrentDate();
        get_current_time();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            switch (requestCode){

                case 100:
                    String json_customer = data.getExtras().getString("json_customer");
                    customer = new Gson().fromJson(json_customer , Customer.class);
                    orderer_name_tv.setText(customer.name);
                    break;

                case 200:
                    not_ordering_tv.setVisibility(View.GONE);
//                    recyclerView.setVisibility(View.VISIBLE);
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
        orderDetailList.add(product);
        orderingAdapter.notifyDataSetChanged();
        initCounter();
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
        date_tv_bttn = findViewById(R.id.date_tv_bttn);
        time_tv_bttn = findViewById(R.id.time_tv_bttn);
    }

    private void callDatabase(){
        databaseHelper = App.getDatabase();
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

    private void click_add_product(){
        add_ordering.setOnClickListener(v ->{
            Intent intent = new Intent(this, ProductActivity.class);
            intent.putExtra("for_order" , true);
            startActivityForResult(intent , 200);
        });
    }

    private void set_recycler(){
        orderDetailList = new ArrayList<>();
        orderingAdapter = new OrderingAdapter(this, orderDetailList, new OrderingAdapter.Listener() {
                    @Override
                    public void onAdded(int pos) {
                        orderDetailList.get(pos).amount = orderDetailList.get(pos).amount + 1;
                        orderingAdapter.notifyItemChanged(pos);
                        initCounter();
                    }

                    @Override
                    public void onRemove(int pos) {
                        if (orderDetailList.get(pos).amount > 1){
                            orderDetailList.get(pos).amount = orderDetailList.get(pos).amount - 1;
                            orderingAdapter.notifyItemChanged(pos);
                        }else {
                            orderDetailList.remove(pos);
                            orderingAdapter.notifyDataSetChanged();

                        }
                        initCounter();
                    }
                });
        recyclerView.setAdapter(orderingAdapter);
        recyclerView.setHasFixedSize(true);
    }

    private void set_submit_order(){
        submit_ordering_tv.setOnClickListener(view -> {
            if( customer == null ){
                Toast.makeText(this, "فیلد مشتری خالی است!", Toast.LENGTH_SHORT).show();
            }else {
                submitOrderDao.insertOrder(new Order(customer.name , CODE , customer.id , 1 , total_price_tv.getText()+"" , "با تمام مخلفات" , getCurrentTime_time() ,
//                        getCurrentTime_Date()
                        "1400/10/20"
                ));

                for (int i = 0; i < orderDetailList.size(); i++) {

                    detailOrderDao.insertDetailOrder(new DetailOrder(
                            orderDetailList.get(i).name_product ,
                            String.valueOf(Tools.convertToPrice(orderDetailList.get(i).price) * orderDetailList.get(i).amount)  ,
                            orderDetailList.get(i).category ,
                            orderDetailList.get(i).amount ,
                            CODE ,
                            orderDetailList.get(i).picture_product
                            ));

                    Toast.makeText(AddOrderingActivity.this, " سفارش " + customer.name + " با موفقیت ثبت شد", Toast.LENGTH_SHORT).show();
                }
                databaseHelper.close();
                finish();
            }

        });
    }

//    public String getCurrentTime_Date(){
//        PersianDate c = new PersianDate();
//        PersianDateFormat dateFormat = new PersianDateFormat(" Y/m/d ");
//        String datetime = dateFormat.format(c);
//        return datetime;
//    }

    public String getCurrentTime_time(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss ");
        String datetime = dateFormat.format(c.getTime());
        return datetime;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(databaseHelper != null){
            databaseHelper.close();
        }

    }

    private void getCurrentDate(){
        PersianCalendar today = new PersianCalendar();
        String currentDate = today.getPersianShortDate();
        date_tv_bttn.setText(currentDate);

    }

    private void get_current_time(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss ");
        String datetime = dateFormat.format(calendar.getTime());
        time_tv_bttn.setText(datetime);
    }

    private void set_date_onClick(){
//        Typeface typeface = Typeface.createFromAsset(getAssets(), "shabnam-light.ttf");
        date_tv_bttn.setOnClickListener(view -> {
            picker = new PersianDatePickerDialog(this)
                    .setPositiveButtonString("باشه")
                    .setNegativeButton("بیخیال")
                    .setTodayButton("امروز")
                    .setTodayButtonVisible(true)
                    .setMinYear(1300)
                    .setMaxYear(PersianDatePickerDialog.THIS_YEAR)
                    .setMaxMonth(PersianDatePickerDialog.THIS_MONTH)
                    .setMaxDay(PersianDatePickerDialog.THIS_DAY)
                    .setInitDate(1370, 3, 13)
                    .setActionTextColor(Color.GRAY)
//                    .setTypeFace(typeface)
                    .setTitleType(PersianDatePickerDialog.WEEKDAY_DAY_MONTH_YEAR)
                    .setShowInBottomSheet(true)
                    .setListener(new PersianPickerListener() {
                        @Override
                        public void onDateSelected(@NotNull PersianPickerDate persianPickerDate) {
                            Log.d(TAG, "onDateSelected: " + persianPickerDate.getTimestamp());//675930448000
                            Log.d(TAG, "onDateSelected: " + persianPickerDate.getGregorianDate());//Mon Jun 03 10:57:28 GMT+04:30 1991
                            Log.d(TAG, "onDateSelected: " + persianPickerDate.getPersianLongDate());// دوشنبه  13  خرداد  1370
                            Log.d(TAG, "onDateSelected: " + persianPickerDate.getPersianMonthName());//خرداد
                            Log.d(TAG, "onDateSelected: " + PersianCalendarUtils.isPersianLeapYear(persianPickerDate.getPersianYear()));//true
                            Toast.makeText(getApplicationContext(), persianPickerDate.getPersianYear() + "/" + persianPickerDate.getPersianMonth() + "/" + persianPickerDate.getPersianDay(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onDismissed() {

                        }
                    });

            picker.show();
        });
    }


}