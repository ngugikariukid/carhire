package com.example.carloan;

import android.app.DatePickerDialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.preference.EditTextPreference;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.text.BreakIterator;
import java.util.Calendar;


public class BookCar extends AppCompatActivity {

    private EditText pick, returned;
    private Button pickb, returnb, book, btnBack;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private int mYear, mMonth, mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ordercar);

        pick = (EditText) findViewById(R.id.pick);
        returned = (EditText) findViewById(R.id.returned);
        pickb = (Button) findViewById(R.id.pickb);
        returnb = (Button) findViewById(R.id.returnb);

        btnBack = (Button) findViewById(R.id.btn_back);
        book = (Button) findViewById(R.id.btn_back);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}
