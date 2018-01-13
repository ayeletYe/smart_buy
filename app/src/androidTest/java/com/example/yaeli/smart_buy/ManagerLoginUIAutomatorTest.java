package com.example.yaeli.smart_buy;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;

import org.junit.Before;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class ManagerLoginUIAutomatorTest {
    private UiDevice mDevice;

    @Before
    public void before() {
        // Initialize UiDevice instance
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        assertThat(mDevice, notNullValue());

        // Start from the home screen
        mDevice.pressHome();

    }

    @org.junit.Test
    public void test() throws InterruptedException {
        /* Open application */
        openApp("com.example.yaeli.smart_buy");

        /* Click on the "LOG IN" */
        UiObject2 loginButton = waitForObject(By.text("LOG IN"));
        loginButton.click();
        /* Now we should be in log-in activity */

        /* Enter email and password */
        UiObject2 emailText = waitForObject(By.res("com.example.yaeli.smart_buy:id/Email"));
        emailText.setText("manager@unit-test.com");

        UiObject2 passwordText = waitForObject(By.res("com.example.yaeli.smart_buy:id/pass"));
        passwordText.setText("manager1234");

        /* Click on the "LOGIN" */
        UiObject2 login2Button = waitForObject(By.text("LOGIN"));
        login2Button.click();
        /* Now we should be in log-in activity */

        /* Make sure we can find "Hello Manager" */
        waitForObject(By.textStartsWith("Hello Manager"));

        Thread.sleep(3000);
    }

    private void openApp(String packageName) {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    private UiObject2 waitForObject(BySelector selector) throws InterruptedException {
        UiObject2 object = null;
        int timeout = 10000;
        int delay = 200;
        long time = System.currentTimeMillis();
        while (object == null) {
            object = mDevice.findObject(selector);
            Thread.sleep(delay);
            if (System.currentTimeMillis() - timeout > time) {
                fail();
            }
        }
        return object;
    }

}
