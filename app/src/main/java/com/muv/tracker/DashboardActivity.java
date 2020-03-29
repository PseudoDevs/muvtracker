package com.muv.tracker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {

    private Client client;
    private Intent intent;
    private String phoneNumber;
    private SharedPreferences mySharedPref;
    private SharedPreferences.Editor myPrefEditor;
    private FirebaseAuth mAuth;
//    private String[] userInfo;
    private DbMUVFirebase dbMUVFirebase;
    private String mobileNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        dbMUVFirebase = new DbMUVFirebase();
        mAuth = FirebaseAuth.getInstance();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bnvDashboard);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.frmFragmentContainer,new HomeFragment(DashboardActivity.this,1)).commit();

        mySharedPref = getSharedPreferences("Login", Context.MODE_PRIVATE);
        phoneNumber = mySharedPref.getString("mobileNumber",null);
//        userInfo = getIntent().getStringArrayExtra("userInfo");

        mobileNumber = "0" + phoneNumber.substring(3,phoneNumber.length());
       // Toast.makeText(this, mobileNumber, Toast.LENGTH_SHORT).show();


    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            int bnvFlag = 0;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    bnvFlag = 1;
                    selectedFragment = new HomeFragment(DashboardActivity.this,bnvFlag);
                    Toast.makeText(DashboardActivity.this, R.string.title_home, Toast.LENGTH_SHORT).show();
                    break;
                case R.id.navigation_routes:
                    bnvFlag = 2;
                    selectedFragment = new HomeFragment(DashboardActivity.this,bnvFlag);
//                    Toast.makeText(DashboardActivity.this, R.string.title_dashboard, Toast.LENGTH_SHORT).show();
                    break;
                case R.id.navigation_tickets:
                    bnvFlag = 3;
                    selectedFragment = new HomeFragment(DashboardActivity.this,bnvFlag);
//                    Toast.makeText(DashboardActivity.this, R.string.title_notifications, Toast.LENGTH_SHORT).show();
                    break;
            }
            if (selectedFragment == null){
                Toast.makeText(DashboardActivity.this, "No fragments", Toast.LENGTH_SHORT).show();
                return false;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.frmFragmentContainer,selectedFragment).commit();
            return true;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dashboard,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.itm_profile){

            client = new Client();
            dbMUVFirebase.checkCommuterExists(mobileNumber, new DbMUVFirebase.CheckExist() {
                @Override
                public void isExist(boolean exists, Commuter c) {
                    if (exists){
                        Toast.makeText(DashboardActivity.this, "User exists", Toast.LENGTH_SHORT).show();
                        client.setFirstname(c.getFirstname());
                        client.setMiddlename(c.getMiddlename());
                        client.setLastname(c.getLastname());
                        client.setEmail(c.getEmail());
                        client.setContactNumber(phoneNumber);

                        String[] clientDetails = {client.getContactNumber(),client.getFirstname(),client.getMiddlename(),client.getLastname(),client.getEmail()};
                        intent = new Intent(DashboardActivity.this,EditProfileActivity.class);
                        intent.putExtra("userInfo",clientDetails);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Toast.makeText(DashboardActivity.this, "User does not exists", Toast.LENGTH_SHORT).show();
                    }
                }
            });



        }
        else if (item.getItemId() == R.id.itm_logout){
            dialogLogout();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        dialogLogout();
    }

    private void dialogLogout(){
        AlertDialog adLogout = new AlertDialog.Builder(DashboardActivity.this).setTitle("Logout").setMessage("Do you want to Logout?")
                .setPositiveButton("No", null)
                .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        myPrefEditor = getSharedPreferences("Login",Context.MODE_PRIVATE).edit();
                        myPrefEditor.clear();
                        myPrefEditor.commit();
                        mAuth.signOut();
                        intent = new Intent(DashboardActivity.this,SignInActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .create();
        adLogout.show();
    }

}