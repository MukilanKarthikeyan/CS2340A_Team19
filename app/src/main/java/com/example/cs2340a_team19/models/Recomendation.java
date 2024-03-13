package com.example.cs2340a_team19.models;

public class Recomendation {
    private int calorie_goal;

    public Recomendation() {
        this.calorie_goal = 2000;
    }

    public Recomendation(int height, int weight, boolean gender) {
        if(gender) {
            //male
            this.calorie_goal = (int) (10 * weight + 6.25 * height- 95);
        } else {
            //female
            this.calorie_goal = (int) (10 * weight + 6.25 * height - 261);
        }
    }
}
