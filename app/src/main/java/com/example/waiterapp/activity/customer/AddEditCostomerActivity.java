package com.example.waiterapp.activity.customer;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.example.waiterapp.R;
import com.example.waiterapp.database.DatabaseHelper;
import com.example.waiterapp.database.dao.CustomerDao;
import com.example.waiterapp.model.Customer;
import com.google.gson.Gson;

public class AddEditCostomerActivity extends AppCompatActivity {

    TextView save_customer,cancle_ed;
    EditText customer_name_edt,customer_phone_edt,customer_adress_edt;
    String customer_name , customer_phone , customer_adress ;
    DatabaseHelper databaseHelper;
    Customer customer;
    CustomerDao customerDao;
    LinearLayout customer_anim_feilds;
    ConstraintLayout desing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideActionBar();
        setContentView(R.layout.activity_add_edit_costomer);

        init();
        animateOb();

        databaseHelper = DatabaseHelper.getInstance(getApplicationContext());
        customerDao = databaseHelper.customerDao();

        if (getIntent().getExtras() != null){
            String getNameCustomer = getIntent().getStringExtra("Customer");
            customer = new Gson().fromJson(getNameCustomer,Customer.class);
            customer_name_edt.setText(customer.name);
            customer_phone_edt.setText(customer.phone);
            customer_adress_edt.setText(customer.address);
        }

        save_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                customer_name = customer_name_edt.getText().toString();
                customer_phone = customer_phone_edt.getText().toString();
                customer_adress = customer_adress_edt.getText().toString();

                if(customer == null){
                    if(TextUtils.isEmpty(customer_name) || TextUtils.isEmpty(customer_phone) || TextUtils.isEmpty(customer_adress)){
                        Toast.makeText(getApplicationContext(),"تمام فیلد هارا پر کنید!",Toast.LENGTH_SHORT).show();
                    }else
                        if(customer_phone.length() != 11){
                            Toast.makeText(getApplicationContext(), "شماره تلفن باید 11 رقم باشد!", Toast.LENGTH_SHORT).show();
                        }else {
                            customerDao.insertCustomer(new Customer(customer_name,customer_phone,customer_adress));
                            Toast.makeText(getApplicationContext(),"با موفقیت به لیست اضافه شد",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                }else {
                    customer.name = customer_name;
                    customer.phone = customer_phone;
                    customer.address = customer_adress;

                    Log.e("qqqq","onClick: update customer =" + customer.id);
                    customerDao.updateCustomer(customer);
                    finish();
                }
                overridePendingTransition(android.R.anim.fade_in , android.R.anim.fade_out);
            }
        });

        cancle_ed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(android.R.anim.fade_in , android.R.anim.fade_out);
            }
        });
    }


    void hideActionBar(){
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    void init(){
        save_customer = findViewById(R.id.save_customer);
        cancle_ed = findViewById(R.id.cancel_customer);
        customer_name_edt = findViewById(R.id.get_customer_name);
        customer_phone_edt = findViewById(R.id.get_customer_phone);
        customer_adress_edt = findViewById(R.id.get_customer_address);
        customer_anim_feilds = findViewById(R.id.customer_info_feilds);
        desing = findViewById(R.id.add_edit_customer_design);
    }

    void animateOb(){
        desing.setTranslationX(-200f);
        desing.animate().translationXBy(+200f).setDuration(200);
        customer_anim_feilds.setTranslationX(+200f);
        customer_anim_feilds.animate().translationXBy(-200f).setDuration(200);
    }


}