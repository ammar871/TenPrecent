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
import com.ammarten.tenpercent.databinding.ActivityPrductesBinding;
import com.google.android.material.snackbar.Snackbar;
import com.tenpercent.activites.interFace.CallFave;
import com.tenpercent.adpters.AdapterOffers;
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

public class PrductesActivity extends AppCompatActivity implements AdapterProductAll.OnClickListenerAll, CallFave {
    ActivityPrductesBinding binding;
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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_prductes);
        inItView();
        getCountCart();
        getCountFave();
        binding.imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PrductesActivity.this, CartActivity.class));
            }
        });
        binding.imgNotiy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PrductesActivity.this, MyFavoraiteActivity.class));
            }
        });
        if (getIntent() != null) {
            getID = getIntent().getStringExtra("id");
            loadProductByCategory(getID);



        }


        binding.edSearsh.setFocusable(false);
        binding.edSearsh.setOnClickListener(v -> {

            binding.edSearsh.setFocusableInTouchMode(true);
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 1);
        });
        // loadDataProduct();

    }
    private void adapterOffers(List<Products> list) {
        adapterProductAll
                = new AdapterProductAll(list, this,this,this);
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

    private void loadProductByCategory(String id) {
        Call<List<Products>> call = apiInterFace.getProductsByCategory(id);
        call.enqueue(new Callback<List<Products>>() {
            @Override
            public void onResponse(@NonNull Call<List<Products>> call, @NonNull Response<List<Products>> response) {
                if (response.code() == 200 || response.body() != null) {

                    assert response.body() != null;

                  adapterOffers(response.body());

                    binding.rcProduct.setAdapter(adapterProductAll);


                }


            }

            @Override
            public void onFailure(@NonNull Call<List<Products>> call, @NonNull Throwable t) {
                Log.d("catogery", "onResponse: " + t.getMessage());

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (adapterProductAll!=null)
                adapterProductAll.notifyDataSetChanged();
        }
    }

    @Override
    public void onClickItemViewAll(Products position) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("pro_cato", position);

        startActivityForResult(intent, 1);
    }

    @Override
    public void getCounterViewAll(int count,String name) {

        binding.tvCounterCart.setText("" + count);

        getCountCart();
        Snackbar snackbar = Snackbar
                .make(binding.containerProducte, name, Snackbar.LENGTH_LONG);
        snackbar.setActionTextColor(Color.YELLOW);
        snackbar.show();
    }
    private void getCountCart() {
        if (database.cartDao().getAll().size() > 0) {

            binding.relatevHidCart.setVisibility(View.VISIBLE);
            binding.tvCounterCart.setText(database.cartDao().getAll().size() + "");

        } else {

            binding.relatevHidCart.setVisibility(View.GONE);

        }}

    @Override
    public void getCounterFaveOffers(int count,String name) {

    }

    @Override
    public void getCounterFaveRecommend(int count,String name) {

    }

    @Override
    public void getCounterFaveProduct(int count,String name) {

    }

    @Override
    public void getCounterFaveViewAll(int count,String name) {
        binding.tvCounterFave.setText(count+"");
        getCountFave();

        Snackbar snackbar = Snackbar
                .make(binding.containerProducte, name, Snackbar.LENGTH_LONG);
        snackbar.setActionTextColor(Color.YELLOW);
        snackbar.show();
    }
    private void getCountFave() {
        if (database.userDao().getAll().size() > 0) {

            binding.relatevHidFave.setVisibility(View.VISIBLE);
            binding.tvCounterFave.setText(database.userDao().getAll().size() + "");

        } else {

            binding.relatevHidFave.setVisibility(View.GONE);

        }
    }
}