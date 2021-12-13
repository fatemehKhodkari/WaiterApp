package com.example.waiterapp.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


@Entity(tableName = "table_order")
public class Order {
    @PrimaryKey(autoGenerate=true)
    public int id;

    @ColumnInfo(name = "name_orderer")
    public String name_orderer;

    @ColumnInfo(name = "unit_code")
    public String unit_code;

    @ColumnInfo (name = "orderer_id")
    public  int orderer_id;

    @ColumnInfo (name = "status")
    public  int status;

    @ColumnInfo (name = "total")
    public  String total;

    @ColumnInfo (name = "description")
    public String description;

    @ColumnInfo(name = "time")
    public String time;


    @ColumnInfo(name = "date")
    public String date;

    public Order(int id, String name_orderer, String unit_code, int orderer_id, int status, String total, String description ,String time, String date) {
        this.id = id;
        this.name_orderer = name_orderer;
        this.unit_code = unit_code;
        this.orderer_id = orderer_id;
        this.status = status;
        this.total = total;
        this.description = description;
        this.time = time;
        this.date = date;
    }

    @Ignore
    public Order(String name_orderer, String unit_code, int orderer_id, int status, String total, String description ,String time, String date) {
        this.name_orderer = name_orderer;
        this.unit_code = unit_code;
        this.orderer_id = orderer_id;
        this.status = status;
        this.total = total;
        this.description = description;
        this.time = time;
        this.date = date;
    }
}
