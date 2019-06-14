package com.digital.gnsbook.Model.Company_list;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Company_list_Model{

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("result")
    @Expose
    private List<Result_Company_list> result = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<Result_Company_list> getResult() {
        return result;
    }

    public void setResult(List<Result_Company_list> result) {
        this.result = result;
    }

}