package com.company1.coms510.ride2isu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.company1.coms510.ride2isu.DatabaseGateway.persistence.testsupport.MemStore;
import com.company1.coms510.ride2isu.DatabaseGateway.persistence.Record;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Memstore testing

public class AcceptRide extends AppCompatActivity {


    ExpandableListView expandableListView;
    ExpandableListAdapter listAdapter;
    List<String> headTag;
    Map<String, List<String>> bodyListing;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_ride);
        String LOG_TAG= AcceptRide.class.getName();
        Intent intent = getIntent();
        List<Record> result = new ArrayList<Record>();
        final MemStore store = (MemStore) intent.getSerializableExtra(getString(R.string.mem_store_name));
        if(store!=null)
        {
            String topicKey="testKey";
            store.read("RidesOffered", topicKey, result);
            if(!result.isEmpty())
            {
                for(Record r:result){
                    Log.d(LOG_TAG,"Record retreived > >" + r.pLoad);
                    System.out.println("---------- " + r.pLoad );
                }
            }
        }
        System.out.print("STORE : "+ store);

        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        fillData();
        listAdapter = new ExpandableAdapter(this,headTag,bodyListing);
        expandableListView.setAdapter(listAdapter);
    }


    public void fillData(){
        headTag = new ArrayList<>();
        bodyListing = new HashMap<>();
        headTag.add("John asked for a ride. Tap to expand");
        headTag.add("Pallavi asked for a ride. Tap to expand");
        headTag.add("Jenny asked for a ride. Tap to expand");
        headTag.add("Pavithra asked for a ride. Tap to expand");
        List<String> java = new ArrayList<>();
        List<String> c = new ArrayList<>();

        java.add("Rider Name: ");
        //java.add("Rider Name: ");
        //c.add("Rider Name: ");
        c.add("Rider Name: ");
        //c.add("Rider Name: ");

        bodyListing.put(headTag.get(0), java);
        bodyListing.put(headTag.get(1), java);
        bodyListing.put(headTag.get(2), java);
        bodyListing.put(headTag.get(3), java);
        //bodyListing.put(headTag.get(1), c);
    }

    public void acceptARide(View view){

    }

}


/**
 * create a class which will contain data variables for different parameters required by the requested rides screen.
 * create multiple objects of this screen. Store a reference based on the ride id, and rider_name.
 *
 *
 * also maintain a separate class of data variables for the rides offered section.
 *
 * so, basically, i need to fetch 2 database connections. RidesOffered, and RidesRequested.
 *
 * fetch the record of rides offered based on driver name and ride_id. need to update the car capacity in this list only.
 *
 *
 *
 **/

