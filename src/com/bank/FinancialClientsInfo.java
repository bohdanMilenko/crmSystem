package com.bank;

import java.util.Map;

public class FinancialClientsInfo {

    private String currentJob;
    private String PreviousJob;
    private int yearsSpentAtCurrentJob;
    private int yearsSpentAtPreviousJob;
    private Map<Integer, Double> salaryHistory;

    public FinancialClientsInfo(String currentJob, String previousJob, int yearsSpentAtCurrentJob, int yearsSpentAtPreviousJob, Map<Integer, Double> salaryHistory) {
        this.currentJob = currentJob;
        PreviousJob = previousJob;
        this.yearsSpentAtCurrentJob = yearsSpentAtCurrentJob;
        this.yearsSpentAtPreviousJob = yearsSpentAtPreviousJob;
        this.salaryHistory = salaryHistory;
    }

    public String getCurrentJob() {
        return currentJob;
    }

    public String getPreviousJob() {
        return PreviousJob;
    }

    public int getYearsSpentAtCurrentJob() {
        return yearsSpentAtCurrentJob;
    }

    public int getYearsSpentAtPreviousJob() {
        return yearsSpentAtPreviousJob;
    }
}
