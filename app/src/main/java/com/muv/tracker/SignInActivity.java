package com.muv.tracker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {

    private Button btnSignIn;
    private Intent intent;
    private TextView tvSignUp, tvForgotPW;
    private EditText etPhoneNumber;
    private SharedPreferences mySharedPref;
    private SharedPreferences.Editor myPrefEditor;
    private AlertDialog adLogout;
    private FirebaseAuth mAuth;
    private DbMUVFirebase dbMUVFirebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        dbMUVFirebase = new DbMUVFirebase();
        mAuth = FirebaseAuth.getInstance();
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
                String mobileNumber = etPhoneNumber.getText().toString();
                if (TextUtils.isEmpty(mobileNumber ) || mobileNumber.length() == 0){
                    etPhoneNumber.setError("Phone Number is Empty.");
                    return;
                }
                else if(mobileNumber.length() < 11){
                    etPhoneNumber.setError("The phone length is not enough.");
                    return;
                }
                else if(!(mobileNumber.substring(0,2).equals("09") || mobileNumber.charAt(0) == '0')){
                    etPhoneNumber.setError("The mobile number is not valid.");
                    return;
                }

                mobileNumber = "+63" + mobileNumber.substring(1,mobileNumber.length());
                intent = new Intent(SignInActivity.this,OTPVerificationActivity.class);
                intent.putExtra("mobileNumber", mobileNumber);
                startActivity(intent);

         /*       dbMUVFirebase.checkCommuterExists(etPhoneNumber.getText().toString(), new DbMUVFirebase.CheckExist() {
                    @Override
                    public void isExist(boolean exists, Commuter c) {
                        if (exists){

                            String fullname = c.getFirstname() + " " + c.getMiddlename().substring(0,1)+". " + c.getLastname() ;
                            Toast.makeText(SignInActivity.this, "Welcome " + fullname, Toast.LENGTH_SHORT).show();
                            myPrefEditor = getSharedPreferences("Login", Context.MODE_PRIVATE).edit();
                            myPrefEditor.putString("PhoneNumber",etPhoneNumber.getText().toString());
                            myPrefEditor.commit();
                            intent = new Intent(SignInActivity.this,DashboardActivity.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(SignInActivity.this, "Not yet exists", Toast.LENGTH_SHORT).show();
                        }
                    }

                });*/

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
