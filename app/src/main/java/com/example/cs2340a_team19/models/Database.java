package com.example.cs2340a_team19.models;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class Database {
    private static Database instance;
    private DatabaseReference database;
    private String userID;
    private final DataHandler<Profile> profileHandler;
    private final AggregateDataHandler<Meal> mealsHandler;
    private final AggregateDataHandler<Recipe> cookbookHandler;
    private final AggregateDataHandler<Ingredient> pantryHandler;
    private final AggregateDataHandler<Ingredient> shoppingListHandler;

    private boolean succesfullyInitialized = false;
    private Database() {
        try {
            database = FirebaseDatabase.getInstance().getReference();
        } catch (NullPointerException ne) {
            Log.d("FBRTDB_ERROR", "Null Pointer in Authentication -> current User ID, "
                    + "check if you are connected and Logged in.");
            profileHandler = null;
            mealsHandler = null;
            cookbookHandler = null;
            pantryHandler = null;
            shoppingListHandler = null;
            return;
        }

        identifyUser();

        DataMutator<Profile> profileCleaner = (profile) -> new Profile(profile);
        DataMutator<Ingredient> ingredientCleaner = (ingredient) -> new Ingredient(ingredient);
        DataMutator<Recipe> recipeCleaner = (recipe) -> new Recipe(recipe);
        DataMutator<Meal> mealCleaner = (meal) -> new Meal(meal);

        profileHandler = new DataHandler<>(this.database.child("profiles").child(this.userID),
                this.userID, Profile.class, profileCleaner);
        mealsHandler = new AggregateDataHandler<>(this.database.child("meals").child(this.userID),
                this.userID, Meal.class, mealCleaner);
        cookbookHandler = new AggregateDataHandler<>(this.database.child("cookbook"),
                this.userID, Recipe.class, recipeCleaner);
        pantryHandler = new AggregateDataHandler<>(this.database.child("pantries").child(this.userID),
                this.userID, Ingredient.class, ingredientCleaner);
        shoppingListHandler = new AggregateDataHandler<>(this.database.child("shoppingLists").child(this.userID),
                this.userID, Ingredient.class, ingredientCleaner);

        this.succesfullyInitialized = true;
    }

    public void identifyUser() {
        try {
            this.userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
            Log.d("FBRTDB_ERROR", "UserID Success " + this.userID);
        } catch (NullPointerException ne) {
            this.userID = null;
            Log.d("FBRTDB_ERROR", "Null Pointer in Authentication: "
                    + FirebaseAuth.getInstance().getUid());
        }
    }

    public static Database getInstance() {
        if (instance == null) {
            synchronized (Database.class) {
                if (instance == null) {
                    instance = new Database();
                }
            }
        }
        return instance;
    }

    public boolean isSuccessfullyInitialized() {
        return succesfullyInitialized;
    }

    public DataHandler<Profile> getProfileHandler() {
        return this.profileHandler;
    }

    public AggregateDataHandler<Meal> getMealsHandler() {
        return this.mealsHandler;
    }

    public AggregateDataHandler<Recipe> getCookbookHandler() {
        return this.cookbookHandler;
    }

    public AggregateDataHandler<Ingredient> getPantryHandler() {
        return this.pantryHandler;
    }

    public AggregateDataHandler<Ingredient> getShoppingListHandler() {
        return this.shoppingListHandler;
    }

    public String getUserID() {
        return this.userID;
    }
}
