package com.httam.thapcamtv.response;

import com.httam.thapcamtv.models.Match;

import java.util.List;

public class MatchResponse {
    private int status;
    private List<Match> data;

    // Getters and setters
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Match> getData() {
        return data;
    }

    public void setData(List<Match> data) {
        this.data = data;
    }
}