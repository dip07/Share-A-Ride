package com.company1.coms510.ride2isu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditProfile extends AppCompatActivity {

    String LOG_TAG = EditProfile.class.getName();
    TextView email;
    TextView username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.editProfile_id);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        email = (TextView)findViewById(R.id.display_email_id);
        username = (TextView)findViewById(R.id.display_username_id);
    }

    public void setData(){
        email.setText("company1@iastate.edu");
        username.setText("company1");
    }

    public void takePicture(View view){
        Log.d(LOG_TAG, "Take new picture.");
        Context context = getApplicationContext();
        CharSequence text = "Take a New Picture";
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void editPicture(View view){
        Log.d(LOG_TAG, "Select new picture");
        Context context = getApplicationContext();
        CharSequence text = "Select new Picture.";
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void removePicture(View view){
        Log.d(LOG_TAG, "Remove Picture.");
        Context context = getApplicationContext();
        CharSequence text = "Removed Picture";
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public boolean validatePassword(String string){
        Log.d(LOG_TAG, "Validate Password for required length and characters.");
        return true;
    }

    public void changePassword(View view){

        //Declare and Initialize UI elements
        EditText previousPassword = (EditText)findViewById(R.id.passwordEditText);
        EditText changedPassword = (EditText)findViewById(R.id.editText);
        EditText confirmNewPassword = (EditText)findViewById(R.id.confirmPasswordEditText);
        String prevPassword = previousPassword.getText().toString();
        String newPassword = changedPassword.getText().toString();
        String confirmPassword = confirmNewPassword.getText().toString();

        Log.d(LOG_TAG, "All Passwords entered are, prev : " + prevPassword + " New : "+ newPassword + " Confirmed : "+ confirmPassword);

        if(prevPassword != null && sameConfirmPassword(newPassword, confirmPassword)){
            Log.d(LOG_TAG, "Entered Passwords are not same");
            Toast toast = Toast.makeText(getApplicationContext(), "Passwords does not match!", Toast.LENGTH_LONG);
            toast.show();
            confirmNewPassword.setError("Please enter the same password!");
        }

        if(prevPassword!=null && newPassword.equals(confirmPassword)){
            Log.d(LOG_TAG, "Entered Passwords are same");
            /*
            validate credentials
            */
            if(validatePassword(newPassword)){
                Log.d(LOG_TAG, "The password entered is " + newPassword);
                Toast toast = Toast.makeText(getApplicationContext(), "Press save for the changes to implement.", Toast.LENGTH_LONG);
                toast.show();
            }
            else{
                Log.d(LOG_TAG, "Entered Password does not match our requirements" + newPassword);
                confirmNewPassword.setError("Please enter another password");
            }
        }

    }

    public boolean sameConfirmPassword (String password, String confirmPassword){
                return (!password.equals(confirmPassword));
    }

    public void goToMainScreen(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
