package com.example.cs2340a_team19.ui.meals;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.example.cs2340a_team19.models.AggregateDataHandler;
import com.example.cs2340a_team19.models.DataHandler;
import com.example.cs2340a_team19.models.Database;
import com.example.cs2340a_team19.models.Meal;
import com.example.cs2340a_team19.models.Profile;
import com.example.cs2340a_team19.models.Recommendation;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class MealsViewModel extends ViewModel {
    private Database database;
    private AggregateDataHandler<Meal> mealsHandler;
    private DataHandler<Profile> profileHandler;
    private final MealsFragment fragment;

    public MealsViewModel(MealsFragment fragment) {
        this.database = Database.getInstance();
        this.mealsHandler = database.getMealsHandler();
        this.profileHandler = database.getProfileHandler();
        this.fragment = fragment;

        if (database.isSuccessfullyInitialized()) {
            this.profileHandler.addDataUpdateListener((profile) -> {
                Recommendation rec = new Recommendation(profile);
                fragment.setPersonalInfo(rec.getCalorieGoal(), profile.getHeight(), profile.getWeight(), profile.getGender());
            });
        } else {
            Log.d("FBRTDB_ERROR", "Couldn't add Listener to Profile, "
                    + "because database Initialization Failed!");
        }
    }

    public void createMeal(Meal meal) {
        this.mealsHandler.append(meal);
    }
    //return the percentage of food someone has consumed
    public String getCalorieProgress() {
        int consumed = 100;
        int goal = 1500;
        //could use string format but can get finicky so avoding any of those issues altogether
        return String.valueOf((consumed * 100) / goal);
    }
    //THIS METHOD IS CALLED BY THE ONE BELOW DO NOT USE ON ITS OWN
    public void getDayMealList(int numDays) {
        if (database.isSuccessfullyInitialized() && database.getUserID() != null) {
            int today = (new GregorianCalendar(TimeZone.getTimeZone("EST"))).get(
                    Calendar.DAY_OF_YEAR);
            this.mealsHandler.addDataUpdateListener((meals) -> {
                int[] days = new int[numDays];
                for (Meal meal : meals) {
                    int day = Integer.parseInt(meal.getDate()) / 10000;
                    if (today - day < numDays) {
                        days[today - day] += meal.getCalories();
                    }
                }
                fragment.createBarChart(days);
            });
        }
    }
}