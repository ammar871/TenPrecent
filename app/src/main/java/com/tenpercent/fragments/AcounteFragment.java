package com.tenpercent.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ammarten.tenpercent.BuildConfig;
import com.ammarten.tenpercent.R;
import com.ammarten.tenpercent.databinding.FragmentAcounteBinding;
import com.tenpercent.activites.EditeProfileActivity;
import com.tenpercent.activites.MyFavoraiteActivity;
import com.tenpercent.auth.LoginActivity;
import com.tenpercent.editor.ShardEditor;

import java.util.Objects;


public class AcounteFragment extends Fragment {

    public AcounteFragment() {

    }

FragmentAcounteBinding binding;
ShardEditor shardEditor;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_acounte, container, false);
        shardEditor=new ShardEditor(Objects.requireNonNull(getActivity()));
        binding.tvName.setText(shardEditor.loadData().get(ShardEditor.KEY_NAME));

        binding.layoutFav.setOnClickListener(v -> startActivity(new Intent(getActivity(), MyFavoraiteActivity.class)));

        binding.layoutLogut.setOnClickListener(v -> {
            shardEditor.logOut();
            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();

        });

        binding.layoutShare.setOnClickListener(v -> {

           shareApp();

        });

        binding.layoutMyProfil.setOnClickListener(v -> startActivity(new Intent(getActivity(), EditeProfileActivity.class)));

        if (shardEditor.loadDataEnter().get(ShardEditor.IS_LOGIN)){

            binding.layoutLogin.setVisibility(View.GONE);
            binding.layoutHome.setVisibility(View.VISIBLE);
        }else {
            binding.layoutLogin.setVisibility(View.VISIBLE);
            binding.layoutHome.setVisibility(View.GONE);
        }
        return binding.getRoot();
    }

    private void  shareApp(){

        try {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");


            String sharApp = "https://play.google.com/store/apps/details?id="
                    + BuildConfig.APPLICATION_ID + "\n\n";
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, sharApp);
            startActivity(Intent.createChooser(sharingIntent, "تطبيق TEN PRECENT"));
        } catch (Exception e) {
            Toast.makeText(getActivity(), "فشلت العملية", Toast.LENGTH_SHORT).show();
        }

    }
}