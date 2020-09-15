package com.srivats.incentive;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Objects;


public class SettingsActivity extends AppCompatActivity {
    ImageButton back;
    TextView appVersion, shareApp, contactDev, privacy, termsOfUse, rateApp, about;

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
        appVersion = findViewById(R.id.app_version);
        shareApp = findViewById(R.id.shareApp);
        contactDev = findViewById(R.id.contactDev);
        termsOfUse = findViewById(R.id.termsOfUseTextView);
        rateApp = findViewById(R.id.appRateTextView);
        about = findViewById(R.id.aboutApp);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });

        try{
            String version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            appVersion.setText("Version Number: " + version);
        }catch (PackageManager.NameNotFoundException e){
            Log.e("tag", e.getMessage());
        }

        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                Uri uri = Uri.parse("https://drive.google.com/file/d/12g55z0QFsjsbYRIE49bTpyjswFTvIf-v/view?usp=sharing");
                i.setData(uri);
                startActivity(i);
            }
        });

        contactDev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent contactDevIntent = new Intent(Intent.ACTION_SENDTO);
                Uri uri = Uri.parse("mailto:srivats.venkataraman@gmail.com");
                contactDevIntent.setData(uri);
                startActivity(contactDevIntent);
            }
        });

        rateApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent appRatingIntent = new Intent(Intent.ACTION_VIEW);
                Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.srivats.incentive");
                appRatingIntent.setData(uri);
                startActivity(appRatingIntent);
            }
        });

        shareApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.srivats.incentive");
//                shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject");
                startActivity(Intent.createChooser(shareIntent, "Share using"));
            }
        });

        termsOfUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(SettingsActivity.this)
                        .setTitle("Terms Of Use")
                        .setMessage("There is nothing fancy here. Incentive was built with users privacy in mind, " +
                                "and will always remain like that. What every your save stays in your device." +
                                " We only collect some logs for analytics purpose, which can be found and" +
                                " read by clicking on privacy.")
                        .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {}
                        })
                        .show();
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(SettingsActivity.this)
                        .setTitle("About App")
                        .setIcon(R.mipmap.ic_launcher)
                        .setMessage("Incentive is a different kind of ToDo App. In other ToDo apps" +
                                " you can create task, sub tasks get reminded and stuff like that." +
                                " But incentive is different, you not only create tasks, and" +
                                " add the required descriptions, but you can also write the reward" +
                                " you want to have after that. This motivates you to completing tasks")
                        .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .show();
            }
        });
    }
}