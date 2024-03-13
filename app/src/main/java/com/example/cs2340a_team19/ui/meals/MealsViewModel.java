package com.example.cs2340a_team19.ui.meals;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.example.cs2340a_team19.models.DatabaseHandler;
import com.example.cs2340a_team19.models.Meal;
import com.example.cs2340a_team19.models.MealHandler;
import com.example.cs2340a_team19.models.Profile;
import com.example.cs2340a_team19.models.ProfileHandler;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class MealsViewModel extends ViewModel {
    private DatabaseHandler dbHandler;
    private MealHandler mealHandler;
    private ProfileHandler profileHandler;

    public MealsViewModel() {
        this.dbHandler = DatabaseHandler.getInstance();
        this.mealHandler = dbHandler.getMealHandler();
        this.profileHandler = dbHandler.getProfileHandler();

        if (dbHandler.isSuccessfullyInitialized() && dbHandler.getUserID() != null) {
            this.profileHandler.listenToProfile(dbHandler.getUserID(), new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.

                    if (!dataSnapshot.exists()) {
                        profileHandler.createProfile(dbHandler.getUserID());
                    } else {
                        // TODO: Use this to update the UI!!!
                        Profile value = dataSnapshot.getValue(Profile.class);
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                }
            });
        } else {
            Log.d("FBRTDB_ERROR", "Couldn't add Listener to Profile because dbHandler Initialization Failed!");
        }
    }

    public void createMeal(String name, int calories, int date) {
        if (dbHandler.isSuccessfullyInitialized() && dbHandler.getUserID() != null) {
            String mealID = this.mealHandler.createMeal(name, calories);
            this.profileHandler.addMeal(dbHandler.getUserID(), mealID, date);
        } else {
            Log.d("FBRTDB_ERROR", "Tried to create meal, but dbHandler was not successfully initialized");
        }
    }


}