package com.digital.gnsbook.Model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Comment_Response {
    @SerializedName("result")
    private List<CommentItem> result;
    @SerializedName("status")
    private boolean status;

    public void setResult(List<CommentItem> list) {
        this.result = list;
    }

    public List<CommentItem> getResult() {
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
        stringBuilder.append("Comment_Response{result = '");
        stringBuilder.append(this.result);
        stringBuilder.append('\'');
        stringBuilder.append(",status = '");
        stringBuilder.append(this.status);
        stringBuilder.append('\'');
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}
