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
import com.example.cs2340a_team19.databinding.ActivityLoginBinding;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private ActivityLoginBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseUser currUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        final EditText usernameEditText = binding.username;
        final EditText passwordEditText = binding.password;
        final Button loginButton = binding.login;
        final ProgressBar loadingProgressBar = binding.loading;
        // final Button skipLoginTESTButton = binding.skipLoginTESTButton;

        loginButton.setEnabled(true);
        final Button newSigninButton = binding.newSignIn;
        final Button exitButton = binding.button;


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
        /*TEST only: skips login step until login screen logic is implemented

//        skipLoginTESTButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(intent);
//            }
        });*/
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //loginViewModel.login(usernameEditText.getText().toString(),
                //passwordEditText.getText().toString());
                if (LoginViewModel.isUserNameValid(usernameEditText.getText().toString())
                    && LoginViewModel.isPasswordValid(passwordEditText.getText().toString())
                ) {
                    loadingProgressBar.setVisibility(View.VISIBLE);
                    mAuth.signInWithEmailAndPassword(usernameEditText.getText().toString().trim(),
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
                                        showLoginFailed("Firebase Authentication Failed");
                                    }
                                }
                            });
                } else {
                    showLoginFailed("Username or Password Invalid");
                }
                
            }
        });
        newSigninButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //setContentView(R.layout.user_sign_up);
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
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
        //Log.d("FavoriteTag", currentUser.getEmail());
        //if (currentUser.getEmail() != null) {
        //updateUiWithUser();
        //}
    }

    private void updateUiWithUser() {
        String welcome = getString(R.string.welcome) + " " + this.currUser.getEmail();
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(String errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}