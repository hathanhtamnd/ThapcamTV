package com.httam.thapcamtv.response;

import com.httam.thapcamtv.models.ReplayLink;

public class ReplayLinkResponse {
    private int status;
    private ReplayLink data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ReplayLink getData() {
        return data;
    }

    public void setData(ReplayLink data) {
        this.data = data;
    }
}
