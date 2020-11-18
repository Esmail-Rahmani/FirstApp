package com.example.cvmaaker;

import android.app.DatePickerDialog;
import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import adapter.AchievementRecyclerViewAdapter;
import model.Achievement;
import model.DynamicAchievementsView;

public class AchievementsFragment extends Fragment implements View.OnClickListener, View.OnFocusChangeListener {
    private static final String TAG = "ListActivity";
    private RecyclerView recyclerView;
    private AchievementRecyclerViewAdapter recyclerViewAdapter;
    private List<Achievement> achievementList;
    private FloatingActionButton fab;
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;
    private Button saveButton;
    private Button nextButton;
    private EditText achievementTitle;
    private EditText achievementDescription;
    private EditText dateOfAchievement;
    private DatePickerDialog datePickerDialog;
    private Calendar calendar;
    private ImageButton imageButton;
    private Bundle bundle;


    @Override
    public void  onStart() {
        super.onStart();
        initWidget();
        recyclerViewAdapter = new AchievementRecyclerViewAdapter(getContext(), achievementList);
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
        recyclerView = getView().findViewById(R.id.recyclerview_achievement_list);
        fab = getView().findViewById(R.id.fab_achievement);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        achievementList = new ArrayList<>();
        nextButton = getView().findViewById(R.id.next_button_in_achievement_information);
        imageButton = getView().findViewById(R.id.image_button_preview_achievement);

    }
    private void createPopDialog() {
        builder = new AlertDialog.Builder(getContext());
        View view = getLayoutInflater().inflate(R.layout.acievement_popup, null);
        achievementTitle = view.findViewById(R.id.achievement_title);
        achievementDescription = view.findViewById(R.id.achievement_description);
        dateOfAchievement = view.findViewById(R.id.date_of_achievement);
        saveButton = view.findViewById(R.id.saveButton);
        dateOfAchievement.setOnFocusChangeListener(this);

        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.show();
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!achievementTitle.getText().toString().isEmpty()
                        && !achievementDescription.getText().toString().isEmpty()
                        && !dateOfAchievement.getText().toString().isEmpty()) {
                    saveItem(v);
                }else {
                    Snackbar.make(v, "Empty Fields not Allowed", Snackbar.LENGTH_SHORT)
                            .show();
                }
            }
        });
    }
    private void saveItem(View v) {

        String title = achievementTitle.getText().toString().trim();
        String description = achievementDescription.getText().toString().trim();
        String date = dateOfAchievement.getText().toString().trim();
        Achievement achievement = new Achievement(title,description,date);

        Snackbar.make(v, "Item Saved",Snackbar.LENGTH_SHORT)
                .show();
        achievementList.add(achievement);
        alertDialog.dismiss();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_achievements, container, false);
        bundle = getArguments();

    return view;
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
                            dateOfAchievement.setText(dayOfMonth + "/" + (month+1) + "/ " + year);
                        }
                    }, year, month, day);
            datePickerDialog.show();
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.next_button_in_achievement_information:

                    EducationFragment educationFragment = new EducationFragment();
                    educationFragment.setArguments(bundle);
                    getFragmentManager().beginTransaction()
                            .replace(R.id.main_contener, educationFragment)
                            .commit();


                break;
            case R.id.image_button_preview_achievement:
                Intent intent = new Intent(getActivity().getApplication(), PreviewActivity.class);
                intent.putExtra("bundle",bundle);

                startActivity(intent);
        }

    }
}