package com.digital.gnsbook.Model.Subscription;


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Compony_chat_model {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("result")
    @Expose
    private List<Result> result = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }
    public class Result {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("customer_id")
        @Expose
        private Integer customerId;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("last_name")
        @Expose
        private String lastName;
        @SerializedName("sex")
        @Expose
        private Object sex;
        @SerializedName("country")
        @Expose
        private String country;
        @SerializedName("state")
        @Expose
        private String state;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("website")
        @Expose
        private Object website;
        @SerializedName("pincode")
        @Expose
        private Integer pincode;
        @SerializedName("about")
        @Expose
        private String about;
        @SerializedName("social_networks")
        @Expose
        private Object socialNetworks;
        @SerializedName("education")
        @Expose
        private Object education;
        @SerializedName("birthdate")
        @Expose
        private String birthdate;
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
        private String employement;
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("company_id")
        @Expose
        private String companyId;
        @SerializedName("channel_id")
        @Expose
        private String channelId;

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

        public Object getSex() {
            return sex;
        }

        public void setSex(Object sex) {
            this.sex = sex;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public Object getWebsite() {
            return website;
        }

        public void setWebsite(Object website) {
            this.website = website;
        }

        public Integer getPincode() {
            return pincode;
        }

        public void setPincode(Integer pincode) {
            this.pincode = pincode;
        }

        public String getAbout() {
            return about;
        }

        public void setAbout(String about) {
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

        public String getBirthdate() {
            return birthdate;
        }

        public void setBirthdate(String birthdate) {
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

        public String getEmployement() {
            return employement;
        }

        public void setEmployement(String employement) {
            this.employement = employement;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getCompanyId() {
            return companyId;
        }

        public void setCompanyId(String companyId) {
            this.companyId = companyId;
        }

        public String getChannelId() {
            return channelId;
        }

        public void setChannelId(String channelId) {
            this.channelId = channelId;
        }

    }
}
