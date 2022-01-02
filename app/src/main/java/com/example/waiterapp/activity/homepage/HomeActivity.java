package com.example.waiterapp.activity.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.waiterapp.R;
import com.example.waiterapp.activity.addordering.AddOrderingActivity;
import com.example.waiterapp.activity.customer.CustomerActivity;
import com.example.waiterapp.activity.grouping.GroupingActivity;
import com.example.waiterapp.activity.product.ProductActivity;
import com.example.waiterapp.activity.submittedorder.SubmittedOrderActivity;
import com.example.waiterapp.database.DatabaseHelper;
import com.example.waiterapp.database.dao.CustomerDao;
import com.example.waiterapp.database.dao.GroupingDao;
import com.example.waiterapp.database.dao.ProductDao;
import com.example.waiterapp.database.dao.SubmitOrderDao;
import com.example.waiterapp.helper.App;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class HomeActivity extends AppCompatActivity {

    private CardView cardViewproduct,cardViewcustomer , cardViewgrouping , cardViewSubmittedOrdering;
    private ImageView add_order;
    private LinearLayout copy , share , upload, download , delete;
    private GraphView graph;
    private TextView num_product , num_customer , num_ordering , num_category ;
    private DatabaseHelper databaseHelper;
    private ProductDao productDao;
    private CustomerDao customerDao;
    private GroupingDao groupingDao;
    private SubmitOrderDao submitOrderDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initDataBase();
        init();
        graph();
        on_click_cards();
        ordering();

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
        graph = (GraphView) findViewById(R.id.graf);
        num_product = findViewById(R.id.number_product);
        num_category = findViewById(R.id.number_category);
        num_ordering = findViewById(R.id.number_orderings);
        num_customer = findViewById(R.id.number_customer);
    }

    void graph(){
        LineGraphSeries<DataPoint> bgseries = new LineGraphSeries<>(new DataPoint[]{

                new DataPoint(0, 1000),
                new DataPoint(1, 8000),
                new DataPoint(2, 3500),
                new DataPoint(3, 24000),
                new DataPoint(4, 60000),
                new DataPoint(5, 1300),
                new DataPoint(6, 5300),
                new DataPoint(7, 3500),
                new DataPoint(8, 80000),
                new DataPoint(9, 3500),
                new DataPoint(10, 1300),
                new DataPoint(11, 5300),
                new DataPoint(12, 80000),
                new DataPoint(13, 3500),
                new DataPoint(14, 60000),
                new DataPoint(15, 5300),
                new DataPoint(16, 8000),
                new DataPoint(17, 1000),
                new DataPoint(18, 5300),
                new DataPoint(19, 60000),
                new DataPoint(20, 5300),
                new DataPoint(21, 1300),
                new DataPoint(22, 60000),
                new DataPoint(23, 3500),
                new DataPoint(24, 1300),
                new DataPoint(25, 1000),
                new DataPoint(26, 8000),
                new DataPoint(27, 5300),
                new DataPoint(28, 1300),
                new DataPoint(29, 60000),
                new DataPoint(30, 5300),
                new DataPoint(31, 80000),
        });

        graph.addSeries(bgseries);
//        graph.setBackgroundColor(getResources().getColor(R.color.brownopa));
        graph.getViewport().setScalable(true);
        graph.getViewport().setScrollable(true);
//        graph.setTitle("نمودار رشد سرمایه");
        graph.setTitleColor(getResources().getColor(R.color.browwn));
//        bgseries.setTitle("foo");
        bgseries.setThickness(5);
        bgseries.setDrawBackground(true);
        bgseries.setColor(getResources().getColor(R.color.browwn));
//        bgseries.setBackgroundColor(Color.rgb(248,243,247));
        bgseries.setBackgroundColor(getResources().getColor(R.color.whitediff));
        bgseries.setAnimated(true);
        bgseries.setColor(R.color.browwn);
//        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
    }

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
    }
}