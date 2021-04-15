package com.tenpercent.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.ammarten.tenpercent.R;
import com.ammarten.tenpercent.databinding.ActivityLoginBinding;
import com.tenpercent.activites.HomeActivity;
import com.tenpercent.activites.PermissionsActivity;
import com.tenpercent.editor.ShardEditor;
import com.tenpercent.firebase.Commen;
import com.tenpercent.network.APIClient;
import com.tenpercent.network.APIInterFace;
import com.tenpercent.pojo.loginpojo.SuccessRsponse;
import com.tenpercent.pojo.rigetserPojo.ModelRegster;
import com.tenpercent.roomdatabase.AppDatabase;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    private AppDatabase database;

    private LinearLayoutManager mLayoutManager;
    ShardEditor sharedEditor;
    APIInterFace apiInterFace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        inItView();
        binding.tvLogin.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, RegisterActivity.class)));
        apiInterFace = APIClient.getClient().create(APIInterFace.class);
binding.imgBack.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        onBackPressed();
    }
});
        binding.layoutLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.progressbar.setVisibility(View.VISIBLE);
                binding.buttonSign.setVisibility(View.GONE);
                if (!binding.layEmail.getEditText().getText().toString().isEmpty()&&! binding.layEmail.getEditText().getText().toString().isEmpty()) {

                    callResponse();
                } else{
                    Toast.makeText(LoginActivity.this, "من فضلك أكل البيانات", Toast.LENGTH_SHORT).show();
                    binding.progressbar.setVisibility(View.GONE);
                    binding.buttonSign.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    private void callResponse() {
        Call<SuccessRsponse.loginRespons> call = apiInterFace.createUser(binding.layEmail.getEditText().getText().toString().trim(), Commen.TOKEN_FIREBASE,
                binding.layPass.getEditText().getText().toString().trim());

        call.enqueue(new Callback<SuccessRsponse.loginRespons>() {
            @Override
            public void onResponse(@NonNull Call<SuccessRsponse.loginRespons> call, @NonNull Response<SuccessRsponse.loginRespons> response) {
                binding.progressbar.setVisibility(View.GONE);
                binding.buttonSign.setVisibility(View.VISIBLE);

                if (response.code() == 200){

                    Toast.makeText(LoginActivity.this, "تم التسجيل بنجاح ", Toast.LENGTH_SHORT).show();
                    ModelRegster loginModel = new ModelRegster(response.body().getSuccess().getToken()
                            , response.body().getSuccess().getName(), response.body().getSuccess().getEmail(),
                            response.body().getSuccess().getPhone(),
                            response.body().getSuccess().getPoints());
                    sharedEditor.saveToken(loginModel);
                    sharedEditor.saveDataEnterd(true);
                    assert response.body() != null;

                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    finish();
                }
                else
                    Toast.makeText(LoginActivity.this, "غير موجود", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(@NonNull Call<SuccessRsponse.loginRespons> call, @NonNull Throwable t) {
                binding.progressbar.setVisibility(View.GONE);
                binding.buttonSign.setVisibility(View.VISIBLE);
                Toast.makeText(LoginActivity.this, "فشلت العملية", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void inItView() {
        database = AppDatabase.getDatabaseInstance(this);
        sharedEditor = new ShardEditor(this);
    }

    private boolean vaildatEmail() {
        String email = binding.inputEmail.getText().toString().trim();

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        return email.matches(emailPattern);
    }





    private static boolean isValidPhoneNumber(String phoneNumber) {
        return !TextUtils.isEmpty(phoneNumber) && android.util.Patterns.PHONE.matcher(phoneNumber).matches();
    }
}