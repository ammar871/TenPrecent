package com.tenpercent.activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.ammarten.tenpercent.R;
import com.ammarten.tenpercent.databinding.ActivityHomeBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tenpercent.adpters.AdapterOffers;
import com.tenpercent.fragments.AcounteFragment;
import com.tenpercent.fragments.CatograyFragment;
import com.tenpercent.fragments.HomeFragment;
import com.tenpercent.fragments.OrdersFragment;
import com.tenpercent.pojo.Products;
import com.tenpercent.roomdatabase.AppDatabase;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener
         {
    ActivityHomeBinding binding;
    AppDatabase appDatabase;
    AdapterOffers mAdapterOffers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        loadFragment(new HomeFragment());
        binding.navigation.setOnNavigationItemSelectedListener(this);
        appDatabase = AppDatabase.getDatabaseInstance(this);






        LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        boolean network_enabled = locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        Location location;

        if (network_enabled) {

            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            }
            location = locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if (location != null) {

                Log.d("location", location.getLongitude() + "\n" + location.getLatitude() + "");

            }
        }

//        binding.imgCart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(HomeActivity.this, CartActivity.class));
//            }
//        });
    }

    @Override
    protected void onResume() {

        super.onResume();
//        if (appDatabase.cartDao().getAll().size() > 0) {
//
//            binding.relatevHidCart.setVisibility(View.VISIBLE);
//            binding.tvCounterCart.setText(appDatabase.cartDao().getAll().size() + "");
//
//        } else {
//
//            binding.relatevHidCart.setVisibility(View.GONE);
//
//        }

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;

        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                fragment = new HomeFragment();
//                binding.tvBar.setText("الرئيسية");
                break;

            case R.id.nav_catogery:

                fragment = new CatograyFragment();
             //   binding.tvBar.setText("الأقسام");
                break;
            case R.id.nav_myorders:

                fragment = new OrdersFragment();
               // binding.tvBar.setText("طلبــاتى");

                break;

            case R.id.nav_myacount:

                fragment = new AcounteFragment();
                //binding.tvBar.setText("حســابي");

                break;
            default:

        }
        return loadFragment(fragment);

    }


    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.layoyt_view, fragment)
                    .commit();
            return true;
        }
        return false;
    }



//    @SuppressLint("SetTextI18n")
//    @Override
//    public void getCounterCart(int count) {
//
//        binding.tvCounterCart.setText( ""+"mmmmm");
//    }
}