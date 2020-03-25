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

    public DbMUVFirebase() {
    }

    public DbMUVFirebase(Context myContext) {
        this.myContext = myContext;
        FirebaseApp.initializeApp(myContext);
    }

    public boolean newCommuter(Commuter commuter){
        myRef = database.getReference();
        String id = myRef.push().getKey();
        commuter.setIdnumber(id);
        //Task task =
        //myRef.keepSynced(true);
        setTaskStatus(myRef.child("tblCommuter").push().setValue(commuter).isSuccessful());

    /*  .addOnCompleteListener(new OnCompleteListener<Void>() {
          @Override
          public void onComplete(@NonNull Task<Void> task) {
              setTaskStatus(task.isSuccessful());
          }
      })*/

    /*  .addOnSuccessListener(new OnSuccessListener<Void>() {
          @Override
          public void onSuccess(Void aVoid) {

          }
      });*/

     /* , new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                if (databaseError != null){
                    setTaskStatus(false);
                    return;
                }
                else{
                    setTaskStatus(true);
                }
            }
        }*/
      /* task1.addOnCompleteListener(new OnCompleteListener() {
           @Override
           public void onComplete(@NonNull Task task) {
               task.
                if (task.isSuccessful()){
                    setTaskStatus(true);
                }
           }
       });*/


      //  return task1;
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
