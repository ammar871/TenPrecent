package com.tenpercent.activites;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.ammarten.tenpercent.R;
import com.ammarten.tenpercent.databinding.ActivityMyFavoraiteBinding;
import com.google.android.material.snackbar.Snackbar;
import com.tenpercent.adpters.AdapterFavorate;
import com.tenpercent.editor.RecyclerItemTouchHelper;
import com.tenpercent.pojo.ProductCart;
import com.tenpercent.pojo.Products;
import com.tenpercent.roomdatabase.AppDatabase;
import com.tenpercent.roomdatabase.RoomAdpter;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class MyFavoraiteActivity extends AppCompatActivity
        implements AdapterFavorate.Callback,
        RecyclerItemTouchHelper.RecyclerItemTouchHelperListener ,AdapterFavorate.OnClickListenerFav{

    ActivityMyFavoraiteBinding binding;
    ArrayList<Products> cartList;
    AppDatabase database;
    AdapterFavorate mUserAdapter;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_favoraite);
        database = AppDatabase.getDatabaseInstance(this);
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(binding.rvFav);

        setUp();


    }

    private void setUp() {
        cartList = (ArrayList<Products>) database.userDao().getAll();
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.rvFav.setLayoutManager(mLayoutManager);
        binding.rvFav.setItemAnimator(new DefaultItemAnimator());


        if (cartList.size()>0){

            mUserAdapter = new AdapterFavorate(cartList, this,this,this);
            mUserAdapter.notifyDataSetChanged();
            mUserAdapter.setmCallback(this);
            binding.rvFav.setAdapter(mUserAdapter);
            binding.tvImpty.setVisibility(View.GONE);
            binding.rvFav.setVisibility(View.VISIBLE);
        }else {
            binding.tvImpty.setVisibility(View.VISIBLE);
            binding.rvFav.setVisibility(View.GONE);

        }




    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof AdapterFavorate.ViewHolderVidio) {
            Products itemsCart = database.userDao().getAll().get(viewHolder.getAdapterPosition());
            database.userDao().delete(itemsCart);
            mUserAdapter.addItems(database.userDao().getAll());
            cartList = (ArrayList<Products>) database.userDao().getAll();

            setUp();
            Snackbar snackbar = Snackbar
                    .make(binding.coordinatorLayoutFav, itemsCart.getName() + " تم حذف ", Snackbar.LENGTH_LONG);
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
    }

    @Override
    public void onDeleteClick(Products mUser) {

    }

    @Override
    public void onClickItemViewFav(Products position) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("pro_cato", position);

        startActivityForResult(intent, 1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (mUserAdapter!=null)
                mUserAdapter.notifyDataSetChanged();
        }
    }

}