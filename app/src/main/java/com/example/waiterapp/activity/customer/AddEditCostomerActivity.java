package com.example.waiterapp.activity.customer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.waiterapp.R;
import com.example.waiterapp.database.DatabaseHelper;
import com.example.waiterapp.database.dao.CustomerDao;
import com.example.waiterapp.model.Customer;

public class AddEditCostomerActivity extends AppCompatActivity {

    TextView save_customer,cancle_ed_add;
    EditText customer_name_edt,customer_phone_edt,customer_adress_edt;
    DatabaseHelper databaseHelper;
    Customer customer;
    CustomerDao customerDao;
    LinearLayout customer_anim_feilds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideActionBar();
        setContentView(R.layout.activity_add_edit_costomer);

        init();
        customer_anim_feilds.setTranslationX(+1500f);
        customer_anim_feilds.animate().translationXBy(-1500f).setDuration(1500);
        databaseHelper = DatabaseHelper.getInstance(getApplicationContext());
        customerDao = databaseHelper.customerDao();
    }


    void hideActionBar(){
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    void init(){
        save_customer = findViewById(R.id.save_customer);
        cancle_ed_add = findViewById(R.id.cancel_customer);
        customer_name_edt = findViewById(R.id.get_customer_name);
        customer_phone_edt = findViewById(R.id.get_customer_phone);
        customer_adress_edt = findViewById(R.id.get_customer_address);
        customer_anim_feilds = findViewById(R.id.customer_info_feilds);
    }


}