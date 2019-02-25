package com.digital.gnsbook.Model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ResSubsItem {
    @SerializedName("result")
    private List<SubsResultItem> result;
    @SerializedName("status")
    private boolean status;

    public void setResult(List<SubsResultItem> list) {
        this.result = list;
    }

    public List<SubsResultItem> getResult() {
        return this.result;
    }

    public void setStatus(boolean z) {
        this.status = z;
    }

    public boolean isStatus() {
        return this.status;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ResSubsItem{result = '");
        stringBuilder.append(this.result);
        stringBuilder.append('\'');
        stringBuilder.append(",status = '");
        stringBuilder.append(this.status);
        stringBuilder.append('\'');
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}
