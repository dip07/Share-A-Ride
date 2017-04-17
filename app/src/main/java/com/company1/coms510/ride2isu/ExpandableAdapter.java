package com.company1.coms510.ride2isu;

import android.app.Activity;
import android.app.admin.SystemUpdatePolicy;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Map;
import com.company1.coms510.ride2isu.LoginActivity;

/**
 * Created by lakshay on 10/30/16.
 */

public class ExpandableAdapter extends BaseExpandableListAdapter {


    Context context;
    List<String> headTag;
    Map<String, List<String>> bodyListing;

    public ExpandableAdapter(Context context, List<String> headTag, Map<String, List<String>> bodyListing) {
        this.context = context;
        this.headTag = headTag;
        this.bodyListing = bodyListing;
    }

    @Override
    public int getGroupCount() {
        return headTag.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return bodyListing.get(headTag.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return headTag.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return bodyListing.get(headTag.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String lang =  (String) getGroup(groupPosition);
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_list_item, null);
        }

        TextView txtParent = (TextView) convertView.findViewById(R.id.txtParent);
        txtParent.setText(lang);
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        String topic =  (String) getChild(groupPosition, childPosition);
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_child, null);
        }

        //Fetch the list of all the elements -
        TextView riderName = (TextView) convertView.findViewById(R.id.riderNameVar);
        TextView seatsRequested = (TextView) convertView.findViewById(R.id.seatsRequestedVar);
        TextView msgToDriver = (TextView) convertView.findViewById(R.id.messageVar);
        TextView timeRequested = (TextView) convertView.findViewById(R.id.requestedTimeVar);
        TextView srcAddress = (TextView) convertView.findViewById(R.id.srcAddVar);
        TextView destAddress = (TextView) convertView.findViewById(R.id.destVar);
        final TextView acceptTextView = (TextView) convertView.findViewById(R.id.acceptTextView);
        final TextView rejectTextView = (TextView) convertView.findViewById(R.id.rejectTextView);


        //final LoginActivity loginObj = new LoginActivity();
        //System.out.println("L is : " + lu.l);
        String rName = "", sReq = "", mtoDriver = "", tReq = "", srcAdd = "", destAdd = "";
        switch(groupPosition){
            case 0:rName = "John Kelly"; sReq = "3"; mtoDriver = "Hi, I am John. I need a ride from North Dakota to South Duff Avenue."; tReq = "10/31/2016 6:15PM"; srcAdd = "North Dakota Avenue"; destAdd = "South Duff Avenue";break;
            case 1:rName = "Pallavi Rao"; sReq = "4"; mtoDriver = "Hi, I am Pallavi. I need a ride for 4 people. Can you help with it?"; tReq = "11/03/2016 2:35PM"; srcAdd = "Hill Crest Avenue"; destAdd = "Des Moines";break;
            case 2:rName = "Jenny Jajoe"; sReq = "1"; mtoDriver = "Hi, I am Jenny. Do you have space left for 1 person in your offered ride?"; tReq = "11/03/2016 8:00AM"; srcAdd = "River Birch"; destAdd = "North Grand Mall";break;
            case 3:rName = "Pavithra Rajarathinam"; sReq = "3"; mtoDriver = "Hi, I am Pavithra. I need a ride from Hickory to Sweeny Hall."; tReq = "11/01/2016 6:15PM"; srcAdd = "Hickory"; destAdd = "Sweeny Hall";break;
        }






        final int seatsReq = Integer.parseInt(sReq);

        riderName.setText(rName);
        seatsRequested.setText(sReq);
        msgToDriver.setText(mtoDriver);
        timeRequested.setText(tReq);
        srcAddress.setText(srcAdd);
        destAddress.setText(destAdd);

        // test
        //long position = 0;
        //position = getChildId(groupPosition, childPosition);
        System.out.print(getChild(groupPosition,childPosition));
        final Button acceptBtn = (Button) convertView.findViewById(R.id.acceptBtn);
        final Button rejectBtn = (Button) convertView.findViewById(R.id.rejectBtn);




        acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(LoginActivity.tempCarCapacity[groupPosition] < seatsReq){
                    Toast.makeText(context,"Cannot Accept! Car capacity is less than the seats requested!!",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(context,"You just accepted this ride!",Toast.LENGTH_SHORT).show();
                    updateNumberofSeatsAvailable();
                    LoginActivity.tempAcceptField[groupPosition] = 0;
                    LoginActivity.tempRejectField[groupPosition] = 1;
                    acceptTextView.setVisibility(View.VISIBLE);
                    rejectTextView.setVisibility(View.GONE);
                    acceptBtn.setVisibility(View.GONE);
                    rejectBtn.setVisibility(View.GONE);
                }
            }
        });
        //System.out.println("loginObj.tempRejectField[groupPosition] " + groupPosition + " -- -- - " + LoginActivity.tempAcceptField[groupPosition] + " === " + LoginActivity.tempRejectField[groupPosition]);
        /*if(LoginActivity.tempRejectField[groupPosition] == 0){
            acceptBtn.setVisibility(View.GONE);
            rejectBtn.setVisibility(View.GONE);
            rejectTextView.setVisibility(View.VISIBLE);
            acceptTextView.setVisibility(View.GONE);
        }*/
        rejectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"The requested ride has been rejected.",Toast.LENGTH_SHORT).show();
                LoginActivity.tempRejectField[groupPosition] = 0;
                LoginActivity.tempAcceptField[groupPosition] = 1;
                acceptTextView.setVisibility(View.GONE);
                rejectTextView.setVisibility(View.VISIBLE);
                acceptBtn.setVisibility(View.GONE);
                rejectBtn.setVisibility(View.GONE);
            }
        });

        if(LoginActivity.tempAcceptField[groupPosition] == 0 || LoginActivity.tempRejectField[groupPosition] == 0){
            acceptBtn.setVisibility(View.GONE);
            rejectBtn.setVisibility(View.GONE);
        }
        else{
            acceptBtn.setVisibility(View.VISIBLE);
            rejectBtn.setVisibility(View.VISIBLE);
            rejectTextView.setVisibility(View.GONE);
            acceptTextView.setVisibility(View.GONE);
        }

        if(LoginActivity.tempAcceptField[groupPosition] == 0){
            rejectTextView.setVisibility(View.GONE);
            acceptTextView.setVisibility(View.VISIBLE);

        }
        if(LoginActivity.tempRejectField[groupPosition] == 0){
            rejectTextView.setVisibility(View.VISIBLE);
            acceptTextView.setVisibility(View.GONE);
        }
        return convertView;
    }

    public void updateNumberofSeatsAvailable(){
        // number of
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
