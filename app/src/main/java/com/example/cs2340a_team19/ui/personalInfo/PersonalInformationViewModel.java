package com.example.cs2340a_team19.ui.personalInfo;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.example.cs2340a_team19.models.DatabaseHandler;
import com.example.cs2340a_team19.models.Profile;
import com.example.cs2340a_team19.models.ProfileHandler;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseError;

public class PersonalInformationViewModel extends ViewModel {
    private DatabaseHandler dbHandler;
    private ProfileHandler profileHandler;

    public PersonalInformationViewModel(PersonalInformationFragment frag) { //
        this.dbHandler = DatabaseHandler.getInstance();
        this.profileHandler = dbHandler.getProfileHandler();
        if (dbHandler.isSuccessfullyInitialized() && dbHandler.getUserID() != null) {
            this.profileHandler.listenToProfile(dbHandler.getUserID(), new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.

                    if (!dataSnapshot.exists()) {
                        // dbHandler.identifyUser();
                        profileHandler.createProfile(dbHandler.getUserID());
                    } else {
                        // Please DO: Use this to update the UI!!!
                        Profile value = dataSnapshot.getValue(Profile.class);
                        frag.updateUI((value.getHeight() == -1 ? "" : "" + value.getHeight()),
                                value.getWeight() == -1 ? "" : ""
                                        + value.getWeight(), value.getGender());

                        // radioGroup.set
                        // UI Stuff
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                }
            });
        } else {
            Log.d("FBRTDB_ERROR", "Couldn't add Listener to Profile, "
                    + "because dbHandler Initialization Failed!");
        }

    }

    public void updateProfile(String height, String weight, boolean gender) {
        this.profileHandler.updateProfile(dbHandler.getUserID(),
                Integer.parseInt(height), Integer.parseInt(weight), gender);
    }



}