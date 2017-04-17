package com.company1.coms510.ride2isu;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by lakshay on 10/27/16.
 */
public class ListAdapter{

}
/*public class ListAdapter extends ArrayAdapter<String>{

    private final Activity context;
    private final String[] progNames;
    private final Integer[] progImages;

    public ListAdapter(Activity context, String[] progNames, Integer[] progImages){
        super(context, R.layout.activity_list_item, progNames);
        this.context = context;
        this.progNames = progNames;
        this.progImages = progImages;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.activity_list_item, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.textView1);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView1);

        txtTitle.setText(progNames[position]);
        imageView.setImageResource(progImages[position]);
        return rowView;
    }



}*/
