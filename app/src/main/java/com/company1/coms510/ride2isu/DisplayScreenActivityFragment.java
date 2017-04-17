package com.company1.coms510.ride2isu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.company1.coms510.ride2isu.DatabaseGateway.gatewaysupport.PsGateway;
import com.company1.coms510.ride2isu.DatabaseGateway.infrastructure.ServiceRegistry;
import com.company1.coms510.ride2isu.DatabaseGateway.persistence.testsupport.MemStore;
import com.company1.coms510.ride2isu.DatabaseGateway.persistence.testsupport.MemStoreGw;
import com.company1.coms510.ride2isu.DatabaseGateway.persistence.Record;
import com.company1.coms510.ride2isu.model.RidesOffered;
import com.google.android.gms.maps.model.LatLng;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dipanjan karmakar
 */



/**
 * A placeholder fragment containing a simple view.
 */
public class DisplayScreenActivityFragment extends Fragment {

    public DisplayScreenActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_display_screen, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Intent intent =getActivity().getIntent();
        final List<Record> result = new ArrayList<Record>();
        //final MemStore store = (MemStore) intent.getSerializableExtra("memStoreObject");

        ServiceRegistry locator  = ServiceRegistry.getInstance();
        if(locator.getService(PsGateway.class) == null) {
            locator.register(PsGateway.class, new MemStoreGw());
        }
        MemStoreGw memStoreGw= (MemStoreGw)locator.getService(PsGateway.class);

        final MemStore store= memStoreGw.getMemStore();
        store.read("RidesOffered", "testKey", result );
        JSONObject json = new JSONObject();
        JSONObject ridesofferedJson = new JSONObject();
        try {
            ridesofferedJson.put("driver_name", "John");
            ridesofferedJson.put("dest_address", "des moines");
            ridesofferedJson.put("source_address", "State Gym");
            ridesofferedJson.put("available_seats",4);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RidesOffered hardCodedTest = new RidesOffered();
        hardCodedTest.setDriver_name("Bill");
        hardCodedTest.setAvailable_seats(4);
        hardCodedTest.setDest_address("World Trade Center");
        hardCodedTest.setSource_address("New York City Hall");
        hardCodedTest.setSource_latLng(new LatLng(40.7118011, -74.01311959999998));
        hardCodedTest.setDest_latLng(new LatLng(40.7127837,-74.00594130000002));

        boolean success = store.addTopic("RidesOffered");
        int status = store.create("RidesOffered", "source-destination-24th october 2016", String.valueOf(ridesofferedJson));
        int status2 = store.create("RidesOffered","source-destination-24th october 2016", hardCodedTest.toJSON());
        status = store.read("RidesOffered", "source-destination-24th october 2016", result );


//        Log.d("CreatedActivity", "LaunchpadFragment");
        final ListView viewRidesList= (ListView) getActivity().findViewById(R.id.list);
        // Gets the ListView from the View list of the parent activity
        // Gets a CursorAdapter

        ArrayAdapter<String> ridesListAdapter;
        List<String> ridesList=new ArrayList<String>();
        String in;

        // List<String> ridesList=getRideDetails().get(0).getRidesList();
        for(Record r:result){
            JSONObject reader = null;
            String count=null;
            String source= null;
            String destination= null;
            try {
                reader = new JSONObject(r.pLoad);
                source = reader.getString("source_address");
                count = reader.getString("available_seats");
                destination=reader.getString("dest_address");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ridesList.add("Available Seats:"+count+", Source :"+source+",Destination:"+destination);
        }
        ridesListAdapter= new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, ridesList);
        viewRidesList.setAdapter(ridesListAdapter);
        viewRidesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int itemPosition = position;
                String selectedRideId = (String) viewRidesList.getItemAtPosition(itemPosition);
                String rideKey = result.get(itemPosition).key;
                int duration = Toast.LENGTH_LONG;
                Toast.makeText(getActivity(), rideKey, duration).show();
            }
        });

        viewRidesList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                int itemPosition = position;
                Record selectedRide =  result.get(itemPosition);
                try {
                    JSONObject reader = new JSONObject(selectedRide.pLoad);
                    String source = reader.getString("source_latLng");
                    String destination = reader.getString("dest_latLng");
                    Intent intent = new Intent(getContext(),MapsActivity.class);
                    intent.putExtra("source",source);
                    intent.putExtra("dest",destination);
//                    Toast.makeText(getContext(),source,Toast.LENGTH_LONG).show();
//                    Toast.makeText(getContext(),destination,Toast.LENGTH_LONG).show();
                    startActivity(intent);

                } catch (JSONException e) {
                    e.printStackTrace();
                    return false;
                }


                return true;
            }
        });

    }

    public static class EditProfile extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_edit_profile2);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }


}
