package com.example.maintpro.employee;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maintpro.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UpdateEmployee extends AppCompatActivity {

    FloatingActionButton fab;
    private RecyclerView csDepartment,mechanicalDepartment,electricalDepartment,civilDepartment;
    private LinearLayout csNoData,mechNoData,electricalNoData,civilNoData;
    private List<EmployeeData> list1,list2,list3,list4;
    private EmployeeAdapter adapter;

    private DatabaseReference reference,dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_employee);

        csNoData = findViewById(R.id.csNoData);
        mechNoData = findViewById(R.id.mechNoData);
        electricalNoData = findViewById(R.id.electricalNoData);
        civilNoData = findViewById(R.id.civilNoData);

        csDepartment =findViewById(R.id.csDepartment);
        mechanicalDepartment =findViewById(R.id.mechanicalDepartment);
        electricalDepartment =findViewById(R.id.electricalDepartment);
        civilDepartment =findViewById(R.id.civilDepartment);

        reference = FirebaseDatabase.getInstance().getReference().child("employee");

        csDepartment();
        mechanicalDepartment();
        electricalDepartment();
        civilDepartment();


        fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UpdateEmployee.this , AddEmployee.class));
            }
        });
    }

    private void csDepartment() {
        dbRef = reference.child("Civil Dept-1"); //Computer Science
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list1= new ArrayList<>();
                if(!dataSnapshot.exists()){
                    csNoData.setVisibility(View.VISIBLE);
                    csDepartment.setVisibility(View.GONE);
                }else{
                    csNoData.setVisibility(View.GONE);
                    csDepartment.setVisibility(View.VISIBLE);
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        EmployeeData data = snapshot.getValue(EmployeeData.class);
                        list1.add(data);

                    }
                    csDepartment.setHasFixedSize(true);
                    csDepartment.setLayoutManager(new LinearLayoutManager(UpdateEmployee.this));
                    adapter = new EmployeeAdapter(list1, UpdateEmployee.this , "Civil Dept-1");
                    csDepartment.setAdapter(adapter);

                }
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {
                Toast.makeText(UpdateEmployee.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void mechanicalDepartment() {
        dbRef = reference.child("Civil Dept-2"); //Mechanical
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list2= new ArrayList<>();
                if(!dataSnapshot.exists()){
                    mechNoData.setVisibility(View.VISIBLE);
                    mechanicalDepartment.setVisibility(View.GONE);
                }else{
                    mechNoData.setVisibility(View.GONE);
                    mechanicalDepartment.setVisibility(View.VISIBLE);
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        EmployeeData data = snapshot.getValue(EmployeeData.class);
                        list2.add(data);

                    }
                    mechanicalDepartment.setHasFixedSize(true);
                    mechanicalDepartment.setLayoutManager(new LinearLayoutManager(UpdateEmployee.this));
                    adapter = new EmployeeAdapter(list2, UpdateEmployee.this , "Civil Dept-2");
                    mechanicalDepartment.setAdapter(adapter);

                }
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {
                Toast.makeText(UpdateEmployee.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void electricalDepartment() {
        dbRef = reference.child("Civil Dept-3"); //Electrical
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list3= new ArrayList<>();
                if(!dataSnapshot.exists()){
                    electricalNoData.setVisibility(View.VISIBLE);
                    electricalDepartment.setVisibility(View.GONE);
                }else{
                    electricalNoData.setVisibility(View.GONE);
                    electricalDepartment.setVisibility(View.VISIBLE);
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        EmployeeData data = snapshot.getValue(EmployeeData.class);
                        list3.add(data);

                    }
                    electricalDepartment.setHasFixedSize(true);
                    electricalDepartment.setLayoutManager(new LinearLayoutManager(UpdateEmployee.this));
                    adapter = new EmployeeAdapter(list3, UpdateEmployee.this , "Civil Dept-3");
                    electricalDepartment.setAdapter(adapter);

                }
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {
                Toast.makeText(UpdateEmployee.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void civilDepartment() {
        dbRef = reference.child("Civil Dept-4");  //Civil
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list4= new ArrayList<>();
                if(!dataSnapshot.exists()){
                    civilNoData.setVisibility(View.VISIBLE);
                    civilDepartment.setVisibility(View.GONE);
                }else{
                    civilNoData.setVisibility(View.GONE);
                    civilDepartment.setVisibility(View.VISIBLE);
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        EmployeeData data = snapshot.getValue(EmployeeData.class);
                        list4.add(data);

                    }
                    civilDepartment.setHasFixedSize(true);
                    civilDepartment.setLayoutManager(new LinearLayoutManager(UpdateEmployee.this));
                    adapter = new EmployeeAdapter(list4, UpdateEmployee.this , "Civil Dept-4");
                    civilDepartment.setAdapter(adapter);

                }
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {
                Toast.makeText(UpdateEmployee.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}