package com.digital.gnsbook.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class FriendResponse {

	@SerializedName("result")
	private List<FriendItem> result;

	@SerializedName("Friend_count")
	private int friendCount;

	@SerializedName("status")
	private boolean status;

	public void setResult(List<FriendItem> result){
		this.result = result;
	}

	public List<FriendItem> getResult(){
		return result;
	}

	public void setFriendCount(int friendCount){
		this.friendCount = friendCount;
	}

	public int getFriendCount(){
		return friendCount;
	}

	public void setStatus(boolean status){
		this.status = status;
	}

	public boolean isStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return
			"FriendResponse{" +
			"result = '" + result + '\'' +
			",friend_count = '" + friendCount + '\'' +
			",status = '" + status + '\'' +
			"}";
		}
}