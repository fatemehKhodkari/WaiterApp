package com.example.waiterapp.helper;



import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.example.waiterapp.database.DatabaseHelper;

public class App extends Application {

    public static DatabaseHelper databaseHelper;
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        databaseHelper = DatabaseHelper.getInstance(context);
        Log.e("qqqqapp", "onCreate: app" );
    }

    public static DatabaseHelper getDatabase(){
        if (!databaseHelper.isOpen() || databaseHelper == null){
            databaseHelper = DatabaseHelper.getInstance(context);
        }
        return databaseHelper;
    }

}
