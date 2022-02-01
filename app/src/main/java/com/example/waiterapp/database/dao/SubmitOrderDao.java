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


    @Query("Select * from table_order order by date DESC")
    List<Order> getOrderListByDate();

    @Query("Select total from table_order where date = :date limit 1")
    String date(String date);

    @Query("Select total from table_order where date = :datee")
    List<String> dailyTotal( String datee);

    @Query("Select * from table_order where name_orderer = :name ")
    List<Order> listByName( String name);

    @Query("select * from table_order where date >= :date")
    List<Order> getOrderListDate(String date);


    @Query("Select * from table_order where orderer_id = :id limit 1 " )
    Order getid( int id);

    @Query("DELETE from table_order where orderer_id = :id " )
    void deteteID(int id);

    @Query("Select date from table_order")
    List<String> getAllDate();

    @Query("Select total from table_order")
    List<String> getAllTotal();




    @Insert
    void insertOrder(Order order);

    @Update
    void updateOrder(Order order );

    @Delete
    void deleteOrder(Order order);

}
