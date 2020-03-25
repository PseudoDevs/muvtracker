package com.muv.tracker;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.flags.IFlagProvider;
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

import java.util.concurrent.Executor;

public class DbMUVFirebase {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private Context myContext;
    private boolean taskStatus;
    private int flags;
    public DbMUVFirebase() {
    }

    public DbMUVFirebase(Context myContext) {
        this.myContext = myContext;
        FirebaseApp.initializeApp(myContext);
    }

    public int newCommuter(Commuter commuter){
        myRef = database.getReference();
        myRef.child("tblCommuter").child(commuter.getContactNumber()).setValue(commuter)
               .addOnCompleteListener(new OnCompleteListener<Void>() {
                   @Override
                   public void onComplete(@NonNull Task<Void> task) {
                       flags = task.isSuccessful() ? 1 : 0;
                   }
               });
        return flags;
    }


    public boolean isTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(boolean taskStatus) {
        this.taskStatus = taskStatus;
    }


    //myRef.setValue("Hello, World!");
}
