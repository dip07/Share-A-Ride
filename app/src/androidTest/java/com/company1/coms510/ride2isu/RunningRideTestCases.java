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
 * Created by Viswanath on 11/2/2016.
 */

@RunWith(AndroidJUnit4.class)
public class RunningRideTestCases {

    @Rule
    public ActivityTestRule<RideRunning> rActivityRule = new ActivityTestRule<>(RideRunning.class);
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
    public void RidersList() throws Exception{
        UiObject2 ridersList = device.findObject(By.text("Riders List"));
        assertTrue(ridersList!=null);
    }

    @Test
    public void call911Button() throws Exception{
        UiObject2 call911Button = device.findObject(By.text("Emergency - Call 911"));
        assertTrue(call911Button!=null);
    }

    @Test
    public void call911ButtonClick() throws Exception{
        UiObject2 call911Button = device.findObject(By.text("Emergency - Call 911"));
        assertTrue(call911Button.isClickable());
    }

    @Test
    public void endRide() throws Exception{
        UiObject2 endRide = device.findObject(By.text("end ride"));
        assertTrue(endRide!=null);
    }

    @Test
    public void endRidegoestoMainActivity(){
        UiObject2 endRide = device.findObject(By.text("end ride"));
        endRide.click();
        device.wait(Until.hasObject(By.text("end ride")), 3000);
        UiObject2  viewProfile = device.findObject(By.text("View Profile"));
        assertTrue(viewProfile!=null);
    }

}
