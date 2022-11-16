package com.example.demoproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirstActivity extends AppCompatActivity {
    private static final String TAG = "FirstActivity";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_activity);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (user != null) {
                    Log.d(TAG, "run: user already logged in..can go to next activity");
                    Intent intent = new Intent(FirstActivity.this, UserInfoActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(FirstActivity.this, SignInActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 1000);
    }
}
