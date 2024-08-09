package com.example.realtmsapp_camel;

public class Trial {
    private String process;
    private String person;
    private String model;
    private int trialSet;
    private String[] trialValues;

    public Trial(String process, String person, String model, int trialSet, String[] trialValues) {
        this.process = process;
        this.person = person;
        this.model = model;
        this.trialSet = trialSet;
        this.trialValues = trialValues;
    }

    public String getProcess() {
        return process;
    }

    public String getPerson() {
        return person;
    }

    public String getModel() {
        return model;
    }

    public int getTrialSet() {
        return trialSet;
    }

    public String[] getTrialValues() {
        return trialValues;
    }
}