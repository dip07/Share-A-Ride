package com.company1.coms510.ride2isu.model;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by dipanjan karmakar
 */

public class RidesOffered implements Serializable
{

    private String driver_name;
    private String source_address;
    private String dest_address;
    private LatLng source_latLng;
    private LatLng dest_latLng;
    private Date date_time_of_ride;
    private Integer offered_seats;
    private Integer available_seats;
    private Date creation_date_time;
    private ArrayList<String> riders_name;

    public String getDriver_name() {
        return driver_name;
    }

    public void setDriver_name(String driver_name) {
        this.driver_name = driver_name;
    }

    public String getSource_address() {
        return source_address;
    }

    public void setSource_address(String source_address) {
        this.source_address = source_address;
    }

    public String getDest_address() {
        return dest_address;
    }

    public void setDest_address(String dest_address) {
        this.dest_address = dest_address;
    }

    public LatLng getSource_latLng() {
        return source_latLng;
    }

    public void setSource_latLng(LatLng source_latLng) {
        this.source_latLng = source_latLng;
    }

    public LatLng getDest_latLng() {
        return dest_latLng;
    }

    public void setDest_latLng(LatLng dest_latLng) {
        this.dest_latLng = dest_latLng;
    }

    public Date getDate_time_of_ride() {
        return date_time_of_ride;
    }

    public void setDate_time_of_ride(Date date_time_of_ride) {
        this.date_time_of_ride = date_time_of_ride;
    }

    public Integer getOffered_seats() {
        return offered_seats;
    }

    public void setOffered_seats(Integer offered_seats) {
        this.offered_seats = offered_seats;
    }

    public Integer getAvailable_seats() {
        return available_seats;
    }

    public void setAvailable_seats(Integer available_seats) {
        this.available_seats = available_seats;
    }

    public Date getCreation_date_time() {
        return creation_date_time;
    }

    public void setCreation_date_time(Date creation_date_time) {
        this.creation_date_time = creation_date_time;
    }

    public ArrayList<String> getRiders_name() {
        return riders_name;
    }

    public void setRiders_name(ArrayList<String> riders_name) {
        this.riders_name = riders_name;
    }

    @Override
    public String toString() {
        return "RidesOffered{" +
                "driver_name='" + driver_name + '\'' +
                ", source_address='" + source_address + '\'' +
                ", dest_address='" + dest_address + '\'' +
                ", source_latLng=" + source_latLng +
                ", dest_latLng=" + dest_latLng +
                ", date_time_of_ride=" + date_time_of_ride +
                ", offered_seats=" + offered_seats +
                ", available_seats=" + available_seats +
                ", creation_date_time=" + creation_date_time +
                ", riders_name=" + riders_name +
                '}';
    }

    public String toJSON(){

        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("driver_name", driver_name);
            jsonObject.put("source_address", source_address);
            jsonObject.put("dest_address", dest_address);
            jsonObject.put("source_latLng", source_latLng);
            jsonObject.put("dest_latLng", dest_latLng);
            jsonObject.put("date_time_of_ride",date_time_of_ride);
            jsonObject.put("offered_seats",offered_seats);
            jsonObject.put("available_seats",available_seats);
            jsonObject.put("creation_date_time",creation_date_time);
            jsonObject.put("riders_name",riders_name);

            return jsonObject.toString();

        }
        catch (JSONException e)
        {
            e.printStackTrace();
            return "";
        }

    }
}
