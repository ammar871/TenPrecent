package com.tenpercent.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ammarten.tenpercent.R;
import com.ammarten.tenpercent.databinding.FragmentCatograyBinding;
import com.tenpercent.activites.CartActivity;
import com.tenpercent.activites.MyFavoraiteActivity;
import com.tenpercent.adpters.AdptereCatogray;
import com.tenpercent.network.APIClient;
import com.tenpercent.network.APIInterFace;
import com.tenpercent.pojo.Categories;
import com.tenpercent.roomdatabase.AppDatabase;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CatograyFragment extends Fragment {


    public CatograyFragment() {
        // Required empty public constructor
    }

    APIInterFace apiInterFace;
FragmentCatograyBinding binding;
AppDatabase database;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_catogray, container, false);
        binding.rcCatogray.setLayoutManager(new GridLayoutManager(getActivity(),2));
        binding.rcCatogray.setHasFixedSize(true);
        database=AppDatabase.getDatabaseInstance(getActivity());
        getCountCart();
        getCountFave();
        loadCategory();
        binding.imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CartActivity.class));
            }
        });
        binding.imgNotiy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MyFavoraiteActivity.class));
            }
        });
    //    loadDataCatogray();
        loadCategory();
        return binding.getRoot();
    }
    private void getCountCart() {
        if (database.cartDao().getAll().size() > 0) {

            binding.relatevHidCart.setVisibility(View.VISIBLE);
            binding.tvCounterCart.setText(database.cartDao().getAll().size() + "");

        } else {

            binding.relatevHidCart.setVisibility(View.GONE);

        }
    }
    private void getCountFave() {
        if (database.userDao().getAll().size() > 0) {

            binding.relatevHidNotyt.setVisibility(View.VISIBLE);
            binding.tvCounterFave.setText(database.userDao().getAll().size() + "");

        } else {

            binding.relatevHidNotyt.setVisibility(View.GONE);

        }
    }
    private void loadCategory() {
        apiInterFace= APIClient.getClient().create(APIInterFace.class);
        Call<List<Categories>> call = apiInterFace.getCategories();
        call.enqueue(new Callback<List<Categories>>() {
            @Override
            public void onResponse(@NonNull Call<List<Categories>> call, @NonNull Response<List<Categories>> response) {
                if (response.code() == 200 || response.body() != null) {


                    binding.rcCatogray.setVisibility(View.VISIBLE);
                    binding.progressbar.setVisibility(View.GONE);
                    assert response.body() != null;


                    AdptereCatogray adptereCatogray=new AdptereCatogray((ArrayList<Categories>) response.body(),getActivity(),R.layout.item_category_all);
                    binding.rcCatogray.setAdapter(adptereCatogray);
                }

                binding.rcCatogray.setVisibility(View.VISIBLE);
                binding.progressbar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(@NonNull Call<List<Categories>> call, @NonNull Throwable t) {


            }
        });
    }


}