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

public class EditProfileActivity extends AppCompatActivity {

    private String clientInfo[];
    private EditText etFirstname, etMiddlename, etLastname, etEmail;
    private TextView tvContactNumber;
    private ImageView imvProfile;
    private Button btnChangePin;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        clientInfo = getIntent().getStringArrayExtra("ClientInfo");
        init();
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

            }
        });
    }
    private void dialogChangePin(){



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_profile,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.itmSaveChanges){
            toDashboard();
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
