package com.company1.coms510.ride2isu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.company1.coms510.ride2isu.DataAccessUsingFirebase.DataAccessLayer;
import com.company1.coms510.ride2isu.DataAccessUsingFirebase.DataAccessLayerImpl;

public class MainActivity extends ActionBarActivity {

    DataAccessLayer daoLayer = new DataAccessLayerImpl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setContentView(R.layout.activity_login);
        //Intent intent= new Intent(this,RegistrationActivity.class);
        //startActivity(intent);

    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    public void goToOfferRideActivity(View view)
    {
        Intent intent= new Intent(this,OfferARideActivity.class);
        //Intent intent= new Intent(this,LoginActivity.class);
        startActivity(intent);
    }

    public void goToSettings(View view){

        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }

    public void goToViewProfile(View view){
        Intent intent = new Intent(this, ViewProfile.class);
        startActivity(intent);
    }

    public void viewRequestedRides(View view){
        Intent intent = new Intent(this, AcceptRide.class);
        startActivity(intent);
    }
    public void goToViewAvailableRides(View view)
    {
        Intent intent= new Intent(this,DisplayScreenActivity.class);
        startActivity(intent);
    }

    public void goTo911(View view)
    {
        Intent intent= new Intent(this,RideRunning.class);
        startActivity(intent);
    }
}
