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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import com.squareup.picasso.Picasso;

public class AllCars extends AppCompatActivity {

    private DatabaseReference reference;
    private RecyclerView recyclerView;
    private ArrayList<CarModel> list;
    private List<CarModel>list_data;
    private DatabaseReference db;
    private MyAdapter adapter;
    FirebaseStorage storage;
    StorageReference storageReference;

    private static final String TAG = "AllCandidates";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_cars);

        //reference= FirebaseDatabase.getInstance().getReference().child("candidates");
        recyclerView = (RecyclerView) findViewById(R.id.myRecycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<CarModel>();
        list_data=new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference();

        Query presidentquery = reference.child("cars");
        presidentquery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        CarModel p = dataSnapshot1.getValue(CarModel.class);
                        String userId = dataSnapshot1.getKey();
                        list.add(p);
                        CarModel listData=dataSnapshot1.getValue(CarModel.class);
                        list_data.add(listData);
                    }
                    adapter = new MyAdapter(AllCars.this, list, list_data);
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