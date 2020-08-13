package com.example.carloan;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

public class Deposit extends AppCompatActivity {
    private Button btnBack, deposit;
    private DatabaseReference mDatabase;
    EditText amount;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addmoney);
        btnBack = (Button) findViewById(R.id.btn_back);
        deposit = (Button) findViewById(R.id.deposit);
        amount = (EditText) findViewById(R.id.amount);
        mDatabase = FirebaseDatabase.getInstance().getReference("accountDeposits");
        mAuth = FirebaseAuth.getInstance();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        deposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String amountt = amount.getText().toString().trim();
                String LoggedUserEmail= mAuth.getCurrentUser().getEmail();

                if (TextUtils.isEmpty(amountt)) {
                    Toast.makeText(getApplicationContext(), "Enter Amount!", Toast.LENGTH_SHORT).show();
                    return;
                }
                insert( LoggedUserEmail, amountt);
                startActivity(new Intent(Deposit.this, Account.class));
                finish();
                //update("increaseTotal", LoggedUserEmail, amountt);
            }
        });
    }

    private void insert(String email, String amount) {
        AccountModel a = new AccountModel(email, amount);
        String key = mDatabase.push().getKey();
        mDatabase.child(key).setValue(a);

        Toast.makeText(getApplicationContext(), "Account Deposited Successfully!", Toast.LENGTH_SHORT).show();
    }
}
