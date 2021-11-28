package com.example.waiterapp.activity.grouping;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.waiterapp.R;
import com.example.waiterapp.database.DatabaseHelper;
import com.example.waiterapp.database.dao.GroupingDao;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

public class AddEditGroupingActivity extends AppCompatActivity {

    SlidrInterface slidrInterface;
    LinearLayout grouping_anim_feilds;
    ConstraintLayout grouping_desing_anim;
    TextView save_grouping , cancle_tv;
    GroupingDao groupingDao;
    DatabaseHelper databaseHelper;
    EditText grouping_name_edt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideActionBar();
        setContentView(R.layout.activity_add_edit_grouping);

        slidrInterface = Slidr.attach(this);

        init();
        animateOb();
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

    void animateOb(){
        grouping_desing_anim.setTranslationX(-200f);
        grouping_desing_anim.animate().translationXBy(+200f).setDuration(200);

        grouping_anim_feilds.setTranslationX(+200f);
        grouping_anim_feilds.animate().translationXBy(-200f).setDuration(200);
    }
}