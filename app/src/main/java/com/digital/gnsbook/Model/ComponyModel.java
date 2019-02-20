package com.digital.gnsbook.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ComponyModel {
    @SerializedName("result")
    @Expose
    private List<Result> result = null;
    @SerializedName("status")
    @Expose
    private Boolean status;

    public class Result {
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("admin_id")
        @Expose
        private Integer adminId;
        @SerializedName("banner")
        @Expose
        private String banner;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("company_id")
        @Expose
        private String companyId;
        @SerializedName("company_type")
        @Expose
        private String companyType;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("introduction")
        @Expose
        private String introduction;
        @SerializedName("location_link")
        @Expose
        private Object locationLink;
        @SerializedName("logo")
        @Expose
        private String logo;
        @SerializedName("mobile")
        @Expose
        private String mobile;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("social_networks")
        @Expose
        private Object socialNetworks;
        @SerializedName("state")
        @Expose
        private String state;
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("web")
        @Expose
        private Object web;
        @SerializedName("working_hours")
        @Expose
        private String workingHours;

        public Integer getId() {
            return this.id;
        }

        public void setId(Integer num) {
            this.id = num;
        }

        public String getCompanyId() {
            return this.companyId;
        }

        public void setCompanyId(String str) {
            this.companyId = str;
        }

        public String getCompanyType() {
            return this.companyType;
        }

        public void setCompanyType(String str) {
            this.companyType = str;
        }

        public Integer getAdminId() {
            return this.adminId;
        }

        public void setAdminId(Integer num) {
            this.adminId = num;
        }

        public String getMobile() {
            return this.mobile;
        }

        public void setMobile(String str) {
            this.mobile = str;
        }

        public String getEmail() {
            return this.email;
        }

        public void setEmail(String str) {
            this.email = str;
        }

        public String getDescription() {
            return this.description;
        }

        public void setDescription(String str) {
            this.description = str;
        }

        public String getIntroduction() {
            return this.introduction;
        }

        public void setIntroduction(String str) {
            this.introduction = str;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String str) {
            this.name = str;
        }

        public String getAddress() {
            return this.address;
        }

        public void setAddress(String str) {
            this.address = str;
        }

        public String getState() {
            return this.state;
        }

        public void setState(String str) {
            this.state = str;
        }

        public String getCity() {
            return this.city;
        }

        public void setCity(String str) {
            this.city = str;
        }

        public Object getWeb() {
            return this.web;
        }

        public void setWeb(Object obj) {
            this.web = obj;
        }

        public Object getLocationLink() {
            return this.locationLink;
        }

        public void setLocationLink(Object obj) {
            this.locationLink = obj;
        }

        public Object getSocialNetworks() {
            return this.socialNetworks;
        }

        public void setSocialNetworks(Object obj) {
            this.socialNetworks = obj;
        }

        public String getWorkingHours() {
            return this.workingHours;
        }

        public void setWorkingHours(String str) {
            this.workingHours = str;
        }

        public String getLogo() {
            return this.logo;
        }

        public void setLogo(String str) {
            this.logo = str;
        }

        public String getBanner() {
            return this.banner;
        }

        public void setBanner(String str) {
            this.banner = str;
        }

        public Integer getStatus() {
            return this.status;
        }

        public void setStatus(Integer num) {
            this.status = num;
        }

        public String getCreatedAt() {
            return this.createdAt;
        }

        public void setCreatedAt(String str) {
            this.createdAt = str;
        }

        public String getUpdatedAt() {
            return this.updatedAt;
        }

        public void setUpdatedAt(String str) {
            this.updatedAt = str;
        }
    }

    public Boolean getStatus() {
        return this.status;
    }

    public void setStatus(Boolean bool) {
        this.status = bool;
    }

    public List<Result> getResult() {
        return this.result;
    }

    public void setResult(List<Result> list) {
        this.result = list;
    }
}
