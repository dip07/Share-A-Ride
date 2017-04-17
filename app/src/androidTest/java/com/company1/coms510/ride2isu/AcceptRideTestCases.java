package com.company1.coms510.ride2isu;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.Until;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * Created by lakshay on 11/15/16.
 */

@RunWith(AndroidJUnit4.class)
public class AcceptRideTestCases {


    @Rule
    public ActivityTestRule<AcceptRide> rActivityRule = new ActivityTestRule<>(AcceptRide.class);
    private UiDevice device;

    @Before
    public void setUp() throws Exception{
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
    }

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = getTargetContext();
        assertEquals("com.company1.coms510.ride2isu", appContext.getPackageName());
    }

    @Test
    public void acceptARide(){
        device.wait(Until.hasObject(By.text("John asked for a ride. Tap to expand")), 2000);
        UiObject2 firstElement = device.findObject(By.text("John asked for a ride. Tap to expand"));
        firstElement.click();



        LoginActivity.tempAcceptField[0] = 1;
        LoginActivity.tempAcceptField[1] = 1;
        LoginActivity.tempAcceptField[2] = 1;
        LoginActivity.tempAcceptField[3] = 1;
        LoginActivity.tempRejectField[0] = 1;
        LoginActivity.tempRejectField[1] = 1;
        LoginActivity.tempRejectField[2] = 1;
        LoginActivity.tempRejectField[3] = 1;
        LoginActivity.tempCarCapacity[0] = 9;
        LoginActivity.tempCarCapacity[1] = 2;
        LoginActivity.tempCarCapacity[2] = 4;
        LoginActivity.tempCarCapacity[3] = 1;

        device.wait(Until.hasObject(By.text("Accept")), 1000);
        UiObject2 acceptRideBtn = device.findObject(By.text("Accept"));
        acceptRideBtn.click();
        device.wait(Until.hasObject(By.text("You have accepted this ride!")), 1000);

        UiObject2 acceptedRideText = device.findObject(By.text("You have accepted this ride!"));
        assertTrue(acceptedRideText!=null);
    }

    @Test
    public void rejectARide(){

        device.wait(Until.hasObject(By.text("John asked for a ride. Tap to expand")), 2000);
        UiObject2 firstElement = device.findObject(By.text("John asked for a ride. Tap to expand"));
        firstElement.click();



        LoginActivity.tempAcceptField[0] = 1;
        LoginActivity.tempAcceptField[1] = 1;
        LoginActivity.tempAcceptField[2] = 1;
        LoginActivity.tempAcceptField[3] = 1;
        LoginActivity.tempRejectField[0] = 1;
        LoginActivity.tempRejectField[1] = 1;
        LoginActivity.tempRejectField[2] = 1;
        LoginActivity.tempRejectField[3] = 1;
        LoginActivity.tempCarCapacity[0] = 9;
        LoginActivity.tempCarCapacity[1] = 2;
        LoginActivity.tempCarCapacity[2] = 4;
        LoginActivity.tempCarCapacity[3] = 1;

        device.wait(Until.hasObject(By.text("Reject")), 1000);
        UiObject2 acceptRideBtn = device.findObject(By.text("Reject"));
        acceptRideBtn.click();
        device.wait(Until.hasObject(By.text("You have rejected this ride!")), 1000);

        UiObject2 acceptedRideText = device.findObject(By.text("You have rejected this ride!"));
        assertTrue(acceptedRideText!=null);
    }
}
