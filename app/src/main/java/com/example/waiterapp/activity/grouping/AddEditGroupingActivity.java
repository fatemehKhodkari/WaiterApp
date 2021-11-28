package com.example.waiterapp.activity.grouping;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.waiterapp.R;
import com.example.waiterapp.database.DatabaseHelper;
import com.example.waiterapp.database.dao.GroupingDao;
import com.example.waiterapp.model.Grouping;
import com.google.gson.Gson;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

public class AddEditGroupingActivity extends AppCompatActivity {

    SlidrInterface slidrInterface;
    LinearLayout grouping_anim_feilds;
    ConstraintLayout grouping_desing_anim;
    TextView save_grouping , cancle_tv;
    GroupingDao groupingDao;
    Grouping grouping;
    DatabaseHelper databaseHelper;
    EditText grouping_name_edt;
    String grouping_name , grouping_previous_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideActionBar();
        setContentView(R.layout.activity_add_edit_grouping);

        slidrInterface = Slidr.attach(this);

        init();
        call_db();
        check_db();
        animateOb();
        save_bttn();
        cancle_bttn();
    }

    void hideActionBar(){
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    void init(){
        save_grouping = findViewById(R.id.save_grouping);
        cancle_tv = findViewById(R.id.cancel_grouping);
        grouping_name_edt = findViewById(R.id.get_grouping_name);
        grouping_anim_feilds = findViewById(R.id.grouping_info_feilds);
        grouping_desing_anim = findViewById(R.id.add_edit_grouping_design);
    }
    void call_db(){
        databaseHelper = DatabaseHelper.getInstance(getApplicationContext());
        groupingDao = databaseHelper.groupingDao();
    }

    void animateOb(){
        grouping_desing_anim.setTranslationX(-200f);
        grouping_desing_anim.animate().translationXBy(+200f).setDuration(200);

        grouping_anim_feilds.setTranslationX(+200f);
        grouping_anim_feilds.animate().translationXBy(-200f).setDuration(200);
    }

    void check_db(){
        if(getIntent().getExtras() != null){
            String getGrouping =getIntent().getStringExtra("Grouping");
            grouping = new Gson().fromJson(getGrouping, Grouping.class);
            grouping_previous_name = grouping.name;
            grouping_name_edt.setText(grouping.name); }
    }

    public void save_bttn(){
        save_grouping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                grouping_name = grouping_name_edt.getText().toString();

                if(grouping == null){

                    if(TextUtils.isEmpty(grouping_name)){

                        Toast.makeText(AddEditGroupingActivity.this, "تمام فیلد هارا پر کنید!", Toast.LENGTH_LONG).show();
                        //
                    }else if(groupingDao.getOneName(grouping_name) != null){
                        Toast.makeText(AddEditGroupingActivity.this, "این دسته بندی تکراری است!", Toast.LENGTH_LONG).show();

                    } else {
                        groupingDao.insertGrouping(new Grouping(grouping_name));
                        Toast.makeText(getApplicationContext(), grouping_name + " با موفقیت به لیست اضافه شد ", Toast.LENGTH_LONG).show();
                        finish();

                    }
                }else {
                    grouping.name = grouping_name;
                    groupingDao.updateGrouping(grouping);
                    Toast.makeText(getApplicationContext(),  grouping_previous_name + " به " + grouping_name + " تغییر کرد", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });

    }
    void cancle_bttn(){
        cancle_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}