package com.srivats.incentive.Auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;
import com.srivats.incentive.OfflineScreen.MainActivity;
import com.srivats.incentive.R;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {
    EditText name, email, pwd;
    MaterialButton signUpBtn;
    ProgressBar pBar;
    private FirebaseAuth mAuth;
    private FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name = findViewById(R.id.nameEditText);
        name.addTextChangedListener(signUpWatcher);
        email = findViewById(R.id.emailEditText);
        email.addTextChangedListener(signUpWatcher);
        pwd = findViewById(R.id.pwdEditText);
        pwd.addTextChangedListener(signUpWatcher);
        signUpBtn = findViewById(R.id.signUpBtn);
        pBar = findViewById(R.id.signUpPBar);

        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
    }

    TextWatcher signUpWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            final String userName = name.getText().toString().trim();
            final String userEmail = email.getText().toString().trim();
            final String userPwd = pwd.getText().toString().trim();

            if(!userName.isEmpty() && !userEmail.isEmpty() && !userPwd.isEmpty()){
                signUpBtn.setEnabled(true);
                signUpBtn.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                signUpBtn.setOnClickListener(view -> {
                    pBar.setVisibility(View.VISIBLE);
                    mAuth.createUserWithEmailAndPassword(userEmail, userPwd)
                            .addOnCompleteListener(task -> {
                                if(task.isSuccessful()){
                                    Map<String, Object> userInfo = new HashMap<>();
                                    userInfo.put("userName", userName);
                                    userInfo.put("userEmail", userEmail);
                                    userInfo.put("userUid", task.getResult().getUser().getUid());
                                    UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                                            .setDisplayName(userName).build();
                                    task.getResult().getUser().updateProfile(profileUpdate);

                                    fStore.collection("user").document(task.getResult().getUser().getUid())
                                            .set(userInfo);

                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
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
                signUpBtn.setEnabled(false);
                signUpBtn.setBackgroundColor(getResources().getColor(R.color.disabledBtnColor));
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
}