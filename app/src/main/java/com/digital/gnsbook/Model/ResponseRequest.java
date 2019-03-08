package com.digital.gnsbook.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseRequest {

	@SerializedName("result")
	private List<RequestAcceptItem> result;

	@SerializedName("result2")
	private List<RquestItem> result2;

	@SerializedName("status")
	private boolean status;

	@SerializedName("request_count")
	private int requestCount;

	public void setResult(List<RequestAcceptItem> result){
		this.result = result;
	}

	public List<RequestAcceptItem> getResult(){
		return result;
	}

	public void setResult2(List<RquestItem> result2){
		this.result2 = result2;
	}

	public List<RquestItem> getResult2(){
		return result2;
	}

	public void setStatus(boolean status){
		this.status = status;
	}

	public boolean isStatus(){
		return status;
	}

	public void setRequestCount(int requestCount){
		this.requestCount = requestCount;
	}

	public int getRequestCount(){
		return requestCount;
	}

	@Override
 	public String toString(){
		return 
			"ResponseRequest{" +
			"result = '" + result + '\'' + 
			",result2 = '" + result2 + '\'' + 
			",status = '" + status + '\'' + 
			",request_count = '" + requestCount + '\'' + 
			"}";
		}
}