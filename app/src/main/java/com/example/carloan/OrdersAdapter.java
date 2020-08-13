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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.MyViewHolder> {

    Context context;
    ArrayList <OrdersModel> candidates;
    private DatabaseReference reference;
    int total =0, balannce, afterd;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    public OrdersAdapter (Context c, ArrayList<OrdersModel> p){
        context = c;
        candidates =p;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.orderscardview, parent, false));
    }
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final String email = candidates.get(position).getBookerEmail();
        final String carId = candidates.get(position).getCarId();
        String pick = candidates.get(position).getPickDate();
        String returndate = candidates.get(position).getReturnDate();
        final int amount = candidates.get(position).getTotalPrice();
        final String orderid = candidates.get(position).getOrderkey();
        String insertorder  = "Order ID: "+orderid;
        Boolean orderstatus = candidates.get(position).getStatus();

        if(orderstatus){ //The order has not been dealt with
            //holder.approve.setEnabled(false);
            holder.approve.setVisibility(View.GONE);
            //holder.deny.setEnabled(false);
            holder.deny.setVisibility(View.GONE);
            holder.texttu.setText("This order has been approaved");
        }else{
            holder.texttu.setText(insertorder);
        }

        DatabaseReference reference;
        reference = FirebaseDatabase.getInstance().getReference();
        Query getcar = reference.child("cars").orderByChild("key").equalTo(carId);
        getcar.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String model;
                String year;
                System.out.println("Datasnap: " +dataSnapshot);
                if (dataSnapshot.exists()) {
                    System.out.print("Inside datasnapshot");
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        CarModel p = dataSnapshot1.getValue(CarModel.class);
                        model = p.getModel();
                        year = p.getYear();
                        System.out.println("fetched data inside datasnapshot: " + model + " and " + year);
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



        String insertamount = "Ksh. "+amount;
        String emaill = "Email: "+ email;
        holder.customer.setText(emaill);
        String fromtoo = "From: " + pick + " | " + returndate;
        holder.fromto.setText(fromtoo);

        holder.price.setText(insertamount);
        holder.deny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                denyOrder(orderid);
            }
        });
        holder.approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCarAvailability(carId);
                setOrderStatus(orderid);
                deductBalance(email, amount);
                //Here we accept the order. And set its availability of car to false and set the order to True
                context.startActivity(new Intent(context, AdminHome.class));
            }
        });
    }

    private void denyOrder(String orderid) {
        DatabaseReference mDatabasee = FirebaseDatabase.getInstance().getReference();
        DeletedModel a = new DeletedModel(orderid);
        mDatabasee.child("RejectedOrders").setValue(a);
        Toast.makeText(context, "Order Rejected!", Toast.LENGTH_LONG);
        context.startActivity(new Intent(context, AdminHome.class));
    }

    private void deductBalance(final String Email, final int amount) {

        reference = FirebaseDatabase.getInstance().getReference();
        Query presidentquery = reference.child("accountDeposits").orderByChild("userEmail").equalTo(Email);
        presidentquery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        AccountModel g = dataSnapshot1.getValue(AccountModel.class);
                        balannce = Integer.parseInt(g.getBalance());
                        total = total + balannce; //Total amount in user account
                    }
                    //bal.setText("Bal: Ksh. "+total);
                    afterd = total - amount;
                    createExpense(Email, amount);
                    //complete(Email, afterd);
                    //Now update user balance
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void createExpense(String email, int amount) {
        DatabaseReference mDatabasee = FirebaseDatabase.getInstance().getReference();
        ExpensesModel a = new ExpensesModel(email, amount);
        String key = mDatabasee.push().getKey();
        mDatabasee.child("Expenses").child(key).setValue(a);
    }

    public void complete(String email, int afterd) {
        DatabaseReference mDatabasee = FirebaseDatabase.getInstance().getReference();
        CustomerBalance cb = new CustomerBalance(email, afterd);
        String key = mDatabasee.push().getKey();
        mDatabasee.child("customerBalance").child(key).setValue(cb);
        //Toast.makeText(context, "Car Availability Updated!", Toast.LENGTH_LONG);
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

        TextView nameyear, customer, fromto, price, texttu;
        Button deny, approve;

        public MyViewHolder(View itemView) {
            super(itemView);
            nameyear = (TextView) itemView.findViewById(R.id.nameyear);
            customer = (TextView) itemView.findViewById(R.id.customer);
            fromto = (TextView) itemView.findViewById(R.id.fromto);
            price = (TextView) itemView.findViewById(R.id.price);
            texttu = (TextView) itemView.findViewById(R.id.texttu);
            deny = (Button) itemView.findViewById(R.id.deny);
            approve = (Button) itemView.findViewById(R.id.approve);
        }
    }


}

