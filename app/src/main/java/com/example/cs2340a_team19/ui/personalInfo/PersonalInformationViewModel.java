package com.example.cs2340a_team19.ui.personalInfo;

import androidx.lifecycle.ViewModel;

import com.example.cs2340a_team19.models.DataUpdateListener;
import com.example.cs2340a_team19.models.Database;
import com.example.cs2340a_team19.models.Profile;

public class PersonalInformationViewModel extends ViewModel {
    private final Database database;

    public PersonalInformationViewModel(DataUpdateListener<Profile> updateUI) { //
        this.database = Database.getInstance();
        database.getProfileHandler().addDataUpdateListener(updateUI);
    }

    public String updateProfile(String height, String weight, boolean gender) {
        Profile profile;
        try {
            profile = Profile.validateProfile(height, weight, gender);
        } catch (IllegalArgumentException iae) {
            return iae.getMessage();
        }
        database.getProfileHandler().setData(profile);
        return "Profile Updated!";
    }
}