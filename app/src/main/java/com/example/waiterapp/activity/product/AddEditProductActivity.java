package com.example.waiterapp.activity.product;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.waiterapp.R;
import com.example.waiterapp.database.DatabaseHelper;
import com.example.waiterapp.database.dao.GroupingDao;
import com.example.waiterapp.database.dao.ProductDao;
import com.example.waiterapp.design.NumberTextWatcherForThousand;
import com.example.waiterapp.helper.App;
import com.example.waiterapp.helper.Tools;
import com.example.waiterapp.model.Product;
import com.google.gson.Gson;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

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
    private static final int PICK_IMAGE = 100;
    Uri imageuri , img_previouse_uri;
    CircleImageView product_add_img;
    String product_name , product_grouping_name , product_price ;
    private String save;
    private String Timemilisecond = String.valueOf(System.currentTimeMillis());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideActionBar();
        setContentView(R.layout.activity_add_edit_product);

        slidrInterface = Slidr.attach(this);

        call_db();
        init();
        check_db();
        onclick_img();
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
        product_add_img = findViewById(R.id.product_add_img);
    }
    void thousandNumbersEdt(){
        product_price_edt.addTextChangedListener(new NumberTextWatcherForThousand(product_price_edt));
    }
    void call_db(){
        databaseHelper = App.getDatabase();
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
            img_previouse_uri = Uri.parse(product.picture_product);
            product_add_img.setImageURI(Uri.parse(product.picture_product));
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
                    if(TextUtils.isEmpty(product_name) || TextUtils.isEmpty(product_grouping_name) || TextUtils.isEmpty(product_price) || TextUtils.isEmpty(save)){
                        Toast.makeText(getApplicationContext(),"تمام فیلد هارا پر کنید!",Toast.LENGTH_SHORT).show();
                    }else
                        if(productDao.getOneName(product_name) != null){
                            Toast.makeText(AddEditProductActivity.this, " این محصول تکراری است! ", Toast.LENGTH_SHORT).show();
                        }else if(groupingDao.getOneName(product_grouping_name) == null){
                            Toast.makeText(AddEditProductActivity.this,  " دسته بندی "+ product_grouping_name + " وجود ندارد ", Toast.LENGTH_SHORT).show();

                        }
                        else{
                        productDao.insertProduct(new Product(product_name,product_grouping_name,product_price , save));
                        Toast.makeText(getApplicationContext(),"با موفقیت به لیست اضافه شد",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }else {
                    product.name_product = product_name;
                    product.category = product_grouping_name;
                    product.price = product_price;
                    if(TextUtils.isEmpty(save)){
                        save = img_previouse_uri.toString();
                        product.picture_product = save;
                    }else {
                        product.picture_product = save;
                    }
                    product.picture_product = save;
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

    void set_autoCompleteTV(){
    }

    void onclick_img(){
        product_add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
//                startActivityForResult(Intent.createChooser(intent , "Select Picture") , PICK_IMAGE);

                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(AddEditProductActivity.this);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if(resultCode == RESULT_OK){
//            if(requestCode == PICK_IMAGE){
//                imageuri=data.getData();
//                product_add_img.setImageURI(imageuri);
//            }
//        }
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(resultCode == RESULT_OK){
                Uri resultUri = result.getUri();
                save = Tools.saveFile(Tools.getBytes(resultUri),new File( Environment.getExternalStorageDirectory() + "/DCIM/Barista") , Timemilisecond +".jpg");
                product_add_img.setImageURI(resultUri);
                Log.e("qqqqfile", "onActivityResult: " + save );
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}