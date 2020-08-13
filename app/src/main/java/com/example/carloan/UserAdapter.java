package com.example.carloan;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {

    Context context;
    ArrayList <OrdersModel> candidates;
    private DatabaseReference reference;
    Boolean check;

    public UserAdapter (Context c, ArrayList<OrdersModel> p){
        context = c;
        candidates =p;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.userorderscardview, parent, false));
    }
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        String email = candidates.get(position).getBookerEmail();
        final String carId = candidates.get(position).getCarId();
        String pick = candidates.get(position).getPickDate();
        String returndate = candidates.get(position).getReturnDate();
        int amount = candidates.get(position).getTotalPrice();
        final String orderid = candidates.get(position).getOrderkey();
        String insertorder  = "Order ID: "+orderid;

        Boolean statusorder = candidates.get(position).getStatus();
        if (statusorder == true) { //has been approaved
            holder.status.setText("Order was APPROAVED");
        } else {
            holder.status.setText("Order PENDING");
        }


        DatabaseReference reference;
        reference = FirebaseDatabase.getInstance().getReference();
        Query getcar = reference.child("cars").orderByChild("key").equalTo(carId);
        getcar.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String model;
                String year;
                //System.out.println("Datasnap: " +dataSnapshot);
                if (dataSnapshot.exists()) {
                    //System.out.print("Inside datasnapshot");
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        CarModel p = dataSnapshot1.getValue(CarModel.class);
                        model = p.getModel();
                        year = p.getYear();
                        //System.out.println("fetched data inside datasnapshot: " + model + " and " + year);
                        holder.nameyear.setText(model + " | "+ year);
                    }
                }else {
                    System.out.print("No records from Query");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("Inside the error");
            }
        });

        holder.texttu.setText(insertorder);
        String insertamount = "Ksh. "+amount;
        String emaill = "Email: "+ email;
        //holder.customer.setText(emaill);
        String fromtoo = "From: " + pick + " | " + returndate;
        holder.fromto.setText(fromtoo);

        holder.price.setText(insertamount);
    }

    private void setOrderStatus(String orderid) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("orders").child(orderid).child("status").setValue(true);
        Toast.makeText(context, "Order Updated!", Toast.LENGTH_LONG);
    }
    private void setCarAvailability(String carId) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("cars").child(carId).child("availability").setValue(false);
        Toast.makeText(context, "Car Availability Updated!", Toast.LENGTH_LONG);
    }
    @Override
    public int getItemCount() {
        return candidates.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView nameyear,  fromto, price, texttu, status;


        public MyViewHolder(View itemView) {
            super(itemView);
            nameyear = (TextView) itemView.findViewById(R.id.nameyear);

            fromto = (TextView) itemView.findViewById(R.id.fromto);
            price = (TextView) itemView.findViewById(R.id.price);
            texttu = (TextView) itemView.findViewById(R.id.texttu);
            status = (TextView) itemView.findViewById(R.id.status);
        }
    }


}

