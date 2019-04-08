package com.digital.gnsbook.Model;

import com.google.gson.annotations.SerializedName;

public class FriendItem {
	@SerializedName("customerid_to")
	private int customeridTo;

	@SerializedName("d_pic")
	private String dPic;

	@SerializedName("name")
	private String name;

	@SerializedName("last_name")
	private String lastName;

	@SerializedName("id")
	private int id;

	@SerializedName("channel_id")
	private String channelId;

	@SerializedName("status")
	private int status;

	@SerializedName("customerid_from")
	private int customeridFrom;

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

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
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
				"ResultItem{" +
						"customerid_to = '" + customeridTo + '\'' +
						",d_pic = '" + dPic + '\'' +
						",name = '" + name + '\'' +
						",last_name = '" + lastName + '\'' +
						",id = '" + id + '\'' +
						",channel_id = '" + channelId + '\'' +
						",status = '" + status + '\'' +
						",customerid_from = '" + customeridFrom + '\'' +
						"}";
	}
}
