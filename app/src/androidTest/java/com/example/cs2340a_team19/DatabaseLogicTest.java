package com.example.cs2340a_team19;

import static org.junit.Assert.assertEquals;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.cs2340a_team19.models.DatabaseHandler;

import org.junit.Test;
import org.junit.runner.RunWith;

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

        assert(dbHandler.getProfileHandler().createProfile("0", 100, 100, true));
    }
}
