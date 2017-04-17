package com.company1.coms510.ride2isu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.company1.coms510.ride2isu.DatabaseGateway.gatewaysupport.PsGateway;
import com.company1.coms510.ride2isu.DatabaseGateway.infrastructure.ServiceRegistry;
import com.company1.coms510.ride2isu.DatabaseGateway.persistence.testsupport.MemStore;
import com.company1.coms510.ride2isu.DatabaseGateway.persistence.testsupport.MemStoreGw;
import com.company1.coms510.ride2isu.DatabaseGateway.persistence.Record;
import com.company1.coms510.ride2isu.model.RidesOffered;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by dipanjan karmakar
 */

public class OfferARideActivity extends AppCompatActivity {

    // Use LOG_TAG for logging purpose
    String LOG_TAG=OfferARideActivity.class.getName();

    // for getting location data
    int SOURCE_PLACE_PICKER_REQUEST = 1;
    int DESTINATION_PLACE_PICKER_REQUEST = 2;

    static MemStore store=null;
    LatLng sourceLatLng=null;
    LatLng destinationLatLng=null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_aride);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView dateTimeText = (TextView)findViewById(R.id.time_text_id);
        Date currDate= new Date();

        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm - EEE MMM d yyyy",new Locale("en_US"));
        StringBuilder dateBuilder= new StringBuilder(formatter.format(currDate).toString());
        dateTimeText.setText(dateBuilder.toString());

        /*Intent prevIntent = getIntent();
        store = (MemStore) prevIntent.getSerializableExtra(getString(R.string.mem_store_name));
        if(store==null)
        {
            Log.d(LOG_TAG,"Memstore >>  Previous store was not found. Creating new.");
            store=new MemStore();
            if(store!=null)
                Log.d(LOG_TAG,"Store created");

        }*/

        ServiceRegistry locator  = ServiceRegistry.getInstance();
        if(locator.getService(PsGateway.class) == null)
        {
            locator.register(PsGateway.class, new MemStoreGw());
        }
        MemStoreGw memStoreGw= (MemStoreGw)locator.getService(PsGateway.class);
        store= memStoreGw.getMemStore();
    }


    public void showRequestedRides()
    {
        Log.d(LOG_TAG,"Request a ride!");
    }

    public void goToDisplayRideActivity(View view)
    {
        try {
            EditText rideSource = (EditText) findViewById(R.id.source_text_id);
            EditText rideDestination = (EditText) findViewById(R.id.dest_text_id);
            EditText rideTime = (EditText) findViewById(R.id.time_text_id);
            EditText countPerson = (EditText) findViewById(R.id.count_person_text_id);

            String sourceAddressString=rideSource.getText().toString();
            // Test if a Source Address has been entered or not
            if(TextUtils.isEmpty(sourceAddressString))
            {
                rideSource.setError("Please select a source address");
                rideSource.requestFocus();
                return;
            }

            String destAddressString=rideDestination.getText().toString();
            // Test if a Destination Address has been entered or not
            if(TextUtils.isEmpty(destAddressString))
            {
                rideDestination.setError("Please select a destination address");
                rideDestination.requestFocus();
                return;
            }


            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm - EEE MMM d yyyy",new Locale("en_US"));
            String dateTimeString=rideTime.getText().toString();
            Date ride_date=formatter.parse(dateTimeString);
            // Test if a a valid date has been entered or not
            if(new Date().after(ride_date))
            {
                rideTime.setError("Set future date");
                rideTime.requestFocus();
                return;
            }

            String countPersonString=countPerson.getText().toString();
            // Test if numbers of riders are entered or not
            if(TextUtils.isEmpty(countPersonString))
            {
                countPerson.setError("Enter number of person");
                countPerson.requestFocus();
                return;
            }
            try{
                Integer.parseInt(countPersonString);
            }
            catch(Exception e)
            {
                Log.d(LOG_TAG,"Error in count of person");
                e.printStackTrace();
                countPerson.requestFocus();
                return;
            }
            Log.d(LOG_TAG,"Ride Source : " + rideSource.getText().toString());
            Log.d(LOG_TAG,"Ride Destination : " + rideDestination.getText().toString());
            Log.d(LOG_TAG,"Ride Time : " + rideTime.getText().toString());
            Log.d(LOG_TAG,"Num of persons : " + countPerson.getText().toString());
            /*OfferARide offerARide= new OfferARide();
            offerARide.setUserId(getUserIdFromPreviousActivity());
            offerARide.setRideSource(rideSource.getText().toString());
            offerARide.setRideDest(rideDestination.getText().toString());
            offerARide.setRideTime(rideTime.getText().toString());
            offerARide.setCountPerson(countPerson.getText().toString());*/

            RidesOffered ride= new RidesOffered();
            ride.setDriver_name(getUserIdFromPreviousActivity());
            ride.setSource_address(rideSource.getText().toString());
            ride.setDest_address(rideDestination.getText().toString());

            ride.setDate_time_of_ride(formatter.parse(rideTime.getText().toString()));
            int seats=Integer.parseInt(countPerson.getText().toString());
            ride.setOffered_seats(seats);
            ride.setAvailable_seats(seats);
            ride.setCreation_date_time(new Date());
            ride.setRiders_name(null);
            ride.setSource_latLng(sourceLatLng);
            ride.setDest_latLng(destinationLatLng);
            Log.w(LOG_TAG,"Ride details >> " + ride.toJSON());
            /*
            Store data in memstore
             */
            boolean success = store.addTopic("RidesOffered");
            if (success)
                Log.d(LOG_TAG,"Topic created");
            else
                Log.d(LOG_TAG,"Topic already exist");
            List<Record> result = new ArrayList<Record>();
            //String topicKey=rideSource.getText().toString() +"-"+rideDestination.getText().toString()+(new Date().getTime());

            String topicKey="testKey";
            //String topicKey=ride.getSource_address()+"-"+ride.getDest_address()+(new Date().getTime());
            //int status = store.create("RidesOffered", topicKey, offerARide.toJSON());
            int status = store.create("RidesOffered", topicKey, ride.toJSON());
            status = store.read("RidesOffered", topicKey, result );


            Intent intent= new Intent(this,DisplayScreenActivity.class);
            //intent.putExtra(EXTRA_MESSAGE,offerARide);
            //intent.putExtra(EXTRA_MESSAGE,ride);
            //intent.putExtra(getString(R.string.mem_store_name),store);
            intent.putExtra("RegistrationMessage","You have been registered successfully");
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private String getUserIdFromPreviousActivity()
    {
        // extract the UserId from previous Activity and return
        // as of now set to static string

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        //editor.putString(getString(R.string.preference_file_key),email);
        //editor.commit();
        // To retrieve the value use
        String username = sharedPref.getString(getString(R.string.preference_file_key),null);
        Log.w(LOG_TAG,"Username from shared pref >> " + username);
        return username;
        //return "C1";

    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void getSourceAddress(View view)
    {
        try {
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
            startActivityForResult(builder.build(this), SOURCE_PLACE_PICKER_REQUEST);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public void getDestAddress(View view)
    {
        try {
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
            startActivityForResult(builder.build(this), DESTINATION_PLACE_PICKER_REQUEST);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SOURCE_PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(this,data);
                String toastMsg = String.format("Place: %s", place.getName());
                //Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
                EditText rideSource = (EditText) findViewById(R.id.source_text_id);
                rideSource.setText(place.getName()+","+place.getAddress());
                rideSource.setError(null);
                sourceLatLng=place.getLatLng();

            }
        }

        if (requestCode == DESTINATION_PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(this,data);
                String toastMsg = String.format("Place: %s", place.getName());
                //Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
                EditText destSource = (EditText) findViewById(R.id.dest_text_id);
                destSource.setText(place.getName()+","+place.getAddress());
                destSource.setError(null);
                destinationLatLng=place.getLatLng();
            }
        }
    }
    public void removeErrorFromDate(View view)
    {
        TextView rideTime = (TextView) findViewById(R.id.time_text_id);
        rideTime.setError(null);
    }

}
