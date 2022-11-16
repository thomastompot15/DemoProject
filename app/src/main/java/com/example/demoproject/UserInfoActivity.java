package com.example.demoproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserInfoActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info_activity);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        TextView textViewUserName = findViewById(R.id.textViewUserName);
        assert user != null;
        textViewUserName.setText(user.getDisplayName());

        TextView textViewUserId = findViewById(R.id.textViewUserId);
        textViewUserId.setText(user.getUid());

        TextView textViewUserEmail = findViewById(R.id.textViewUserEmail);
        textViewUserEmail.setText(user.getEmail());

        CircleImageView profile_image = findViewById(R.id.profile_image);

        Glide.with(this).load(user.getPhotoUrl()).into(profile_image);


        Button buttonSignOut = findViewById(R.id.buttonSignOut);
        buttonSignOut.setOnClickListener(v -> {
            mAuth.signOut();
            Intent intent = new Intent(UserInfoActivity.this, SignInActivity.class);
            startActivity(intent);
            finish();
        });

        Button buttonSendData = findViewById(R.id.buttonSendData);
        buttonSendData.setOnClickListener(v -> {
            Intent intent = new Intent(UserInfoActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
