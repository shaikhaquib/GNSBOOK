package com.digital.gnsbook.Model;

import com.google.gson.annotations.SerializedName;

public class CommentItem {
    @SerializedName("comment")
    private String comment;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("customer_id")
    private int customerId;
    @SerializedName("d_pic")
    private String dPic;
    @SerializedName("name")
    private String name;
    @SerializedName("post_id")
    private int postId;

    public void setDPic(String str) {
        this.dPic = str;
    }

    public String getDPic() {
        return this.dPic;
    }

    public void setPostId(int i) {
        this.postId = i;
    }

    public int getPostId() {
        return this.postId;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getName() {
        return this.name;
    }

    public void setCreatedAt(String str) {
        this.createdAt = str;
    }

    public String getCreatedAt() {
        return this.createdAt;
    }

    public void setComment(String str) {
        this.comment = str;
    }

    public String getComment() {
        return this.comment;
    }

    public void setCustomerId(int i) {
        this.customerId = i;
    }

    public int getCustomerId() {
        return this.customerId;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CommentItem{d_pic = '");
        stringBuilder.append(this.dPic);
        stringBuilder.append('\'');
        stringBuilder.append(",post_id = '");
        stringBuilder.append(this.postId);
        stringBuilder.append('\'');
        stringBuilder.append(",name = '");
        stringBuilder.append(this.name);
        stringBuilder.append('\'');
        stringBuilder.append(",created_at = '");
        stringBuilder.append(this.createdAt);
        stringBuilder.append('\'');
        stringBuilder.append(",comment = '");
        stringBuilder.append(this.comment);
        stringBuilder.append('\'');
        stringBuilder.append(",customer_id = '");
        stringBuilder.append(this.customerId);
        stringBuilder.append('\'');
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}
