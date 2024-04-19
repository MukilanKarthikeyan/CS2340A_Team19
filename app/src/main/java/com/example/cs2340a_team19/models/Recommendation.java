package com.example.cs2340a_team19.models;

public class Recommendation {
    private int calorieGoal;

    public Recommendation() {
        this.calorieGoal = 2000;
    }

    public Recommendation(Profile profile) {
        this(profile.getHeight(), profile.getWeight(), profile.getGender());
    }
    public Recommendation(int height, int weight, boolean gender) {
        if (gender) {
            //male
            this.calorieGoal = (int) (10 * weight + 6.25 * height - 95);
        } else {
            //female
            this.calorieGoal = (int) (10 * weight + 6.25 * height - 261);
        }
    }

    public int getCalorieGoal() {
        return this.calorieGoal;
    }
}
