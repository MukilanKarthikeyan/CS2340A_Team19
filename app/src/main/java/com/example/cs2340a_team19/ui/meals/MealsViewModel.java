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
    private Recommendation recommendation;
    private int[] caloriesOverTime;

    public MealsViewModel(MealsFragment fragment) {
        this.database = Database.getInstance();
        this.mealsHandler = database.getMealsHandler();
        this.profileHandler = database.getProfileHandler();
        this.fragment = fragment;
        this.caloriesOverTime = new int[]{100, 100, 100, 100, 100, 100, 100};

        if (database.isSuccessfullyInitialized()) {
            this.profileHandler.addDataUpdateListener((profile) -> {
                recommendation = new Recommendation(profile);
                fragment.setPersonalInfo(recommendation.getCalorieGoal(), profile.getHeight(),
                        profile.getWeight(), profile.getGender());
            });
            int today = (new GregorianCalendar(TimeZone.getTimeZone("EST"))).get(
                    Calendar.DAY_OF_YEAR);
            this.mealsHandler.addDataUpdateListener((meals) -> {
                for (Meal meal : meals) {
                    caloriesOverTime = new int[caloriesOverTime.length];
                    int day = Integer.parseInt(meal.getDate()) / 10000;
                    if (today - day < caloriesOverTime.length) {
                        caloriesOverTime[today - day] += meal.getCalories();
                    }
                }
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
        if (this.recommendation != null) {
            return String.valueOf((caloriesOverTime[0] * 100)
                    / this.recommendation.getCalorieGoal());
        } else {
            return String.valueOf((caloriesOverTime[0] * 100) / 2000);
        }
    }

    public int[] getDayMealList() {
        return this.caloriesOverTime;
    }
}