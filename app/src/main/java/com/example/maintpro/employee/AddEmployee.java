package com.example.maintpro.employee;

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

public class AddEmployee extends AppCompatActivity {

    private ImageView addEmployeeImage;
    private EditText addEmployeeName , addEmployeeEmail ,addEmployeePost;
    private Spinner addEmployeeCategory;
    private Button addEmployeeBtn;
    private final int REQ = 1;
    private Bitmap bitmap = null;
    private String category;
    private String name, email ,post, downloadUrl="";
    private ProgressDialog pd;
    private StorageReference storageReference;
    private DatabaseReference reference,dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);

        addEmployeeImage = findViewById(R.id.addEmployeeImage);
        addEmployeeName = findViewById(R.id.addEmployeeName);
        addEmployeeEmail = findViewById(R.id.addEmployeeEmail);
        addEmployeePost = findViewById(R.id.addEmployeePost);
        addEmployeeCategory = findViewById(R.id.addEmployeeCategory);
        addEmployeeBtn = findViewById(R.id.addEmployeeBtn);

        pd = new ProgressDialog(this);

        reference = FirebaseDatabase.getInstance().getReference().child("employee");
        storageReference = FirebaseStorage.getInstance().getReference();

        String [] items = new String[]{"Select Category" , "Civil Dept-1" ,  "Civil Dept-2", "Civil Dept-3" ,"Civil Dept-4"};
        addEmployeeCategory.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items));

        addEmployeeCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = addEmployeeCategory.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        addEmployeeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        addEmployeeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
            }
        });

    }

    private void checkValidation() {
        name = addEmployeeName.getText().toString();
        email = addEmployeeEmail.getText().toString();
        post = addEmployeePost.getText().toString();

        if(name.isEmpty()){
            addEmployeeName.setError("Empty");
            addEmployeeName.requestFocus();
        }else if(email.isEmpty()){
            addEmployeeEmail.setError("Empty");
            addEmployeeEmail.requestFocus();
        }else if(post.isEmpty()){
            addEmployeePost.setError("Empty");
            addEmployeePost.requestFocus();
        }else if(category.equals("Select Category")){
            Toast.makeText(this, "Please provide Employee category", Toast.LENGTH_SHORT).show();
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
        filePath = storageReference.child("Employees").child(finalimg+"jpg");
        final UploadTask uploadTask = filePath.putBytes(finalimg);
        uploadTask.addOnCompleteListener(AddEmployee.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
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
                    Toast.makeText(AddEmployee.this, "Something Went wrong!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void insertData() {
        dbRef = reference.child(category);
        final String uniqueKey = dbRef.push().getKey();


        EmployeeData employeeData =new EmployeeData(name,email,post,downloadUrl,uniqueKey);

        dbRef.child(uniqueKey).setValue(employeeData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                pd.dismiss();
                Toast.makeText(AddEmployee.this, "Employee Added", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(AddEmployee.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
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
            addEmployeeImage.setImageBitmap(bitmap);
        }
    }
}