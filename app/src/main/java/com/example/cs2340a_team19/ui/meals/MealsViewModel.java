package com.example.cs2340a_team19.ui.meals;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.ViewModel;

import com.example.cs2340a_team19.models.DatabaseHandler;
import com.example.cs2340a_team19.models.MealHandler;
import com.example.cs2340a_team19.models.Profile;
import com.example.cs2340a_team19.models.ProfileHandler;
import com.example.cs2340a_team19.models.Recommendation;
import com.example.cs2340a_team19.models.UserMeal;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class MealsViewModel extends ViewModel {
    private DatabaseHandler dbHandler;
    private MealHandler mealHandler;
    private ProfileHandler profileHandler;

    public MealsViewModel(MealsFragment frag) {
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
                        Recommendation rec = new Recommendation(value.getHeight(), value.getWeight(), value.getGender());
                        frag.setPersonalInfo(rec.getCalorie_goal(), value.getHeight(), value.getWeight(), value.getGender());

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
    //TODO: return the percentatge of food someone has consumed
    public String getCalorieProgress() {
        int consumed = 100;
        int goal = 1500;
        //could use string format but can get finicky so avoding any of those issues altogether
        return String.valueOf((consumed * 100)/goal);
    }
    //THIS METHOD IS CALLED BY THE ONE BELOW DO NOT USE ON ITS OWN
    public void getDayMealList(List<UserMeal> userMeals, int[] days, int numDays, MealsFragment frag, View view) {
        if (dbHandler.isSuccessfullyInitialized() && dbHandler.getUserID() != null) {
            this.mealHandler.listenToMealList(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.

                    if (!dataSnapshot.exists()) {
                        Log.d("FBRTDB_ERROR", "Tried to get list of meals, but the snapshot does not exist");
                    } else {
//                        Map<String, UserMeal> mealList = dataSnapshot.getValue(Map.class);
                        int size = userMeals.size();
                        int today = (new GregorianCalendar(TimeZone.getTimeZone("EST"))).get(Calendar.DAY_OF_YEAR);

                        for (int i = size - 1; i >= 0; i--) {
                            int day = Integer.parseInt(userMeals.get(i).date) / 10000;
                            //stop iterating once you pass the end date
                            if (day <= today - numDays) {
                                i = -1;
                            } else {
                                days[today - day] += dataSnapshot.child(userMeals.get(i).mealId).getValue(Meal.class).getCalories();
                            }
                        }
                    }

                    frag.createBarChart(view, days);
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                }
            });
        }
    }

    //GET LIST OF MEALS FOR THE LAST numDays days and put calories into days array
    public void getLastDays(int[] days, int numDays, MealsFragment frag, View view) {
        if (dbHandler.isSuccessfullyInitialized() && dbHandler.getUserID() != null) {
            this.profileHandler.listenToProfileUserMeals(dbHandler.getUserID(), new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    if (!dataSnapshot.exists()) {
                        //cry a little bit
                        Log.d("FBRTDB_ERROR", "Tried to get list of meals and dates for user, but the snapshot does not exist");
                    } else {
//                        List<String> mealIDs = dataSnapshot.child("mealIDs").getValue(List.class);
//                        List<Integer> mealDays = dataSnapshot.child("mealDates").getValue(List.class);
                        List<UserMeal> userMeals = new ArrayList<>();
                        for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                            userMeals.add(postSnapshot.getValue(UserMeal.class));
                            Log.d("FinalCount", userMeals.get(userMeals.size() - 1).date);
                            Log.d("FinalCount", userMeals.get(userMeals.size() - 1).mealId);
                        }
                        getDayMealList(userMeals, days, numDays, frag, view);
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                }
            });
        } else {
            Log.d("FBRTDB_ERROR", "Tried to get last days meals, but dbHandler was not successfully initialized");
        }
    }
}