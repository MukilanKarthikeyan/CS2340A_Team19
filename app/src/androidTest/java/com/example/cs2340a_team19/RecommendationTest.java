package com.example.cs2340a_team19;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.cs2340a_team19.models.DatabaseHandler;
import com.example.cs2340a_team19.models.Meal;
import com.example.cs2340a_team19.models.Profile;
import com.example.cs2340a_team19.models.Recomendation;
import com.example.cs2340a_team19.ui.personalInfo.PersonalInformationViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class RecommendationTest {
}
//setting up test structure for implementation