package com.example.cs2340a_team19;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.example.cs2340a_team19.ui.login.LoginActivity;
@RunWith(AndroidJUnit4.class)
public class SplashActivityTest {
    @Test
    public void testSplashActivity() {
        ActivityScenario<SplashActivity> activityScenario = ActivityScenario.launch(SplashActivity.class);
        activityScenario.onActivity(activity -> {
            assertNotNull(activity);
        });
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> {
            activityScenario.onActivity(activity -> {
                Intent nextActivityIntent = activity.getIntent();
                assertNotNull(nextActivityIntent);
                assertTrue(nextActivityIntent.getComponent().getClassName().equals(LoginActivity.class.getName()));
            });
            activityScenario.close();
        }, 2500);
    }
}
