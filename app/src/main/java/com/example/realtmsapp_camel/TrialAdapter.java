package com.example.realtmsapp_camel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class TrialAdapter extends RecyclerView.Adapter<TrialAdapter.TrialViewHolder> {

    private List<Trial> trialsList;

    public TrialAdapter(List<Trial> trialsList) {
        this.trialsList = trialsList;
    }

    @NonNull
    @Override
    public TrialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trial, parent, false);
        return new TrialViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull TrialViewHolder holder, int position) {
        Trial trial = trialsList.get(position);
        holder.processTextView.setText(trial.getProcess());
        holder.personTextView.setText(trial.getPerson());
        holder.modelTextView.setText(trial.getModel());

        List<String> trialValues = Arrays.asList(trial.getTrialValues());
        holder.trial1TextView.setText(trialValues.size() > 0 ? trialValues.get(0) : "");
        holder.trial2TextView.setText(trialValues.size() > 1 ? trialValues.get(1) : "");
        holder.trial3TextView.setText(trialValues.size() > 2 ? trialValues.get(2) : "");
        holder.trial4TextView.setText(trialValues.size() > 3 ? trialValues.get(3) : "");
        holder.trial5TextView.setText(trialValues.size() > 4 ? trialValues.get(4) : "");
        holder.trial6TextView.setText(trialValues.size() > 5 ? trialValues.get(5) : "");
        holder.trial7TextView.setText(trialValues.size() > 6 ? trialValues.get(6) : "");
        holder.trial8TextView.setText(trialValues.size() > 7 ? trialValues.get(7) : "");
        holder.trial9TextView.setText(trialValues.size() > 8 ? trialValues.get(8) : "");
        holder.trial10TextView.setText(trialValues.size() > 9 ? trialValues.get(9) : "");
    }

    @Override
    public int getItemCount() {
        return trialsList.size();
    }

    public void setTrialsList(List<Trial> trialsList) {
        this.trialsList = trialsList;
        notifyDataSetChanged(); // para refresh adapter pag may bagong mga data
    }

    public static class TrialViewHolder extends RecyclerView.ViewHolder {
        TextView processTextView, personTextView, modelTextView;
        TextView trial1TextView, trial2TextView, trial3TextView, trial4TextView, trial5TextView;
        TextView trial6TextView, trial7TextView, trial8TextView, trial9TextView, trial10TextView;
        LinearLayout detailsContainer;

        public TrialViewHolder(@NonNull View itemView) {
            super(itemView);
            processTextView = itemView.findViewById(R.id.processTextView);
            personTextView = itemView.findViewById(R.id.personTextView);
            modelTextView = itemView.findViewById(R.id.modelTextView);
            trial1TextView = itemView.findViewById(R.id.trial1TextView);
            trial2TextView = itemView.findViewById(R.id.trial2TextView);
            trial3TextView = itemView.findViewById(R.id.trial3TextView);
            trial4TextView = itemView.findViewById(R.id.trial4TextView);
            trial5TextView = itemView.findViewById(R.id.trial5TextView);
            trial6TextView = itemView.findViewById(R.id.trial6TextView);
            trial7TextView = itemView.findViewById(R.id.trial7TextView);
            trial8TextView = itemView.findViewById(R.id.trial8TextView);
            trial9TextView = itemView.findViewById(R.id.trial9TextView);
            trial10TextView = itemView.findViewById(R.id.trial10TextView);

            detailsContainer = itemView.findViewById(R.id.detailsContainer);

            itemView.setOnClickListener(v -> { //para makita mga values pag needed lang
                if (detailsContainer.getVisibility() == View.GONE) {
                    detailsContainer.setVisibility(View.VISIBLE);
                } else {
                    detailsContainer.setVisibility(View.GONE);
                }
            });
        }
    }
}