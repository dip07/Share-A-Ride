package com.company1.coms510.ride2isu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class ViewProfile extends AppCompatActivity {

    String LOG_TAG = ViewProfile.class.getName();
    TextView fullName;
    TextView username;
    TextView birthDate;
    TextView email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fullName = (TextView)findViewById(R.id.display_fullName_id);
        username = (TextView)findViewById(R.id.display_username_id);
        birthDate = (TextView)findViewById(R.id.display_birthDate);
        email = (TextView)findViewById(R.id.display_email_id);
        fetchFromMemstore();
    }


    public void fetchFromMemstore(){

        fullName.setText("Company1 Company1");
        username.setText("Company1");
        birthDate.setText("04/18/1994");
        email.setText("company1@iastate.edu");
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void goToMainActivity(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
