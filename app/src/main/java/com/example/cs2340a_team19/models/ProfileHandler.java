package com.example.cs2340a_team19.models;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseError;

import java.util.HashMap;
import java.util.Map;

public class ProfileHandler {
    private DatabaseReference profiles;
    private boolean successfullyInitialized = false;
    public ProfileHandler(DatabaseReference db) {
        try{
            this.profiles = db.child("profiles");
        } catch (NullPointerException ne) {
            Log.d("FBRTDB_ERROR", "Getting reference for profiles reached Null Pointer!");
            return;
        }

        successfullyInitialized = true;
    }

    public void updateProfile(String userID, int height, int weight, boolean gender) {
        if (successfullyInitialized) {
            Profile profile = new Profile(height, weight, gender);
            Map<String, Object> data = profile.toMap();
//            Map<String, Object> updates = new HashMap<>();
//            updates.put("/" + userID, data);
            this.profiles.child(userID).updateChildren(data);
        } else {
            Log.d("FBRTDB_ERROR", "Tried to update profile but the View Model was not sucsessfully instantiated!");
        }
    }

    public void listenToProfile(String userID, ValueEventListener updater) {
        if (successfullyInitialized) {
            this.profiles.child(userID).addValueEventListener(updater);
        } else {
            Log.d("FBRTDB_ERROR", "Tried to attach event listener, but View Model was not successfully instantiated");
        }
    }

    public void addMeal(String userID, String mealID) {
        this.profiles.child(userID).child("mealIDs").push().setValue(mealID);
    }

    public boolean createProfile(String userID) {
        Profile profile = new Profile();
        return this.createProfile(profile, userID);
    }

    public boolean createProfile(String userID, int height, int weight, boolean gender) {
        Profile profile = new Profile(height, weight, gender);
        return this.createProfile(profile, userID);
    }

    private boolean createProfile(Profile profile, String userID) {
        if (successfullyInitialized) {
            this.profiles.child(userID).setValue(profile).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("MyJUNIT", e.getMessage());
                }
            });
            return true;
        } else {
            Log.d("FBRTDB_ERROR", "Tried to create profile but the View Model was not sucsessfully instantiated!");
            return false;
        }
    }

    public boolean isSuccessfullyInitialized() {
        return successfullyInitialized;
    }

}