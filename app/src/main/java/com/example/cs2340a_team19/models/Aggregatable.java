package com.example.cs2340a_team19.models;

import com.google.firebase.database.Exclude;

public class Aggregatable {
    private String id;

    @Exclude
    public String getId() {
        return id;
    }

    @Exclude
    public void setId(String id) {
        this.id = id;
    }
}
