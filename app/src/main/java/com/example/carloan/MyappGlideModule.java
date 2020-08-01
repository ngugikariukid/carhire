package com.example.carloan;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.google.firebase.storage.StorageReference;
//import com.google.firebase.database.core.Context;
import com.firebase.ui.storage.images.FirebaseImageLoader;

import java.io.InputStream;
import java.net.ContentHandler;

@GlideModule

public class MyappGlideModule extends AppGlideModule {

    @Override
    public void registerComponents(Context context, Glide glide, Registry registry){
        //Register firebase image loader to handle storageReference
        registry.append(StorageReference.class, InputStream.class, new FirebaseImageLoader.Factory());
    }
    //GlideApp.with(MyAdapter);
}



