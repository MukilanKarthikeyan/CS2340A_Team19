package com.example.cs2340a_team19.ui.personalInfo;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.cs2340a_team19.R;
import com.example.cs2340a_team19.databinding.FragmentPersonalInformationBinding;

public class PersonalInformationFragment extends Fragment {

    private PersonalInformationViewModel mViewModel;
    private FragmentPersonalInformationBinding binding;

    public static PersonalInformationFragment newInstance() {
        return new PersonalInformationFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_personal_information, container, false);
    }


    @Override
    public void onViewCreated(View view, @NonNull Bundle savedInstanceState) {

    }

}