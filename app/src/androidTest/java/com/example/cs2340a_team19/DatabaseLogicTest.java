package com.example.cs2340a_team19;

import static org.junit.Assert.assertEquals;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.cs2340a_team19.models.AggregateDataHandler;
import com.example.cs2340a_team19.models.DataHandler;
import com.example.cs2340a_team19.models.Database;
import com.example.cs2340a_team19.models.Ingredient;
import com.example.cs2340a_team19.models.Meal;
import com.example.cs2340a_team19.models.Profile;
import com.example.cs2340a_team19.models.Recipe;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
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
//    @Test // Not a real test, just a shortcut
//    public void superTest1() {
//        this.databaseInitializes();
//        this.createPantryIngredient();
//        this.readPantry();
////        this.updateIngredientQuantityTest();
////        this.removeIngredient();
//        this.createRecipe();
//        this.readCookbook();
////        this.updateRecipe();
////        this.createProfile();
////        this.readProfile();
////        this.createMeal();
////        this.readMeal();
////        this.addMeal();
//    }
//
//    @Test // Not a real test, just a handy shortcut
//    public void superTest2() {
//        this.updateRecipe();
//        this.updateIngredientQuantity();
//        this.removeIngredient();
//    }
    @Test
    public void databaseInitializes() {
        Database dbHandler = Database.getInstance();
        assert(dbHandler.isSuccessfullyInitialized());
    }
//
    @Test
    public void signInTest() {
        CountDownLatch lock = new CountDownLatch(1);
        FirebaseAuth.getInstance().signInWithEmailAndPassword("testuser@email.com",
                "testpassword").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                assert(task.isSuccessful());
            }
        });
        try {
            lock.await(2000, TimeUnit.MILLISECONDS);
        } catch(InterruptedException ie) {
            Log.d("MyJUNIT", "lock was interrupted in authentication test");
        }
    }
    @Test
    public void createPantryIngredient() {
        CountDownLatch lock = new CountDownLatch(1);
        FirebaseAuth.getInstance().signInWithEmailAndPassword("testuser@email.com",
                "testpassword").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                AggregateDataHandler<Ingredient> pantryHandler
                        = Database.getInstance().getPantryHandler();
                pantryHandler.append(new Ingredient("Test Pasta", 100, 2));
            }
        });
        try {
            lock.await(2000, TimeUnit.MILLISECONDS);
        } catch(InterruptedException ie) {
            Log.d("MyJUNIT", "lock was interrupted in authentication test");
        }
    }

    @Test
    public void createShoppingListIngredient() {
        CountDownLatch lock = new CountDownLatch(1);
        FirebaseAuth.getInstance().signInWithEmailAndPassword("testuser@email.com",
                "testpassword").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                AggregateDataHandler<Ingredient> shoppingHandler
                        = Database.getInstance().getShoppingListHandler();
                shoppingHandler.append(new Ingredient("Test Pasta", 100, 2));
            }
        });
        try {
            lock.await(2000, TimeUnit.MILLISECONDS);
        } catch(InterruptedException ie) {
            Log.d("MyJUNIT", "lock was interrupted in authentication test");
        }
    }

    @Test
    public void createRecipe() {
        CountDownLatch lock = new CountDownLatch(1);
        FirebaseAuth.getInstance().signInWithEmailAndPassword("testuser@email.com",
                "testpassword").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Database database = Database.getInstance();
                AggregateDataHandler<Recipe> cookbookHandler
                        = database.getCookbookHandler();
                cookbookHandler.append(new Recipe("Test Recipe", database.getUserID(),
                        "Test Description"));
            }
        });
        try {
            lock.await(2000, TimeUnit.MILLISECONDS);
        } catch(InterruptedException ie) {
            Log.d("MyJUNIT", "lock was interrupted in authentication test");
        }
    }

    @Test
    public void createProfile() {
        CountDownLatch lock = new CountDownLatch(1);
        FirebaseAuth.getInstance().signInWithEmailAndPassword("testuser@email.com",
                "testpassword").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Database database = Database.getInstance();
                DataHandler<Profile> profileHandler
                        = database.getProfileHandler();
                profileHandler.setData(new Profile(160, 80, true));
            }
        });
        try {
            lock.await(2000, TimeUnit.MILLISECONDS);
        } catch(InterruptedException ie) {
            Log.d("MyJUNIT", "lock was interrupted in authentication test");
        }
    }

    @Test
    public void createMeal() {
        CountDownLatch lock = new CountDownLatch(1);
        FirebaseAuth.getInstance().signInWithEmailAndPassword("testuser@email.com",
                "testpassword").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Database database = Database.getInstance();
                AggregateDataHandler<Meal> mealHandler
                        = database.getMealsHandler();
                mealHandler.append(new Meal("Test Meal", 100, "Today"));
            }
        });
        try {
            lock.await(2000, TimeUnit.MILLISECONDS);
        } catch(InterruptedException ie) {
            Log.d("MyJUNIT", "lock was interrupted in authentication test");
        }
    }

    @Test
    public void readPantry() {
        CountDownLatch lock = new CountDownLatch(1);
        FirebaseAuth.getInstance().signInWithEmailAndPassword("testuser@email.com",
                "testpassword").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                AggregateDataHandler<Ingredient> pantryHandler
                        = Database.getInstance().getPantryHandler();
//                pantryHandler.append(new Ingredient("Test Pasta", 100, 2));
                pantryHandler.addDataUpdateListener((pantry) -> {
                    assert(pantry != null);
                });
            }
        });
        try {
            lock.await(2000, TimeUnit.MILLISECONDS);
        } catch(InterruptedException ie) {
            Log.d("MyJUNIT", "lock was interrupted in authentication test");
        }
    }

    @Test
    public void readShoppingList() {
        CountDownLatch lock = new CountDownLatch(1);
        FirebaseAuth.getInstance().signInWithEmailAndPassword("testuser@email.com",
                "testpassword").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                AggregateDataHandler<Ingredient> shoppingHandler
                        = Database.getInstance().getShoppingListHandler();
                shoppingHandler.addDataUpdateListener((value) -> {
                    assert(value != null);
                });
            }
        });
        try {
            lock.await(2000, TimeUnit.MILLISECONDS);
        } catch(InterruptedException ie) {
            Log.d("MyJUNIT", "lock was interrupted in authentication test");
        }
    }

    @Test
    public void readCookbook() {
        CountDownLatch lock = new CountDownLatch(1);
        FirebaseAuth.getInstance().signInWithEmailAndPassword("testuser@email.com",
                "testpassword").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Database database = Database.getInstance();
                AggregateDataHandler<Recipe> cookbookHandler
                        = database.getCookbookHandler();
                cookbookHandler.addDataUpdateListener((value) -> {
                    assert(value != null);
                });
            }
        });
        try {
            lock.await(2000, TimeUnit.MILLISECONDS);
        } catch(InterruptedException ie) {
            Log.d("MyJUNIT", "lock was interrupted in authentication test");
        }
    }

    @Test
    public void readProfile() {
        CountDownLatch lock = new CountDownLatch(1);
        FirebaseAuth.getInstance().signInWithEmailAndPassword("testuser@email.com",
                "testpassword").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Database database = Database.getInstance();
                DataHandler<Profile> profileHandler
                        = database.getProfileHandler();
                profileHandler.addDataUpdateListener((value) -> {
                    assert(value != null);
                });
            }
        });
        try {
            lock.await(2000, TimeUnit.MILLISECONDS);
        } catch(InterruptedException ie) {
            Log.d("MyJUNIT", "lock was interrupted in authentication test");
        }
    }

    @Test
    public void readMeal() {
        CountDownLatch lock = new CountDownLatch(1);
        FirebaseAuth.getInstance().signInWithEmailAndPassword("testuser@email.com",
                "testpassword").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Database database = Database.getInstance();
                AggregateDataHandler<Meal> mealHandler
                        = database.getMealsHandler();
                mealHandler.addDataUpdateListener((value) -> {
                    assert(value != null);
                });
            }
        });
        try {
            lock.await(2000, TimeUnit.MILLISECONDS);
        } catch(InterruptedException ie) {
            Log.d("MyJUNIT", "lock was interrupted in authentication test");
        }
    }

    @Test
    public void updatePantry() {
        CountDownLatch lock = new CountDownLatch(1);
        FirebaseAuth.getInstance().signInWithEmailAndPassword("testuser@email.com",
                "testpassword").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                AggregateDataHandler<Ingredient> pantryHandler
                        = Database.getInstance().getPantryHandler();
                pantryHandler.append(new Ingredient("Test Pasta", 100, 2));
                pantryHandler.addDataUpdateListener((pantry) -> {
                    if (pantry.size() != 0 && pantry.get(pantry.size() - 1).getQuantity() != 4) {
                        pantry.get(pantry.size() - 1).setQuantity(4);
                        pantryHandler.update(pantry.get(pantry.size() - 1));
                    }
                });
            }
        });
        try {
            lock.await(2000, TimeUnit.MILLISECONDS);
        } catch(InterruptedException ie) {
            Log.d("MyJUNIT", "lock was interrupted in authentication test");
        }
    }



//
//    @Test
//    public void updateIngredientQuantity() {
//        Database dbHandler = Database.getInstance();
//        assert(dbHandler.isSuccessfullyInitialized());
//        CountDownLatch lock = new CountDownLatch(1);
//        dbHandler.getPantryHandler().updateIngredientQuantity("0", "-NuFuMz8c9iZUkzDPNDL", 10);
//
//        try {
//            lock.await(2000, TimeUnit.MILLISECONDS);
//        } catch(InterruptedException ie) {
//            Log.d("MyJUNIT", "lock was interrupted in createPantryIngredient test");
//        }
//    }
//
//    @Test
//    public void removeIngredient() {
//        Database dbHandler = Database.getInstance();
//        assert(dbHandler.isSuccessfullyInitialized());
//        CountDownLatch lock = new CountDownLatch(1);
//        dbHandler.getPantryHandler().removeIngredient("0", "-NuFuMz8c9iZUkzDPNDL");
//
//        try {
//            lock.await(2000, TimeUnit.MILLISECONDS);
//        } catch(InterruptedException ie) {
//            Log.d("MyJUNIT", "lock was interrupted in createPantryIngredient test");
//        }
//    }
//

//
//
//    @Test
//    public void updateRecipe() {
//        Database dbHandler = Database.getInstance();
//        assert(dbHandler.isSuccessfullyInitialized());
//        CountDownLatch lock = new CountDownLatch(1);
//        dbHandler.getCookbookHandler().updateRecipe("-NuFuNbDu3s7rZOs1JMv", new Recipe("Pizza", "0", "This is a pizza"));
//
//        try {
//            lock.await(2000, TimeUnit.MILLISECONDS);
//        } catch(InterruptedException ie) {
//            Log.d("MyJUNIT", "lock was interrupted in createPantryIngredient test");
//        }
//    }
//

//
//    @Test
//    public void readProfile() {
//        Database dbHandler = Database.getInstance();
//        assert(dbHandler.isSuccessfullyInitialized());
//        CountDownLatch lock = new CountDownLatch(1);
//
//        dbHandler.getProfileHandler().listenToProfile("0", new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
////                Log.d("MyJUNIT", "Test: ");
//                assert(dataSnapshot.exists());
//                Profile value = dataSnapshot.getValue(Profile.class);
//                assert(value != null);
//                assert(value.getHeight() == 100);
//
//                lock.countDown();
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                Log.d("MyJUNIT", "Read Profile Cancelled Request " + error.getMessage());
//                assert(false);
//            }
//        });
//
//        try {
//            lock.await(2000, TimeUnit.MILLISECONDS);
//        } catch(InterruptedException ie) {
////            Log.d("MyJUNIT", "lock was interrupted in profile read test");
//        }
////        Log.d("MyJUNIT", "Finished Profile Read Test: " + dbHandler.isSuccessfullyInitialized());
//    }
//
//
//    @Test
//    public void createMeal() {
//        Database dbHandler = Database.getInstance();
//        assert(dbHandler.isSuccessfullyInitialized());
//
//        CountDownLatch lock = new CountDownLatch(1);
//        String key = dbHandler.getMealsHandler().createMeal("Spaighetti", 42);
//        Log.d("MyJUNIT", "Meal Key: " + key);
//        assert(key != null);
//
//        try {
//            lock.await(2000, TimeUnit.MILLISECONDS);
//        } catch(InterruptedException ie) {
//            Log.d("MyJUNIT", "lock was interrupted in profile read test");
//        }
//    }
//

//
//    @Test
//    public void addMeal() {
//        Database dbHandler = Database.getInstance();
//        assert(dbHandler.isSuccessfullyInitialized());
//
//        CountDownLatch lock = new CountDownLatch(1);
//
//        dbHandler.getProfileHandler().addMeal("0", "-NspSkX0JX2DgIvqtITf", 1);
//
//        try {
//            lock.await(2000, TimeUnit.MILLISECONDS);
//        } catch(InterruptedException ie) {
//            Log.d("MyJUNIT", "lock was interrupted in Meal read test");
//        }
//    }
}
