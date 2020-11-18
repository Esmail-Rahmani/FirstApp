package com.example.cvmaaker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.Toast;
import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import adapter.ViewFragmentAdapter;
public class MainActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private Button mainButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidget();
        mainButton.setOnClickListener(this);

    }

    private void createPdf() {
        PdfDocument myPdfDocument = new PdfDocument();
        Paint mPaint = new Paint();
        PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(
                250,400,1).create();
        PdfDocument.Page myPage = myPdfDocument.startPage(myPageInfo);
        Canvas canvas = myPage.getCanvas();
        canvas.drawText("welcome here",40,50,mPaint);
        myPdfDocument.finishPage(myPage);

        File file = new File(Environment.getExternalStorageDirectory(),"/hello.pdf");

        try {
            myPdfDocument.writeTo(new FileOutputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        myPdfDocument.close();

    }

    private void initWidget() {
        mainButton = findViewById(R.id.main_button);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_button:
                mainButton.setVisibility(View.GONE);
                FragmentTransaction fr = getSupportFragmentManager().beginTransaction();
                fr.replace(R.id.main_contener,new PersonalInformationFragment()).commit();


        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//        if (isChecked){
////            toolbar.setBackground(getDrawable(R.color.colorAccent));
//            tabLayout.setBackground(getDrawable(R.color.colorAccent));
////            imageButton.setBackground(getDrawable(R.color.colorAccent));
//        }else{
////            toolbar.setBackground(getDrawable(R.color.colorPrimary));
////            imageButton.setBackground(getDrawable(R.color.colorPrimary));
//            tabLayout.setBackground(getDrawable(R.color.colorPrimary));
//
//        }
    }
}