package com.example.maintpro.employee;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maintpro.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewAdapter> {

    private List<EmployeeData> list;
    private Context context;
    private String category;

    public EmployeeAdapter(List<EmployeeData> list, Context context , String category) {
        this.list = list;
        this.context = context;
        this.category = category;
    }

    @NonNull
    @Override
    public EmployeeViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.employee_item_layout, parent,false);
        return new EmployeeViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewAdapter holder, int position) {

        EmployeeData item = list.get(position);
        holder.name.setText(item.getName());
        holder.email.setText(item.getEmail());
        holder.post.setText(item.getPost());
        try {
            Picasso.get().load(item.getImage()).into(holder.imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,InfoUpdateEmployee.class);
                intent.putExtra("name",item.getName());
                intent.putExtra("email",item.getEmail());
                intent.putExtra("post",item.getPost());
                intent.putExtra("image",item.getImage());
                intent.putExtra("key",item.getKey());
                intent.putExtra("category",category);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class EmployeeViewAdapter extends RecyclerView.ViewHolder{

        private TextView name,email,post;
        private Button update;
        private ImageView imageView;

        public EmployeeViewAdapter(@NonNull  View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.employeeName);
            email = itemView.findViewById(R.id.employeeEmail);
            post = itemView.findViewById(R.id.employeePost);
            imageView = itemView.findViewById(R.id.employeeImage);
            update = itemView.findViewById(R.id.employeeUpdate);
        }
    }
}
