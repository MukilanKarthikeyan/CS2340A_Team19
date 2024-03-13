package com.example.cs2340a_team19;

import static org.junit.Assert.assertEquals;

import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.cs2340a_team19.models.DatabaseHandler;
import com.example.cs2340a_team19.models.Meal;
import com.example.cs2340a_team19.models.Profile;
import com.example.cs2340a_team19.ui.personalInfo.PersonalInformationViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@RunWith(AndroidJUnit4.class)
public class DatabaseLogicTest {
    @Test
    public void databaseInitializes() {
        DatabaseHandler dbHandler = DatabaseHandler.getInstance();
        assert(dbHandler.isSuccessfullyInitialized());
    }

    @Test
    public void createProfile() {
        DatabaseHandler dbHandler = DatabaseHandler.getInstance();
        assert(dbHandler.isSuccessfullyInitialized());
        CountDownLatch lock = new CountDownLatch(1);
        assert(dbHandler.getProfileHandler().createProfile("0", 100, 100, true));

        try {
            lock.await(2000, TimeUnit.MILLISECONDS);
        } catch(InterruptedException ie) {
            Log.d("MyJUNIT", "lock was interrupted in profile read test");
        }
    }

    @Test
    public void readProfile() {
        DatabaseHandler dbHandler = DatabaseHandler.getInstance();
        assert(dbHandler.isSuccessfullyInitialized());
        CountDownLatch lock = new CountDownLatch(1);

        dbHandler.getProfileHandler().listenToProfile("0", new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                Log.d("MyJUNIT", "Test: ");
                assert(dataSnapshot.exists());
                Profile value = dataSnapshot.getValue(Profile.class);
                assert(value != null);
                assert(value.getHeight() == 100);

                lock.countDown();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.d("MyJUNIT", "Read Profile Cancelled Request " + error.getMessage());
                assert(false);
            }
        });

        try {
            lock.await(2000, TimeUnit.MILLISECONDS);
        } catch(InterruptedException ie) {
//            Log.d("MyJUNIT", "lock was interrupted in profile read test");
        }
//        Log.d("MyJUNIT", "Finished Profile Read Test: " + dbHandler.isSuccessfullyInitialized());
    }


    @Test
    public void createMeal() {
        DatabaseHandler dbHandler = DatabaseHandler.getInstance();
        assert(dbHandler.isSuccessfullyInitialized());

        CountDownLatch lock = new CountDownLatch(1);
        String key = dbHandler.getMealHandler().createMeal("Spaighetti", 42);
        Log.d("MyJUNIT", "Meal Key: " + key);
        assert(key != null);

        try {
            lock.await(2000, TimeUnit.MILLISECONDS);
        } catch(InterruptedException ie) {
            Log.d("MyJUNIT", "lock was interrupted in profile read test");
        }
    }

    @Test
    public void readMeal() {
        DatabaseHandler dbHandler = DatabaseHandler.getInstance();
        assert(dbHandler.isSuccessfullyInitialized());

        CountDownLatch lock = new CountDownLatch(1);

        dbHandler.getMealHandler().listenToMeal("-NspSkX0JX2DgIvqtITf", new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                assert(dataSnapshot.exists());
                Meal value = dataSnapshot.getValue(Meal.class);
                assert(value != null);
                assert(value.getCalories() == 42);
//                lock.countDown();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.d("MyJUNIT", "Read Meal Cancelled Request  " + error.getMessage());
                assert(false);
            }
        });

        try {
            lock.await(2000, TimeUnit.MILLISECONDS);
        } catch(InterruptedException ie) {
            Log.d("MyJUNIT", "lock was interrupted in Meal read test");
        }
    }

    @Test
    public void addMeal() {
        DatabaseHandler dbHandler = DatabaseHandler.getInstance();
        assert(dbHandler.isSuccessfullyInitialized());

        CountDownLatch lock = new CountDownLatch(1);

        dbHandler.getProfileHandler().addMeal("0", "-NspSkX0JX2DgIvqtITf", 1);

        try {
            lock.await(2000, TimeUnit.MILLISECONDS);
        } catch(InterruptedException ie) {
            Log.d("MyJUNIT", "lock was interrupted in Meal read test");
        }
    }
}