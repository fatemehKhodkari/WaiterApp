package com.example.waiterapp.activity.grouping;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.waiterapp.R;
import com.example.waiterapp.activity.customer.AddEditCostomerActivity;
import com.example.waiterapp.activity.customer.CustomerActivity;
import com.example.waiterapp.adapter.GroupingAdapter;
import com.example.waiterapp.database.DatabaseHelper;
import com.example.waiterapp.database.dao.GroupingDao;
import com.example.waiterapp.model.Grouping;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

import java.util.ArrayList;

public class GroupingActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    GroupingDao groupingDao;
    RecyclerView recyclerView;
    GroupingAdapter groupingAdapter;
    FloatingActionButton floatingActionButton;
    SlidrInterface slidrInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grouping);

        slidrInterface = Slidr.attach(this);

        init();
        set_recycler();
        set_floatingActionBttn();
        hide_floatingActionBttn();
    }


    public void init(){
        recyclerView = findViewById(R.id.grouping_recycler);
        floatingActionButton = findViewById(R.id.grouping_flabttn);
    }

    public void set_recycler(){
        databaseHelper = DatabaseHelper.getInstance(getApplicationContext());
        groupingDao = databaseHelper.groupingDao();

        recyclerView.setHasFixedSize(true);
        groupingAdapter = new GroupingAdapter(new ArrayList<>(),this);
        recyclerView.setAdapter(groupingAdapter);
    }

    public void set_floatingActionBttn(){
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GroupingActivity.this, AddEditGroupingActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in , android.R.anim.fade_out);
            }
        });
    }

    public void hide_floatingActionBttn(){
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if(dy >0 ){
                    floatingActionButton.hide();
                }else {
                    floatingActionButton.show();
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (databaseHelper != null) databaseHelper.close();
    }


    @Override
    protected void onResume() {
        super.onResume();
        if( groupingAdapter!= null){
            groupingAdapter.addList(groupingDao.getGroupingList());
        }
    }
}