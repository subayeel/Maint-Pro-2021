package com.example.maintpro.ui.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.maintpro.R;
import com.example.maintpro.client.UpdateClient;
import com.example.maintpro.employee.UpdateEmployee;
import com.example.maintpro.report.ReportActivity;
import com.example.maintpro.ui.notice.NoticeFragment;


public class HomeFragment extends Fragment implements View.OnClickListener {

    CardView viewNotice , viewGalleryImage ,viewReports ,addComplaint, viewClients, viewEmployees;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);



        viewReports = view.findViewById(R.id.viewReports);
        addComplaint = view.findViewById(R.id.addComplaint);
        viewClients = view.findViewById(R.id.viewClients);
        viewEmployees = view.findViewById(R.id.viewEmployees);





        viewReports.setOnClickListener(this);
        addComplaint.setOnClickListener(this);
        viewClients.setOnClickListener(this);
        viewEmployees.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.viewReports:
                intent = new Intent(getContext(), ReportActivity.class);
                startActivity(intent);
                break;

            case R.id.addComplaint:
                intent = new Intent(getContext(), UpdateClient.class);
                startActivity(intent);
                break;

            case R.id.viewEmployees:
                intent = new Intent(getContext(), UpdateEmployee.class);
                startActivity(intent);
                break;

            case R.id.viewClients:
                intent = new Intent(getContext(), UpdateClient.class);
                startActivity(intent);
                break;

            default:
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                break;
        }

    }
}