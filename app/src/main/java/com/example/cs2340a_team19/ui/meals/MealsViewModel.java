package com.example.cs2340a_team19.ui.meals;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MealsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private final MutableLiveData<Integer> mcalories;

    public MealsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is meals fragment");
        mcalories = new MutableLiveData<>();
        //get from database
        mcalories.setValue(0);

    }

    public LiveData<String> getText() {
        return mText;
    }
    public LiveData<Integer> getCalories() {
        return mcalories;
    }
}