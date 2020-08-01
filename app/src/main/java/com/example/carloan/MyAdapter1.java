package com.example.carloan;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;


public class MyAdapter1 extends RecyclerView.Adapter<MyAdapter1.ViewHolder> {

    private Context context;
    private List<CarModel> uploads;
    FirebaseStorage storage = FirebaseStorage.getInstance();

    public MyAdapter1(Context context, List<CarModel> uploads) {
        this.uploads = uploads;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        CarModel upload = uploads.get(position);
        holder.nameyear.setText(upload.getModel());
        final String c = uploads.get(position).getPicURL();

        /*StorageReference storageReference = FirebaseStorage.getInstance().getReference("images/"+c);
        Glide.with(context)
                .load(storageReference)
                .into(holder.profilepic);*/

        //Glide.with(context).load(upload.getPicURL()).into(holder.profilepic);
        //Picasso.get().load(upload.getPicURL()).into(holder.profilepic);



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

        // Reference to an image file in Cloud Storage





    }

    @Override
    public int getItemCount() {
        return uploads.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewName;
        public ImageView profilepic;
        TextView nameyear, colortrans, available, price;

        public ViewHolder(View itemView) {
            super(itemView);
            nameyear = (TextView) itemView.findViewById(R.id.nameyear);
            profilepic = (ImageView) itemView.findViewById(R.id.profilepic);

        }
    }
}
