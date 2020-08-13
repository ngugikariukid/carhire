package com.example.carloan;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

//import com.squareup.picasso.Picasso;
import com.bumptech.glide.Glide;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    private List<CarModel> list_data;
    ArrayList <CarModel> candidates;
    StorageReference storageReference;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    public List<CarModel> shopsList;

    public MyAdapter (Context c, ArrayList<CarModel> p, List<CarModel>list_data){
        context = c;
        candidates =p;
        this.list_data = list_data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.cardview, parent, false));
    }
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        String model = candidates.get(position).getModel();
        String year = candidates.get(position).getYear();
        String color = candidates.get(position).getColor();
        String trans = candidates.get(position).getTransmission();
        Boolean availab = candidates.get(position).getAvailability();
        final String pric = candidates.get(position).getPrice();
        final String carId = candidates.get(position).getKey();
        String url = candidates.get(position).getPicURL();

        String Storage_Path = "images/";
        StorageReference mImageRef = FirebaseStorage.getInstance().getReference(Storage_Path + url +".jpg");
        Glide.with(context).load(mImageRef).into(holder.profilepic);

        String namyea = model +" | " + year;
        holder.nameyear.setText(namyea);
        String coltra = color + " | " + trans;
        holder.colortrans.setText(coltra);
        String pricee = "Ksh. " + pric +" per DAY";

        if(availab){
            holder.available.setText("AVAILABLE");
        }else{
            holder.available.setText("Not Available");
            holder.book.setVisibility(View.GONE);
        }
        holder.price.setText(pricee);
        holder.book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TryPicker.class);
                intent.putExtra("carId", carId);
                intent.putExtra("priceperD", pric);
                context.startActivity(intent);
            }
        });
        final String c = candidates.get(position).getPicURL();
        StorageReference storageRef = storage.getReference();
        StorageReference islandRef = storageRef.child("images/"+c);

        final long ONE_MEGABYTE = 1024 * 1024;
        islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                DisplayMetrics dm = new DisplayMetrics();
                //holder.profilepic.setMaxHeight(dm.heightPixels);
                //holder.profilepic.setMinimumWidth(dm.widthPixels);
                holder.profilepic.setImageBitmap(bm);
                //Glide.with(context).load(c).into(holder.profilepic);
                // Data for "images/island.jpg" is returns, use this as needed
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
    }

    @Override
    public int getItemCount() {
        return candidates.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView nameyear, colortrans, available, price;
        ImageView profilepic;
        Button book;

        public MyViewHolder(View itemView) {
            super(itemView);
            nameyear = (TextView) itemView.findViewById(R.id.nameyear);
            colortrans = (TextView) itemView.findViewById(R.id.colortrans);
            available = (TextView) itemView.findViewById(R.id.availability);
            price = (TextView) itemView.findViewById(R.id.price);
            book = (Button) itemView.findViewById(R.id.book);
            profilepic = (ImageView) itemView.findViewById(R.id.profilepic);
        }
    }

}
