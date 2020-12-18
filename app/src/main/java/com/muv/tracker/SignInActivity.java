package com.muv.tracker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SignInActivity extends AppCompatActivity {

    private Button btnSignIn;
    private Intent intent;
    private TextView tvSignUp, tvForgotPW;
    private EditText etPhoneNumber;
    private SharedPreferences mySharedPref;
    private SharedPreferences.Editor myPrefEditor;
    private AlertDialog adLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        init();
    }

    private void init(){
        etPhoneNumber = findViewById(R.id.etPhoneNum);
        tvSignUp = findViewById(R.id.tvsignUp);
        tvForgotPW = findViewById(R.id.tvForgotPass);
        btnSignIn = findViewById(R.id.btnLogin);

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(SignInActivity.this,SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });

        tvForgotPW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myPrefEditor = getSharedPreferences("Login", Context.MODE_PRIVATE).edit();
                myPrefEditor.putString("PhoneNumber",etPhoneNumber.getText().toString());
                myPrefEditor.commit();
                intent = new Intent(SignInActivity.this,DashboardActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        dialogLogout();
    }

    private void dialogLogout(){
        adLogout = new AlertDialog.Builder(SignInActivity.this).setTitle("Exit").setMessage("Do you want to exit the App?")
                .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setPositiveButton("No",null).create();
        adLogout.show();
    }


}
