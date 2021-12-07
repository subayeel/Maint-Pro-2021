package com.example.maintpro.client;

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

public class UpdateClient extends AppCompatActivity {

    FloatingActionButton fab;

    private RecyclerView csClientComplaint,mechanicalClientComplaint,electricalClientComplaint,civilClientComplaint;
    private LinearLayout csNoComplaint,mechanicalNoComplaint,electricalNoComplaint,civilNoComplaint;
    private List<ClientData> list1,list2,list3,list4;
    private ClientAdapter adapter;



    private DatabaseReference reference,dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_client);

        csNoComplaint = findViewById(R.id.csNoComplaint); //civil dept1
        mechanicalNoComplaint = findViewById(R.id.mechanicalNoComplaint);//civil dept2
        electricalNoComplaint = findViewById(R.id.electricalNoComplaint);//civil dept3
        civilNoComplaint = findViewById(R.id.civilNoComplaint);//civil dept3

        csClientComplaint =findViewById(R.id.csClientComplaint);
        mechanicalClientComplaint =findViewById(R.id.mechanicalClientComplaint);
        electricalClientComplaint =findViewById(R.id.electricalClientComplaint);
        civilClientComplaint =findViewById(R.id.civilClientComplaint);

        reference = FirebaseDatabase.getInstance().getReference().child("client");

        csClientComplaint();
        mechanicalClientComplaint();
        electricalClientComplaint();
        civilClientComplaint();



        fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UpdateClient.this , AddClient.class));
            }
        });
    }

    private void csClientComplaint() {
        dbRef = reference.child("Civil Dept-1"); //Computer Science
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list1 = new ArrayList<>();
                if (!dataSnapshot.exists()) {
                    csNoComplaint.setVisibility(View.VISIBLE);
                    csClientComplaint.setVisibility(View.GONE);
                } else {
                    csNoComplaint.setVisibility(View.GONE);
                    csClientComplaint.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ClientData data = snapshot.getValue(ClientData.class);
                        list1.add(data);

                    }
                    csClientComplaint.setHasFixedSize(true);
                    csClientComplaint.setLayoutManager(new LinearLayoutManager(UpdateClient.this));
                    adapter = new ClientAdapter(list1, UpdateClient.this , "Civil Dept-1");
                    csClientComplaint.setAdapter(adapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateClient.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void mechanicalClientComplaint() {
        dbRef = reference.child("Civil Dept-2"); //Mechanical
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list2 = new ArrayList<>();
                if (!dataSnapshot.exists()) {
                    mechanicalNoComplaint.setVisibility(View.VISIBLE);
                    mechanicalClientComplaint.setVisibility(View.GONE);
                } else {
                    mechanicalNoComplaint.setVisibility(View.GONE);
                    mechanicalClientComplaint.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ClientData data = snapshot.getValue(ClientData.class);
                        list2.add(data);

                    }
                    mechanicalClientComplaint.setHasFixedSize(true);
                    mechanicalClientComplaint.setLayoutManager(new LinearLayoutManager(UpdateClient.this));
                    adapter = new ClientAdapter(list2, UpdateClient.this, "Civil Dept-2");
                    mechanicalClientComplaint.setAdapter(adapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateClient.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void electricalClientComplaint() {
        dbRef = reference.child("Civil Dept-3"); //Electrical
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list3 = new ArrayList<>();
                if (!dataSnapshot.exists()) {
                    electricalNoComplaint.setVisibility(View.VISIBLE);
                    electricalClientComplaint.setVisibility(View.GONE);
                } else {
                    electricalNoComplaint.setVisibility(View.GONE);
                    electricalClientComplaint.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ClientData data = snapshot.getValue(ClientData.class);
                        list3.add(data);

                    }
                    electricalClientComplaint.setHasFixedSize(true);
                    electricalClientComplaint.setLayoutManager(new LinearLayoutManager(UpdateClient.this));
                    adapter = new ClientAdapter(list3, UpdateClient.this , "Civil Dept-3");
                    electricalClientComplaint.setAdapter(adapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateClient.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void civilClientComplaint(){
        dbRef = reference.child("Civil Dept-4");  //Civil
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list4 = new ArrayList<>();
                if (!dataSnapshot.exists()) {
                    civilNoComplaint.setVisibility(View.VISIBLE);
                    civilClientComplaint.setVisibility(View.GONE);
                } else {
                    civilNoComplaint.setVisibility(View.GONE);
                    civilClientComplaint.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ClientData data = snapshot.getValue(ClientData.class);
                        list4.add(data);

                    }
                    civilClientComplaint.setHasFixedSize(true);
                    civilClientComplaint.setLayoutManager(new LinearLayoutManager(UpdateClient.this));
                    adapter = new ClientAdapter(list4, UpdateClient.this , "Civil Dept-4");
                    civilClientComplaint.setAdapter(adapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateClient.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }






}