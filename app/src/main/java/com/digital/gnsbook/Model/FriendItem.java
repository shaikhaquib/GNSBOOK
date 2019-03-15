package com.digital.gnsbook.Model;

import com.google.gson.annotations.SerializedName;

public class FriendItem {

	@SerializedName("d_pic")
	private String dPic;

	@SerializedName("customerid_from")
	private int customeridFrom;

	@SerializedName("name")
	private String name;

	@SerializedName("last_name")
	private String lastName;

	public void setDPic(String dPic){
		this.dPic = dPic;
	}

	public String getDPic(){
		return dPic;
	}

	public void setCustomeridFrom(int customeridFrom){
		this.customeridFrom = customeridFrom;
	}

	public int getCustomeridFrom(){
		return customeridFrom;
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

	@Override
 	public String toString(){
		return 
			"FriendItem{" +
			"d_pic = '" + dPic + '\'' + 
			",customerid_from = '" + customeridFrom + '\'' + 
			",name = '" + name + '\'' + 
			",last_name = '" + lastName + '\'' + 
			"}";
		}
}