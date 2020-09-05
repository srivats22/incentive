package com.srivats.incentive;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceFragmentCompat;

public class SettingsActivity extends AppCompatActivity {
    ImageButton back;
    TextView appVersion, shareApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        Toolbar toolbar = findViewById(R.id.settings_toolbar);
        setSupportActionBar(toolbar);
        back = findViewById(R.id.settingsBack);
        appVersion = findViewById(R.id.app_version);
        shareApp = findViewById(R.id.shareApp);

        try{
            String version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            appVersion.setText("Version Number: " + version);
        }catch (PackageManager.NameNotFoundException e){
            Log.e("tag", e.getMessage());
        }

        shareApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Text");
                shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject");
                startActivity(Intent.createChooser(shareIntent, "Share using"));
            }
        });

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
    }
}