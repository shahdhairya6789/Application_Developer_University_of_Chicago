package com;

public class ExpiryStatus {
    private String training;
    private String status;

    public ExpiryStatus(String training, String status) {
        this.training = training;
        this.status = status;
    }

    public String getTraining() {
        return training;
    }

    public void setTraining(String training) {
        this.training = training;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

