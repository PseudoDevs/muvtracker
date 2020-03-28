package com.muv.tracker;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OTPVerificationActivity extends AppCompatActivity {

    private ProgressBar pbOTPCode;
    private EditText etOTPCode;
    private Button btnVerify;
    private TextView tvMobileNumber;
    private String myCode;
    private String mobileNumber;
    private FirebaseAuth mAuth;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverification);
        mobileNumber = getIntent().getStringExtra("mobileNumber");
        Toast.makeText(this, mobileNumber, Toast.LENGTH_SHORT).show();
        init();
        sendVerificationCode(mobileNumber);


    }
    private void init(){
        tvMobileNumber = findViewById(R.id.tvMobileNumber);
        tvMobileNumber.setText(mobileNumber);

        pbOTPCode = findViewById(R.id.pbOTPCode);
        etOTPCode = findViewById(R.id.etOTPCode);
        btnVerify = findViewById(R.id.btnOTPVerify);
        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = etOTPCode.getText().toString();
                if (TextUtils.isEmpty(code) || code.length() == 0 ){
                    etOTPCode.setError("OTP Code is empty");
                    return;
                }
                else if(code.length() < 6){
                    etOTPCode.setError("OTP Code is not enough");
                    return;
                }
                verifyCode(code);
            }
        });
    }
    private void sendVerificationCode(String mobileNumber){
        PhoneAuthProvider.getInstance().verifyPhoneNumber(mobileNumber, 60, TimeUnit.SECONDS, OTPVerificationActivity.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                myCode = s;
                super.onCodeSent(s, forceResendingToken);

            }

            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                String code = phoneAuthCredential.getSmsCode();
                if (code != null){
                    pbOTPCode.setVisibility(View.GONE);
                    verifyCode(code);
                }
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(OTPVerificationActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                pbOTPCode.setVisibility(View.GONE);
                intent = new Intent(OTPVerificationActivity.this,SignInActivity.class);
                startActivity(intent);
            }
        });


    }
    private void verifyCode(String code){
        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(myCode,code);
        signWithPhoneNumber(phoneAuthCredential);

    }
    private void signWithPhoneNumber(PhoneAuthCredential phoneAuthCredential){
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    intent = new Intent(OTPVerificationActivity.this,DashboardActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        });
    }
}
