package com.example.cs2340a_team19;

import org.junit.Test;
import static org.junit.Assert.*;

import com.example.cs2340a_team19.ui.login.LoginViewModel;
import java.util.Random;
//JUnit Test for the LoginViewModel class
public class LoginViewModelTest {

    @Test
    public void testValidEmail() {
        assertTrue(LoginViewModel.isUserNameValid("example@example.com"));
        assertTrue(LoginViewModel.isUserNameValid("example+alias@example.com"));
    }

    @Test
    public void testInvalidEmail() {
        assertFalse(LoginViewModel.isUserNameValid("example@example"));
        assertFalse(LoginViewModel.isUserNameValid("example;@example.com"));
    }

    @Test
    public void testNullEmail() {
        assertFalse(LoginViewModel.isUserNameValid(null));
    }

    @Test
    public void testValidPassword() {
        assertTrue(LoginViewModel.isPasswordValid("password123"));
    }

    @Test
    public void testInvalidPassword() {
        assertFalse(LoginViewModel.isPasswordValid("pass"));
    }

    @Test
    public void testNullPassword() {
        assertFalse(LoginViewModel.isPasswordValid(null));
    }
    @Test
    public void testWhiteSpaceEmail() {
        Random rand = new Random();
        String a = "Hello@MyNameIs";
        int genRandInt = rand.nextInt(100);
        String b = a;
        for (int i = 1; i <= genRandInt; i++) {
            b+= " ";
        }
        assertFalse(LoginViewModel.isUserNameValid(b));

    }
    @Test
    public void testWhiteSpacePassword() {
        String password = "password123";
        assertTrue(LoginViewModel.isPasswordValid("password123 "));
    }
}
