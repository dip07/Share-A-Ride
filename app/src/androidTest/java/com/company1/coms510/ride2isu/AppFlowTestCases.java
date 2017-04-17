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
 * Created by Viswanath on 11/4/2016.
 */

@RunWith(AndroidJUnit4.class)
public class AppFlowTestCases {

    private UiDevice device;

    @Rule
    public ActivityTestRule<LoginActivity> rActivityRule = new ActivityTestRule<>(LoginActivity.class);

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
    public void login() throws Exception{
        UiObject2 object = device.findObject(By.desc("Enter your email to login"));
        object.setText("company1@iastate.edu");
        object = device.findObject(By.desc("Enter your password to login"));
        object.setText("hello");
        object = device.findObject(By.desc("Log me into the app"));
        object.click();
        device.wait(Until.hasObject(By.text("View Profile")), 3000);
        UiObject2 viewProfile = device.findObject(By.text("View Profile"));
        assertTrue(viewProfile!=null);
    }

    @Test
    public void loginwithErrors() throws Exception{
        UiObject2 object = device.findObject(By.desc("Enter your email to login"));
        object.setText("company2@iastate.edu");
        object = device.findObject(By.desc("Enter your password to login"));
        object.setText("hello");
        object = device.findObject(By.desc("Log me into the app"));
        object.click();
        device.wait(Until.hasObject(By.text("View Profile")), 3000);
        UiObject2 viewProfile = device.findObject(By.text("View Profile"));
        assertTrue(viewProfile==null);
    }


    @Test
    public void loginwithErrors2() throws Exception{
        UiObject2 object = device.findObject(By.desc("Enter your email to login"));
        object.setText("company2iastateedu");
        object = device.findObject(By.desc("Enter your password to login"));
        object.setText("hello");
        object = device.findObject(By.desc("Log me into the app"));
        object.click();
        device.wait(Until.hasObject(By.text("View Profile")), 3000);
        UiObject2 viewProfile = device.findObject(By.text("View Profile"));
        assertTrue(viewProfile==null);
    }

    @Test
    public void loginwithErrors3() throws Exception{
        UiObject2 object = device.findObject(By.desc("Enter your email to login"));
        object.setText("company2@iastate.edu");
        object = device.findObject(By.desc("Enter your password to login"));
        object.setText("h");
        object = device.findObject(By.desc("Log me into the app"));
        object.click();
        device.wait(Until.hasObject(By.text("View Profile")), 3000);
        UiObject2 viewProfile = device.findObject(By.text("View Profile"));
        assertTrue(viewProfile==null);
    }


    @Test
    public void loginwithErrors4() throws Exception{
        UiObject2 object = device.findObject(By.desc("Enter your email to login"));
        object.setText("company2@iastate.edu");
        object = device.findObject(By.desc("Enter your password to login"));
        object.setText("");
        object = device.findObject(By.desc("Log me into the app"));
        object.click();
        device.wait(Until.hasObject(By.text("View Profile")), 3000);
        UiObject2 viewProfile = device.findObject(By.text("View Profile"));
        assertTrue(viewProfile==null);
    }
}
