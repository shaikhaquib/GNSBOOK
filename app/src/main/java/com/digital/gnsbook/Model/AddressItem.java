package com.digital.gnsbook.Model;

import com.google.gson.annotations.SerializedName;

public class AddressItem {

	@SerializedName("zipcode")
	private int zipcode;

	@SerializedName("country")
	private String country;

	@SerializedName("city")
	private String city;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("phone_number")
	private String phoneNumber;

	@SerializedName("apartment_name")
	private String apartmentName;

	@SerializedName("id")
	private int id;

	@SerializedName("customer_name")
	private String customerName;

	@SerializedName("state")
	private String state;

	@SerializedName("landmark")
	private String landmark;

	@SerializedName("type")
	private int type;

	public void setZipcode(int zipcode){
		this.zipcode = zipcode;
	}

	public int getZipcode(){
		return zipcode;
	}

	public void setCountry(String country){
		this.country = country;
	}

	public String getCountry(){
		return country;
	}

	public void setCity(String city){
		this.city = city;
	}

	public String getCity(){
		return city;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setPhoneNumber(String phoneNumber){
		this.phoneNumber = phoneNumber;
	}

	public String getPhoneNumber(){
		return phoneNumber;
	}

	public void setApartmentName(String apartmentName){
		this.apartmentName = apartmentName;
	}

	public String getApartmentName(){
		return apartmentName;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setCustomerName(String customerName){
		this.customerName = customerName;
	}

	public String getCustomerName(){
		return customerName;
	}

	public void setState(String state){
		this.state = state;
	}

	public String getState(){
		return state;
	}

	public void setLandmark(String landmark){
		this.landmark = landmark;
	}

	public String getLandmark(){
		return landmark;
	}

	public void setType(int type){
		this.type = type;
	}

	public int getType(){
		return type;
	}

	@Override
	public String toString(){
		return
				"ResultItem{" +
						"zipcode = '" + zipcode + '\'' +
						",country = '" + country + '\'' +
						",city = '" + city + '\'' +
						",created_at = '" + createdAt + '\'' +
						",phone_number = '" + phoneNumber + '\'' +
						",apartment_name = '" + apartmentName + '\'' +
						",id = '" + id + '\'' +
						",customer_name = '" + customerName + '\'' +
						",state = '" + state + '\'' +
						",landmark = '" + landmark + '\'' +
						",type = '" + type + '\'' +
						"}";
	}
}