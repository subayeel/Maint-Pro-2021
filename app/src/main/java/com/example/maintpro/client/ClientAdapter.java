package com.example.maintpro.client;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.maintpro.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.squareup.picasso.Picasso;

import java.util.List;

public class ClientAdapter extends RecyclerView.Adapter<ClientAdapter.ClientViewAdapter> {

    private List<ClientData> list;
    private Context context;
    private String category;

    public ClientAdapter(List<ClientData> list, Context context , String category) {
        this.list = list;
        this.context = context;
        this.category = category;
    }

    @NonNull
    @Override
    public ClientViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.client_item_layout, parent,false);
        return new ClientViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientViewAdapter holder, int position) {

        ClientData item = list.get(position);
        holder.name.setText(item.getName());
        holder.email.setText(item.getEmail());
        holder.complaint.setText(item.getComplaint());

        try {
            Picasso.get().load(item.getImage()).into(holder.imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, InfoUpdateClient.class);
                intent.putExtra("name",item.getName());
                intent.putExtra("email",item.getEmail());
                intent.putExtra("complaint",item.getComplaint());
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

    public class ClientViewAdapter extends RecyclerView.ViewHolder{

        private TextView name,email ,complaint;
        private Button update;
        private ImageView imageView;

        public ClientViewAdapter(@NonNull  View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.clientName);
            email = itemView.findViewById(R.id.clientEmail);
            complaint = itemView.findViewById(R.id.clientComplaint);
            imageView = itemView.findViewById(R.id.clientImage);
            update = itemView.findViewById(R.id.clientUpdate);
        }
    }
}
