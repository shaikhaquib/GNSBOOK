package com.digital.gnsbook.Model;

import com.google.gson.annotations.SerializedName;

public class ResultItem {
    @SerializedName("collected_amount")
    private int collectedAmount;
    @SerializedName("details")
    private String details;
    @SerializedName("id")
    private int id;
    @SerializedName("pool_id")
    private int poolId;
    @SerializedName("reward_amount")
    private int rewardAmount;

    public void setRewardAmount(int i) {
        this.rewardAmount = i;
    }

    public int getRewardAmount() {
        return this.rewardAmount;
    }

    public void setDetails(String str) {
        this.details = str;
    }

    public String getDetails() {
        return this.details;
    }

    public void setId(int i) {
        this.id = i;
    }

    public int getId() {
        return this.id;
    }

    public void setPoolId(int i) {
        this.poolId = i;
    }

    public int getPoolId() {
        return this.poolId;
    }

    public void setCollectedAmount(int i) {
        this.collectedAmount = i;
    }

    public int getCollectedAmount() {
        return this.collectedAmount;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ResultItem{reward_amount = '");
        stringBuilder.append(this.rewardAmount);
        stringBuilder.append('\'');
        stringBuilder.append(",details = '");
        stringBuilder.append(this.details);
        stringBuilder.append('\'');
        stringBuilder.append(",id = '");
        stringBuilder.append(this.id);
        stringBuilder.append('\'');
        stringBuilder.append(",pool_id = '");
        stringBuilder.append(this.poolId);
        stringBuilder.append('\'');
        stringBuilder.append(",collected_amount = '");
        stringBuilder.append(this.collectedAmount);
        stringBuilder.append('\'');
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}
