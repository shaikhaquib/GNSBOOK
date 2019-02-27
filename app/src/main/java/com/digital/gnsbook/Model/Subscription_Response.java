package com.digital.gnsbook.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Subscription_Response {

	@SerializedName("result")
	private List<Subscription_Response_ResultItem> result;

	@SerializedName("count")
	private int count;

	@SerializedName("status")
	private boolean status;

	public void setResult(List<Subscription_Response_ResultItem> result){
		this.result = result;
	}

	public List<Subscription_Response_ResultItem> getResult(){
		return result;
	}

	public void setCount(int count){
		this.count = count;
	}

	public int getCount(){
		return count;
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
			"Subscription_Response{" +
			"result = '" + result + '\'' + 
			",count = '" + count + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}