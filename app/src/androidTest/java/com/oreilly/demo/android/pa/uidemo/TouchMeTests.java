package com.oreilly.demo.android.pa.uidemo;

import android.test.ActivityInstrumentationTestCase2;

import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import android.content.pm.ActivityInfo;
import android.app.KeyguardManager;
import android.content.Context;

/**
 * Created by sonia_lin on 5/1/16.
 */
public class TouchMeTests extends ActivityInstrumentationTestCase2<TouchMe> {
    public TouchMeTests() {
        super(TouchMe.class);
    }

    // The application is launched successfully
    public void testLaunchSuccessful() {
        TouchMe activity = getActivity();
        assertNotNull("activity should be launched successfully", activity);
    }

    // The screen is locked and will not change orientation when the device is rotated
    public void testOrientationLockAtRotation( ) {
        KeyguardManager km = (KeyguardManager) getActivity().getSystemService(getActivity().KEYGUARD_SERVICE);
        boolean locked = km.inKeyguardRestrictedInputMode();
        assertTrue(locked);
    }

}
