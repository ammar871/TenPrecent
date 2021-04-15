package com.tenpercent.activites;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.ammarten.tenpercent.R;
import com.ammarten.tenpercent.databinding.ActivitySearshBinding;
import com.google.android.material.snackbar.Snackbar;
import com.tenpercent.activites.interFace.CallFave;
import com.tenpercent.adpters.AdapterProductAll;
import com.tenpercent.editor.ShardEditor;
import com.tenpercent.network.APIClient;
import com.tenpercent.network.APIInterFace;
import com.tenpercent.pojo.Products;
import com.tenpercent.roomdatabase.AppDatabase;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearshActivity extends AppCompatActivity implements AdapterProductAll.OnClickListenerAll, CallFave {
    ActivitySearshBinding binding;
    ArrayList<Products> listpro;

    private AppDatabase database;
    String getID;
    private LinearLayoutManager mLayoutManager;
    ShardEditor sharedEditor;
    APIInterFace apiInterFace;
    AdapterProductAll adapterProductAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_searsh);
        inItView();
        binding.buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hidekeybeard();
                binding.edSearsh.getText().toString().trim();
                if (!binding.edSearsh.getText().toString().trim().equals("")) {
                    binding.progressbar.setVisibility(View.VISIBLE);
                    binding.tvSearch.setVisibility(View.GONE);

                    loadDataResulteSearch(binding.edSearsh.getText().toString().trim());
                } else {
                    Snackbar snackbar = Snackbar
                            .make(binding.containerProducte, "أدخــل كلمة البحث ", Snackbar.LENGTH_LONG);
                    snackbar.setActionTextColor(Color.YELLOW);
                    snackbar.show();
                }
            }
        });


    }

    private void loadDataResulteSearch(String nameOfProduct) {
        Call<List<Products>> call =
                apiInterFace.searshProduct(sharedEditor.loadData().get(ShardEditor.KEY_TOKEN), nameOfProduct);
        call.enqueue(new Callback<List<Products>>() {
            @Override
            public void onResponse(@NonNull Call<List<Products>> call, @NonNull Response<List<Products>> response) {
                binding.progressbar.setVisibility(View.GONE);
                binding.tvSearch.setVisibility(View.VISIBLE);
                if (response.code() == 200 || response.body() != null) {

                    assert response.body() != null;
                    if (response.body().size() > 0) {
                        binding.rcProduct.setVisibility(View.VISIBLE);
                        binding.tvImpty.setVisibility(View.GONE);
                        adapterOffers(response.body());

                        binding.rcProduct.setAdapter(adapterProductAll);
                    } else {

                        binding.rcProduct.setVisibility(View.GONE);
                        binding.tvImpty.setVisibility(View.VISIBLE);
                    }


                }


            }

            @Override
            public void onFailure(@NonNull Call<List<Products>> call, @NonNull Throwable t) {
                Log.d("catogery", "onResponse: " + t.getMessage());
                binding.progressbar.setVisibility(View.GONE);
                binding.tvSearch.setVisibility(View.VISIBLE);
            }
        });
    }

    private void adapterOffers(List<Products> list) {
        adapterProductAll
                = new AdapterProductAll(list, this, this, this);
    }

    private void inItView() {
        binding.rcProduct.setLayoutManager(new GridLayoutManager(this, 2));
        binding.rcProduct.setHasFixedSize(true);
        database = AppDatabase.getDatabaseInstance(this);
        sharedEditor = new ShardEditor(this);
        apiInterFace = APIClient.getClient().create(APIInterFace.class);
        database = AppDatabase.getDatabaseInstance(this);
        sharedEditor = new ShardEditor(this);
    }

    @Override
    public void getCounterFaveOffers(int count, String name) {

    }

    @Override
    public void getCounterFaveRecommend(int count, String name) {

    }

    @Override
    public void getCounterFaveProduct(int count, String name) {

    }

    @Override
    public void getCounterFaveViewAll(int count, String name) {
        Snackbar snackbar = Snackbar
                .make(binding.containerProducte, name, Snackbar.LENGTH_LONG);
        snackbar.setActionTextColor(Color.YELLOW);
        snackbar.show();
    }

    @Override
    public void onClickItemViewAll(Products position) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("pro_cato", position);

        startActivityForResult(intent, 1);
    }

    @Override
    public void getCounterViewAll(int count, String name) {
        Snackbar snackbar = Snackbar
                .make(binding.containerProducte, name, Snackbar.LENGTH_LONG);
        snackbar.setActionTextColor(Color.YELLOW);
        snackbar.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (adapterProductAll != null)
                adapterProductAll.notifyDataSetChanged();
        }
    }

    void hidekeybeard() {
        // Then just use the following:
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(binding.containerProducte.getWindowToken(), 0);
    }
}