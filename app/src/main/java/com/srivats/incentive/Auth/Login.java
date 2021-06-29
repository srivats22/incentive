package com.srivats.incentive.Auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.srivats.incentive.BottomNavi;
import com.srivats.incentive.OfflineScreen.MainActivity;
import com.srivats.incentive.R;

public class Login extends AppCompatActivity {
    ImageView backBtn;
    ProgressBar pBar;
    EditText email, pwd;
    MaterialButton loginBtn;
    FirebaseAuth mAuth;
    Button forgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        backBtn = findViewById(R.id.loginBackBtn);
        pBar = findViewById(R.id.loginPBar);
        email = findViewById(R.id.emailEditText);
        pwd = findViewById(R.id.pwdEditText);
        loginBtn = findViewById(R.id.loginBtn);
        mAuth = FirebaseAuth.getInstance();

        email.addTextChangedListener(loginWatcher);
        pwd.addTextChangedListener(loginWatcher);

        forgotPassword = findViewById(R.id.forgotPwd);

        forgotPassword.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), ForgotPassword.class);
            startActivity(i);
        });

        backBtn.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), Landing.class);
            startActivity(i);
        });

    }

    private TextWatcher loginWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            final String userEmail = email.getText().toString().trim();
            final String userPwd = pwd.getText().toString().trim();

            if(!userEmail.isEmpty() && !userPwd.isEmpty()){
                loginBtn.setEnabled(true);
                loginBtn.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                loginBtn.setOnClickListener(view -> {
                    pBar.setVisibility(View.VISIBLE);
                    mAuth.signInWithEmailAndPassword(userEmail, userPwd)
                            .addOnCompleteListener(task -> {
                                if(task.isSuccessful()){
                                    Intent intent = new Intent(getApplicationContext(), BottomNavi.class);
                                    pBar.setVisibility(View.GONE);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();
                                }
                                else{
                                    pBar.setVisibility(View.GONE);
                                }
                            });
                });
            }
            else{
                loginBtn.setEnabled(false);
                loginBtn.setBackgroundColor(getResources().getColor(R.color.disabledBtnColor));
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
}