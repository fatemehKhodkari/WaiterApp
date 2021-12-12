package com.example.waiterapp.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.waiterapp.model.DetailOrder;

import java.util.List;

@Dao
public interface DetailOrderDao {
    @Query("Select * from detailorder")
    List<DetailOrder> getDetailOrderList();

    @Query("Select * from detailorder where code = :code")
    List<DetailOrder> getSpecificOrder(String code);

    @Insert
    void insertDetailOrder(DetailOrder detailOrder);

    @Update
    void updateDetailOrder(DetailOrder detailOrder);

    @Delete
    void deleteDetailOrder(DetailOrder detailOrder);

}
