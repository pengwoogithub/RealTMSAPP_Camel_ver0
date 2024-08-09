package com.example.realtmsapp_camel.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.realtmsapp_camel.DatabaseHelper;
import com.example.realtmsapp_camel.R;
import com.example.realtmsapp_camel.Trial;
import com.example.realtmsapp_camel.TrialAdapter;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;




public class TrialsFragment extends Fragment {

    private RecyclerView recyclerView;
    private TrialAdapter trialAdapter;
    private DatabaseHelper databaseHelper;

    public TrialsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trials, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        databaseHelper = new DatabaseHelper(getContext());
        loadTrialsData();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Reload data when the fragment is resumed
        loadTrialsData();
    }

    private void loadTrialsData() {
        List<Trial> trialsList = databaseHelper.getAllTrials();
        if (trialAdapter == null) {
            trialAdapter = new TrialAdapter(trialsList);
            recyclerView.setAdapter(trialAdapter);
        } else {
            trialAdapter.setTrialsList(trialsList);
        }
    }
}