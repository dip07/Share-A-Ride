package com.company1.coms510.ride2isu;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        showSourceAndDestination(mMap);
    }

    private void showSourceAndDestination(GoogleMap mMap)
    {
        String source = getIntent().getStringExtra("source");
        String dest = getIntent().getStringExtra("dest");
        LatLng sourceMarker = stringToLatLng(source);
        LatLng destMarker = stringToLatLng(dest);
        mMap.addMarker(new MarkerOptions().position(sourceMarker).title("Starting Point"));
        mMap.addMarker(new MarkerOptions().position(destMarker).title("Ending Point"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(destMarker));
    }

    private LatLng stringToLatLng(String input)
    {
        input = input.substring(input.indexOf('(')+1,input.length()-1);
        input.trim();
        String[] arr = input.split(",");
        double lat = Double.parseDouble(arr[0]);
        double lng = Double.parseDouble(arr[1]);
        return new LatLng(lat,lng);

    }
}


