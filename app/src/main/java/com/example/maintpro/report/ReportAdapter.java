package com.example.maintpro.report;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maintpro.R;
import com.example.maintpro.client.ClientData;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportViewHolder> {

    private final Context context;
    private final List<ReportData> list;

    public ReportAdapter(Context context, List<ReportData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ReportViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.report_item_layout ,parent , false);
        return new ReportViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  ReportAdapter.ReportViewHolder holder,int position) {

        ReportData item = list.get(position);

        holder.reportName.setText(item.getPdfTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context,PdfViewerActivity.class);
                intent.putExtra("pdfUrl",list.get(position).getPdfUrl());
                context.startActivity(intent);

            }
        });

        holder.reportDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(list.get(position).getPdfUrl()));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ReportViewHolder extends RecyclerView.ViewHolder {

        private TextView reportName;
        private ImageView reportDownload;

        public ReportViewHolder(@NonNull View itemView) {
            super(itemView);

            reportName = itemView.findViewById(R.id.reportName);
            reportDownload = itemView.findViewById(R.id.reportDownload);
        }
    }

}
