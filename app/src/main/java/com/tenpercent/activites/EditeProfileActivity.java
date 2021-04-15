package com.tenpercent.activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ammarten.tenpercent.R;
import com.ammarten.tenpercent.databinding.ActivityEditeProfileBinding;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tenpercent.auth.RegisterActivity;
import com.tenpercent.editor.ShardEditor;
import com.tenpercent.network.APIClient;
import com.tenpercent.network.APIInterFace;
import com.tenpercent.pojo.loginpojo.EditeModel;
import com.tenpercent.pojo.loginpojo.SuccessRsponse;
import com.tenpercent.pojo.rigetserPojo.ModelRegster;
import com.tenpercent.roomdatabase.AppDatabase;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditeProfileActivity extends AppCompatActivity {
    ActivityEditeProfileBinding binding;
    ShardEditor sharedEditor;
    APIInterFace apiInterFace;
    AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_edite_profile);

        apiInterFace = APIClient.getClient().create(APIInterFace.class);
        database = AppDatabase.getDatabaseInstance(this);
        sharedEditor = new ShardEditor(this);


        getDataProfile();
        binding.buttonSign.setOnClickListener(v -> {
            binding.progressbar.setVisibility(View.VISIBLE);
            binding.buttonSign.setVisibility(View.GONE);
            if (binding.layoutLastName.getEditText().getText().toString().isEmpty()
                    && binding.layoutLastName.getEditText().getText().toString().equals("")) {
                binding.progressbar.setVisibility(View.GONE);
                binding.buttonSign.setVisibility(View.VISIBLE);
                Toast.makeText(EditeProfileActivity.this, "من فضلك أكمل البيانات", Toast.LENGTH_SHORT).show();
            } else {
                callResponse();
            }
        });

        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void getDataProfile() {
        Objects.requireNonNull(binding.layoutLastName.getEditText()).setText(sharedEditor.loadData().get(ShardEditor.KEY_NAME));
        Objects.requireNonNull(binding.layoutPhone.getEditText()).setText(sharedEditor.loadData().get(ShardEditor.KEY_PHONE));


    }

    private void callResponse() {
        Call<EditeModel> call = apiInterFace.updateProfile(
                "Bearer" + " " + sharedEditor.loadData().get(ShardEditor.KEY_TOKEN),
                Objects.requireNonNull(binding.layoutLastName.getEditText()).getText().toString().trim(),
                Objects.requireNonNull(binding.layoutPhone.getEditText()).getText().toString().trim())
             ;

        call.enqueue(new Callback<EditeModel>() {
            @Override
            public void onResponse(@NonNull Call<EditeModel> call, @NonNull Response<EditeModel> response) {
                binding.progressbar.setVisibility(View.GONE);
                binding.buttonSign.setVisibility(View.VISIBLE);

                if (response.code() == 200) {

                    ModelRegster loginModel = new ModelRegster(sharedEditor.loadData().get(ShardEditor.KEY_TOKEN)
                            , response.body().getName(), response.body().getEmail(), response.body().getPhone(),
                            response.body().getPoints());
                    sharedEditor.saveToken(loginModel);
                    Toast.makeText(EditeProfileActivity.this, "تم التعديل بنجاح ", Toast.LENGTH_SHORT).show();


                }else {
                    Toast.makeText(EditeProfileActivity.this, "الرقم موجود ", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure
                    (@NonNull Call<EditeModel> call, @NonNull Throwable t) {
                binding.progressbar.setVisibility(View.GONE);
                binding.buttonSign.setVisibility(View.VISIBLE);
                Toast.makeText(EditeProfileActivity.this, "فشلت العملية", Toast.LENGTH_SHORT).show();
            }
        });
    }
}