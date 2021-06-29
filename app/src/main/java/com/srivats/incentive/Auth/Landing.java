package com.srivats.incentive.Auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.srivats.incentive.OfflineScreen.MainActivity;
import com.srivats.incentive.R;

public class Landing extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        MaterialButton loginBtn, signUpBtn;
        TextView guest;

        loginBtn = findViewById(R.id.landingLoginBtn);
        signUpBtn = findViewById(R.id.landingSignupBtn);
        guest = findViewById(R.id.continueOffline);

        loginBtn.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), Login.class);
            startActivity(i);
        });

        signUpBtn.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), SignUp.class);
            startActivity(i);
        });

        guest.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        });
    }
}