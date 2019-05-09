package com.digital.gnsbook.Model.TimeLine_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Suggestion {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("customer_id")
    @Expose
    private Integer customerId;
    @SerializedName("referral_id")
    @Expose
    private Integer referralId;
    @SerializedName("spill_id")
    @Expose
    private Integer spillId;
    @SerializedName("position")
    @Expose
    private Integer position;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("premium_status")
    @Expose
    private Integer premiumStatus;
    @SerializedName("agent_id")
    @Expose
    private Integer agentId;
    @SerializedName("agent_status")
    @Expose
    private Integer agentStatus;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("added_time")
    @Expose
    private String addedTime;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("sex")
    @Expose
    private String sex;
    @SerializedName("country")
    @Expose
    private Object country;
    @SerializedName("state")
    @Expose
    private Object state;
    @SerializedName("city")
    @Expose
    private Object city;
    @SerializedName("website")
    @Expose
    private Object website;
    @SerializedName("pincode")
    @Expose
    private Object pincode;
    @SerializedName("about")
    @Expose
    private Object about;
    @SerializedName("social_networks")
    @Expose
    private Object socialNetworks;
    @SerializedName("education")
    @Expose
    private Object education;
    @SerializedName("birthdate")
    @Expose
    private Object birthdate;
    @SerializedName("hobbies")
    @Expose
    private Object hobbies;
    @SerializedName("d_pic")
    @Expose
    private String dPic;
    @SerializedName("b_pic")
    @Expose
    private String bPic;
    @SerializedName("employement")
    @Expose
    private Object employement;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getReferralId() {
        return referralId;
    }

    public void setReferralId(Integer referralId) {
        this.referralId = referralId;
    }

    public Integer getSpillId() {
        return spillId;
    }

    public void setSpillId(Integer spillId) {
        this.spillId = spillId;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getPremiumStatus() {
        return premiumStatus;
    }

    public void setPremiumStatus(Integer premiumStatus) {
        this.premiumStatus = premiumStatus;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public Integer getAgentStatus() {
        return agentStatus;
    }

    public void setAgentStatus(Integer agentStatus) {
        this.agentStatus = agentStatus;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAddedTime() {
        return addedTime;
    }

    public void setAddedTime(String addedTime) {
        this.addedTime = addedTime;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Object getCountry() {
        return country;
    }

    public void setCountry(Object country) {
        this.country = country;
    }

    public Object getState() {
        return state;
    }

    public void setState(Object state) {
        this.state = state;
    }

    public Object getCity() {
        return city;
    }

    public void setCity(Object city) {
        this.city = city;
    }

    public Object getWebsite() {
        return website;
    }

    public void setWebsite(Object website) {
        this.website = website;
    }

    public Object getPincode() {
        return pincode;
    }

    public void setPincode(Object pincode) {
        this.pincode = pincode;
    }

    public Object getAbout() {
        return about;
    }

    public void setAbout(Object about) {
        this.about = about;
    }

    public Object getSocialNetworks() {
        return socialNetworks;
    }

    public void setSocialNetworks(Object socialNetworks) {
        this.socialNetworks = socialNetworks;
    }

    public Object getEducation() {
        return education;
    }

    public void setEducation(Object education) {
        this.education = education;
    }

    public Object getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Object birthdate) {
        this.birthdate = birthdate;
    }

    public Object getHobbies() {
        return hobbies;
    }

    public void setHobbies(Object hobbies) {
        this.hobbies = hobbies;
    }

    public String getDPic() {
        return dPic;
    }

    public void setDPic(String dPic) {
        this.dPic = dPic;
    }

    public String getBPic() {
        return bPic;
    }

    public void setBPic(String bPic) {
        this.bPic = bPic;
    }

    public Object getEmployement() {
        return employement;
    }

    public void setEmployement(Object employement) {
        this.employement = employement;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}