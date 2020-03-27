package com.muv.tracker;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.SuccessContinuation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private Intent intent;
    private EditText etMobilePhone, etFirstname, etMiddlename, etLastname, etEmail;
    private Button btnSignUp;
    private Commuter commuter;
    private DbMUVFirebase dbMUVFirebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        dbMUVFirebase = new DbMUVFirebase(SignUpActivity.this);
        init();
    }
    private void init(){
        etMobilePhone = findViewById(R.id.etPhoneNum);
        etFirstname = findViewById(R.id.etFirstname);
        etMiddlename = findViewById(R.id.etMiddlename);
        etLastname = findViewById(R.id.etLastname);
        etEmail = findViewById(R.id.etEmail);

        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               final EditText[] editTexts = {etMobilePhone,etFirstname,etMiddlename,etLastname,etEmail};
                for (int i = 0; i < editTexts.length; i++) {
                    if (TextUtils.isEmpty(editTexts[i].getText())){
                        editTexts[i].setError(editTexts[i].getHint() + " is Empty. Please fill it up");
                        return;
                    }
                }
                commuter = new Commuter();
                commuter.setContactNumber(etMobilePhone.getText().toString());
                commuter.setFirstname(etFirstname.getText().toString());
                commuter.setMiddlename(etMiddlename.getText().toString());
                commuter.setLastname(etLastname.getText().toString());
                commuter.setEmail(etEmail.getText().toString());
                commuter.setPin("");

          /*      final boolean[] isExist = new boolean[1];
                dbMUVFirebase.checkCommuterExists(commuter.getContactNumber(), new DbMUVFirebase.CheckExist() {
                    @Override
                    public void isExist(boolean exists) {
                        isExist[0] = exists;
                        if (exists){
                            Toast.makeText(SignUpActivity.this, "The account is already existed in our database.", Toast.LENGTH_SHORT).show();
                        }
                        //return;
                    }
                });*/

              /*  if (isExist[0]){
                    return;
                }*/

                dbMUVFirebase.newCommuter(commuter, new DbMUVFirebase.DataStatus() {
                    //Pass to this method.
                    @Override
                    public void isInserted(int flags) {
                        if(flags == 0){
                            Toast.makeText(SignUpActivity.this, "Please click it again", Toast.LENGTH_SHORT).show();
                            for (int i = 0; i < editTexts.length; i++) {
                                editTexts[i].setEnabled(false);
                            }
                        }
                        else if (flags == 1){
                            Toast.makeText(SignUpActivity.this, "Successfully created your account.", Toast.LENGTH_SHORT).show();
                            backToSignIn();
                        }

                    }
                });

                etEmail.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                        if (actionId == EditorInfo.IME_ACTION_DONE){
                            btnSignUp.callOnClick();
                            return true;
                        }
                        return false;
                    }
                });

            }
        });
    }

    @Override
    public void onBackPressed() {
       backToSignIn();
    }

    private void backToSignIn(){
        intent = new Intent(SignUpActivity.this,SignInActivity.class);
        startActivity(intent);
    }

}
