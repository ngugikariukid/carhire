package com.example.carloan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Account extends AppCompatActivity {
    private ProgressBar progressBar;
    private Button btnBack, balance;
    private DatabaseReference reference;
    TextView name, dl, bal, email;
    private FirebaseAuth mAuth;
    int total =0, balannce, totalExp = 0,  expenses;
    private FirebaseAuth.AuthStateListener authListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accountdetails);
        btnBack = (Button) findViewById(R.id.btn_back);
        balance = (Button) findViewById(R.id.load);
        name = (TextView) findViewById(R.id.name);
        dl = (TextView) findViewById(R.id.dl);
        bal = (TextView) findViewById(R.id.balance);
        email = (TextView) findViewById(R.id.email);
        reference = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        getExpenses();
        populate();
        getBalance();

        balance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Account.this, Deposit.class));
                finish();
            }
        });
    }

    private void getExpenses() {
        String LoggedUserEmail= mAuth.getCurrentUser().getEmail();
        Query presidentquery = reference.child("Expenses").orderByChild("email").equalTo(LoggedUserEmail);
        presidentquery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        ExpensesModel g = dataSnapshot1.getValue(ExpensesModel.class);
                        expenses = g.getAmount();
                        totalExp = totalExp + expenses;
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void getBalance() {
        String LoggedUserEmail= mAuth.getCurrentUser().getEmail();
        Query presidentquery = reference.child("accountDeposits").orderByChild("userEmail").equalTo(LoggedUserEmail);
        presidentquery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        AccountModel g = dataSnapshot1.getValue(AccountModel.class);
                        balannce = Integer.parseInt(g.getBalance());
                        total = total + balannce;
                    }
                    int toset = total - totalExp;
                    bal.setText("Bal: Ksh. "+toset);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }


    private void populate() {
        String LoggedUserEmail= mAuth.getCurrentUser().getEmail();
        Query presidentquery = reference.child("users").orderByChild("email").equalTo(LoggedUserEmail);
        presidentquery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        RegisterModel p = dataSnapshot1.getValue(RegisterModel.class);
                        name.setText(p.getFirstname() + " " + p.getLastname());
                        dl.setText("DL: "+p.getDl());
                        email.setText(p.getEmail());
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
