package com.digital.gnsbook.Model.Notification;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result_Accept {

    @SerializedName("customerid_to")
    @Expose
    private String customeridTo;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("d_pic")
    @Expose
    private String dPic;
    @SerializedName("status")
    @Expose
    private Integer status;

    public String getCustomeridTo() {
        return customeridTo;
    }

    public void setCustomeridTo(String customeridTo) {
        this.customeridTo = customeridTo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDPic() {
        return dPic;
    }

    public void setDPic(String dPic) {
        this.dPic = dPic;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}