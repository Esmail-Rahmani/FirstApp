package com.example.cvmaaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;

import java.util.ArrayList;

import model.Education;

public class PreviewActivity extends AppCompatActivity {
    PDFView mPdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");




    }

    private void printCV(Bundle bundle) {
        ArrayList<Education> educations =  new ArrayList<>();
        educations = (ArrayList<Education>)bundle.getSerializable("EducationList");
        Toast.makeText(this, ""+ educations.get(0).getCourseTitle().toString(), Toast.LENGTH_SHORT).show();
    }


}