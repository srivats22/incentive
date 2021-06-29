package com.srivats.incentive.Common;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.srivats.incentive.OfflineScreen.MainActivity;
import com.srivats.incentive.R;

import java.util.Objects;


public class SettingsActivity extends AppCompatActivity {
    ImageButton back;
    TextView shareApp, contactDev, privacy, termsOfUse, rateApp, about, whatsNew;
    private FirebaseFirestore fb = FirebaseFirestore.getInstance();

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        Toolbar toolbar = findViewById(R.id.settings_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        back = findViewById(R.id.settingsBack);
        privacy = findViewById(R.id.privacyTextView);
        shareApp = findViewById(R.id.shareApp);
        contactDev = findViewById(R.id.contactDev);
        termsOfUse = findViewById(R.id.termsOfUseTextView);
        rateApp = findViewById(R.id.appRateTextView);
        about = findViewById(R.id.aboutApp);
        whatsNew = findViewById(R.id.whats_new);

        whatsNew.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), WhatsNew.class);
            startActivity(i);
        });

        back.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        });

        privacy.setOnClickListener(view -> privacyLink());

        contactDev.setOnClickListener(view -> {
            Intent contactDevIntent = new Intent(Intent.ACTION_SENDTO);
            Uri uri = Uri.parse("mailto:srivats.venkataraman@gmail.com");
            contactDevIntent.setData(uri);
            startActivity(contactDevIntent);
        });

        rateApp.setOnClickListener(view -> {
            Intent appRatingIntent = new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.srivats.incentive");
            appRatingIntent.setData(uri);
            startActivity(appRatingIntent);
        });

        shareApp.setOnClickListener(view -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.srivats.incentive");
//                shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject");
            startActivity(Intent.createChooser(shareIntent, "Share using"));
        });

        termsOfUse.setOnClickListener(view -> new AlertDialog.Builder(SettingsActivity.this)
                .setTitle("Terms Of Use")
                .setMessage("There is nothing fancy here. The incentive app is built with users privacy in mind" +
                        "and will always remain like that. What you save stays in your device." +
                        "We only collect some logs for analytics purposes, which can read by clicking on privacy.")
                .setPositiveButton("Close", (dialogInterface, i) -> {})
                .show());

        about.setOnClickListener(view -> new AlertDialog.Builder(SettingsActivity.this)
                .setTitle("About App")
                .setIcon(R.mipmap.ic_launcher)
                .setMessage("The Incentive app is a different kind of ToDo App. In other ToDo apps" +
                        "you can create tasks, sub-tasks get reminded, and stuff like that." +
                        "But the Incentive app is different, you not only create tasks, and " +
                        "add the required descriptions, but you can also write the reward " +
                        "you want to have after that. Which is an incentive to motivate you to complete your tasks")
                .setPositiveButton("Close", (dialogInterface, i) -> {

                })
                .show());
    }

    void privacyLink(){
        fb.collection("settingsLink")
                .get().addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        for (QueryDocumentSnapshot document : task.getResult()) {
//                            Log.d("Settings", document.getId() + " => " + document.getString("url"));
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            String link = document.getString("url");
                            Uri uri = Uri.parse(link);
                            i.setData(uri);
                            startActivity(i);
                        }
                    }
                    else {
                        Log.w("Settings", "Error getting documents.", task.getException());
                    }
                });
    }
}