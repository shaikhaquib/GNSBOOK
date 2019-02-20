package com.digital.gnsbook.Model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Response {
    @SerializedName("result")
    private List<ResultItem> result;
    @SerializedName("status")
    private boolean status;

    public void setResult(List<ResultItem> list) {
        this.result = list;
    }

    public List<ResultItem> getResult() {
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
        stringBuilder.append("Response{result = '");
        stringBuilder.append(this.result);
        stringBuilder.append('\'');
        stringBuilder.append(",status = '");
        stringBuilder.append(this.status);
        stringBuilder.append('\'');
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}
