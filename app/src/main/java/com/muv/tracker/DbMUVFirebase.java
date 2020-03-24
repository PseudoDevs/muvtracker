package com.muv.tracker;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DbMUVFirebase {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private Context myContext;
    private boolean taskStatus;

    public DbMUVFirebase() {
    }

    public DbMUVFirebase(Context myContext) {
        this.myContext = myContext;
        FirebaseApp.initializeApp(myContext);
    }

    public boolean newCommuter(Commuter commuter){



        myRef = database.getReference();
        Task task = myRef.child("tblCommuter").push().setValue(commuter);
        task.addOnCompleteListener((Activity) myContext, new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                setTaskStatus(task.isSuccessful());
            }
        });
        return isTaskStatus();
    }


    public boolean isTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(boolean taskStatus) {
        this.taskStatus = taskStatus;
    }


    //myRef.setValue("Hello, World!");
}
