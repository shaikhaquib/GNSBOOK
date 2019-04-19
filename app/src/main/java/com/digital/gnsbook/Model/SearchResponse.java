package com.digital.gnsbook.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResponse {

	@SerializedName("result")
	private List<SearchItem> result;

	@SerializedName("status")
	private boolean status;

	public void setResult(List<SearchItem> result){
		this.result = result;
	}

	public List<SearchItem> getResult(){
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
			"SearchResponse{" +
			"result = '" + result + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}