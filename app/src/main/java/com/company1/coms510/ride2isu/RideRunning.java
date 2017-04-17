package com.company1.coms510.ride2isu;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/*
 * Created by Viswanath on 11/02/2016.
 */

public class RideRunning extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_running);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ListView listView = (ListView) findViewById(R.id.view_riders_id);

        String[] values = new String[] { "Venkat\n\n" +
                "Ames",
                "Viswanath\n\nBeyer Hall",
                "Sachin\n\n" +
                        "Curtis Hall",
                "Dhoni\n\n" +
                        "Jack Trice",
                "Virat\n\n" +
                        "Jack Trice",
                "Dhoni\n\n" +
                        "Jack Trice",
                "Venkat\n\n" +
                        "Ames",
                "Viswanath\n\nBeyer Hall",
                "Sachin\n\n" +
                        "Curtis Hall"
        };



        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);


        // Assign adapter to ListView
        listView.setAdapter(adapter);
    }

    public void call911(View view){
        try {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:911"));
            startActivity(callIntent);
            Toast.makeText(getApplicationContext(),"Call is in Progress",Toast.LENGTH_SHORT).show();
        } catch (ActivityNotFoundException activityException) {
            Log.e("Calling a Phone Number", "Call failed", activityException);
        }
        catch(SecurityException securityException){
            Log.e("Calling a Phone Number", "Call failed", securityException);
        }
    }

    public void goToMainScreen(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
