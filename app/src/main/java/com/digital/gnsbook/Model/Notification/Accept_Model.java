package com.digital.gnsbook.Model.Notification;

import android.app.Notification;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Accept_Model {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("result")
    @Expose
    private List<Result_Accept> result = null;
    @SerializedName("request_count")
    @Expose
    private Integer requestCount;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<Result_Accept> getResult() {
        return result;
    }

    public void setResult(List<Result_Accept> result) {
        this.result = result;
    }

    public Integer getRequestCount() {
        return requestCount;
    }

    public void setRequestCount(Integer requestCount) {
        this.requestCount = requestCount;
    }

}