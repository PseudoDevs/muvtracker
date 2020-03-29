package com.muv.tracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class EditProfileActivity extends AppCompatActivity {

    private String clientInfo[];
    private EditText etFirstname, etMiddlename, etLastname, etEmail;
    private TextView tvContactNumber;
    private ImageView imvProfile;
    private Button btnChangePin;
    private Intent intent;
    private DbMUVFirebase dbMUVFirebase;
    private Commuter c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        dbMUVFirebase = new DbMUVFirebase();
        clientInfo = getIntent().getStringArrayExtra("userInfo");
        String phoneNumber = clientInfo[0] ;
        String mobileNumber = "0" + phoneNumber.substring(3,phoneNumber.length());
        init();

        c = new Commuter();
        c.setContactNumber(mobileNumber);
        c.setFirstname(etFirstname.getText().toString());
        c.setMiddlename(etMiddlename.getText().toString());
        c.setLastname(etLastname.getText().toString());
        c.setEmail(etEmail.getText().toString());
    }
    private void init(){
        tvContactNumber = findViewById(R.id.tvClientContactNo);
        etFirstname = findViewById(R.id.etFirstname);
        etMiddlename = findViewById(R.id.etMiddlename);
        etLastname = findViewById(R.id.etLastname);
        etEmail = findViewById(R.id.etEmail);



        tvContactNumber.setText(clientInfo[0]);
        etFirstname.setText(clientInfo[1]);
        etMiddlename.setText(clientInfo[2]);
        etLastname.setText(clientInfo[3]);
        etEmail.setText(clientInfo[4]);

        btnChangePin = findViewById(R.id.btnChangePin);

        buttonClick();
    }
    private void buttonClick(){
        btnChangePin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(EditProfileActivity.this, "change pin", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_profile,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.itmSaveChanges){
            dbMUVFirebase.newCommuter(c, new DbMUVFirebase.DataStatus() {
                @Override
                public void isInserted(int flags) {
                    if (flags == 1){
                        Toast.makeText(EditProfileActivity.this, "Successfully save changes", Toast.LENGTH_SHORT).show();
                        toDashboard();
                    }
                    else{
                        Toast.makeText(EditProfileActivity.this, "Failed to save changes", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            //toDashboard();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        toDashboard();
    }
    private void toDashboard(){
        intent = new Intent(EditProfileActivity.this,DashboardActivity.class);
        startActivity(intent);
        finish();
    }
}