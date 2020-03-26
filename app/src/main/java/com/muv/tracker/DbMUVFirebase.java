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

public class DbMUVFirebase {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private Context myContext;
    private boolean taskStatus;
    private int flags;

    //Reason: We have interface DataStatus because Firebase does Async task.
    //and they are late to give the results so you have to double click the button
    //This really helps
    public interface DataStatus{
         void isInserted(int flags);
    }
    public interface CheckExist{
        void isExist(boolean exists);
    }

    public DbMUVFirebase(Context myContext) {
        this.myContext = myContext;
        FirebaseApp.initializeApp(myContext);
    }

    public void newCommuter(Commuter commuter, boolean exists, final DataStatus dataStatus){
        myRef = database.getReference();

        if (exists){
            return;
        }
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

        myRef.child("tblCommuter").child(contactNumber)/*.runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                if (mutableData.getValue() == null){

                }
                return Transaction.abort();
            }

            @Override
            public void onComplete(@Nullable DatabaseError databaseError, boolean b, @Nullable DataSnapshot dataSnapshot) {

            }
        });*/
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        checkExist.isExist(dataSnapshot.getValue() != null);
                        boolean ch = dataSnapshot.getValue() == null;
                        Log.d("DbHelper", "onDataChange: " + ch);
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
