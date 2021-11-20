package com.example.waiterapp.database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.waiterapp.database.dao.CustomerDao;
import com.example.waiterapp.database.dao.GroupingDao;
import com.example.waiterapp.database.dao.ProductDao;
import com.example.waiterapp.model.Customer;
import com.example.waiterapp.model.Grouping;
import com.example.waiterapp.model.Product;

@Database(entities = {Grouping.class, Product.class, Customer.class} , exportSchema = false , version = 1)
public abstract class DatabaseHelper extends RoomDatabase {
    private static final String DB_NAME = "db_name";
    private static DatabaseHelper instance;


    public static synchronized DatabaseHelper getInstance(Context context){
        if(instance ==null){
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    DatabaseHelper.class,
                    DB_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public abstract ProductDao productDao();
    public abstract GroupingDao groupingDao();
    public abstract CustomerDao customerDao();

}
