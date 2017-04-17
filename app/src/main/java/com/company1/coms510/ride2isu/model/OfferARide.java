package com.company1.coms510.ride2isu.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by dipanjankarmakar on 10/14/16.
 */

public class OfferARide implements Serializable {

    private String rideSource;
    private String rideDest;
    private String rideTime;
    private String countPerson;
    private String userId;

    public String getRideSource() {
        return rideSource;
    }

    public String getRideDest() {
        return rideDest;
    }

    public String getRideTime() {
        return rideTime;
    }

    public String getCountPerson() {
        return countPerson;
    }

    public void setRideSource(String rideSource) {
        this.rideSource = rideSource;
    }

    public void setRideDest(String rideDest) {
        this.rideDest = rideDest;
    }

    public void setRideTime(String rideTime) {
        this.rideTime = rideTime;
    }

    public void setCountPerson(String countPerson) {
        this.countPerson = countPerson;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "OfferARide{" +
                "rideSource='" + rideSource + '\'' +
                ", rideDest='" + rideDest + '\'' +
                ", rideTime='" + rideTime + '\'' +
                ", countPerson='" + countPerson + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }

    public String rideString(){
        String returnString="UserId " + getUserId() + ": Ride offered from " + rideSource +
                " to " + rideDest +
                " at " + rideTime + '\'' +
                " for " + countPerson + " persons.";
        return returnString;
    }

    public String toJSON(){

        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("rideSource", rideSource);
            jsonObject.put("rideDest", rideDest);
            jsonObject.put("rideTime", rideTime);
            jsonObject.put("countPerson",countPerson);
            jsonObject.put("userId",userId);

            return jsonObject.toString();
        } catch (JSONException e)
        {
            e.printStackTrace();
            return "";
        }

    }

}
