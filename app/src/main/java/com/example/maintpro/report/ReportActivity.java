package com.example.maintpro.report;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maintpro.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ReportActivity extends AppCompatActivity {

    RecyclerView reportRecycler;
    private DatabaseReference reference;
    private List<ReportData> list;
    private ReportAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        reportRecycler = findViewById(R.id.reportRecycler);
        reportRecycler.setAdapter(adapter);
        reference = FirebaseDatabase.getInstance().getReference().child("report");
        getData();
    }

    private void getData() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ReportData data = snapshot.getValue(ReportData.class);
                    list.add(data);
                }
                reportRecycler = findViewById(R.id.reportRecycler);
                reportRecycler.setLayoutManager(new LinearLayoutManager(ReportActivity.this));
                reportRecycler.setHasFixedSize(true);

                adapter = new ReportAdapter(ReportActivity.this,list);
                reportRecycler.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ReportActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}