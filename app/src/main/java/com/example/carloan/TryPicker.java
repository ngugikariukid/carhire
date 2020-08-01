package com.example.carloan;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TryPicker extends AppCompatActivity implements
        View.OnClickListener {

    Button pickb, returnb, book;
    private FirebaseAuth mAuth;
    EditText pick, returned;
    private int mYear, mMonth, mDay;
    int pickk, returnn;
    String pickString;
    String returnString;
    public DatabaseReference reference;
    DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ordercar);
        pickb=(Button)findViewById(R.id.pickb);
        book = (Button) findViewById(R.id.book);
        returnb=(Button)findViewById(R.id.returnb);
        pick=(EditText)findViewById(R.id.pick);
        returned=(EditText)findViewById(R.id.returned);

        databaseReference = FirebaseDatabase.getInstance().getReference("orders");
        pickb.setOnClickListener(this);
        returnb.setOnClickListener(this);
        book.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v == pickb) {
            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            pick.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            pickString = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                            pickk = dayOfMonth + (monthOfYear + 1) +  year;
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v == returnb) {
            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            returned.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            returnString = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                            returnn = dayOfMonth  + (monthOfYear + 1)  + year;
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }

        if(v == book){
            int newD = returnn-pickk;
            Boolean defaultStatus = false;
            String carId, pric;
            Intent intent =  getIntent();
            carId = intent.getStringExtra("carId");
            pric = intent.getStringExtra("priceperD");
            int pricetouse = Integer.parseInt(pric);
            int totalprice= pricetouse * newD;
            String todayDate = Calendar.getInstance().getTime().toString();
            mAuth = FirebaseAuth.getInstance();
            String LoggedUserEmail= mAuth.getCurrentUser().getEmail();
            final String orderkey = databaseReference.push().getKey();
            insertData(orderkey, todayDate, LoggedUserEmail, carId, pickString, returnString, totalprice, false);
            startActivity(new Intent(TryPicker.this, MainPage.class));
        }
    }
    public void insertData(String orderKey, String orderDate, String bookerEmail, String CarId, String pickDate, String returnDate, int totalPrice, Boolean status){
        OrdersModel o = new OrdersModel(orderKey, orderDate, bookerEmail, CarId, pickDate, returnDate, totalPrice, status);
        databaseReference.child(orderKey).setValue(o);
        Toast.makeText(this, "Your Order has been reserved", Toast.LENGTH_LONG);
    }
    public void bookCar(){

    }

}