package com.srivats.incentive.Auth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.srivats.incentive.R;

public class ForgotPassword extends AppCompatActivity {
    MaterialButton submitBtn;
    EditText emailField;
    TextView instructions;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        submitBtn = findViewById(R.id.submitForgotPwd);
        emailField = findViewById(R.id.forgotPwdEmail);
        instructions = findViewById(R.id.forgotPwdInstruction);

        emailField.addTextChangedListener(forgotTextWatcher);
    }

    TextWatcher forgotTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            final String email = emailField.getText().toString().trim();

            if(!email.isEmpty()){
                submitBtn.setEnabled(true);
                submitBtn.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                submitBtn.setOnClickListener(view -> mAuth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(task -> {
                            if(task.isSuccessful()){
                                instructions.setVisibility(View.VISIBLE);
                            }
                        }));
            }
            else{
                submitBtn.setEnabled(false);
                submitBtn.setBackgroundColor(getResources().getColor(R.color.disabledBtnColor));
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
}