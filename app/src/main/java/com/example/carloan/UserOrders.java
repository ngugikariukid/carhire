package com.example.carloan;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class UserOrders extends AppCompatActivity {

    private DatabaseReference reference;
    private FirebaseAuth mAuth;
    private RecyclerView recyclerView;
    private ArrayList<OrdersModel> list;
    private UserAdapter adapter;
    FirebaseStorage storage;
    StorageReference storageReference;
    //public DatabaseReference reference;
    DatabaseReference databaseReference;

    private static final String TAG = "AllCandidates";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.allorders);

        //reference= FirebaseDatabase.getInstance().getReference().child("candidates");
        recyclerView = (RecyclerView) findViewById(R.id.myRecycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<OrdersModel>();

        reference = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        String LoggedUserEmail= mAuth.getCurrentUser().getEmail();

        Query presidentquery = reference.child("orders").orderByChild("bookerEmail").equalTo(LoggedUserEmail);
        presidentquery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        OrdersModel p = dataSnapshot1.getValue(OrdersModel.class);
                        list.add(p);
                    }
                    adapter = new UserAdapter(UserOrders.this, list);
                    recyclerView.setAdapter(adapter);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    };

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.vieworders, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        switch(id){
            case R.id.logout:
                startActivity(new Intent(this, LoginActivity.class));
                Toast.makeText(getApplicationContext(),"Logged Out", Toast.LENGTH_LONG).show();
                return true;
            case R.id.search:
                startActivity(new Intent(this, Search.class));
                //Toast.makeText(getApplicationContext(),"Logged Out", Toast.LENGTH_LONG).show();
                return true;
            case R.id.orders:
                startActivity(new Intent(this, Orders.class));
                //Toast.makeText(getApplicationContext(),"Logged Out", Toast.LENGTH_LONG).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
