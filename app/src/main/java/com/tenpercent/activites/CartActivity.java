package com.tenpercent.activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ammarten.tenpercent.R;
import com.ammarten.tenpercent.databinding.ActivityCartBinding;
import com.tenpercent.editor.ShardEditor;
import com.tenpercent.network.APIClient;
import com.tenpercent.network.APIInterFace;
import com.tenpercent.pojo.ProductCart;
import com.tenpercent.pojo.Products;
import com.tenpercent.pojo.loginpojo.EditeModel;
import com.tenpercent.pojo.rigetserPojo.ModelRegster;
import com.tenpercent.roomdatabase.AppDatabase;
import com.tenpercent.roomdatabase.RoomAdpter;
import com.tenpercent.roomdatabase.RoomAdpter.ListAdapterListeneradd;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity implements RoomAdpter.Callback, RoomAdpter.ListAdapterListeneradd
        , RoomAdpter.ListAdapterListenerminus {
    private AppDatabase database;

    private LinearLayoutManager mLayoutManager;
    ShardEditor sharedEditor;
    ActivityCartBinding binding;
    ArrayList<ProductCart> cartList;
    RoomAdpter mUserAdapter;
    double total;
    APIInterFace apiInterFace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart);
        apiInterFace = APIClient.getClient().create(APIInterFace.class);
        database = AppDatabase.getDatabaseInstance(this);
        sharedEditor = new ShardEditor(this);
        setUp();

        binding.btnAddOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONArray array = new JSONArray();
                if (cartList.size() > 0) {
                    for (int i = 0; i < cartList.size(); i++) {
                        JSONObject obj = new JSONObject();


                        try {
                            obj.put("id", cartList.get(i).getId());
                            obj.put("quantity", cartList.get(i).getOrderCount());

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        array.put(obj);

                    }
                    callResponse(binding.tvTotal.getText().toString(), array);

                } else {
                    dialogWrong();

                }
            }
        });


    }

    private void callResponse(String toString, JSONArray array) {
        Call<Boolean> call = apiInterFace.addOrders(
                "Bearer" + " " + sharedEditor.loadData().get(ShardEditor.KEY_TOKEN),
                total,
                array);

        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(@NonNull Call<Boolean> call, @NonNull Response<Boolean> response) {


                if (response.code() == 200) {


                    dialogSuccess();
                    database.cartDao().nukeTable();
                    setUp();
                    binding.tvTotal.setText("0.0");


                } else {

                }


            }

            @Override
            public void onFailure
                    (@NonNull Call<Boolean> call, @NonNull Throwable t) {
                Log.d("error", "onFailure: " + t.getMessage());
                Toast.makeText(CartActivity.this, "فشلت العملية", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void dialogSuccess() {

        new SweetAlertDialog(CartActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("حــالة الطلب ")
                .setContentText("تــم ارسال الطلب بنجاح سيتم التواصل معك ")
                .setConfirmText("رجــوع")
                .setConfirmClickListener(sDialog -> sDialog.dismissWithAnimation())
                .show();
    }

    private void dialogWrong() {

        new SweetAlertDialog(CartActivity.this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("حــالة الطلب ")
                .setContentText("الســلة فارغة ..الرجاء الاضافة فى السلــة  ")
                .setConfirmText("حسنــا")
                .setConfirmClickListener(sDialog -> sDialog.dismissWithAnimation())
                .show();
    }

    private void inItView() {
        database = AppDatabase.getDatabaseInstance(this);
        sharedEditor = new ShardEditor(this);
    }


    private void setUp() {
        cartList = (ArrayList<ProductCart>) database.cartDao().getAll();
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.rvCart.setLayoutManager(mLayoutManager);
        binding.rvCart.setItemAnimator(new DefaultItemAnimator());
        mUserAdapter = new RoomAdpter(cartList, this, this, this, this);
        mUserAdapter.notifyDataSetChanged();
        mUserAdapter.setmCallback(this);
        binding.rvCart.setAdapter(mUserAdapter);


        //total
        if (cartList.size() > 0) {
            binding.rvCart.setVisibility(View.VISIBLE);
            binding.tvImpty.setVisibility(View.GONE);
            for (ProductCart orders : cartList) {


                total += orders.getPrice() * orders.getOrderCount();
                Locale locale = new Locale("eng", "EG");
                NumberFormat ftm = NumberFormat.getCurrencyInstance(locale);
                binding.tvTotal.setText(ftm.format(total));


            }


        } else {
            binding.rvCart.setVisibility(View.GONE);
            binding.tvImpty.setVisibility(View.VISIBLE);
        }


    }


    @Override
    public void onClickAtOKButtonadd(ProductCart position) {
        int newCounter = position.getOrderCount();
        newCounter++;
        ProductCart productCart = new ProductCart(position.getId(),
                position.getName(),
                position.getDesc(),
                position.getImage(),
                position.getCategoryId(),
                position.getSellerId(),
                newCounter,
                position.getRate(),
                position.getPrice(),
                position.getDiscount()
        );

        database.cartDao().updateUser(productCart);
        cartList = (ArrayList<ProductCart>) database.cartDao().getAll();

        double total = 0.0;
        for (ProductCart orders : cartList) {
            total += orders.getPrice() * orders.getOrderCount();
            Locale locale = new Locale("eng", "EG");
            NumberFormat ftm = NumberFormat.getCurrencyInstance(locale);
            binding.tvTotal.setText(ftm.format(total));
        }

        mUserAdapter.updateData(cartList);


    }

    @Override
    public void onDeleteClick(ProductCart mUser) {

        database.cartDao().delete(mUser);
        mUserAdapter.addItems(database.cartDao().getAll());
        cartList = (ArrayList<ProductCart>) database.cartDao().getAll();
        double total = 0.0;
        for (ProductCart orders : cartList) {
            total += orders.getPrice() * orders.getOrderCount();
            Locale locale = new Locale("eng", "EG");
            NumberFormat ftm = NumberFormat.getCurrencyInstance(locale);
            binding.tvTotal.setText(ftm.format(total));
        }

        mUserAdapter.updateData(cartList);

        if (cartList.size() > 0) {
            binding.rvCart.setVisibility(View.VISIBLE);
            binding.tvImpty.setVisibility(View.GONE);


        } else {
            binding.rvCart.setVisibility(View.GONE);
            binding.tvImpty.setVisibility(View.VISIBLE);
            binding.tvTotal.setText("0.0");
        }
    }

    @Override
    public void onClickAtOKButtonmins(ProductCart position) {

        int newCounter = position.getOrderCount();

        if (newCounter > 1) {

            newCounter--;

            ProductCart productCart = new ProductCart(position.getId(),
                    position.getName(),
                    position.getDesc(),
                    position.getImage(),
                    position.getCategoryId(),
                    position.getSellerId(),
                    newCounter,
                    position.getRate(),
                    position.getPrice(),
                    position.getDiscount()
            );

            database.cartDao().updateUser(productCart);

            cartList = (ArrayList<ProductCart>) database.cartDao().getAll();

            double total = 0.0;

            for (ProductCart orders : cartList) {

                total += orders.getPrice() * orders.getOrderCount();
                Locale locale = new Locale("eng", "EG");
                NumberFormat ftm = NumberFormat.getCurrencyInstance(locale);
                binding.tvTotal.setText(ftm.format(total));
            }

            mUserAdapter.updateData(cartList);

        }
    }

    @Override
    public void onBackPressed() {

        startActivity(new Intent(CartActivity.this, HomeActivity.class));
        finish();

    }


}