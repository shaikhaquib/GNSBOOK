package com.digital.gnsbook.Model;

import com.google.gson.annotations.SerializedName;

public class SearchItem {

	@SerializedName("company_type")
	private String companyType;

	@SerializedName("company_id")
	private String companyId;

	@SerializedName("Search_type")
	private int searchType;

	@SerializedName("city")
	private String city;

	@SerializedName("name")
	private String name;

	@SerializedName("logo")
	private String logo;

	@SerializedName("banner")
	private String banner;

	@SerializedName("state")
	private String state;

	@SerializedName("d_pic")
	private String dPic;

	@SerializedName("last_name")
	private String lastName;

	@SerializedName("customer_id")
	private int customerId;

	public void setCompanyType(String companyType){
		this.companyType = companyType;
	}

	public String getCompanyType(){
		return companyType;
	}

	public void setCompanyId(String companyId){
		this.companyId = companyId;
	}

	public String getCompanyId(){
		return companyId;
	}

	public void setSearchType(int searchType){
		this.searchType = searchType;
	}

	public int getSearchType(){
		return searchType;
	}

	public void setCity(String city){
		this.city = city;
	}

	public String getCity(){
		return city;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setLogo(String logo){
		this.logo = logo;
	}

	public String getLogo(){
		return logo;
	}

	public void setBanner(String banner){
		this.banner = banner;
	}

	public String getBanner(){
		return banner;
	}

	public void setState(String state){
		this.state = state;
	}

	public String getState(){
		return state;
	}

	public void setDPic(String dPic){
		this.dPic = dPic;
	}

	public String getDPic(){
		return dPic;
	}

	public void setLastName(String lastName){
		this.lastName = lastName;
	}

	public String getLastName(){
		return lastName;
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
			"SearchItem{" +
			"company_type = '" + companyType + '\'' + 
			",company_id = '" + companyId + '\'' + 
			",search_type = '" + searchType + '\'' + 
			",city = '" + city + '\'' + 
			",name = '" + name + '\'' + 
			",logo = '" + logo + '\'' + 
			",banner = '" + banner + '\'' + 
			",state = '" + state + '\'' + 
			",d_pic = '" + dPic + '\'' + 
			",last_name = '" + lastName + '\'' + 
			",customer_id = '" + customerId + '\'' + 
			"}";
		}
}