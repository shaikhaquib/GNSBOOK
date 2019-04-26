package com.digital.gnsbook.Model.TimeLine_Model;

import com.google.gson.annotations.SerializedName;

public class LikesItem{

	@SerializedName("d_pic")
	private String dPic;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	@SerializedName("customer_id")
	private int customerId;

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

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setCustomerId(int customerId){
		this.customerId = customerId;
	}

	public int getCustomerId(){
		return customerId;
	}

	@Override
 	public String toString(){
		return 
			"LikesItem{" + 
			"d_pic = '" + dPic + '\'' + 
			",name = '" + name + '\'' + 
			",id = '" + id + '\'' + 
			",customer_id = '" + customerId + '\'' + 
			"}";
		}
}