package com.example.cs2340a_team19;

import static org.junit.Assert.assertEquals;

import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.cs2340a_team19.models.DatabaseHandler;
import com.example.cs2340a_team19.models.Ingredient;
import com.example.cs2340a_team19.models.Meal;
import com.example.cs2340a_team19.models.Profile;
import com.example.cs2340a_team19.models.Recipe;
import com.example.cs2340a_team19.models.UserMeal;
import com.example.cs2340a_team19.ui.personalInfo.PersonalInformationViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@RunWith(AndroidJUnit4.class)
public class DatabaseLogicTest {
    @Test // Not a real test, just a shortcut
    public void superTest1() {
        this.databaseInitializes();
        this.createPantryIngredient();
        this.readPantry();
//        this.updateIngredientQuantityTest();
//        this.removeIngredient();
        this.createRecipe();
        this.readCookbook();
//        this.updateRecipe();
//        this.createProfile();
//        this.readProfile();
//        this.createMeal();
//        this.readMeal();
//        this.addMeal();
    }

    @Test // Not a real test, just a handy shortcut
    public void superTest2() {
        this.updateRecipe();
        this.updateIngredientQuantity();
        this.removeIngredient();
    }
    @Test
    public void databaseInitializes() {
        DatabaseHandler dbHandler = DatabaseHandler.getInstance();
        assert(dbHandler.isSuccessfullyInitialized());
    }

    @Test
    public void createPantryIngredient() {
        DatabaseHandler dbHandler = DatabaseHandler.getInstance();
        assert(dbHandler.isSuccessfullyInitialized());
        CountDownLatch lock = new CountDownLatch(1);
        assert(null != dbHandler.getPantryHandler().createIngredient("0", "Milk", 100, 2));

        try {
            lock.await(2000, TimeUnit.MILLISECONDS);
        } catch(InterruptedException ie) {
            Log.d("MyJUNIT", "lock was interrupted in createPantryIngredient test");
        }
    }

    @Test
    public void readPantry() {
        DatabaseHandler dbHandler = DatabaseHandler.getInstance();
        assert(dbHandler.isSuccessfullyInitialized());

        CountDownLatch lock = new CountDownLatch(1);

        dbHandler.getPantryHandler().listenToPantry("0", new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                assert(dataSnapshot.exists());
                List<Ingredient> ingredients = new ArrayList<>();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    ingredients.add(postSnapshot.getValue(Ingredient.class));
                }
                assert(ingredients.size() != 0);
                assert(ingredients.get(0).name.equals("Milk"));
                assert(ingredients.get(0).calories == 100);
                assert(ingredients.get(0).quantity == 2);
                Log.d("MyJUNIT", "Reached the end of the pantry read test!");
//                lock.countDown();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.d("MyJUNIT", "Read Pantry Cancelled Request  " + error.getMessage());
                assert(false);
            }
        });

        try {
            lock.await(2000, TimeUnit.MILLISECONDS);
        } catch(InterruptedException ie) {
            Log.d("MyJUNIT", "lock was interrupted in Pantry Read test");
        }
    }

    @Test
    public void updateIngredientQuantity() {
        DatabaseHandler dbHandler = DatabaseHandler.getInstance();
        assert(dbHandler.isSuccessfullyInitialized());
        CountDownLatch lock = new CountDownLatch(1);
        dbHandler.getPantryHandler().updateIngredientQuantity("0", "-NuFuMz8c9iZUkzDPNDL", 10);

        try {
            lock.await(2000, TimeUnit.MILLISECONDS);
        } catch(InterruptedException ie) {
            Log.d("MyJUNIT", "lock was interrupted in createPantryIngredient test");
        }
    }

    @Test
    public void removeIngredient() {
        DatabaseHandler dbHandler = DatabaseHandler.getInstance();
        assert(dbHandler.isSuccessfullyInitialized());
        CountDownLatch lock = new CountDownLatch(1);
        dbHandler.getPantryHandler().removeIngredient("0", "-NuFuMz8c9iZUkzDPNDL");

        try {
            lock.await(2000, TimeUnit.MILLISECONDS);
        } catch(InterruptedException ie) {
            Log.d("MyJUNIT", "lock was interrupted in createPantryIngredient test");
        }
    }

    @Test
    public void createRecipe() {
        DatabaseHandler dbHandler = DatabaseHandler.getInstance();
        assert(dbHandler.isSuccessfullyInitialized());
        CountDownLatch lock = new CountDownLatch(1);
        assert(null != dbHandler.getCookbookHandler().createRecipe("0", "Alfredo", "Yummy"));

        try {
            lock.await(2000, TimeUnit.MILLISECONDS);
        } catch(InterruptedException ie) {
            Log.d("MyJUNIT", "lock was interrupted in createRecipe test");
        }
    }

    @Test
    public void readCookbook() {
        DatabaseHandler dbHandler = DatabaseHandler.getInstance();
        assert(dbHandler.isSuccessfullyInitialized());

        CountDownLatch lock = new CountDownLatch(1);

        dbHandler.getCookbookHandler().listenToCookbook(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                assert(dataSnapshot.exists());
                List<Recipe> recipes = new ArrayList<>();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    recipes.add(postSnapshot.getValue(Recipe.class));
                }
                assert(recipes.size() != 0);
                assert(recipes.get(0).name.equals("Alfredo"));
                assert(recipes.get(0).userId.equals("0"));
                Log.d("MyJUNIT", "Reached the end of the cookbook read test!");
//                lock.countDown();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.d("MyJUNIT", "Read Cookbook Cancelled Request  " + error.getMessage());
                assert(false);
            }
        });

        try {
            lock.await(2000, TimeUnit.MILLISECONDS);
        } catch(InterruptedException ie) {
            Log.d("MyJUNIT", "lock was interrupted in Pantry Read test");
        }
    }

    @Test
    public void updateRecipe() {
        DatabaseHandler dbHandler = DatabaseHandler.getInstance();
        assert(dbHandler.isSuccessfullyInitialized());
        CountDownLatch lock = new CountDownLatch(1);
        dbHandler.getCookbookHandler().updateRecipe("-NuFuNbDu3s7rZOs1JMv", new Recipe("Pizza", "0", "This is a pizza"));

        try {
            lock.await(2000, TimeUnit.MILLISECONDS);
        } catch(InterruptedException ie) {
            Log.d("MyJUNIT", "lock was interrupted in createPantryIngredient test");
        }
    }

    @Test
    public void createProfile() {
        DatabaseHandler dbHandler = DatabaseHandler.getInstance();
        assert(dbHandler.isSuccessfullyInitialized());
        CountDownLatch lock = new CountDownLatch(1);
        assert(dbHandler.getProfileHandler().createProfile("0", 100, 100, true));

        try {
            lock.await(2000, TimeUnit.MILLISECONDS);
        } catch(InterruptedException ie) {
            Log.d("MyJUNIT", "lock was interrupted in profile read test");
        }
    }

    @Test
    public void readProfile() {
        DatabaseHandler dbHandler = DatabaseHandler.getInstance();
        assert(dbHandler.isSuccessfullyInitialized());
        CountDownLatch lock = new CountDownLatch(1);

        dbHandler.getProfileHandler().listenToProfile("0", new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                Log.d("MyJUNIT", "Test: ");
                assert(dataSnapshot.exists());
                Profile value = dataSnapshot.getValue(Profile.class);
                assert(value != null);
                assert(value.getHeight() == 100);

                lock.countDown();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.d("MyJUNIT", "Read Profile Cancelled Request " + error.getMessage());
                assert(false);
            }
        });

        try {
            lock.await(2000, TimeUnit.MILLISECONDS);
        } catch(InterruptedException ie) {
//            Log.d("MyJUNIT", "lock was interrupted in profile read test");
        }
//        Log.d("MyJUNIT", "Finished Profile Read Test: " + dbHandler.isSuccessfullyInitialized());
    }


    @Test
    public void createMeal() {
        DatabaseHandler dbHandler = DatabaseHandler.getInstance();
        assert(dbHandler.isSuccessfullyInitialized());

        CountDownLatch lock = new CountDownLatch(1);
        String key = dbHandler.getMealHandler().createMeal("Spaighetti", 42);
        Log.d("MyJUNIT", "Meal Key: " + key);
        assert(key != null);

        try {
            lock.await(2000, TimeUnit.MILLISECONDS);
        } catch(InterruptedException ie) {
            Log.d("MyJUNIT", "lock was interrupted in profile read test");
        }
    }

    @Test
    public void readMeal() {
        DatabaseHandler dbHandler = DatabaseHandler.getInstance();
        assert(dbHandler.isSuccessfullyInitialized());

        CountDownLatch lock = new CountDownLatch(1);

        dbHandler.getMealHandler().listenToMeal("-NspSkX0JX2DgIvqtITf", new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                assert(dataSnapshot.exists());
                Meal value = dataSnapshot.getValue(Meal.class);
                assert(value != null);
                assert(value.getCalories() == 42);
//                lock.countDown();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.d("MyJUNIT", "Read Meal Cancelled Request  " + error.getMessage());
                assert(false);
            }
        });

        try {
            lock.await(2000, TimeUnit.MILLISECONDS);
        } catch(InterruptedException ie) {
            Log.d("MyJUNIT", "lock was interrupted in Meal read test");
        }
    }

    @Test
    public void addMeal() {
        DatabaseHandler dbHandler = DatabaseHandler.getInstance();
        assert(dbHandler.isSuccessfullyInitialized());

        CountDownLatch lock = new CountDownLatch(1);

        dbHandler.getProfileHandler().addMeal("0", "-NspSkX0JX2DgIvqtITf", 1);

        try {
            lock.await(2000, TimeUnit.MILLISECONDS);
        } catch(InterruptedException ie) {
            Log.d("MyJUNIT", "lock was interrupted in Meal read test");
        }
    }
}
