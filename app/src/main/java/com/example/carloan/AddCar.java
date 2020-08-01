package com.example.carloan;

import android.content.ContentResolver;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import android.widget.ImageView;

import java.io.IOException;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddCar extends AppCompatActivity {

    private EditText model, year, color, trans, price;
    private Button addthecar, back;
    private ProgressBar progressBar;

    // Folder path for Firebase Storage.
    private String Storage_Path = "images/";

    // Root Database Name for Firebase Database.
    private String Database_Path = "cars";

    // Creating URI.
    private Uri FilePathUri;
    private ProgressDialog progressDialog ;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    int Image_Request_Code = 7;

    private static final String TAG = "AddCar";

    private Button btnChoose;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addcar);
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference(Database_Path);

        model = (EditText) findViewById(R.id.model);
        year = (EditText) findViewById(R.id.year);
        color = (EditText) findViewById(R.id.color);
        trans = (EditText) findViewById(R.id.transmission);
        price = (EditText) findViewById(R.id.price);

        addthecar = (Button) findViewById(R.id.addthecar);
        btnChoose = (Button) findViewById(R.id.btnChoose);
        back = (Button) findViewById(R.id.back);


        imageView = (ImageView) findViewById(R.id.imgView);

        //btnChoose = (Button) findViewById(R.id.btnChoose);


        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        // Assigning Id to ProgressDialog.
        progressDialog = new ProgressDialog(AddCar.this);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Creating intent.
                Intent intent = new Intent();

                // Setting intent type as image to select image from phone storage.
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Please Select Image"), Image_Request_Code);
            }
        });

        addthecar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);
//                //create user
                UploadImageFileToFirebaseStorage();

                startActivity(new Intent(AddCar.this, AdminHome.class));
                //finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {

            FilePathUri = data.getData();

            try {

                // Getting selected image into Bitmap.
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri);

                // Setting up bitmap selected image into ImageView.
                imageView.setImageBitmap(bitmap);

                // After selecting image change choose button above text.
                btnChoose.setText("Image Selected");

            }
            catch (IOException e) {

                e.printStackTrace();
            }
        }
    }
    // Creating Method to get the selected image file Extension from File Path URI.
    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        // Returning the file Extension.
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;

    }

    // Creating UploadImageFileToFirebaseStorage method to upload image on storage.
    public void UploadImageFileToFirebaseStorage() {

        // Checking whether FilePathUri Is empty or not.
        if (FilePathUri != null) {

            final String mode = model.getText().toString().trim();
            final String yea = year.getText().toString().trim();
            final String col = color.getText().toString().trim();
            final String tran = trans.getText().toString().trim();
            final String pri = price.getText().toString().trim();

            final Boolean avalilable = true;


            if (TextUtils.isEmpty(mode)) {
                Toast.makeText(getApplicationContext(), "Enter Car Model!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(yea)) {
                Toast.makeText(getApplicationContext(), "Enter Year!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(col)) {
                Toast.makeText(getApplicationContext(), "Enter Color!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(tran)) {
                Toast.makeText(getApplicationContext(), "Enter Transmission!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(pri)) {
                Toast.makeText(getApplicationContext(), "Enter Price!", Toast.LENGTH_SHORT).show();
                return;
            }
            progressDialog.setTitle("Data is Uploading...");
            progressDialog.show();
            final String userId = databaseReference.push().getKey();

            StorageReference storageReference2nd = storageReference.child(Storage_Path + userId + "." + GetFileExtension(FilePathUri));

            storageReference2nd.putFile(FilePathUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Getting image name from EditText and store into string variable.
                            final String TempImageName = userId+".jpg";
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Image Uploaded Successfully ", Toast.LENGTH_LONG).show();

                            @SuppressWarnings("VisibleForTests")
                            CarModel c = new CarModel(mode, userId, yea, col, tran, pri, TempImageName, avalilable);

                            //CarModel imageUploadInfo = new CarModel(TempImageName, taskSnapshot.getDownloadUrl().toString());

                            // Getting image upload ID.
                            String ImageUploadId = userId;

                            // Adding image upload id s child element into databaseReference.
                            databaseReference.child(ImageUploadId).setValue(c);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Hiding the progressDialog.
                            progressDialog.dismiss();
                            // Showing exception error message.
                            Toast.makeText(AddCar.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })

                    // On progress change upload time.
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                            // Setting progressDialog Title.
                            progressDialog.setTitle("Image is Uploading...");

                        }
                    });
        }
        else {

            Toast.makeText(AddCar.this, "Please Select Image or Add Image Name", Toast.LENGTH_LONG).show();
        }
    }



    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}