package com.example.waiterapp.activity.grouping;

import android.content.Intent;
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
import com.example.waiterapp.adapter.GroupingAdapter;
import com.example.waiterapp.database.DatabaseHelper;
import com.example.waiterapp.database.dao.GroupingDao;
import com.example.waiterapp.helper.App;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mig35.carousellayoutmanager.CarouselLayoutManager;
import com.mig35.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.mig35.carousellayoutmanager.CenterScrollListener;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

import java.util.ArrayList;

public class GroupingActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private GroupingDao groupingDao;
    private RecyclerView recyclerView;
    private GroupingAdapter groupingAdapter;
    private FloatingActionButton floatingActionButton;
    private SlidrInterface slidrInterface;
    private Toolbar grouping_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grouping);

        slidrInterface = Slidr.attach(this);

        init();
        set_search();
        set_recycler();
        set_floatingActionBttn();
        hide_floatingActionBttn();
    }


    public void init(){
        recyclerView = findViewById(R.id.grouping_recycler);
        floatingActionButton = findViewById(R.id.grouping_flabttn);
        grouping_toolbar = findViewById(R.id.grouping_toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search_grouping,menu);
        MenuItem item = menu.findItem(R.id.menu_item_search_grouping);
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
        searchEdit.setHint("جستجوی دسته بندی..");
        searchEdit.setTextSize(14);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newtxt) {
                groupingAdapter.getFilter().filter(newtxt);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void set_search(){
        grouping_toolbar.setTitle("");
        grouping_toolbar.setTitleTextColor(getResources().getColor(R.color.whitediff));
        setSupportActionBar(grouping_toolbar);
    }

    public void set_recycler(){
        final CarouselLayoutManager layoutManager = new CarouselLayoutManager(CarouselLayoutManager.VERTICAL, true);
        layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());

        databaseHelper = App.getDatabase();
        groupingDao = databaseHelper.groupingDao();

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addOnScrollListener(new CenterScrollListener());
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
//        if (databaseHelper != null)
//            databaseHelper.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if( groupingAdapter!= null){
            groupingAdapter.addList(groupingDao.getGroupingList());
        }
    }
}