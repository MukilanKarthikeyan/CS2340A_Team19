package com.example.cs2340a_team19.models;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;

public class DatabaseHandler {
    private static DatabaseHandler instance;
    private DatabaseReference database;
    private String userID;
    private ProfileHandler profileHandler;
    private MealHandler mealHandler;
    private CookbookHandler cookbookHandler;
    private PantryHandler pantryHandler;

    private boolean succesfullyInitialized;
    private DatabaseHandler() {
        try {
            database = FirebaseDatabase.getInstance().getReference();
        } catch (NullPointerException ne) {
            Log.d("FBRTDB_ERROR", "Null Pointer in Authentication -> current User ID, check if you are connected and Logged in.");
            return;
        }

        identifyUser();

        profileHandler = new ProfileHandler(this.database);
        mealHandler = new MealHandler(this.database);
        cookbookHandler = new CookbookHandler(this.database);
        pantryHandler = new PantryHandler(this.database);

        succesfullyInitialized =
                profileHandler.isSuccessfullyInitialized() &&
                mealHandler.isSuccessfullyInitialized() &&
                cookbookHandler.isSuccessfullyInitialized() &&
                pantryHandler.isSuccessfullyInitialized();

    }

    public void identifyUser() {
        try {
            this.userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
            Log.d("FBRTDB_ERROR", "UserID Success " + this.userID);
        } catch (NullPointerException ne) {
            this.userID = null;
            Log.d("FBRTDB_ERROR", "Null Pointer in Authentication: " + FirebaseAuth.getInstance().getUid());
        }
    }

    public static DatabaseHandler getInstance() {
        if (instance == null) {
            synchronized (DatabaseHandler.class) {
                if (instance == null) {
                    instance = new DatabaseHandler();
                }
            }
        }
        return instance;
    }

    public boolean isSuccessfullyInitialized() {
        return succesfullyInitialized;
    }

    public ProfileHandler getProfileHandler() {
        return this.profileHandler;
    }

    public MealHandler getMealHandler() {
        return this.mealHandler;
    }

    public CookbookHandler getCookbookHandler() {
        return this.cookbookHandler;
    }

    public PantryHandler getPantryHandler() {
        return this.pantryHandler;
    }

    public String getUserID() {
        return this.userID;
    }
}
