package com.tenpercent.roomdatabase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.tenpercent.pojo.Products;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface UserDao {

    @Query("SELECT * FROM products")
    List<Products> getAll();

    @Insert(onConflict = REPLACE)
    void insertUser(Products mUser);

    @Insert
    void insertAllUser(Products... mUsersList);

    @Delete
    void delete(Products mUser);

    @Update
    void updateUser(Products mUser);

    @Query("SELECT * FROM Products WHERE `id` = :uId")
    Products getUserById(int uId);

    @Query("SELECT * FROM Products WHERE `id` IN (:userIds)")
    List<Products> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM products WHERE `id` LIKE :name LIMIT 1")
    boolean findByName(int name);

    @Query("DELETE FROM Products")
    void nukeTable();

    @Query("SELECT `id` FROM products")
    List<Integer> getCount();
}
