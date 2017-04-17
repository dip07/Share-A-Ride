package com.company1.coms510.ride2isu;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.Until;
import android.test.InstrumentationTestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by Viswanath on 10/27/2016.
 */

@RunWith(AndroidJUnit4.class)
public class MainScreenTestCases extends InstrumentationTestCase {

    private UiDevice device;


    @Override
    @Before
    public void setUp() throws Exception {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        device.pressHome();
        device.wait(Until.hasObject(By.desc("Apps")), 3000);
        UiObject2 appsButton = device.findObject(By.desc("Apps"));
        appsButton.click();

    }

    @Test
    public void mainScreen() throws Exception{
        device.wait(Until.hasObject(By.text("Ride2ISU")), 3000);
        UiObject2 App = device.findObject(By.text("Ride2ISU"));
        App.click();
        assertTrue(App != null);
    }

}
