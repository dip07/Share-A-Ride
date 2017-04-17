package com.company1.coms510.ride2isu.DataAccessUsingFirebase;

/*
 * Created by Viswanath on 10/12/2016.
 */

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DataAccessLayerImpl implements DataAccessLayer{

    private String TAG = DataAccessLayerImpl.class.getName();

    public DataAccessLayerImpl() {
    }

    private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference mconditionRef;

    @Override
    public void setData(String condition, Object object) {
        mconditionRef = mRootRef.child(condition);
        mconditionRef.setValue(object);
    }

    @Override
    public void fetchData(String condition, final FirebaseHelper firebaseHelper) {
        mconditionRef = mRootRef.child(condition);

        mconditionRef.addListenerForSingleValueEvent(new ValueEventListener(){

        //mconditionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Object object = dataSnapshot.getValue();
                Log.d(TAG, "Fetched data from Firebase. Firebasehelper method called.");
                firebaseHelper.onFinishLoading(object);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "Error in connecting to Firebase database");
            }
        });

        Log.d(TAG, "Data fetch from Firebase completed");
    }
}
