package com.example.cs2340a_team19.ui.personalInfo;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.example.cs2340a_team19.models.DatabaseHandler;
import com.example.cs2340a_team19.models.Profile;
import com.example.cs2340a_team19.models.ProfileHandler;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseError;

import java.util.HashMap;
import java.util.Map;

public class PersonalInformationViewModel extends ViewModel {
    private DatabaseHandler dbHandler;
    private ProfileHandler profileHandler;

    public PersonalInformationViewModel() {
        this.dbHandler = DatabaseHandler.getInstance();
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


}