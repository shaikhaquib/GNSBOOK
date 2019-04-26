package com.digital.gnsbook.Model;

import com.google.gson.annotations.SerializedName;

public class FriendItem {
	public String friendID;
	@SerializedName("customerid_to")
	private String customeridTo;

	@SerializedName("d_pic")
	private String dPic;

	@SerializedName("name")
	private String name;

	@SerializedName("last_name")
	private String lastName;

	@SerializedName("id")
	private int id;

	@SerializedName("type")
	private int type;

	@SerializedName("b_pic")
	private String bPic;

	@SerializedName("channel_id")
	private String channelId;

	@SerializedName("status")
	private int status;

	@SerializedName("customerid_from")
	private int customeridFrom;

	public void setCustomeridTo(String customeridTo){
		this.customeridTo = customeridTo;
	}

	public String getCustomeridTo(){
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

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setType(int type){
		this.type = type;
	}

	public int getType(){
		return type;
	}

	public void setBPic(String bPic){
		this.bPic = bPic;
	}

	public String getBPic(){
		return bPic;
	}

	public void setChannelId(String channelId){
		this.channelId = channelId;
	}

	public String getChannelId(){
		return channelId;
	}

	public void setStatus(int status){
		this.status = status;
	}

	public int getStatus(){
		return status;
	}

	public void setCustomeridFrom(int customeridFrom){
		this.customeridFrom = customeridFrom;
	}

	public int getCustomeridFrom(){
		return customeridFrom;
	}

	@Override
	public String toString(){
		return
				"TimeLineItem{" +
						"customerid_to = '" + customeridTo + '\'' +
						",d_pic = '" + dPic + '\'' +
						",name = '" + name + '\'' +
						",last_name = '" + lastName + '\'' +
						",id = '" + id + '\'' +
						",type = '" + type + '\'' +
						",b_pic = '" + bPic + '\'' +
						",channel_id = '" + channelId + '\'' +
						",status = '" + status + '\'' +
						",customerid_from = '" + customeridFrom + '\'' +
						"}";
	}
}
