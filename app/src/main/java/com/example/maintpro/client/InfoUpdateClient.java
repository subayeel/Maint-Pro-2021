package com.example.maintpro.client;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.maintpro.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class InfoUpdateClient extends AppCompatActivity {

    private ImageView updateClientImage;
    private TextView updateClientName,updateClientEmail,updateClientComplaint;
    private Button updateClientBtn,deleteClientBtn;

    private String name,email,image,complaint;

    private final int REQ = 1;
    private Bitmap bitmap;

    private StorageReference storageReference;
    private DatabaseReference reference;

    private ProgressDialog pd;

    private String downloadUrl , category , uniqueKey;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_update_client);

        name = getIntent().getStringExtra("name");
        email = getIntent().getStringExtra("email");
        image = getIntent().getStringExtra("image");
        complaint = getIntent().getStringExtra("complaint");

        uniqueKey = getIntent().getStringExtra("key");
        category = getIntent().getStringExtra("category");


        updateClientImage = findViewById(R.id.updateClientImage);
        updateClientName = findViewById(R.id.updateClientName);
        updateClientEmail = findViewById(R.id.updateClientEmail);
        updateClientComplaint = findViewById(R.id.updateClientComplaint);
        updateClientBtn = findViewById(R.id.updateClientBtn);
        deleteClientBtn = findViewById(R.id.deleteClientBtn);

        reference = FirebaseDatabase.getInstance().getReference().child("client");
        storageReference = FirebaseStorage.getInstance().getReference();

        pd = new ProgressDialog(this);

        try {
            Picasso.get().load(image).into(updateClientImage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        updateClientEmail.setText(email);
        updateClientName.setText(name);
        updateClientComplaint.setText(complaint);

        updateClientImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        updateClientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = updateClientName.getText().toString();
                email = updateClientEmail.getText().toString();
                complaint = updateClientComplaint.getText().toString();

                checkValidation();
            }
        });

        deleteClientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData();
            }
        });


    }

    private void deleteData() {
        reference.child(category).child(uniqueKey).removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(InfoUpdateClient.this, "Client Info Deleted Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(InfoUpdateClient.this, UpdateClient.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |  Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(InfoUpdateClient.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void checkValidation() {
        if(name.isEmpty()){
            updateClientName.setError("Empty");
            updateClientName.requestFocus();
        }else if(email.isEmpty()){
            updateClientEmail.setError("Empty");
            updateClientEmail.requestFocus();
        }else if(complaint.isEmpty()){
            updateClientComplaint.setError("Empty");
            updateClientComplaint.requestFocus();
        }else if(bitmap == null){
            updateData(image);
        }else {
            uploadImage();
        }
    }

    private void uploadImage() {
        pd.setMessage("Uploading...");
        pd.show();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,baos);
        byte[] finalimg = baos.toByteArray();
        final StorageReference filePath;
        filePath = storageReference.child("Client").child(finalimg+"jpg");
        final UploadTask uploadTask = filePath.putBytes(finalimg);
        uploadTask.addOnCompleteListener(InfoUpdateClient.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful()){
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    downloadUrl = String.valueOf(uri);
                                    updateData(downloadUrl);
                                }
                            });
                        }
                    });
                }else {
                    pd.dismiss();
                    Toast.makeText(InfoUpdateClient.this, "Something Went wrong!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateData(String s) {

        HashMap hp = new HashMap();
        hp.put("name",name);
        hp.put("email",email);
        hp.put("complaint",complaint);
        hp.put("image",s);


        reference.child(category).child(uniqueKey).updateChildren(hp).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(InfoUpdateClient.this, "Client Info Updated Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(InfoUpdateClient.this, UpdateClient.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |  Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(InfoUpdateClient.this, "Something went Wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void openGallery() {
        Intent pickImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickImage, REQ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQ && resultCode == RESULT_OK){
            Uri uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            updateClientImage.setImageBitmap(bitmap);
        }
    }

}