package com.digital.gnsbook.Model;

import com.google.gson.annotations.SerializedName;

public class RquestItem {

	@SerializedName("customerid_to")
	private int customeridTo;

	@SerializedName("d_pic")
	private String dPic;

	@SerializedName("name")
	private String name;

	@SerializedName("last_name")
	private String lastName;

	@SerializedName("status")
	private int status;

	public void setCustomeridTo(int customeridTo){
		this.customeridTo = customeridTo;
	}

	public int getCustomeridTo(){
		return customeridTo;
	}

	public void setDPic(String dPic){
		this.dPic = dPic;
	}

	public String getDPic(){
		return dPic;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setLastName(String lastName){
		this.lastName = lastName;
	}

	public String getLastName(){
		return lastName;
	}

	public void setStatus(int status){
		this.status = status;
	}

	public int getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"RquestItem{" +
			"customerid_to = '" + customeridTo + '\'' + 
			",d_pic = '" + dPic + '\'' + 
			",name = '" + name + '\'' + 
			",last_name = '" + lastName + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}