package com.tenpercent.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ammarten.tenpercent.R;
import com.ammarten.tenpercent.databinding.FragmentHomeBinding;
import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.tenpercent.activites.CartActivity;
import com.tenpercent.activites.DetailsActivity;
import com.tenpercent.activites.MyFavoraiteActivity;
import com.tenpercent.activites.PrductesActivity;
import com.tenpercent.activites.SearshActivity;
import com.tenpercent.activites.interFace.CallFave;
import com.tenpercent.adpters.AdapterOffers;
import com.tenpercent.adpters.AdapterProductAll;
import com.tenpercent.adpters.AdapterRecommend;
import com.tenpercent.adpters.AdpterProducte;
import com.tenpercent.adpters.AdptereCatogray;
import com.tenpercent.adpters.GenericSliderAdapter;
import com.tenpercent.editor.ShardEditor;
import com.tenpercent.network.APIClient;
import com.tenpercent.network.APIInterFace;
import com.tenpercent.pojo.APIResponse;
import com.tenpercent.pojo.Categories;
import com.tenpercent.pojo.Products;
import com.tenpercent.pojo.Slider;
import com.tenpercent.roomdatabase.AppDatabase;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


public class HomeFragment extends Fragment implements AdapterOffers.OnClickListenerGrid, AdapterRecommend.OnClickListenerReccommend
        , AdpterProducte.OnClickListenerTheBest , CallFave {

    public HomeFragment() {

    }

    private AppDatabase database;

    private LinearLayoutManager mLayoutManager;
    ShardEditor sharedEditor;
    FragmentHomeBinding binding;
    APIInterFace apiInterFace;

    ArrayList<Products> listpro;
    Handler handler = new Handler();
    private int dotscount = 0;
    GenericSliderAdapter<Slider> sliderAdapter;
    private ImageView[] dots;
    AdapterOffers adpterOffers;
    AdapterRecommend adapterRecommend;
    AdpterProducte adpterProducte;
    AdapterProductAll adapterProductAll;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        View view = binding.getRoot();
        inItView();
        getCountCart();
        getCountFave();

        binding.edSearsh.setOnClickListener(v -> {

            startActivity(new Intent(getActivity(), SearshActivity.class));
        });


        loadHome();
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

        binding.searshBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
//loadProductByCategory();
        return view;
    }

    private void loadCategory() {
        Call<List<Categories>> call = apiInterFace.getCategories();
        call.enqueue(new Callback<List<Categories>>() {
            @Override
            public void onResponse(@NonNull Call<List<Categories>> call, @NonNull Response<List<Categories>> response) {
                if (response.code() == 200 || response.body() != null) {

                    assert response.body() != null;


                    AdptereCatogray adptereCatogray = new AdptereCatogray((ArrayList<Categories>) response.body(), getActivity(), R.layout.item_catogray);
                    binding.rcCatogery.setAdapter(adptereCatogray);
                }


            }

            @Override
            public void onFailure(@NonNull Call<List<Categories>> call, @NonNull Throwable t) {


            }
        });
    }

    private void adapterOffers(List<Products> list) {
        adpterOffers = new
                AdapterOffers(list, getActivity(), this,this);
    }

    private void adapterRecommend(List<Products> list) {
        adapterRecommend = new
                AdapterRecommend(list, getActivity(), this,this);
    }

    private void loadHome() {

        Call<APIResponse.HomeResponse> call = apiInterFace.getHomeLists();
        call.enqueue(new Callback<APIResponse.HomeResponse>() {
            @Override
            public void onResponse(@NonNull Call<APIResponse.HomeResponse> call, @NonNull Response<APIResponse.HomeResponse> response) {
                if (response.code() == 200 || response.body() != null) {
                    binding.linerHome.setVisibility(View.VISIBLE);
                    binding.progressbar.setVisibility(View.GONE);
                    assert response.body() != null;
                    initSliderAdapter(response.body().getSlider());
                    initViewPager(response.body().getSlider());
                    gettop_lists(binding.rcBast, response.body().getTopSeled());

                    adapterOffers(response.body().getOffers());

                    binding.rcOffers.setAdapter(adpterOffers);

                    adapterRecommend(response.body().getRecommended());
                    binding.rcRecmmende.setAdapter(adapterRecommend);


                } else {
                    binding.linerHome.setVisibility(View.GONE);
                    binding.progressbar.setVisibility(View.VISIBLE);
                }


            }

            @Override
            public void onFailure(@NonNull Call<APIResponse.HomeResponse> call, @NonNull Throwable t) {

            }
        });
    }

    private void gettop_lists(RecyclerView rcBast, List<Products> topSeled) {

        adpterProducte = new AdpterProducte(topSeled, getActivity(), this,this);
        rcBast.setAdapter(adpterProducte);
    }

    private void inItView() {
        binding.rcCatogery.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        binding.rcCatogery.setHasFixedSize(true);
        binding.rcOffers.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        binding.rcOffers.setHasFixedSize(true);
        binding.rcBast.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        binding.rcBast.setHasFixedSize(true);
        binding.rcRecmmende.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        binding.rcRecmmende.setHasFixedSize(true);
        apiInterFace = APIClient.getClient().create(APIInterFace.class);
        database = AppDatabase.getDatabaseInstance(getActivity());
        sharedEditor = new ShardEditor(getActivity());
    }

    private void initViewPager(List<Slider> silderList) {

        dotscount = sliderAdapter.getCount();

        dots = new ImageView[dotscount];

        for (int i = 0; i < dotscount; i++) {
            dots[i] = new ImageView(getContext());
            dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.nonactive_dot));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            binding.sliderDots.addView(dots[i], params);

        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.active_dot));

        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for (int i = 0; i < dotscount; i++) {
                    dots[i].setImageResource(R.drawable.nonactive_dot);
                }

                dots[position].setImageResource(R.drawable.active_dot);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        handler.postDelayed(new Runnable() {
            // int item_count = 0;

            @Override
            public void run() {


                if (binding.viewPager.getCurrentItem() < silderList.size() - 1) {
                    binding.viewPager.setCurrentItem(binding.viewPager.getCurrentItem() + 1);
                } else {
                    binding.viewPager.setCurrentItem(0);
                }
                handler.postDelayed(this, 7000);


            }
        }, 2000);


    }

    private void initSliderAdapter(final List<Slider> silderList) {
        sliderAdapter = new GenericSliderAdapter<Slider>(getContext(), silderList) {
            @Override
            public View getMyView(ViewGroup container, int position, final Slider data) {

                LayoutInflater layoutInflater = (LayoutInflater)
                        getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View item_view = layoutInflater.inflate(R.layout.slidingimages_layout, container, false);
                ImageView imgSlide = item_view.findViewById(R.id.image);


                Glide.with(getContext())
                        .load(silderList.get(position).getImage())
                        //.placeholder(R.drawable.ic_exit)
                        .into(imgSlide);


                container.addView(item_view);
                return item_view;
            }
        };
        binding.viewPager.setAdapter(sliderAdapter);
    }

    @Override
    public void onClickItemViewGrid(Products position) {

        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        intent.putExtra("pro_cato", position);

        startActivityForResult(intent, 1);
    }

    @Override
    public void getCounterCart(int count,String name) {

        binding.tvCounterCart.setText("" + count);

        getCountCart();
        snakBarView(name);
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
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (adpterOffers != null)
                adpterOffers.notifyDataSetChanged();
            if (adapterRecommend != null)
                adapterRecommend.notifyDataSetChanged();
            if (adpterProducte != null)
                adpterProducte.notifyDataSetChanged();


        }
    }

    @Override
    public void onClickListenerReccommend(Products position) {

        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        intent.putExtra("pro_cato", position);

        startActivityForResult(intent, 1);
    }

    @Override
    public void getCounterReccommend(int count,String name) {

        binding.tvCounterCart.setText("" + count);

        getCountCart();

        snakBarView(name);
    }

    private void snakBarView(String name) {
        Snackbar snackbar = Snackbar
                .make(binding.containerHome, name, Snackbar.LENGTH_LONG);
        snackbar.setActionTextColor(Color.YELLOW);
        snackbar.show();
    }

    @Override
    public void onClickItemViewTheBest(Products position) {
        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        intent.putExtra("pro_cato", position);

        startActivityForResult(intent, 1);
    }

    @Override
    public void getCounterCartbest(int count,String name) {

        binding.tvCounterCart.setText("" + count);

        getCountCart();
        snakBarView(name);
    }




    @SuppressLint("SetTextI18n")
    @Override
    public void getCounterFaveOffers(int count,String name) {

        binding.tvCounterFave.setText(count+"");
        getCountFave();
        snakBarView(name);
    }

    @Override
    public void getCounterFaveRecommend(int count,String name) {
        binding.tvCounterFave.setText(count+"");
        getCountFave();
        snakBarView(name);
    }

    @Override
    public void getCounterFaveProduct(int count,String name) {
        binding.tvCounterFave.setText(count+"");
        getCountFave();
        snakBarView(name);
    }

    @Override
    public void getCounterFaveViewAll(int count,String name) {

    }
}