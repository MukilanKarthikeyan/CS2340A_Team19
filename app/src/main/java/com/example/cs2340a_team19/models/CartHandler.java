package com.example.cs2340a_team19.models;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;

public class CartHandler {
    //private static CartHandler instance;
    private DatabaseReference database;
    private boolean successfullyInitialized;

    public CartHandler(DatabaseReference db) {
        try {
            this.database = db.child("cart");
        } catch (NullPointerException ne) {
            Log.d("DB_ERROR", "getting reference for cart reached Null Pointer");
            return;
        }
        successfullyInitialized = true;
    }

    //TODO: add this function
    public String createCartItem(String userID, String name ) {
        return "";
    }

}
