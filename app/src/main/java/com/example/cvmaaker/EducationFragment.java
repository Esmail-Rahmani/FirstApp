package com.example.cvmaaker;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import adapter.EducationRecyclerViewAdapter;
import model.Education;

public class EducationFragment extends Fragment implements View.OnClickListener, View.OnFocusChangeListener {
    private static final String TAG = "EducationFragment";
    private RecyclerView recyclerView;
    private EducationRecyclerViewAdapter recyclerViewAdapter;
    private List<Education> educationList;
    private FloatingActionButton fab;
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;
    private Button saveButton;
    private EditText educationTitle;
    private EditText instituteName;
    private EditText startDate;
    private EditText endDate;
    private DatePickerDialog datePickerDialog;
    private Calendar calendar;
    private ImageButton imageButton;
    private Bundle bundle;
    private Button nextButton;

    @Override
    public void  onStart() {
        super.onStart();
        initWidget();
        recyclerViewAdapter = new EducationRecyclerViewAdapter(getContext(), educationList);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();
        imageButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPopDialog();
            }
        });
    }
    public void initWidget(){
        recyclerView = getView().findViewById(R.id.recyclerview_education_list);
        fab = getView().findViewById(R.id.fab_education);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        educationList = new ArrayList<>();
        imageButton = getView().findViewById(R.id.image_button_preview_education);
        nextButton = getView().findViewById(R.id.next_button_in_education);

    }
    private void createPopDialog() {
        builder = new AlertDialog.Builder(getContext());
        View view = getLayoutInflater().inflate(R.layout.education_popup, null);
        educationTitle = view.findViewById(R.id.education_title);
        instituteName = view.findViewById(R.id.institute_name);
        startDate = view.findViewById(R.id.start_date_of_education);
        endDate = view.findViewById(R.id.end_date_of_education);

        saveButton = view.findViewById(R.id.save_button_education);
        startDate.setOnFocusChangeListener(this);
        endDate.setOnFocusChangeListener(this);

        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.show();
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!educationTitle.getText().toString().isEmpty()
                        && !instituteName.getText().toString().isEmpty()
                        && !startDate.getText().toString().isEmpty()
                        && !endDate.getText().toString().isEmpty()) {
                    saveItem(v);
                }else {
                    Snackbar.make(v, "Empty Fields not Allowed", Snackbar.LENGTH_SHORT)
                            .show();
                }
            }
        });
    }
    private void saveItem(View v) {

        String title = educationTitle.getText().toString().trim();
        String description = instituteName.getText().toString().trim();
        String sDate = startDate.getText().toString().trim();
        String eDate = endDate.getText().toString().trim();
        Education education = new Education(title,description,sDate,eDate);

        Snackbar.make(v, "Item Saved",Snackbar.LENGTH_SHORT)
                .show();
        educationList.add(education);
        Log.d(TAG, "saveItem: "+ educationList.get(0).getInstituteName().toString() );

        alertDialog.dismiss();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_education, container, false);
        bundle = getArguments();

        return view;
    }
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()){
            case R.id.start_date_of_education:
                if (hasFocus) {
                    calendar = Calendar.getInstance();
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    int month = calendar.get(Calendar.MONTH);
                    int year = calendar.get(Calendar.YEAR);

                    datePickerDialog = new DatePickerDialog(getContext(),
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                    startDate.setText(dayOfMonth + "/" + (month+1) + "/ " + year);
                                }
                            }, year, month, day);
                    datePickerDialog.show();
                }
                break;
            case R.id.end_date_of_education:
                if (hasFocus) {
                    calendar = Calendar.getInstance();
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    int month = calendar.get(Calendar.MONTH);
                    int year = calendar.get(Calendar.YEAR);

                    datePickerDialog = new DatePickerDialog(getContext(),
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                    endDate.setText(dayOfMonth + "/" +( month+1) + "/ " + year);
                                }
                            }, year, month, day);
                    datePickerDialog.show();
                }
                break;

        }

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.next_button_in_education:

                ExperienceFragment experienceFragment = new ExperienceFragment();
                experienceFragment.setArguments(bundle);
                getFragmentManager().beginTransaction()
                        .replace(R.id.main_contener, experienceFragment)
                        .commit();


                break;
            case R.id.image_button_preview_education:
                Intent intent = new Intent(getActivity().getApplication(), PreviewActivity.class);
//                intent.putExtra("bundle",bundle);
//                intent.putExtra("EducationList", (Parcelable) educationList);

                startActivity(intent);
        }

    }
}