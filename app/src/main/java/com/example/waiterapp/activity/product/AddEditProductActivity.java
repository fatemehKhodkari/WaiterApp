package com.example.waiterapp.activity.product;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.waiterapp.R;
import com.example.waiterapp.database.DatabaseHelper;
import com.example.waiterapp.database.dao.GroupingDao;
import com.example.waiterapp.database.dao.ProductDao;
import com.example.waiterapp.model.Grouping;
import com.example.waiterapp.model.Product;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

public class AddEditProductActivity extends AppCompatActivity {

    SlidrInterface slidrInterface;
    EditText product_name_edt , product_price_edt;
    AutoCompleteTextView product_grouping_name_actv;
    TextView save_product,cancle_tv;
    LinearLayout product_anim_feilds;
    ConstraintLayout product_desing_anim;
    DatabaseHelper databaseHelper;
    ProductDao productDao;
    Product product;
    GroupingDao groupingDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideActionBar();
        setContentView(R.layout.activity_add_edit_product);

        slidrInterface = Slidr.attach(this);
        init();
        call_db();
        animateOb();

    }
    void hideActionBar(){
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    void init(){
        save_product = findViewById(R.id.save_customer);
        cancle_tv = findViewById(R.id.cancel_customer);
        product_name_edt = findViewById(R.id.get_customer_name);
        product_anim_feilds = findViewById(R.id.product_info_feilds);
        product_desing_anim = findViewById(R.id.add_edit_product_design);
    }
    void call_db(){
        databaseHelper = DatabaseHelper.getInstance(getApplicationContext());
        productDao = databaseHelper.productDao();
        groupingDao = databaseHelper.groupingDao();
    }
    void animateOb(){
        product_desing_anim.setTranslationX(-200f);
        product_desing_anim.animate().translationXBy(+200f).setDuration(200);

        product_anim_feilds.setTranslationX(+200f);
        product_anim_feilds.animate().translationXBy(-200f).setDuration(200);
    }

}