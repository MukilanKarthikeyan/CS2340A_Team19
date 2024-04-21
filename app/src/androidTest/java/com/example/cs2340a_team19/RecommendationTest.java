package com.example.cs2340a_team19;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import com.example.cs2340a_team19.models.Recommendation;

import org.junit.Test;

public class RecommendationTest {
    @Test
    public void testRecCalcMale() {
        Recommendation r = new Recommendation(100, 100, true);
        assertEquals(r.getCalorieGoal(), 1530);
    }
    @Test
    public void testRecCalcMaleWithFemale() {
        Recommendation r = new Recommendation(100, 100, true);
        assertNotEquals(r.getCalorieGoal(), 1364);
    }
    @Test
    public void testRecCalcFemale() {
        Recommendation r = new Recommendation(100, 100, false);
        assertEquals(r.getCalorieGoal(), 1364);
    }
    @Test
    public void testRecCalcFemaleWithMale() {
        Recommendation r = new Recommendation(100, 100, false);
        assertNotEquals(r.getCalorieGoal(), 1530);
    }
    @Test
    public void testRecCalcFemaleTwo() {
        Recommendation r = new Recommendation(200, 500, false);
        assertEquals(r.getCalorieGoal(), 5989);
    }
    @Test
    public void testRecCalcFemaleWithMaleTwo() {
        Recommendation r = new Recommendation(200, 500, false);
        assertEquals(r.getCalorieGoal(), 6155);
}
