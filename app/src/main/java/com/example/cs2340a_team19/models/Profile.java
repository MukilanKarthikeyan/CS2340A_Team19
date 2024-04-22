package com.example.cs2340a_team19.models;

public class Profile {
    // private String userID;
    private int height; //centimeters
    private int weight; //kilograms
    private boolean gender; //male is true, female is false

    public Profile() {
        // userID = "";
        height = -1;
        weight = -1;
        gender = false;
    }

    public Profile(int height, int weight, boolean gender) {
        // this.userID = uid;
        this.height = height;
        this.weight = weight;
        this.gender = gender;
    }

    public Profile(Profile other) {
        if (other == null) {
            return;
        }
        this.height = other.height;
        this.weight = other.weight;
        this.gender = other.gender;
    }

    public static Profile validateProfile(String height, String weight, boolean gender) {
        int pHeight;
        try {
            pHeight = Integer.parseInt(height.trim());
        } catch (NumberFormatException nfe) {
            throw new IllegalArgumentException("Height must be valid number");
        }
        if (pHeight > 300 || pHeight < 0) {
            throw new IllegalArgumentException("Height must be between 0 and 300 cm");
        }

        int pWeight;
        try {
            pWeight = Integer.parseInt(weight.trim());
        } catch (NumberFormatException nfe) {
            throw new IllegalArgumentException("Weight must be valid number");
        }
        if (pWeight > 250 || pWeight < 0) {
            throw new IllegalArgumentException("Weight must be between 0 and 250 kg");
        }

        return new Profile(pHeight, pWeight, gender);
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return this.weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public boolean getGender() {
        return this.gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

}
