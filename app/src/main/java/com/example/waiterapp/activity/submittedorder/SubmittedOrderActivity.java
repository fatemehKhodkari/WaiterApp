package com.example.waiterapp.activity.submittedorder;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
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
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submitted_order);

        slidrInterface = Slidr.attach(this);

        call_db();
        initID();
        set_search();
        initRecycler();

    }

    private void call_db(){
        databaseHelper = App.getDatabase();
        submitOrderDao = databaseHelper.submitOrderDao();
    }
    private void initID(){
        recyclerView = findViewById(R.id.submited_ordering_recycler);
        not_submitted_order = findViewById(R.id.not_submitted_order);
        toolbar = findViewById(R.id.ordered_toolbar);
    }

    private void initRecycler(){
        recyclerView.setHasFixedSize(true);
        listSubmittedAdapter = new ListSubmittedAdapter(this,submitOrderDao.getOrderListByDate());
        recyclerView.setAdapter(listSubmittedAdapter);
    }


    @SuppressLint("ResourceAsColor")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search_ordering,menu);
        MenuItem item = menu.findItem(R.id.menu_item_search_ordering);
        SearchView searchView = (SearchView)item.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setBackground(getResources().getDrawable(R.drawable.rippler));

        TextView searchText = (TextView) searchView.findViewById(R.id.search_src_text);
        Typeface myCustomFont = Typeface.createFromAsset(getAssets(),"font/iran_sans.ttf");
        searchText.setTypeface(myCustomFont);



        EditText searchEdit = ((EditText)searchView.findViewById(androidx.appcompat.R.id.search_src_text));
        searchEdit.setTextColor(getResources().getColor(R.color.whitediff));
        searchEdit.setHintTextColor(getResources().getColor(R.color.brownlight));
        searchEdit.setTypeface(myCustomFont);
        searchEdit.setHint("جستجوی سفارش..");
        searchEdit.setTextSize(14);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newtxt) {
                listSubmittedAdapter.getFilter().filter(newtxt);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void set_search(){

        toolbar.setTitle("");
        toolbar.setTitleTextColor(getResources().getColor(R.color.whitediff));
        setSupportActionBar(toolbar);
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