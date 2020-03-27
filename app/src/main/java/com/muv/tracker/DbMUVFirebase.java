package com.muv.tracker;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class DbMUVFirebase {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private Context myContext;
    private boolean taskStatus;
    private int flags;
    private ArrayList<NewsAndAnnouncement> newsList;


    //Reason: We have interface DataStatus because Firebase does Async task.
//and they are late to give the results so you have to double click the button
//This really helps
    public interface DataStatus{
        void isInserted(int flags);
    }
    public interface CheckExist{
        void isExist(boolean exists,Commuter c);
    }
    public interface LoadAccount{
        void loadAccount(Commuter c);
    }
    public interface LoadNewsAndAnnouncement{
        void loadNews(ArrayList<NewsAndAnnouncement> newsAndAnnouncements);
    }
    public DbMUVFirebase() {
    }

    public DbMUVFirebase(Context myContext) {
        this.myContext = myContext;
        FirebaseApp.initializeApp(myContext);
    }

    public void newCommuter(Commuter commuter, final DataStatus dataStatus){
        myRef = database.getReference();
/*
        if (exists){
            return;
        }*/
            myRef.child("tblCommuter").child(commuter.getContactNumber()).setValue(commuter)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            int flags = task.isSuccessful() ? 1 : 0;
                            dataStatus.isInserted(flags);
                        }
                    });

    }
    public void checkCommuterExists(String contactNumber, final CheckExist checkExist) {
        myRef = database.getReference();
        myRef.child("tblCommuter").child(contactNumber)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Commuter c = dataSnapshot.getValue(Commuter.class);
                        checkExist.isExist(dataSnapshot.getValue() != null, c);
                        boolean ch = dataSnapshot.getValue() == null;
                        Log.d("DbHelper", "onDataChange: " + ch);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }
    public void loadAccount(String contactNumber, final CheckExist checkExist) {
        myRef = database.getReference();
        myRef.child("tblCommuter").child(contactNumber)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        boolean ch = dataSnapshot.getValue() == null;
                        Log.d("DbHelper", "onDataChange: " + ch);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }
    public void loadNewsAndAnnouncement(final LoadNewsAndAnnouncement newsAndAnnouncement){
        myRef = database.getReference("tblNews");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               newsList = new ArrayList<>();
                for (DataSnapshot newsSnapshot : dataSnapshot.getChildren()) {
                    NewsAndAnnouncement newsAndAnnouncement1 = newsSnapshot.getValue(NewsAndAnnouncement.class);
                    newsList.add(newsAndAnnouncement1);
            }
                newsAndAnnouncement.loadNews(newsList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    public boolean isTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(boolean taskStatus) {
        this.taskStatus = taskStatus;
    }


    //myRef.setValue("Hello, World!");
}
