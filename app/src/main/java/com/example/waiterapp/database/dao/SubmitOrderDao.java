package com.example.waiterapp.database.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.waiterapp.model.Order;
import java.util.List;

@Dao
public interface SubmitOrderDao {
    @Query("Select * from table_order")
    List<Order> getOrderList();

    @Query("Select total from table_order where date = :date limit 1")
    String date(String date);

    @Query("Select total from table_order where date = :datee ")
    List<String> alldate( String datee);


    @Insert
    void insertOrder(Order order);

    @Update
    void updateOrder(Order order );

    @Delete
    void deleteOrder(Order order);

}
