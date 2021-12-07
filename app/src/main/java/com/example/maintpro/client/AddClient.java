package com.example.maintpro.client;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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


import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AddClient extends AppCompatActivity {

    private ImageView addClientImage;
    private EditText addClientName , addClientEmail , addClientComplaint ;
    private Spinner addClientCategory;
    private Button addClientBtn;
    private final int REQ = 1;
    private Bitmap bitmap = null;
    private String category;
    private String name, email , complaint , downloadUrl="";
    private ProgressDialog pd;
    private StorageReference storageReference;
    private DatabaseReference reference,dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);

        addClientImage = findViewById(R.id.addClientImage);
        addClientName = findViewById(R.id.addClientName);
        addClientEmail = findViewById(R.id.addClientEmail);
        addClientCategory = findViewById(R.id.addClientCategory);
        addClientBtn = findViewById(R.id.addClientBtn);
        addClientComplaint = findViewById(R.id.addClientComplaint);

        pd = new ProgressDialog(this);

        reference = FirebaseDatabase.getInstance().getReference().child("client");
        storageReference = FirebaseStorage.getInstance().getReference();

        String [] items = new String[]{"Select Complaint Subject" , "Civil Dept-1" , "Civil Dept-2" , "Civil Dept-3" ,"Civil Dept-4"};
        addClientCategory.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items));

        addClientCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = addClientCategory.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        addClientImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        addClientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
            }
        });

    }

    private void checkValidation() {
        name = addClientName.getText().toString();
        email = addClientEmail.getText().toString();
        complaint = addClientComplaint.getText().toString();


        if(name.isEmpty()){
            addClientName.setError("Empty");
            addClientName.requestFocus();
        }else if(email.isEmpty()){
            addClientEmail.setError("Empty");
            addClientEmail.requestFocus();
        }else if(complaint.isEmpty()){
            addClientComplaint.setError("Empty");
            addClientComplaint.requestFocus();
        } else if(category.equals("Select Complaint Subject")){
            Toast.makeText(this, "Please provide Complaint Subject", Toast.LENGTH_SHORT).show();
        }else if(bitmap == null){
            insertData();
        }else{
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
        filePath = storageReference.child("clients").child(finalimg+"jpg");
        final UploadTask uploadTask = filePath.putBytes(finalimg);
        uploadTask.addOnCompleteListener(AddClient.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
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
                                    insertData();
                                }
                            });
                        }
                    });
                }else {
                    pd.dismiss();
                    Toast.makeText(AddClient.this, "Something Went wrong!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void insertData() {
        dbRef = reference.child(category);
        final String uniqueKey = dbRef.push().getKey();


        ClientData clientData = new ClientData(name,email,downloadUrl,complaint,uniqueKey);

        dbRef.child(uniqueKey).setValue(clientData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                pd.dismiss();
                Toast.makeText(AddClient.this, "Client and complaint added Added", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(AddClient.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
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
            addClientImage.setImageBitmap(bitmap);
        }
    }


}