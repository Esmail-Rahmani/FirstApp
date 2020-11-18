package com.example.cvmaaker;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
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

import adapter.ExperienceRecyclerViewAdapter;
import model.Experience;


public class ExperienceFragment extends Fragment implements View.OnClickListener, View.OnFocusChangeListener {
    private static final String TAG = "experienceFragment";
    private RecyclerView recyclerView;
    private ExperienceRecyclerViewAdapter recyclerViewAdapter;
    private List<Experience> experienceList;
    private FloatingActionButton fab;
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;
    private Button saveButton;
    private EditText experienceTitle;
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
        recyclerViewAdapter = new ExperienceRecyclerViewAdapter(getContext(), experienceList);
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
        recyclerView = getView().findViewById(R.id.recyclerview_experience_list);
        fab = getView().findViewById(R.id.fab_experience);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        experienceList = new ArrayList<>();
        imageButton = getView().findViewById(R.id.image_button_preview_experience);
        nextButton = getView().findViewById(R.id.next_button_in_experience);

    }
    private void createPopDialog() {
        builder = new AlertDialog.Builder(getContext());
        View view = getLayoutInflater().inflate(R.layout.experience_popup, null);
        experienceTitle = view.findViewById(R.id.experience_title);
        instituteName = view.findViewById(R.id.institute_name_experience);
        startDate = view.findViewById(R.id.start_date_of_experience);
        endDate = view.findViewById(R.id.end_date_of_experience);

        saveButton = view.findViewById(R.id.save_button_experience);
        startDate.setOnFocusChangeListener(this);
        endDate.setOnFocusChangeListener(this);

        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.show();
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!experienceTitle.getText().toString().isEmpty()
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

        String title = experienceTitle.getText().toString().trim();
        String description = instituteName.getText().toString().trim();
        String sDate = startDate.getText().toString().trim();
        String eDate = endDate.getText().toString().trim();
        Experience experience = new Experience(title,description,sDate,eDate);

        Snackbar.make(v, "Item Saved",Snackbar.LENGTH_SHORT)
                .show();
        experienceList.add(experience);
        Log.d(TAG, "saveItem: "+ experienceList.get(0).getCompanyName().toString() );

        alertDialog.dismiss();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_experience, container, false);
        bundle = getArguments();

        return view;
    }
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()){
            case R.id.start_date_of_experience:
                if (hasFocus) {
                    calendar = Calendar.getInstance();
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    int month = calendar.get(Calendar.MONTH);
                    int year = calendar.get(Calendar.YEAR);

                    datePickerDialog = new DatePickerDialog(getContext(),
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                    startDate.setText(dayOfMonth + "/" +( month+1 )+ "/ " + year);
                                }
                            }, year, month, day);
                    datePickerDialog.show();
                }
                break;
            case R.id.end_date_of_experience:
                if (hasFocus) {
                    calendar = Calendar.getInstance();
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    int month = calendar.get(Calendar.MONTH);
                    int year = calendar.get(Calendar.YEAR);

                    datePickerDialog = new DatePickerDialog(getContext(),
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                    endDate.setText(dayOfMonth + "/" + (month+1) + "/ " + year);
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
            case R.id.next_button_in_experience:


            case R.id.image_button_preview_experience:
                Intent intent = new Intent(getActivity().getApplication(), PreviewActivity.class);
                intent.putExtra("bundle",bundle);

                startActivity(intent);
        }

    }
}