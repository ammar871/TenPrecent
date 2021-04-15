package com.tenpercent.roomdatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.tenpercent.pojo.ProductCart;
import com.tenpercent.pojo.Products;


@Database(entities = {Products.class, ProductCart.class}, version = 9, exportSchema = false)

public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase mInstance;
    private static final String DATABASE_NAME = "wave-database";
    public abstract UserDao userDao();
    public abstract CartDao cartDao();

    public synchronized static AppDatabase getDatabaseInstance(Context context) {

        if (mInstance == null) {
            mInstance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return mInstance;
    }

    public static void destroyInstance() {
        mInstance = null;
    }

}
