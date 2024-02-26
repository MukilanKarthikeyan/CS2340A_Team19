package com.example.cs2340a_team19.ui.login;

import androidx.lifecycle.ViewModel;

import android.util.Patterns;

public class LoginViewModel extends ViewModel {

    /*
//    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
//    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
//    private LoginRepository loginRepository;

//    LoginViewModel(LoginRepository loginRepository) {
//        this.loginRepository = loginRepository;
//    }

//    LiveData<LoginFormState> getLoginFormState() {
//        return loginFormState;
//    }

//    LiveData<LoginResult> getLoginResult() {
//        return loginResult;
//    }

//    public void login(String username, String password) {
//        // can be launched in a separate asynchronous job
//        loginRepository.login(username, password, (Result<LoggedInUser> result) -> {
////            Log.d("MyFavoriteTag", result instanceof Result.Success ? "True" : "False");
//            if (result instanceof Result.Success) {
//                LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
//               loginResult.setValue(new LoginResult(new LoggedInUserView(data.getDisplayName())));
//            } else {
//                loginResult.setValue(new LoginResult(R.string.login_failed));
//            }
//        });
//    }

//    public void loginDataChanged(String username, String password) {
//        if (!isUserNameValid(username)) {
//            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
//        } else if (!isPasswordValid(password)) {
//            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
//        } else {
//            loginFormState.setValue(new LoginFormState(true));
//        }
//    }*/

    // A placeholder username validation check
    public static boolean isUserNameValid(String username) {
        if (username == null || username.toLowerCase().equals("null")) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username.trim()).matches();
        }
        return false;
    }

    // A placeholder password validation check
    public static boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
}