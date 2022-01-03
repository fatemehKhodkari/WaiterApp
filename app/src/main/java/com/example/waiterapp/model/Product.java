package com.example.waiterapp.model;

import androidx.room.Ignore;
import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;
import androidx.room.Entity;

@Entity(tableName = "Product_table")
public class Product {

    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "name_product")
    public String name_product;
    @ColumnInfo(name = "category")
    public String category;
    @ColumnInfo(name = "price")
    public String price;
    @ColumnInfo(name = "picture_product")
    public String picture_product;
    @Ignore
    public int amount = 1;

    public Product(int id, String name_product, String category, String price , String picture_product) {
        this.id = id;
        this.name_product = name_product;
        this.category = category;
        this.price = price;
        this.picture_product = picture_product;
    }

    @Ignore
    public Product(String name_product, String category, String price , String picture_product) {
        this.name_product = name_product;
        this.category = category;
        this.price = price;
        this.picture_product = picture_product;
    }
}
