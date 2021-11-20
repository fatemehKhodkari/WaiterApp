package com.example.waiterapp.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.waiterapp.model.Customer;
import com.example.waiterapp.model.Grouping;
import com.example.waiterapp.model.Product;

import java.util.List;

@Dao
public interface GroupingDao {
    @Query("Select * from grouping_table")
    List<Grouping> getGroupingList();

    @Query("Select name_group from grouping_table")
    List<String> getname();

    @Query("Select * from grouping_table where name_group = :name limit 1")
    Grouping getOneName(String name);


    @Insert
    void insertGrouping(Grouping grouping);

    @Update
    void updateGrouping(Grouping grouping );

    @Delete
    void deleteGrouping(Grouping grouping);

}
