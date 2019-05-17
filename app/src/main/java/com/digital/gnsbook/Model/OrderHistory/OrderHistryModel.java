package com.digital.gnsbook.Model.OrderHistory;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderHistryModel {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("result")
    @Expose
    private List<Result_Order> result = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<Result_Order> getResult() {
        return result;
    }

    public void setResult(List<Result_Order> result) {
        this.result = result;
    }

}