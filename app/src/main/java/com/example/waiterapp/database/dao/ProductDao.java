package com.example.waiterapp.database.dao;

import androidx.room.Dao;
import androidx.room.Update;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.waiterapp.model.Customer;
import com.example.waiterapp.model.Product;
import java.util.List;

@Dao
public interface ProductDao {
    @Query("Select * from Product_table")
    List<Customer> getProductList();

    @Insert
    void insertProduct(Product product);
    @Delete
    void deleteProduct(Product product);
    @Update
    void updateProduct(Product product);
}
