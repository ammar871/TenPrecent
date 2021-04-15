package com.tenpercent.editor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.tenpercent.pojo.loginpojo.LoginModel;
import com.tenpercent.pojo.rigetserPojo.ModelRegster;

import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;

public class ShardEditor {
    SharedPreferences mSharedPreferences;
    SharedPreferences.Editor mEditor;
    Context mContext;
    private static final String FILE_NAME = "precentApp";
    public static final String KEY_LATITUDE = "Latitude";
    public static final String KEY_LONGITUDE = "Longitude";
    public static final String KEY_TOKEN = "token";
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PHONE = "phone";
    public static final String IS_LOGIN = "islogin";
    @SuppressLint("CommitPrefEdits")
    public ShardEditor(Context mContext) {
        this.mContext = mContext;
        mSharedPreferences = mContext.getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }

    public void saveData(String latitude,String longitude) {
        mEditor.putString(KEY_LATITUDE, latitude);
        mEditor.putString(KEY_LONGITUDE, longitude);
        mEditor.commit();
    }

    public void saveToken(ModelRegster token) {
        mEditor.putString(KEY_TOKEN, token.getToken());
        mEditor.putString(KEY_NAME, token.getName());
        mEditor.putString(KEY_PHONE, token.getPhone());
        mEditor.putString(KEY_EMAIL, token.getEmail());

        mEditor.commit();
    }


    public void getToken(String token) {
        HashMap<String, String> userData = new HashMap<>();

        userData.put(KEY_TOKEN, mSharedPreferences.getString(KEY_TOKEN, ""));
        mEditor.commit();
    }

    public void saveDataEnterd(boolean entred) {

        mEditor.putBoolean(IS_LOGIN, entred);
        mEditor.commit();
    }
    public HashMap<String, String> loadData() {
        HashMap<String, String> userData = new HashMap<>();

        userData.put(KEY_LATITUDE, mSharedPreferences.getString(KEY_LATITUDE, ""));
        userData.put(KEY_LONGITUDE, mSharedPreferences.getString(KEY_LONGITUDE, ""));
        userData.put(KEY_TOKEN, mSharedPreferences.getString(KEY_TOKEN, ""));
        userData.put(KEY_NAME, mSharedPreferences.getString(KEY_NAME, ""));
        userData.put(KEY_EMAIL, mSharedPreferences.getString(KEY_EMAIL, ""));
        userData.put(KEY_PHONE, mSharedPreferences.getString(KEY_PHONE, ""));
        return userData;
    }

    public HashMap<String, Boolean> loadDataEnter() {
        HashMap<String, Boolean> userData = new HashMap<>();

        userData.put(IS_LOGIN, mSharedPreferences.getBoolean(IS_LOGIN, false));

        return userData;
    }
    public void logOut() {
        mEditor.clear();
        mEditor.commit();

    }


}
