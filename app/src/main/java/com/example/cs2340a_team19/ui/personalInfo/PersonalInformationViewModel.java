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
    private DatabaseReference profile;
    private String userID;
    private boolean successfullyInstantiated = false;
    public PersonalInformationViewModel () {
        try {
            this.userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        } catch (NullPointerException ne) {
            Log.d("FBRTDB_ERROR", "Null Pointer in Authentication -> current User ID, check if you are connected and Logged in.");
            return;
        }

        try{
            this.profile = FirebaseDatabase.getInstance().getReference().child("profiles").child(this.userID);
        } catch (NullPointerException ne) {
            Log.d("FBRTDB_ERROR", "Getting reference for profiles reached Null Pointer!");
            return;
        }


        this.profile.addValueEventListener(new ValueEventListener() {
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

    public void updateProfile(int height, int weight, boolean gender) {
        Profile profile = new Profile(height, weight, gender);
        this.updateProfile(profile);
    }

    public void createProfile() {
        Profile profile = new Profile();
        this.updateProfile(profile);
    }

    public void createProfile(int height, int weight, boolean gender) {
        Profile profile = new Profile(height, weight, gender);
        this.updateProfile(profile);
    }

    private void updateProfile(Profile profile) {
        if (successfullyInstantiated) {
            this.profile.setValue(profile);
        } else {
            Log.d("FBRTDB_ERROR", "Tried to update/create profile but the View Model was not sucsessfully instantiated!");
        }
    }

}