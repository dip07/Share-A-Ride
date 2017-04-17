package com.company1.coms510.ride2isu;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * Created by dipanjan karmakar
 */


/**
 * A simple {@link Fragment} subclass.
 */
public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener  {

    private String LOG_CAT=DatePickerFragment.class.getSimpleName();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        try {
        Date oldDate=null;
        Log.d(LOG_CAT,"Time : " + year + " month : " + month  + " day : " + day);
        TextView rideTime = (TextView) getActivity().findViewById(R.id.time_text_id);
        String dateString=rideTime.getText().toString();
        Log.d(LOG_CAT,"dateString >> " + dateString);
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm - EEE MMM d yyyy",new Locale("en_US"));
            //Log.d(LOG_CAT,formatter.parse(dateString).toString());
            oldDate=formatter.parse(dateString);
            Log.d(LOG_CAT,"Old date >> " + oldDate);
            Calendar cal = Calendar.getInstance();
            cal.setTime(oldDate);

            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, month);
            cal.set(Calendar.DAY_OF_MONTH, day);
            //int hourOfDay=cal.get(Calendar.HOUR_OF_DAY);
            //Log.d(LOG_CAT,"Hour Of Day  > " + hourOfDay);
            //cal.set(Calendar.HOUR,hourOfDay<12?hourOfDay:hourOfDay-12);
            //cal.set(Calendar.AM_PM,hourOfDay<12?0:1);

            Date newDate = cal.getTime();
            Log.d(LOG_CAT,"new Date >> " + newDate);
            StringBuilder dateBuilder= new StringBuilder(formatter.format(newDate).toString());
            rideTime.setText(dateBuilder.toString());
            if(new Date().after(newDate)) {
                rideTime.requestFocus();
                rideTime.setError("Enter a future Date Time");
                rideTime.requestFocus();
            }
            else
                rideTime.setError(null);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(LOG_CAT,"Error while parsing date");
        }

    }



}
