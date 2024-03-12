package com.example.cs2340a_team19;

import static org.junit.Assert.assertEquals;

import com.example.cs2340a_team19.models.DatabaseHandler;

import org.junit.Test;

public class DatabaseLogicTest {
    @Test
    public void databaseInitializes() {
        DatabaseHandler dbHandler = DatabaseHandler.getInstance();
        assert(dbHandler.isSuccessfullyInitialized());
    }
}
