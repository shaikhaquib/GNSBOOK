package com.digital.gnsbook.Model;

import com.google.gson.annotations.SerializedName;

public class FriendSuggestiontem {

	@SerializedName("premium_status")
	private int premiumStatus;

	@SerializedName("country")
	private String country;

	@SerializedName("social_networks")
	private String socialNetworks;

	@SerializedName("d_pic")
	private String dPic;

	@SerializedName("added_time")
	private String addedTime;

	@SerializedName("education")
	private String education;

	@SerializedName("birthdate")
	private String birthdate;

	@SerializedName("agent_id")
	private int agentId;

	@SerializedName("city")
	private String city;

	@SerializedName("about")
	private String about;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("employement")
	private String employement;

	@SerializedName("password")
	private String password;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("agent_status")
	private int agentStatus;

	@SerializedName("id")
	private int id;

	@SerializedName("state")
	private String state;

	@SerializedName("email")
	private String email;

	@SerializedName("pincode")
	private int pincode;

	@SerializedName("website")
	private String website;

	@SerializedName("sex")
	private String sex;

	@SerializedName("mobile")
	private String mobile;

	@SerializedName("last_name")
	private String lastName;

	@SerializedName("hobbies")
	private String hobbies;

	@SerializedName("name")
	private String name;

	@SerializedName("referral_id")
	private int referralId;

	@SerializedName("position")
	private int position;

	@SerializedName("customer_id")
	private int customerId;

	@SerializedName("spill_id")
	private int spillId;

	@SerializedName("b_pic")
	private String bPic;

	@SerializedName("status")
	private int status;

	public void setPremiumStatus(int premiumStatus){
		this.premiumStatus = premiumStatus;
	}

	public int getPremiumStatus(){
		return premiumStatus;
	}

	public void setCountry(String country){
		this.country = country;
	}

	public String getCountry(){
		return country;
	}

	public void setSocialNetworks(String socialNetworks){
		this.socialNetworks = socialNetworks;
	}

	public String getSocialNetworks(){
		return socialNetworks;
	}

	public void setDPic(String dPic){
		this.dPic = dPic;
	}

	public String getDPic(){
		return dPic;
	}

	public void setAddedTime(String addedTime){
		this.addedTime = addedTime;
	}

	public String getAddedTime(){
		return addedTime;
	}

	public void setEducation(String education){
		this.education = education;
	}

	public String getEducation(){
		return education;
	}

	public void setBirthdate(String birthdate){
		this.birthdate = birthdate;
	}

	public String getBirthdate(){
		return birthdate;
	}

	public void setAgentId(int agentId){
		this.agentId = agentId;
	}

	public int getAgentId(){
		return agentId;
	}

	public void setCity(String city){
		this.city = city;
	}

	public String getCity(){
		return city;
	}

	public void setAbout(String about){
		this.about = about;
	}

	public String getAbout(){
		return about;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setEmployement(String employement){
		this.employement = employement;
	}

	public String getEmployement(){
		return employement;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setAgentStatus(int agentStatus){
		this.agentStatus = agentStatus;
	}

	public int getAgentStatus(){
		return agentStatus;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setState(String state){
		this.state = state;
	}

	public String getState(){
		return state;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setPincode(int pincode){
		this.pincode = pincode;
	}

	public int getPincode(){
		return pincode;
	}

	public void setWebsite(String website){
		this.website = website;
	}

	public String getWebsite(){
		return website;
	}

	public void setSex(String sex){
		this.sex = sex;
	}

	public String getSex(){
		return sex;
	}

	public void setMobile(String mobile){
		this.mobile = mobile;
	}

	public String getMobile(){
		return mobile;
	}

	public void setLastName(String lastName){
		this.lastName = lastName;
	}

	public String getLastName(){
		return lastName;
	}

	public void setHobbies(String hobbies){
		this.hobbies = hobbies;
	}

	public String getHobbies(){
		return hobbies;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setReferralId(int referralId){
		this.referralId = referralId;
	}

	public int getReferralId(){
		return referralId;
	}

	public void setPosition(int position){
		this.position = position;
	}

	public int getPosition(){
		return position;
	}

	public void setCustomerId(int customerId){
		this.customerId = customerId;
	}

	public int getCustomerId(){
		return customerId;
	}

	public void setSpillId(int spillId){
		this.spillId = spillId;
	}

	public int getSpillId(){
		return spillId;
	}

	public void setBPic(String bPic){
		this.bPic = bPic;
	}

	public String getBPic(){
		return bPic;
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
			"TimeLineItem{" +
			"premium_status = '" + premiumStatus + '\'' + 
			",country = '" + country + '\'' + 
			",social_networks = '" + socialNetworks + '\'' + 
			",d_pic = '" + dPic + '\'' + 
			",added_time = '" + addedTime + '\'' + 
			",education = '" + education + '\'' + 
			",birthdate = '" + birthdate + '\'' + 
			",agent_id = '" + agentId + '\'' + 
			",city = '" + city + '\'' + 
			",about = '" + about + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",employement = '" + employement + '\'' + 
			",password = '" + password + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",agent_status = '" + agentStatus + '\'' + 
			",id = '" + id + '\'' + 
			",state = '" + state + '\'' + 
			",email = '" + email + '\'' + 
			",pincode = '" + pincode + '\'' + 
			",website = '" + website + '\'' + 
			",sex = '" + sex + '\'' + 
			",mobile = '" + mobile + '\'' + 
			",last_name = '" + lastName + '\'' + 
			",hobbies = '" + hobbies + '\'' + 
			",name = '" + name + '\'' + 
			",referral_id = '" + referralId + '\'' + 
			",position = '" + position + '\'' + 
			",customer_id = '" + customerId + '\'' + 
			",spill_id = '" + spillId + '\'' + 
			",b_pic = '" + bPic + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}