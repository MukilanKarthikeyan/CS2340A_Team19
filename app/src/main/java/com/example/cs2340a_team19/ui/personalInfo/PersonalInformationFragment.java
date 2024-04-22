package com.example.cs2340a_team19.ui.personalInfo;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.cs2340a_team19.R;
import com.example.cs2340a_team19.databinding.FragmentPersonalInformationBinding;
import com.example.cs2340a_team19.models.Profile;

public class PersonalInformationFragment extends Fragment {

    private PersonalInformationViewModel vm;
    private FragmentPersonalInformationBinding binding;
    private View view;

    private EditText heightInput;
    private EditText weightInput;
    private RadioGroup radioGroup;
    private Button enterButton;

    public static PersonalInformationFragment newInstance() {
        return new PersonalInformationFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentPersonalInformationBinding.inflate(inflater, container, false);

        // this.updateUI("222", "333");


        return inflater.inflate(R.layout.fragment_personal_information, container, false);
    }


    @Override
    public void onViewCreated(View view, @NonNull Bundle savedInstanceState) {
        this.view = view;
        this.heightInput = view.findViewById(R.id.input_height);
        this.weightInput = view.findViewById(R.id.input_weight);
        this.radioGroup = view.findViewById(R.id.radioGroup);
        this.enterButton = view.findViewById(R.id.personalInfoSubmitButton);
        vm = new PersonalInformationViewModel(this::updateUI);
        enterButton.setOnClickListener(this::submitProfile);
    }

    public void submitProfile(View v) {
        String result = vm.updateProfile(
                this.heightInput.getText().toString(), this.weightInput.getText().toString(),
                this.radioGroup.getCheckedRadioButtonId() == R.id.radioButton4);
        Toast.makeText(getContext(), result, Toast.LENGTH_LONG).show();
    }

    public void updateUI(Profile profile) {
        if (profile != null) {
            this.heightInput.setText(String.valueOf(profile.getHeight()));
            this.weightInput.setText(String.valueOf(profile.getWeight()));
            this.radioGroup.check(profile.getGender() ? R.id.radioButton4 : R.id.radioButton5);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        this.vm.onViewDestroyed();
    }
}