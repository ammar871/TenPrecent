package com.tenpercent.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ammarten.tenpercent.R;
import com.ammarten.tenpercent.databinding.ActivityDetailsBinding;
import com.bumptech.glide.Glide;
import com.tenpercent.auth.LoginActivity;
import com.tenpercent.editor.ShardEditor;
import com.tenpercent.pojo.ProductCart;
import com.tenpercent.pojo.Products;
import com.tenpercent.roomdatabase.AppDatabase;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {
    ActivityDetailsBinding binding;
    Products producte;
    ArrayList<Products> listpro;
    private AppDatabase database;

    private LinearLayoutManager mLayoutManager;
    ShardEditor sharedEditor;
    int newcounter =1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
          sharedEditor=new ShardEditor(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details);
        inItView();
        if (getIntent() != null) {
            producte = getIntent().getParcelableExtra("pro_cato");
            binding.tvName.setText(producte.getDesc());
            binding.tvPrice.setText(producte.getDiscount()+"");
            Glide.with(this)
                    .load(producte.getImage())
                    .into(binding.imagDetailes);
            binding.tvQuntity.setText(newcounter+"");
        }

        addQuantity();
        minusQuantity();
     //   loadDataProduct();
             checkLogin();
             getCountCart();
        binding.imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DetailsActivity.this, CartActivity.class));
            }
        });
        binding.btnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharedEditor.loadDataEnter().get(ShardEditor.IS_LOGIN)){



                    if (producte!=null){

                        ProductCart productCart = new ProductCart(producte.getId(),
                                producte.getName(),
                                producte.getDesc(),
                                producte.getImage(),
                                producte.getCategoryId(),
                                producte.getSellerId(),
                                newcounter,
                                producte.getRate(),
                                producte.getPrice(),
                                producte.getDiscount()

                        );
                        if (database.cartDao().findByName(productCart.getId())){
                            database.cartDao().updateUser(productCart);
                            Toast.makeText(DetailsActivity.this, "تمت الاضافة والتعديل ", Toast.LENGTH_SHORT).show();
                            binding.btnAddCart.setText("تمت اضافتها للسلة  ");
                        }else {
                            database.cartDao().insertAllUser(productCart);
                            Toast.makeText(DetailsActivity.this, "تمت الاضافة ", Toast.LENGTH_SHORT).show();

                            binding.btnAddCart.setText("تمت اضافتها للسلة  ");
                        }


                    }

                    getItemConterCart();


                }
                else
                startActivity(new Intent(DetailsActivity.this, LoginActivity.class));



            }
        });


    }
    private void getCountCart() {
        if (database.cartDao().getAll().size() > 0) {

            binding.relatevHidCart.setVisibility(View.VISIBLE);
            binding.tvCounterCart.setText(database.cartDao().getAll().size() + "");

        } else {

            binding.relatevHidCart.setVisibility(View.GONE);

        }
    }
    @SuppressLint("SetTextI18n")
    private void getItemConterCart() {

        int size=database.cartDao().getAll().size();
        binding.tvCounterCart.setText(size+"");
        getCountCart();
    }

    private void minusQuantity() {

        binding.imgMins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (newcounter > 1) {

                    newcounter--;
                    binding.tvQuntity.setText(newcounter + "");


                }}
        });


    }

    private void addQuantity() {
        binding.imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newcounter++;
                binding.tvQuntity.setText(newcounter+"");
            }
        });

    }

    private void checkLogin() {

        if (sharedEditor.loadDataEnter().get(ShardEditor.IS_LOGIN)){
          binding.layoutAdd.setVisibility(View.VISIBLE);
            binding.btnAddCart.setText("اضف للسلة ");
            binding.btnAddCart.setBackgroundColor(getResources().getColor(R.color.purple_500));

        }
        else if(database.cartDao().findByName(producte.getId())){
            binding.btnAddCart.setText("تمت اضافتها للسلة  ");

        }else{
            binding.layoutAdd.setVisibility(View.GONE);
            binding.btnAddCart.setText("سجل الدخول لتضف للسلة");
            binding.btnAddCart.setBackgroundColor(getResources().getColor(R.color.dont_login));
        }
      //  binding.btnAddCart.setText(" ");
    }

    private void inItView() {
        database = AppDatabase.getDatabaseInstance(this);
        sharedEditor = new ShardEditor(this);
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        super.onBackPressed();
    }
}