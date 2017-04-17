package com.company1.coms510.ride2isu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.company1.coms510.ride2isu.DataAccessUsingFirebase.DataAccessLayer;
import com.company1.coms510.ride2isu.DataAccessUsingFirebase.DataAccessLayerImpl;
import com.company1.coms510.ride2isu.DatabaseGateway.gatewaysupport.PsGateway;
import com.company1.coms510.ride2isu.DatabaseGateway.infrastructure.ServiceRegistry;
import com.company1.coms510.ride2isu.DatabaseGateway.persistence.Record;
import com.company1.coms510.ride2isu.DatabaseGateway.persistence.testsupport.MemStore;
import com.company1.coms510.ride2isu.DatabaseGateway.persistence.testsupport.MemStoreGw;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by dipanjan karmakar
 */


public class DisplayScreenActivity extends AppCompatActivity {

    DataAccessLayer DAOLayer = new DataAccessLayerImpl();


    String LOG_TAG= DisplayScreenActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

       Intent intent = getIntent();
        /*final OfferARide rideDetails = (OfferARide) intent.getSerializableExtra(EXTRA_MESSAGE);
        Log.d(LOG_TAG,"Inside Display screen ~~~~~~~~~~~~~~~~~~~~~~~~~ >>>>>>>>>>>>   ");
        Log.d(LOG_TAG,"User Id :" + rideDetails.getUserId());
        Log.d(LOG_TAG,"Ride Source : " + rideDetails.getRideSource());
        Log.d(LOG_TAG,"Ride Destination : " + rideDetails.getRideDest());
        Log.d(LOG_TAG,"Ride Time : " + rideDetails.getRideTime());
        Log.d(LOG_TAG,"Count of person :" + rideDetails.getCountPerson());*/

//        final RidesOffered ridesOffered = (RidesOffered) intent.getSerializableExtra(EXTRA_MESSAGE);
//        Log.d(LOG_TAG,"Inside Display screen ~~~~~~~~~~~~~~~~~~~~~~~~~ >>>>>>>>>>>>   ");
//        Log.d(LOG_TAG,"Driver's name :" + ridesOffered.getDriver_name());
//        Log.d(LOG_TAG,"Ride Source : " + ridesOffered.getSource_address());
//        Log.d(LOG_TAG,"Ride Destination : " + ridesOffered.getDest_address());
//        Log.d(LOG_TAG,"Ride Time : " + ridesOffered.getDate_time_of_ride());
//        Log.d(LOG_TAG,"Count of person : " + ridesOffered.getOffered_seats());


        String post_register_message=intent.getStringExtra("RegistrationMessage");



        List<Record> result = new ArrayList<Record>();
        //final MemStore store = (MemStore) intent.getSerializableExtra(getString(R.string.mem_store_name));
        ServiceRegistry locator  = ServiceRegistry.getInstance();
        if(locator.getService(PsGateway.class) == null)
        {
            locator.register(PsGateway.class, new MemStoreGw());
        }
        MemStoreGw memStoreGw= (MemStoreGw)locator.getService(PsGateway.class);
        MemStore store= memStoreGw.getMemStore();
        if(store!=null)
        {
            String topicKey="testKey";
            store.read("RidesOffered", topicKey, result );
            if(!result.isEmpty())
            {
                for(Record r:result){
                    Log.d(LOG_TAG,"Record retreived >>" + r.pLoad);
                }
            }
        }


        /*TextView textView=(TextView)findViewById(R.id.post_register_string_id);
        textView.setText(post_register_message);*/


//        Context context = getApplicationContext();
//        CharSequence text = ridesOffered.toString();
//        int duration = Toast.LENGTH_LONG;
//
//        Toast toast = Toast.makeText(context, text, duration);
//        toast.show();

//        final TextView displayRides = (TextView)findViewById(R.id.rides_display_id);
//        DAOLayer.fetchData("rides", new FirebaseHelper() {
//            @Override
//            public void onFinishLoading(Object object) {
//                ArrayList<String> ridesList = (ArrayList<String>)object;
//                if(ridesList == null){
//                    ridesList = new ArrayList<String>();
//                }
//                //ridesList.add(rideDetails.rideString());
//                ridesList.add(ridesOffered.toJSON());
//                DAOLayer.setData("rides", ridesList);
//                displayRides.setText(ridesList.get(ridesList.size() - 1));
//                Log.d("LOG_TAG", "Number of rides fetched from Firebase : " + ridesList.size());
//
//                try {
//                    JSONObject test= new JSONObject(ridesOffered.toJSON());
//                    Log.d(LOG_TAG, "Destination from JsonString : " + test.getString("dest_address"));
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });

    }


}
