package com.tenpercent.spilash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;

import com.ammarten.tenpercent.R;
import com.ammarten.tenpercent.databinding.ActivitySpilashBinding;
import com.google.firebase.messaging.FirebaseMessaging;
import com.tenpercent.activites.HomeActivity;
import com.tenpercent.activites.PermissionsActivity;
import com.tenpercent.auth.LoginActivity;
import com.tenpercent.editor.ShardEditor;
import com.tenpercent.firebase.Commen;

import java.security.Key;
import java.util.Timer;
import java.util.TimerTask;

public class SpilashActivity extends AppCompatActivity {
ActivitySpilashBinding binding;
    private static final int WAIT_TIME = 1000;
    ShardEditor shardEditor;
    Timer waitTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_spilash);
        shardEditor=new ShardEditor(this);
        waitTimer = new Timer();
        //Check is login
        getToken();


        waitTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                SpilashActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                               if (shardEditor.loadDataEnter().get(ShardEditor.IS_LOGIN)){
                                   startActivity(new Intent(SpilashActivity.this, HomeActivity.class));
                                   finish();

                               }else {
                                   startActivity(new Intent(SpilashActivity.this, LoginActivity.class));
                                   finish();
                               }



                    }
                });
            }
        }, WAIT_TIME);
    }

   void getToken(){


        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(token ->
        {
            if (!TextUtils.isEmpty(token))
                Commen.TOKEN_FIREBASE=token;
            Log.d("token",Commen.TOKEN_FIREBASE);
        {

        }
        }).addOnFailureListener(e -> {


            //handle e }).addOnCanceledListener(() -> { //handle cancel }).addOnCompleteListener(task -> Log.v(TAG, "This is the token : " + task.getResult()));

    });}

}