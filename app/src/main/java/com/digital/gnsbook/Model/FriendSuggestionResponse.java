package com.digital.gnsbook.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class FriendSuggestionResponse {

	@SerializedName("result")
	private List<FriendSuggestiontem> result;

	@SerializedName("status")
	private boolean status;

	public void setResult(List<FriendSuggestiontem> result){
		this.result = result;
	}

	public List<FriendSuggestiontem> getResult(){
		return result;
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
			"Response{" + 
			"result = '" + result + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}