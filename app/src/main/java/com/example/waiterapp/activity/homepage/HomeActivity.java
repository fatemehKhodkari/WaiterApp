package com.example.waiterapp.activity.homepage;

import static com.github.mikephil.charting.utils.ColorTemplate.rgb;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.waiterapp.R;
import com.example.waiterapp.activity.addordering.AddOrderingActivity;
import com.example.waiterapp.activity.customer.CustomerActivity;
import com.example.waiterapp.activity.grouping.GroupingActivity;
import com.example.waiterapp.activity.product.ProductActivity;
import com.example.waiterapp.activity.setting.SettingActivity;
import com.example.waiterapp.activity.submittedorder.SubmittedOrderActivity;
import com.example.waiterapp.database.DatabaseHelper;
import com.example.waiterapp.database.dao.CustomerDao;
import com.example.waiterapp.database.dao.GroupingDao;
import com.example.waiterapp.database.dao.ProductDao;
import com.example.waiterapp.database.dao.SubmitOrderDao;
import com.example.waiterapp.helper.App;
import com.example.waiterapp.helper.Tools;
import com.example.waiterapp.model.ChartModel;
import com.example.waiterapp.model.Order;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private CardView cardViewproduct,cardViewcustomer , cardViewgrouping , cardViewSubmittedOrdering;
    private ImageView add_order , menu_drawle;
    private LinearLayout copy , share , upload, download , delete;
    private BarChart graph;
    private TextView num_product , num_customer , num_ordering , num_category ;
    private DatabaseHelper databaseHelper;
    private ProductDao productDao;
    private CustomerDao customerDao;
    private DrawerLayout mydrawer;
    private LinearLayout setting_nav , about_nav , guid_nav , signup_nav;
    private GroupingDao groupingDao;
    private TextView today_profit , week_profit , month_profit , total_profit;
    private TextView dayName , monthName , cafeName;
    private SubmitOrderDao submitOrderDao;
    private ArrayList<ChartModel> chartModels;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initDataBase();
        init();
        on_click_cards();
        ordering();
        set_menu_drawle();
        set_drawer_setting();

    }

    private void initDataBase(){
        databaseHelper = App.getDatabase();
        productDao = databaseHelper.productDao();
        customerDao = databaseHelper.customerDao();
        groupingDao = databaseHelper.groupingDao();
        submitOrderDao = databaseHelper.submitOrderDao();
    }

    void init(){
        cardViewproduct = findViewById(R.id.products);
        cardViewcustomer=findViewById(R.id.customer);
        cardViewgrouping=findViewById(R.id.grouping);
        cardViewSubmittedOrdering = findViewById(R.id.submitted_orders);
        add_order = findViewById(R.id.add_order_ic);
        graph = (BarChart) findViewById(R.id.graf);
        num_product = findViewById(R.id.number_product);
        num_category = findViewById(R.id.number_category);
        num_ordering = findViewById(R.id.number_orderings);
        num_customer = findViewById(R.id.number_customer);
        today_profit = findViewById(R.id.today_profit);
        week_profit = findViewById(R.id.week_profit);
        month_profit = findViewById(R.id.month_profit);
        monthName = findViewById(R.id.monthName);
        dayName = findViewById(R.id.dayName);
        total_profit = findViewById(R.id.total_profit);
        menu_drawle = findViewById(R.id.menu_drawle);
        mydrawer = findViewById(R.id.mydrawer);
        setting_nav = findViewById(R.id.setting_nav);
        about_nav = findViewById(R.id.about_nav);
        guid_nav = findViewById(R.id.about_nav);
        signup_nav = findViewById(R.id.signup_nav);
        cafeName = findViewById(R.id.cafe_name);
    }



    private void populateChart(){
        Log.e("qqqqmain", "populateChart: started" );
        ArrayList<Order> list = new ArrayList<>(submitOrderDao.getOrderList());
        chartModels = new ArrayList<>();

        Log.e("qqqqmain", "populateChart: "+list.size() + "-"+ chartModels.size() );

        for (int i = 0; i < list.size(); i++) {


            Log.e("qqqqmain2", "populateChart: for list is started \n"
                    + list.get(i).date
            );

            if (chartModels.size() == 0){
                Log.e("qqqqmain", "populateChart: chartModels.size() == 0" );

                chartModels.add(new ChartModel(list.get(i).date,Tools.convertToPrice(list.get(i).total)));
            }else {
                Log.e("qqqqmain", "populateChart: chartModels.size() > 0" );

                for (int c = 0; c < chartModels.size(); c++) {
                    Log.e("qqqqmain2", "populateChart: for chart model is started\n"
                            + chartModels.get(c).getDate() );

                    if (list.get(i).date.equals(chartModels.get(c).getDate())){
                        chartModels.get(c).setTotal(chartModels.get(c).getTotal() + Tools.convertToPrice(list.get(i).total));
//                        return;
                    }else {
                        chartModels.add(new ChartModel(list.get(i).date,Tools.convertToPrice(list.get(i).total)));
                    }
                }
            }
        }
    }

    private void graph(){
//        BarData barData;
//        BarDataSet barDataSet;
//        ArrayList barEntries;
//
//        barEntries = new ArrayList<>();
//
//        barEntries.add(new BarEntry(1f, 0.5f));
//        barEntries.add(new BarEntry(2f, 1.8f));
//        barEntries.add(new BarEntry(3f, 1));
//        barEntries.add(new BarEntry(4f, 3));
//        barEntries.add(new BarEntry(5f, 1));
//        barEntries.add(new BarEntry(6f, 4));
//        barEntries.add(new BarEntry(7f, 3));
        ArrayList<BarEntry> visitor = new ArrayList<>();

        for (int i = 0; i < chartModels.size(); i++) {
            visitor.add(new BarEntry( i, (int) chartModels.get(i).getTotal() ));

        }

        BarDataSet barDataSet = new BarDataSet(visitor, "");
        barDataSet.setColors(MY_COLOES);
        barDataSet.setValueTextSize(0f);

        BarData barData = new BarData(barDataSet);
        graph.setFitBars(true);
        graph.setData(barData);
        graph.getDescription().setText("");
        graph.animateY(1000);

        XAxis xAxis = graph.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);

//        barDataSet = new BarDataSet(visitor, "");
//        barData = new BarData(barDataSet);
//        graph.setData(barData);
//        barDataSet.setColors(MY_COLOES);
//        barDataSet.setValueTextColor(Color.BLACK);
//        barDataSet.setValueTextSize(18f);
//        Description description = new Description();
//        description.setEnabled(false);
//        graph.setDescription(description);
//        graph.setFitBars(true);
//        graph.animateY(1000);
//
//        XAxis xAxis = graph.getXAxis();
//        xAxis.setGranularity(1f);
//        xAxis.setAxisMinimum(0f);
//        xAxis.setDrawAxisLine(true);
//        xAxis.setDrawGridLines(false);
//

    }

    private static final int[] MY_COLOES = {
            rgb("#C1A49A"), rgb("#FF202E39"), rgb("#DDC8BF"), rgb("#404040")
    };


    void on_click_cards(){
        cardViewproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentP = new Intent(HomeActivity.this, ProductActivity.class);
                startActivity(intentP);
                overridePendingTransition(android.R.anim.fade_in , android.R.anim.fade_out);
            }
        });
        cardViewcustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentC= new Intent(HomeActivity.this, CustomerActivity.class);
                startActivity(intentC);
                overridePendingTransition(android.R.anim.fade_in , android.R.anim.fade_out);
            }
        });
        cardViewgrouping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentG= new Intent(HomeActivity.this, GroupingActivity.class);
                startActivity(intentG);
                overridePendingTransition(android.R.anim.fade_in , android.R.anim.fade_out);
            }
        });

        cardViewSubmittedOrdering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentSO = new Intent(HomeActivity.this , SubmittedOrderActivity.class);
                startActivity(intentSO);
                overridePendingTransition(android.R.anim.fade_in , android.R.anim.fade_out);
            }
        });


    }


    void ordering(){

        add_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                showBottomSheetDialog();
                Intent addorder= new Intent(HomeActivity.this, AddOrderingActivity.class);
                startActivity(addorder);

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        num_product.setText(Integer.toString(productDao.getProductList().size()));
        num_customer.setText(Integer.toString(customerDao.getCustomerList().size()));
        num_category.setText(Integer.toString(groupingDao.getGroupingList().size()));
        num_ordering.setText(Integer.toString(submitOrderDao.getOrderList().size()));
        populateChart();
        graph();
        getTotalDaily();
        getTotalWeekly();
        getTotalMonthly();
        setName();
        initSetName();
        setTotal_profit();
    }

    private int getTotalDaily(){
        List<String> total = new ArrayList<>();
        int j = 0 ;
        try {
            total.addAll(submitOrderDao.dailyTotal(Tools.getCurrentDate()));
            for (int i = 0; i < submitOrderDao.getOrderList().size() ; i++) {
                String t = total.get(i);
                j = j + Tools.convertToPrice(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        today_profit.setText(Tools.getForamtPrice(String.valueOf(j)));
        return j;
    }
    private void getTotalWeekly(){
        List<Order> total = new ArrayList<>();
        total.addAll(submitOrderDao.getOrderListDate(Tools.getSevenDayAgo()));
        int j = 0 ;
        for (int i = 0; i < total.size() ; i++) {
            String t = total.get(i).total;
            j = j + Tools.convertToPrice(t);
        }
        week_profit.setText(Tools.getForamtPrice(String.valueOf(j)));
    }

    private void getTotalMonthly(){
        List<Order> total = new ArrayList<>();
        total.addAll(submitOrderDao.getOrderListDate(Tools.getThirtyDaysAgo()));
        int j = 0 ;
        for (int i = 0; i < total.size() ; i++) {
            String t = total.get(i).total;
            j = j + Tools.convertToPrice(t);
        }
        month_profit.setText(Tools.getForamtPrice(String.valueOf(j)));
    }
//    total_profit
    private void setTotal_profit(){
        List<String> allTotal = new ArrayList<>();
        allTotal.addAll(submitOrderDao.getAllTotal());
        int t = 0 ;
        for (int i = 0; i < allTotal.size() ; i++) {
            String at = allTotal.get(i);
            t = t + Tools.convertToPrice(at);
        }
        total_profit.setText(Tools.getForamtPrice(String.valueOf(t)));

    }

    private void setName(){
        dayName.setText(" ( " + Tools.getDayName() + " ) ");
        monthName.setText(" ( " + Tools.getMonthName() + " ) ");
    }


    private void set_menu_drawle(){

        menu_drawle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mydrawer.openDrawer((int) GravityCompat.END);
            }
        });
    }

    private void set_drawer_setting(){

        setting_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this , SettingActivity.class);
                startActivity(intent);
                mydrawer.closeDrawer(GravityCompat.END);
            }
        });
    }

    private void initSetName() {

        Intent intent = getIntent();
        String a = intent.getStringExtra("cafe_name");
        cafeName.setText(a);
    }

}