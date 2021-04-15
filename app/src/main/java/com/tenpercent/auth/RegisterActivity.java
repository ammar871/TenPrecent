package com.tenpercent.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.ammarten.tenpercent.R;
import com.ammarten.tenpercent.databinding.ActivityRegisterBinding;

import com.tenpercent.activites.HomeActivity;
import com.tenpercent.activites.PermissionsActivity;
import com.tenpercent.editor.ShardEditor;
import com.tenpercent.firebase.Commen;
import com.tenpercent.network.APIClient;
import com.tenpercent.network.APIInterFace;
import com.tenpercent.pojo.loginpojo.LoginModel;
import com.tenpercent.pojo.loginpojo.SuccessRsponse;
import com.tenpercent.pojo.rigetserPojo.ModelRegster;
import com.tenpercent.roomdatabase.AppDatabase;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

public class RegisterActivity extends AppCompatActivity {
    ActivityRegisterBinding binding;
    private AppDatabase database;

    private LinearLayoutManager mLayoutManager;
    ShardEditor sharedEditor;
    APIInterFace apiInterFace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);

        binding.tvLogin.setOnClickListener(v -> startActivity(new Intent(RegisterActivity.this,
                LoginActivity.class)));
        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        inItView();
        apiInterFace = APIClient.getClient().create(APIInterFace.class);

        binding.btnCreateAcounte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.progressbar.setVisibility(View.VISIBLE);
                binding.buttonSign.setVisibility(View.GONE);
                if (isValidPhoneNumber(binding.layoutPhone.getEditText().getText().toString()) &&
                        isValidPass(binding.layoutPass.getEditText().getText().toString()) &&
                        vaildatEmail(binding.layoutEmail.getEditText().getText().toString().trim())) {
                    callResponse();

                }else {
                    Toast.makeText(RegisterActivity.this, "من فضلك أكمل البيانات ", Toast.LENGTH_SHORT).show();
                    binding.progressbar.setVisibility(View.GONE);
                    binding.buttonSign.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    private void inItView() {
        database = AppDatabase.getDatabaseInstance(this);
        sharedEditor = new ShardEditor(this);
    }


    private boolean vaildatEmail(String email) {


        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        return email.matches(emailPattern);
    }


    public final static boolean isValidPass(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return true;
        }

    }

    private static boolean isValidPhoneNumber(String phoneNumber) {
        return !TextUtils.isEmpty(phoneNumber) && android.util.Patterns.PHONE.matcher(phoneNumber).matches();
    }


    private void callResponse() {
        Call<SuccessRsponse.loginregister> call = apiInterFace.createUserReigster(
                binding.layoutPhone.getEditText().getText().toString().trim(),
                binding.layoutPass.getEditText().getText().toString().trim(),
                Commen.TOKEN_FIREBASE,
                binding.layoutFristName.getEditText().getText().toString().trim() + " " +
                        binding.layoutLastName.getEditText().getText().toString().trim(),
                binding.layoutEmail.getEditText().getText().toString().trim());

        call.enqueue(new Callback<SuccessRsponse.loginregister>() {
            @Override
            public void onResponse(@NonNull Call<SuccessRsponse.loginregister> call, @NonNull Response<SuccessRsponse.loginregister> response) {
                binding.progressbar.setVisibility(View.GONE);
                binding.buttonSign.setVisibility(View.VISIBLE);

                if (response.code() == 200){

                    Toast.makeText(RegisterActivity.this, "تم التسجيل بنجاح ", Toast.LENGTH_SHORT).show();

                    sharedEditor.saveDataEnterd(true);
                    ModelRegster loginModel=response.body().getSuccess();
                    sharedEditor.saveToken(loginModel);
                    sharedEditor.loadData().get(ShardEditor.KEY_NAME);
                    startActivity(new Intent(RegisterActivity.this, PermissionsActivity.class));
                    finish();
                }

                else if (response.code()==500)
                    Toast.makeText(RegisterActivity.this, "الحساب موجود", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(RegisterActivity.this, "الحساب موجود", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure
                    (@NonNull Call<SuccessRsponse.loginregister> call, @NonNull Throwable t) {
                binding.progressbar.setVisibility(View.GONE);
                binding.buttonSign.setVisibility(View.VISIBLE);
                Toast.makeText(RegisterActivity.this, "فشلت العملية", Toast.LENGTH_SHORT).show();
            }
        });
    }
}