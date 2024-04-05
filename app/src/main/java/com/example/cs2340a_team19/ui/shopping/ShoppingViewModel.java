package com.example.cs2340a_team19.ui.shopping;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cs2340a_team19.models.DatabaseHandler;

public class ShoppingViewModel extends ViewModel {
    private DatabaseHandler dbHandler;
    private final MutableLiveData<String> mText;

    public ShoppingViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is shopping fragment");
    }

    public void addItem(String name, String quantity) {
        cartHandler.creatIngredient(dbHandler.getUserID(), name, quantity);
    }

    public LiveData<String> getText() {
        return mText;
    }
}