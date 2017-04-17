package com.company1.coms510.ride2isu;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.Until;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static junit.framework.Assert.assertTrue;

/**
 * Created by Dipanjan on 11/16/16.
 */

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OfferARideTestCases {

    private UiDevice device;
    @Rule
    public ActivityTestRule<OfferARideActivity> rActivityRule = new ActivityTestRule<>(OfferARideActivity.class);

    @Before
    public void setUp() throws Exception{
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
    }

    @Test
    public void aTestSourcePresent() throws Exception{
        UiObject2 object = device.findObject(By.desc("Get Source Address"));
        object.setText("");
        object = device.findObject(By.text("Submit Ride Details"));
        object.click();
        device.wait(Until.hasObject(By.text("DisplayScreenActivity")), 3000);
        UiObject2 ridesOfferred = device.findObject(By.text("DisplayScreenActivity"));
        assertTrue(ridesOfferred==null);
    }
    @Test
    public void bTestDestinationPresent() throws Exception{
        UiObject2 object = device.findObject(By.desc("Get Source Address"));
        object.setText("Vegas");
        device.waitForIdle(3000);
        object = device.findObject(By.desc("Get Destination Address"));
        object.setText("");
        object = device.findObject(By.text("Submit Ride Details"));
        object.click();
        device.wait(Until.hasObject(By.text("DisplayScreenActivity")), 3000);
        UiObject2 ridesOfferred = device.findObject(By.text("DisplayScreenActivity"));
        assertTrue(ridesOfferred==null);
    }

    @Test
    public void cTestValidDateTime() throws Exception{
        UiObject2 object = device.findObject(By.desc("Get Source Address"));
        object.setText("Vegas");
        device.waitForIdle(3000);
        object = device.findObject(By.desc("Get Destination Address"));
        object.setText("Miami");
        object = device.findObject(By.text("Submit Ride Details"));
        object.click();
        device.wait(Until.hasObject(By.text("DisplayScreenActivity")), 3000);
        UiObject2 ridesOfferred = device.findObject(By.text("DisplayScreenActivity"));
        assertTrue(ridesOfferred==null);
    }


    @Test
    public void dTestDateButton() throws Exception{
        UiObject2 object = device.findObject(By.desc("Get Source Address"));
        object.setText("Vegas");
        device.waitForIdle(3000);
        object = device.findObject(By.desc("Get Destination Address"));
        object.setText("Miami");
        object = device.findObject(By.desc("Get Date Dialogue"));
        object.click();
        device.wait(Until.hasObject(By.text("com.android.internal.widget.ViewPager")), 3000);
        UiObject2 dateDiaObj = device.findObject(By.text("com.android.internal.widget.ViewPager"));
        assertTrue(dateDiaObj==null);
    }
    @Test
    public void eTestTimeButton() throws Exception{
        UiObject2 object = device.findObject(By.desc("Get Source Address"));
        object.setText("Vegas");
        device.waitForIdle(3000);
        object = device.findObject(By.desc("Get Destination Address"));
        object.setText("Miami");
        object = device.findObject(By.desc("Get Time Dialogue"));
        object.click();
        device.wait(Until.hasObject(By.res("com.company1.coms510.ride2isu","android:id/radial_picker")), 3000);
        UiObject2 dateDiaObj = device.findObject(By.res("com.company1.coms510.ride2isu","android:id/radial_picker"));
        assertTrue(dateDiaObj==null);
    }
    @Test
    public void fTestNumPerson() throws Exception{
        UiObject2 object = device.findObject(By.desc("Get Source Address"));
        object.setText("Vegas");
        device.waitForIdle(3000);
        object = device.findObject(By.desc("Get Destination Address"));
        object.setText("Miami");
        object = device.findObject(By.desc("Get DateTime of Ride"));
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, 1); //minus number would decrement the days
        Date dateToSet=cal.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm - EEE MMM d yyyy",new Locale("en_US"));
        object.setText(formatter.format(dateToSet).toString());
        object = device.findObject(By.text("Submit Ride Details"));
        object.click();
        device.wait(Until.hasObject(By.text("DisplayScreenActivity")), 3000);
        UiObject2 ridesOfferred = device.findObject(By.text("DisplayScreenActivity"));
        assertTrue(ridesOfferred==null);
    }

    @Test
    public void gTestMapsOpenforSource() throws Exception{
        UiObject2 object = device.findObject(By.desc("Get Source Address"));
        object.click();
        object.click();
        device.wait(Until.hasObject(By.pkg("com.google.android.gms")), 6000);
        UiObject2 mapsObject = device.findObject(By.pkg("com.google.android.gms"));
        device.pressBack();
        //device.pressBack();
        assertTrue(mapsObject!=null);
    }

    @Test
    public void hTestMapsOpenforDest() throws Exception{
        UiObject2 object = device.findObject(By.desc("Get Source Address"));
        object.setText("Memorial Union");
        object = device.findObject(By.desc("Get Destination Address"));
        object.click();
        object.click();
        device.wait(Until.hasObject(By.pkg("com.google.android.gms")), 6000);
        UiObject2 mapsObject = device.findObject(By.pkg("com.google.android.gms"));
        device.pressBack();
        //device.pressBack();
        assertTrue(mapsObject!=null);
    }

    @Test
    public void iAllDataValid() throws Exception{
        UiObject2 object = device.findObject(By.desc("Get Source Address"));
        object.setText("Vegas");
        device.waitForIdle(3000);
        object = device.findObject(By.desc("Get Destination Address"));
        object.setText("Miami");
        object = device.findObject(By.desc("Get DateTime of Ride"));
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, 1); //minus number would decrement the days
        Date dateToSet=cal.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm - EEE MMM d yyyy",new Locale("en_US"));
        object.setText(formatter.format(dateToSet).toString());
        object = device.findObject(By.desc("Get no of riders"));
        object.setText("1");
        object = device.findObject(By.text("Submit Ride Details"));
        object.click();
        device.wait(Until.hasObject(By.text("DisplayScreenActivity")), 3000);
        UiObject2 ridesOfferred = device.findObject(By.text("DisplayScreenActivity"));
        assertTrue(ridesOfferred!=null);
    }
}
