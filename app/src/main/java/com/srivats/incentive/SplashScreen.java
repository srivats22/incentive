package com.srivats.incentive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.inappmessaging.FirebaseInAppMessaging;
import com.srivats.incentive.Auth.Landing;
import com.srivats.incentive.OfflineScreen.MainActivity;

public class SplashScreen extends AppCompatActivity {
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        FirebaseInAppMessaging.getInstance().setMessagesSuppressed(true);

        handler = new Handler();

        handler.postDelayed(() -> {
            Intent i = new Intent(SplashScreen.this, Landing.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
        }, 3000);
    }
}