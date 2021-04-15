package com.tenpercent.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ammarten.tenpercent.R;
import com.ammarten.tenpercent.databinding.FragmentOrdersBinding;
import com.tenpercent.adpters.AdpterOrders;
import com.tenpercent.adpters.AdptereCatogray;
import com.tenpercent.auth.LoginActivity;
import com.tenpercent.editor.ShardEditor;
import com.tenpercent.network.APIClient;
import com.tenpercent.network.APIInterFace;
import com.tenpercent.pojo.Categories;
import com.tenpercent.pojo.ModelsOrders;
import com.tenpercent.pojo.Orders;
import com.tenpercent.pojo.Products;
import com.tenpercent.roomdatabase.AppDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OrdersFragment extends Fragment {


    public OrdersFragment() {
        // Required empty public constructor
    }

    private AppDatabase database;
APIInterFace apiInterFace;
    private LinearLayoutManager mLayoutManager;
    ShardEditor sharedEditor;
    FragmentOrdersBinding binding;
    ArrayList<ModelsOrders> list;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_orders, container, false);
        sharedEditor=new ShardEditor(Objects.requireNonNull(getActivity()));
        apiInterFace = APIClient.getClient().create(APIInterFace.class);
        inItView();
        if (sharedEditor.loadDataEnter().get(ShardEditor.IS_LOGIN)){

            binding.layoutLogin.setVisibility(View.GONE);
            binding.rvMyOrders.setVisibility(View.VISIBLE);
        }else {
            binding.layoutLogin.setVisibility(View.VISIBLE);
            binding.rvMyOrders.setVisibility(View.GONE);
        }




        loadDataCatogray();

        binding.btnCreateAcounte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });
        return binding.getRoot();
    }




    private void loadDataCatogray() {
        list = new ArrayList<>();
        binding.rvMyOrders.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        binding.rvMyOrders.setHasFixedSize(true);

        Call<List<ModelsOrders>> call = apiInterFace.getMyOrders("Bearer" + " " +sharedEditor.loadData().get(ShardEditor.KEY_TOKEN));
        call.enqueue(new Callback<List<ModelsOrders>>() {
            @Override
            public void onResponse(@NonNull Call<List<ModelsOrders>> call, @NonNull Response<List<ModelsOrders>> response) {
                if (response.code() == 200 || response.body() != null) {

                    assert response.body() != null;
                    if (response.body().size()>0){
                        binding.rvMyOrders.setVisibility(View.VISIBLE);
                        binding.progressbar.setVisibility(View.GONE);
                        binding.tvImpty.setVisibility(View.GONE);
                        AdpterOrders adptereCatogray = new AdpterOrders((ArrayList<ModelsOrders>) response.body(), getActivity());

                        binding.rvMyOrders.setAdapter(adptereCatogray);


                    }else {
                        binding.rvMyOrders.setVisibility(View.GONE);
                        binding.progressbar.setVisibility(View.GONE);
                        binding.tvImpty.setVisibility(View.VISIBLE);

                    }

                }


            }

            @Override
            public void onFailure(@NonNull Call<List<ModelsOrders>> call, @NonNull Throwable t) {


            }
        });




    }

    private void inItView() {
        database = AppDatabase.getDatabaseInstance(getActivity());
        sharedEditor = new ShardEditor(getActivity());
    }
}