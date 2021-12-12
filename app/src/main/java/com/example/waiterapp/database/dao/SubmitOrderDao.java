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


    @Insert
    void insertOrder(Order order);

    @Update
    void updateOrder(Order order );

    @Delete
    void deleteOrder(Order order);

}
