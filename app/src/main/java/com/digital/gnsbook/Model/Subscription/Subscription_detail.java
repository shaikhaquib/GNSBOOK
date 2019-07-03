package com.digital.gnsbook.Model.Subscription;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Subscription_detail {

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
        @SerializedName("cid")
        @Expose
        private String cid;
        @SerializedName("plan_type")
        @Expose
        private Integer planType;
        @SerializedName("plan_name")
        @Expose
        private String planName;
        @SerializedName("amount")
        @Expose
        private Integer amount;
        @SerializedName("details")
        @Expose
        private String details;
        @SerializedName("validity")
        @Expose
        private Integer validity;
        @SerializedName("subscription_type")
        @Expose
        private Integer subscriptionType;
        @SerializedName("offer")
        @Expose
        private Integer offer;
        @SerializedName("offer_date")
        @Expose
        private Integer offerDate;
        @SerializedName("offer_validity")
        @Expose
        private Integer offerValidity;
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("customer_id")
        @Expose
        private String customerId;
        @SerializedName("sid")
        @Expose
        private String sid;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("company_id")
        @Expose
        private String companyId;
        @SerializedName("company_type")
        @Expose
        private String companyType;
        @SerializedName("company_cat")
        @Expose
        private String companyCat;
        @SerializedName("admin_id")
        @Expose
        private Integer adminId;
        @SerializedName("mobile")
        @Expose
        private String mobile;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("introduction")
        @Expose
        private String introduction;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("state")
        @Expose
        private String state;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("web")
        @Expose
        private String web;
        @SerializedName("location_link")
        @Expose
        private Object locationLink;
        @SerializedName("social_networks")
        @Expose
        private Object socialNetworks;
        @SerializedName("working_hours")
        @Expose
        private String workingHours;
        @SerializedName("logo")
        @Expose
        private String logo;
        @SerializedName("banner")
        @Expose
        private String banner;
        @SerializedName("pdf_file")
        @Expose
        private Object pdfFile;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public Integer getPlanType() {
            return planType;
        }

        public void setPlanType(Integer planType) {
            this.planType = planType;
        }

        public String getPlanName() {
            return planName;
        }

        public void setPlanName(String planName) {
            this.planName = planName;
        }

        public Integer getAmount() {
            return amount;
        }

        public void setAmount(Integer amount) {
            this.amount = amount;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }

        public Integer getValidity() {
            return validity;
        }

        public void setValidity(Integer validity) {
            this.validity = validity;
        }

        public Integer getSubscriptionType() {
            return subscriptionType;
        }

        public void setSubscriptionType(Integer subscriptionType) {
            this.subscriptionType = subscriptionType;
        }

        public Integer getOffer() {
            return offer;
        }

        public void setOffer(Integer offer) {
            this.offer = offer;
        }

        public Integer getOfferDate() {
            return offerDate;
        }

        public void setOfferDate(Integer offerDate) {
            this.offerDate = offerDate;
        }

        public Integer getOfferValidity() {
            return offerValidity;
        }

        public void setOfferValidity(Integer offerValidity) {
            this.offerValidity = offerValidity;
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

        public String getCustomerId() {
            return customerId;
        }

        public void setCustomerId(String customerId) {
            this.customerId = customerId;
        }

        public String getSid() {
            return sid;
        }

        public void setSid(String sid) {
            this.sid = sid;
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

        public String getCompanyType() {
            return companyType;
        }

        public void setCompanyType(String companyType) {
            this.companyType = companyType;
        }

        public String getCompanyCat() {
            return companyCat;
        }

        public void setCompanyCat(String companyCat) {
            this.companyCat = companyCat;
        }

        public Integer getAdminId() {
            return adminId;
        }

        public void setAdminId(Integer adminId) {
            this.adminId = adminId;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
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

        public String getWeb() {
            return web;
        }

        public void setWeb(String web) {
            this.web = web;
        }

        public Object getLocationLink() {
            return locationLink;
        }

        public void setLocationLink(Object locationLink) {
            this.locationLink = locationLink;
        }

        public Object getSocialNetworks() {
            return socialNetworks;
        }

        public void setSocialNetworks(Object socialNetworks) {
            this.socialNetworks = socialNetworks;
        }

        public String getWorkingHours() {
            return workingHours;
        }

        public void setWorkingHours(String workingHours) {
            this.workingHours = workingHours;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getBanner() {
            return banner;
        }

        public void setBanner(String banner) {
            this.banner = banner;
        }

        public Object getPdfFile() {
            return pdfFile;
        }

        public void setPdfFile(Object pdfFile) {
            this.pdfFile = pdfFile;
        }

    }
}