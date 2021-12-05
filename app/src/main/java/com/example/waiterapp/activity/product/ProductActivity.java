package com.example.waiterapp.activity.product;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.waiterapp.R;
import com.example.waiterapp.adapter.GroupingProductAdapter;
import com.example.waiterapp.adapter.ProductAdapter;
import com.example.waiterapp.database.DatabaseHelper;
import com.example.waiterapp.database.dao.GroupingDao;
import com.example.waiterapp.database.dao.ProductDao;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mig35.carousellayoutmanager.CarouselLayoutManager;
import com.mig35.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.mig35.carousellayoutmanager.CenterScrollListener;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

public class ProductActivity extends AppCompatActivity {

    FloatingActionButton floatingActionButton;
    RecyclerView category_recycler , product_recycler;
    DatabaseHelper databaseHelper;
    ProductDao productDao;
    GroupingDao groupingDao;
    ProductAdapter productAdapter;
    GroupingProductAdapter groupingProductAdapter;

    private Boolean for_order = false ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);


        init();
        set_floatingActtionButton();
        hide_floatingActionButton();

        databaseHelper= DatabaseHelper.getInstance(getApplicationContext());
        productDao= databaseHelper.productDao();
        groupingDao = databaseHelper.groupingDao();

        set_grouping_recycler();



    }

    public void init(){
        category_recycler = findViewById(R.id.grouping_product_recycler);
        product_recycler = findViewById(R.id.product_recycler);
        floatingActionButton = findViewById(R.id.product_flabttn);
    }

    public void set_floatingActtionButton(){
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductActivity.this, AddEditProductActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in , android.R.anim.fade_out);
            }
        });
    }

    public void hide_floatingActionButton(){
        product_recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

    public void set_grouping_recycler(){
        category_recycler.setHasFixedSize(true);
//
//        final CarouselLayoutManager layoutManager = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL,true);
//        layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());

        LinearLayoutManager layoutManager = new LinearLayoutManager(this ,LinearLayoutManager.HORIZONTAL , false);
        category_recycler.setLayoutManager(layoutManager);

        category_recycler.addOnScrollListener(new CenterScrollListener());
        groupingProductAdapter = new GroupingProductAdapter(this, groupingDao.getGroupingList());
        category_recycler.setAdapter(groupingProductAdapter);
    }

    void set_product_recycler(){

        product_recycler.setHasFixedSize(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(productAdapter != null){
            productAdapter.addList(productDao.getProductList());
        }
    }

}