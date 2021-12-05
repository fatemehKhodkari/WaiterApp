package com.example.waiterapp.activity.product;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.waiterapp.R;
import com.example.waiterapp.database.DatabaseHelper;
import com.example.waiterapp.database.dao.GroupingDao;
import com.example.waiterapp.database.dao.ProductDao;
import com.example.waiterapp.design.NumberTextWatcherForThousand;
import com.example.waiterapp.model.Customer;
import com.example.waiterapp.model.Grouping;
import com.example.waiterapp.model.Product;
import com.google.gson.Gson;
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
    String product_name , product_grouping_name , product_price ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideActionBar();
        setContentView(R.layout.activity_add_edit_product);

        slidrInterface = Slidr.attach(this);
        init();
        call_db();
        check_db();
        thousandNumbersEdt();
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
        save_product = findViewById(R.id.save_product);
        cancle_tv = findViewById(R.id.cancel_product);
        product_name_edt = findViewById(R.id.get_product_name);
        product_price_edt = findViewById(R.id.get_product_price);
        product_grouping_name_actv = findViewById(R.id.get_product_grouping_name);
        product_anim_feilds = findViewById(R.id.product_info_feilds);
        product_desing_anim = findViewById(R.id.add_edit_product_design);
    }
    void thousandNumbersEdt(){
        product_price_edt.addTextChangedListener(new NumberTextWatcherForThousand(product_price_edt));
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
    void check_db(){
        if (getIntent().getExtras() != null){
            String getNameProduct = getIntent().getStringExtra("product");
            product = new Gson().fromJson(getNameProduct,Product.class);
            product_name_edt.setText(product.name_product);
            product_grouping_name_actv.setText(product.category);
            product_price_edt.setText(product.price);
        }
    }

    void save_bttn(){
        save_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                product_name = product_name_edt.getText().toString();
                product_grouping_name = product_grouping_name_actv.getText().toString();
                product_price = product_price_edt.getText().toString();

                if(product == null){
                    if(TextUtils.isEmpty(product_name) || TextUtils.isEmpty(product_grouping_name) || TextUtils.isEmpty(product_price)){
                        Toast.makeText(getApplicationContext(),"تمام فیلد هارا پر کنید!",Toast.LENGTH_SHORT).show();
                    }else{
                        productDao.insertProduct(new Product(product_name,product_grouping_name,product_price));
                        Toast.makeText(getApplicationContext(),"با موفقیت به لیست اضافه شد",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }else {
                    product.name_product = product_name;
                    product.category = product_grouping_name;
                    product.price = product_price;

                    Log.e("qqqq","onClick: update product =" + product.id);
                    productDao.updateProduct(product);
                    Toast.makeText(getApplicationContext(),"تغییرات با موفقیت اعمال شد!" , Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
    }

    void cancle_bttn(){
        cancle_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}