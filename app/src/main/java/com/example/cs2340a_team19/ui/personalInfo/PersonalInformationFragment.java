package com.example.cs2340a_team19.ui.personalInfo;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.cs2340a_team19.R;
import com.example.cs2340a_team19.databinding.FragmentMealsBinding;
import com.example.cs2340a_team19.databinding.FragmentPersonalInformationBinding;

public class PersonalInformationFragment extends Fragment {

    private PersonalInformationViewModel mViewModel;
    private FragmentPersonalInformationBinding binding;
    private View view;
    public static PersonalInformationFragment newInstance() {
        return new PersonalInformationFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentPersonalInformationBinding.inflate(inflater, container, false);

//        this.updateUI("222", "333");


        return inflater.inflate(R.layout.fragment_personal_information, container, false);
    }

    @Override
    public void onViewCreated(View view, @NonNull Bundle savedInstanceState) {
        this.view = view;
        mViewModel = new PersonalInformationViewModel(this);
    }

    public void updateUI(String newHeight, String newWeight, boolean newGender) {
        final EditText heightInput = view.findViewById(R.id.input_height);
        final EditText weightInput = view.findViewById(R.id.input_weight);
        final RadioGroup radioGroup = view.findViewById(R.id.radioGroup);
        Log.d("DebugTag", "In updateUI!");
        heightInput.setText(newHeight);
        weightInput.setText(newWeight);
    }


//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
////        mViewModel = new ViewModelProvider(this).get(PersonalInformationViewModel.class);
//
//        // TODO: Use the ViewModel
//    }

}