package com.example.waiterapp.database.dao;

import androidx.room.Dao;
import androidx.room.Update;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.waiterapp.model.Product;
import java.util.List;

@Dao
public interface ProductDao {
    @Query("Select * from Product_table")
    List<Product> getProductList();

    @Query("SELECT * FROM Product_table WHERE category = :category")
    List<Product> getListByCtegory(String category);

    @Insert
    void insertProduct(Product product);
    @Delete
    void deleteProduct(Product product);
    @Update
    void updateProduct(Product product);
}
