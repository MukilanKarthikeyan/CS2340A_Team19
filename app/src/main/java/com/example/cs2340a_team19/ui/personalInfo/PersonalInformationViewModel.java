package com.example.cs2340a_team19.ui.personalInfo;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.example.cs2340a_team19.models.Profile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseError;

public class PersonalInformationViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private FirebaseDatabase database;
    private DatabaseReference dbRef;
    private DatabaseReference profile;
    private String userID;
    private boolean successfullyInstantiated = false;
    public PersonalInformationViewModel () {
        this.database = FirebaseDatabase.getInstance();
        this.dbRef = database.getReference();
        FirebaseAuth fbAuth = FirebaseAuth.getInstance();
        if (fbAuth == null) {
            Log.d("FBRTDB_ERROR", "The FireBaseAuth Instance was null, check if you are connected.");
            return;
        }
        FirebaseUser fbUser = fbAuth.getCurrentUser();
        if (fbUser == null) {
            Log.d("FBRTDB_ERROR", "The current user was null, check if you are logged in.");
            return;
        }
        this.userID = fbUser.getUid();
        if (userID == null) {
            Log.d("FBRTDB_ERROR", "The userID was null, check if you are logged in.");
            return;
        }
        dbRef.child("profiles").child(this.userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                if (!dataSnapshot.exists()) {
                    createProfile();
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

        successfullyInstantiated = true;
    }

    public void createProfile() {
        Profile profile = new Profile();
        this.createProfile(profile);

    }

    public void createProfile(int height, int weight, boolean gender) {
        Profile profile = new Profile(height, weight, gender);
        this.createProfile(profile);
    }

    private void createProfile(Profile profile) {
        if (successfullyInstantiated) {
            DatabaseReference childLoc = dbRef.child("profiles").child(this.userID);
            childLoc.setValue(profile);
        }
    }

}