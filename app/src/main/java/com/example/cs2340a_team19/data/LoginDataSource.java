package com.example.cs2340a_team19.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.cs2340a_team19.data.model.LoggedInUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    public Result<LoggedInUser> login(String email, String password, UpdateLogin update) {
        // TODO: handle loggedInUser authentication
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d("MyFavoriteTag", task.isSuccessful() ? "True" : "False");
                if (task.isSuccessful()) {
                    FirebaseUser fbUser = mAuth.getCurrentUser();
                    if (fbUser == null) {
                        update.primary(new Result.Success<>(new LoggedInUser(fbUser.getUid(), email)));
                    }
                }
                update.primary(new Result.Error(new Exception("Firebase Failed!!!")));
            }});
        return new Result.Error(new Exception("Awaiting Firebase Completion"));
    }

    public void logout() {
        // TODO: revoke authentication
        FirebaseAuth.getInstance().signOut();
    }
}