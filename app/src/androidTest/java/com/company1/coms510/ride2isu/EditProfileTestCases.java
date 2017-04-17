package com.company1.coms510.ride2isu;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertTrue;

/**
 * Created by Viswanath on 11/4/2016.
 */
@RunWith(AndroidJUnit4.class)
public class EditProfileTestCases {

    @Rule
    public ActivityTestRule<EditProfile> rActivityRule = new ActivityTestRule<>(EditProfile.class);
    private UiDevice device;

    @Before
    public void setUp() throws Exception{
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
    }

    @Test
    public void editProfile() throws Exception{
        UiObject2 editProfileTextView = device.findObject(By.text("Edit Profile"));
        assertTrue(editProfileTextView!=null);
    }

    @Test
    public void editPicture() throws Exception{
        UiObject2 editPicture = device.findObject(By.text("Edit Picture"));
        assertTrue(editPicture!=null);
    }

    @Test
    public void clickEditPicture() throws Exception{
        UiObject2 editPicture = device.findObject(By.text("Edit Picture"));
        editPicture.click();
        assertTrue(editPicture.isClickable());

    }

    @Test
    public void TakePicture() throws Exception{
        UiObject2 editPicture = device.findObject(By.text("Take Picture"));
        assertTrue(editPicture!=null);
    }

    @Test
    public void clickTakePicture() throws Exception{
        UiObject2 takePicture = device.findObject(By.text("Take Picture"));
        takePicture.click();
        assertTrue(takePicture.isClickable());
    }
    @Test
    public void removePicture() throws Exception{
        UiObject2 removePicture = device.findObject(By.desc("Remove the display Picture"));
        assertTrue(removePicture!=null);
    }

    @Test
    public void clickRemovePicture() throws Exception{
        UiObject2 removePicture = device.findObject(By.desc("Remove the display Picture"));
        assertTrue(removePicture.isClickable());
        removePicture.click();
    }

    @Test
    public void emailDisplayed()throws Exception{
        UiObject2 email = device.findObject(By.desc("Email of the user"));
        assertTrue(email!=null);
    }

    @Test
    public void usernameDisplayed()throws Exception{
        UiObject2 username = device.findObject(By.text("Username"));
        assertTrue(username!=null);
    }

    @Test
    public void passwordDisplayed()throws Exception{
        UiObject2 object = device.findObject(By.desc("Previous password"));
        assertTrue(object!=null);
    }
    @Test
    public void newPasswordDisplayed()throws Exception{
        UiObject2 object = device.findObject(By.desc("New Password to be entered"));
        assertTrue(object!=null);
    }
    @Test
    public void confirmPasswordDisplayed()throws Exception{
        UiObject2 object = device.findObject(By.text("Confirm Password"));
        assertTrue(object!=null);
    }

    @Test
    public void submitButton()throws Exception{
        UiObject2 object = device.findObject(By.desc("Click here to save changes"));
        assertTrue(object!=null);
    }

    @Test
    public void clickSubmitButton()throws Exception{
        UiObject2 object = device.findObject(By.desc("Click here to save changes"));
        assertTrue(object.isClickable());
    }

    @Test
    public void enterPassword()throws Exception{
        UiObject2 object = device.findObject(By.desc("Enter your previous password here"));
        assertTrue(object!=null);
    }
    @Test
    public void newPassword()throws Exception{
        UiObject2 object = device.findObject(By.desc("Enter your new password here"));
        assertTrue(object!=null);
    }
    @Test
    public void confirmPassword()throws Exception{
        UiObject2 object = device.findObject(By.desc("Confirm your new password here"));
        assertTrue(object!=null);
    }
    @Test
    public void processFlow()throws Exception{
        UiObject2 object = device.findObject(By.desc("Enter your previous password here"));
        object.setText("something");
        object = device.findObject(By.desc("Enter your new password here"));
        object.setText("somethingElse");
        object = device.findObject(By.desc("Confirm your new password here"));
        object.setText("somethingElse");
        object = device.findObject(By.desc("Click here to save changes"));
        assertTrue(object!=null);
    }

    @Test
    public void saveAndReturn() throws Exception{
        UiObject2 object = device.findObject(By.text("Save and Return"));
        assertTrue(object!=null);
    }

    @Test
    public void saveAndReturnClick() throws Exception{
        UiObject2 object = device.findObject(By.text("Save and Return"));
        assertTrue(object.isClickable());
    }

    @Test
    public void saveAndReturnClickReturn() throws Exception{
        UiObject2 object = device.findObject(By.text("Save and Return"));
        object.click();
        UiObject2  viewProfile = device.findObject(By.text("View Profile"));
        assertTrue(viewProfile!=null);
    }
}
