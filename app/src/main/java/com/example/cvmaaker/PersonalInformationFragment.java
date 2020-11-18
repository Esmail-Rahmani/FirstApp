package com.example.cvmaaker;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.autofill.AutofillId;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Checked;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Pattern;
import com.mobsandgeeks.saripaar.annotation.ValidateUsing;

import java.util.Calendar;
import java.util.List;


public class PersonalInformationFragment extends Fragment implements View.OnClickListener, View.OnFocusChangeListener, Validator.ValidationListener {

    @NotEmpty
    private EditText dateOfBirthEditText;
    @Checked
    private RadioGroup genderRadioGroup;
    @Email
    private EditText emailEditText;
    @Pattern(regex = "^\\(?([0-9]{3})\\)?[-.\\s]?([0-9]{3})[-.\\s]?([0-9]{4})$")
    private EditText phoneEditText;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText addressEditText;
    private EditText writeAboutYourself;
    private Calendar calendar;
    private DatePickerDialog datePickerDialog;
    private String dateOfBirth;
    private Button nextButton;
    private RadioGroup r1, r2;
    private AwesomeValidation awesomeValidation;
    private Validator validator;
    private Bundle bundle ;
    private ImageButton imageButton;


    @Override
    public void onStart() {
        super.onStart();
        initWidget();

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation((Activity) getContext(), R.id.firstName, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation((Activity) getContext(), R.id.last_name, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);

        bundle.putString("FirstName", firstNameEditText.getText().toString());
        bundle.putString("LastName", lastNameEditText.getText().toString());
        bundle.putString("Email", emailEditText.getText().toString());
        bundle.putString("Address", addressEditText.getText().toString());
        String value;
//        if (r1.isSelected()) {
//        }

//        bundle.putString("Gender", value);
        bundle.putString("DateOfBirth", dateOfBirth);
        bundle.putString("WriteAboutYourself", writeAboutYourself.getText().toString());

        dateOfBirthEditText.setOnFocusChangeListener(this);
        validator = new Validator(this);
        validator.setValidationListener(this);
        imageButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);


    }

    private void initWidget() {
        dateOfBirthEditText = getView().findViewById(R.id.date_of_birth_edit_text);
        nextButton = getView().findViewById(R.id.next_button_in_personal_information);
        genderRadioGroup = getView().findViewById(R.id.gender);
        emailEditText = getView().findViewById(R.id.email_edit_text);
        phoneEditText = getView().findViewById(R.id.edit_text_Phone_number);
        firstNameEditText = getView().findViewById(R.id.firstName);
        lastNameEditText = getView().findViewById(R.id.last_name);
        addressEditText = getView().findViewById(R.id.address_edit_text);
        writeAboutYourself = getView().findViewById(R.id.about_yourself);
        imageButton = getView().findViewById(R.id.image_button_preview);
        bundle = new Bundle();
//        r1 = getView().findViewById(R.id.male);
//        r2 = getView().findViewById(R.id.female);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_personal_information, container, false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next_button_in_personal_information:
                validator.validate();
                if (awesomeValidation.validate()) {

                    AchievementsFragment achievementsFragment = new AchievementsFragment();
                    achievementsFragment.setArguments(bundle);
                    getFragmentManager().beginTransaction()
                            .replace(R.id.main_contener, achievementsFragment)
                            .commit();
                }
                break;
            case R.id.image_button_preview:
                Intent intent = new Intent(getActivity().getApplication(), PreviewActivity.class);
                intent.putExtra("bundle", bundle);
                startActivity(intent);
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);


            datePickerDialog = new DatePickerDialog(getContext(),
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            dateOfBirth = dayOfMonth + "/" + (month+1) + "/ " + year;
                            dateOfBirthEditText.setText(dateOfBirth);
                        }
                    }, year, month, day);

            datePickerDialog.show();
        }
    }

    @Override
    public void onValidationSucceeded() {

    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(getContext());
            // Display error messages
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else if (view instanceof RadioGroup) {
                ((RadioGroup) view).check(R.id.male);
                Toast.makeText(getContext(), "By default Male select you can change it ", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
            }
        }
    }
}