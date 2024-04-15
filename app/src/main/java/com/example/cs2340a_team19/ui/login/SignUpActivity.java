package com.example.cs2340a_team19.ui.login;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cs2340a_team19.MainActivity;
import com.example.cs2340a_team19.R;
import com.example.cs2340a_team19.databinding.UserSignUpBinding;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
public class SignUpActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private UserSignUpBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseUser currUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        binding = UserSignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //loginViewModel = new LoginViewModel();

        final EditText usernameEditText = binding.username;
        final EditText passwordEditText = binding.password;
        final Button loginButton = binding.login;
        final ProgressBar loadingProgressBar = binding.loading;
        final Button exitButton = binding.button;

        loginButton.setEnabled(true);

        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    //loginViewModel.login(usernameEditText.getText().toString(),
                    //passwordEditText.getText().toString());
                    return false;
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //loginViewModel.login(usernameEditText.getText().toString(),
                //passwordEditText.getText().toString());
                if (LoginViewModel.isUserNameValid(usernameEditText.getText().toString())
                        && LoginViewModel.isPasswordValid(passwordEditText.getText().toString())
                ) {
                    loadingProgressBar.setVisibility(View.VISIBLE);
                    mAuth.createUserWithEmailAndPassword(
                            usernameEditText.getText().toString().trim(),
                                    passwordEditText.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    loadingProgressBar.setVisibility(View.INVISIBLE);
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the user's information
                                        currUser = mAuth.getCurrentUser();
                                        updateUiWithUser();
                                    } else {
                                        showLoginFailed("Firebase User Creation Failed");
                                    }
                                }
                            });
                } else {
                    showLoginFailed("Username or Password Invalid");
                }
            }
        });
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //Log.d("FavoriteTag", currentUser.toString());

        /*
//        if (currentUser != null) {
//            updateUiWithUser();
//        }*/
    }

    private void updateUiWithUser() {
        String welcome = getString(R.string.welcome) + " " + this.currUser.getDisplayName();
        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(intent);
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(String errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}