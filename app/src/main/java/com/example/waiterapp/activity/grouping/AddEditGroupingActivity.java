package com.example.waiterapp.activity.grouping;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.waiterapp.R;
import com.example.waiterapp.database.DatabaseHelper;
import com.example.waiterapp.database.dao.GroupingDao;
import com.example.waiterapp.helper.App;
import com.example.waiterapp.helper.Tools;
import com.example.waiterapp.model.Grouping;
import com.google.gson.Gson;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddEditGroupingActivity extends AppCompatActivity {

    SlidrInterface slidrInterface;
    LinearLayout grouping_anim_feilds;
    ConstraintLayout grouping_desing_anim;
    TextView save_grouping , cancle_tv;
    GroupingDao groupingDao;
    Grouping grouping;
    DatabaseHelper databaseHelper;
    CircleImageView grouping_add_img;
    EditText grouping_name_edt;
    String save;
    String grouping_name , grouping_previous_name;
    private static final int PICK_IMAGE = 100;
    Uri imageuri , img_previouse_uri;
    private String Timemilisecond = String.valueOf(System.currentTimeMillis());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideActionBar();
        setContentView(R.layout.activity_add_edit_grouping);

        slidrInterface = Slidr.attach(this);

        init();
        call_db();
        check_db();
        click_img();
        animateOb();
        save_bttn();
        cancle_bttn();
        hideKeyBoard();

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
        grouping_add_img = findViewById(R.id.grouping_add_img);
    }
    void call_db(){
        databaseHelper = App.getDatabase();
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
            grouping_name_edt.setText(grouping.name);
            img_previouse_uri = Uri.parse(grouping.picture);
            grouping_add_img.setImageURI(Uri.parse(grouping.picture));
        }
    }

    public void save_bttn(){
        save_grouping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                grouping_name = grouping_name_edt.getText().toString();

                if(grouping == null){

                    if(TextUtils.isEmpty(grouping_name) || TextUtils.isEmpty(save)){

                        Toast.makeText(AddEditGroupingActivity.this, "تمام فیلد هارا پر کنید!", Toast.LENGTH_LONG).show();

                    }else if(groupingDao.getOneName(grouping_name) != null){
                        Toast.makeText(AddEditGroupingActivity.this, "این دسته بندی تکراری است!", Toast.LENGTH_LONG).show();

                    } else {
                        groupingDao.insertGrouping(new Grouping(grouping_name , save));
                        Toast.makeText(getApplicationContext(), grouping_name + " با موفقیت به لیست اضافه شد ", Toast.LENGTH_LONG).show();
                        finish();

                    }
                }else {
                    grouping.name = grouping_name;
                    if(TextUtils.isEmpty(save)){
                        save = img_previouse_uri.toString();
                        grouping.picture = save;
                    }else {
                        grouping.picture = save;
                    }
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
    void click_img(){
        grouping_add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
//                startActivityForResult(Intent.createChooser(intent , "Select Picture") , PICK_IMAGE);

                if(checkStoregPermition()){
                    CropImage.activity()
                            .setGuidelines(CropImageView.Guidelines.ON)
                            .start(AddEditGroupingActivity.this);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(resultCode == RESULT_OK){
//                imageuri=data.getData();
//                grouping_add_img.setImageURI(imageuri);
                Uri resultUri = result.getUri();
                save = Tools.saveFile(Tools.getBytes(resultUri),new File( Environment.getExternalStorageDirectory() + "/DCIM/Barista") , Timemilisecond +".jpg");
                grouping_add_img.setImageURI(resultUri);
//                imageView_back.setVisibility(View.GONE);
                Log.e("qqqqfile", "onActivityResult: " + save );
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    private void hideKeyBoard(){

        grouping_name_edt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken() , 0);
                }

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 200) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(this, "Storage Permission Granted", Toast.LENGTH_SHORT).show();

            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(" دسترسی به مجوزها ");
                builder.setPositiveButton("برو به تنظیمات", (dialog, which) -> {
                    dialog.cancel();
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivityForResult(intent, 200);
                });
                builder.setNegativeButton("بستن", (dialog, which) -> {
                    dialog.cancel();
//                        finish();
                });
                builder.show();
            }
        }
    }
    public Boolean checkStoregPermition() {
        String[] permission = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        if (ContextCompat.checkSelfPermission(this, permission[0]) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(AddEditGroupingActivity.this, new String[]{permission[0]}, 200);
        } else if (ContextCompat.checkSelfPermission(this, permission[1]) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(AddEditGroupingActivity.this, new String[]{permission[1]}, 200);
        } else {
            return true;
        }
        return false;
    }

}