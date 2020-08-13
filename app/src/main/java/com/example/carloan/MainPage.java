package com.example.carloan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;


public class MainPage extends AppCompatActivity {
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private Button browse, orders, btnBack, balance;
    private FirebaseAuth.AuthStateListener authListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_user_home);

        browse = (Button) findViewById(R.id.browse);
        orders = (Button) findViewById(R.id.orders);
        btnBack = (Button) findViewById(R.id.btn_back);
        balance = (Button) findViewById(R.id.account);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainPage.this, LoginActivity.class));
                //finish();
            }
        });

        browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainPage.this, AllCars.class));
                //finish();
            }
        });
        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainPage.this, UserOrders.class));
                //finish();
            }
        });
        balance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainPage.this, Account.class));
                //finish();
            }
        });
    }
}