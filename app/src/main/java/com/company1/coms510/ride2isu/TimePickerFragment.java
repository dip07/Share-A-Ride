package com.company1.coms510.ride2isu;


import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.ParseException;
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
public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {


    private String LOG_CAT=TimePickerFragment.class.getSimpleName();

    /*public TimePickerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_time_picker, container, false);
    }
*/
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Do something with the time chosen by the user
        Log.d(LOG_CAT,"Time : " + hourOfDay + " hrs " + minute);

        Date oldDate;
        TextView rideTime = (TextView) getActivity().findViewById(R.id.time_text_id);
        String dateString=rideTime.getText().toString();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm - EEE MMM d yyyy",new Locale("en_US"));
        try {
            oldDate=formatter.parse(dateString);
            Log.d(LOG_CAT,"Old date >> " + oldDate);
            Calendar cal = Calendar.getInstance();
            cal.setTime(oldDate);
            //cal.set(Calendar.HOUR, (hourOfDay<12?hourOfDay:hourOfDay-12));
            cal.set(Calendar.HOUR_OF_DAY,hourOfDay);
            cal.set(Calendar.MINUTE, minute);
            //cal.set(Calendar.AM_PM,hourOfDay<12?0:1);

            Date newDate = cal.getTime();
            Log.d(LOG_CAT, "Date >> " + newDate);
            StringBuilder dateBuilder= new StringBuilder(formatter.format(newDate).toString());
            Log.d(LOG_CAT,"Date : " + dateBuilder.toString());
            rideTime.setText(dateBuilder.toString());
            if(new Date().after(newDate)) {
                rideTime.requestFocus();
                rideTime.setError("Enter a future Date Time");
                rideTime.requestFocus();
            }
            else
                rideTime.setError(null);
        } catch (ParseException e) {
            e.printStackTrace();
            Log.e(LOG_CAT,"Error while parsing date");
        }
    }

}
