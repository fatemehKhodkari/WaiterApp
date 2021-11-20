package com.example.waiterapp.database.dao;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Insert;
import androidx.room.Delete;
import androidx.room.Update;

import com.example.waiterapp.model.Customer;

import java.util.List;

@Dao
public interface CustomerDao {

    @Query("Select * from customer_table")
    List<Customer> getCustomerList();

    @Query("Select * from customer_table where phone = :phone limit 1")
    Customer getCustomer(String phone);

    @Insert
    void insertCustomer(Customer customer);
    @Update
    void updateCustomer(Customer customer);
    @Delete
    void deleteCustomer(Customer customer);
}
