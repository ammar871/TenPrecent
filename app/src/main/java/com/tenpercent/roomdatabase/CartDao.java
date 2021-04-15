package com.tenpercent.roomdatabase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.tenpercent.pojo.ProductCart;
import com.tenpercent.pojo.Products;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface CartDao {
    @Query("SELECT * FROM ProductCart")
    List<ProductCart> getAll();

    @Insert(onConflict = REPLACE)
    void insertUser(ProductCart mUser);

    @Insert
    void insertAllUser(ProductCart... mUsersList);

    @Delete
    void delete(ProductCart mUser);

    @Update
    void updateUser(ProductCart mUser);

    @Query("SELECT * FROM ProductCart WHERE `id` = :uId")
    ProductCart getUserById(int uId);

    @Query("SELECT * FROM ProductCart WHERE `id` IN (:userIds)")
    List<ProductCart> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM ProductCart WHERE `id` LIKE :name LIMIT 1")
    boolean findByName(int name);

    @Query("DELETE FROM ProductCart")
    void nukeTable();

    @Query("SELECT `id` FROM ProductCart")
    List<Integer> getCount();


}
