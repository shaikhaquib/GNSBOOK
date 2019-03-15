package com.digital.gnsbook.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class CartResponse {

	@SerializedName("result")
	private List<CartItem> result;

	@SerializedName("count")
	private int count;

	@SerializedName("status")
	private boolean status;

	public void setResult(List<CartItem> result){
		this.result = result;
	}

	public List<CartItem> getResult(){
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
			"CartResponse{" +
			"result = '" + result + '\'' + 
			",count = '" + count + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}