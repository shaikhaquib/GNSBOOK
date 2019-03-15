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

    public class Result{

        @SerializedName("company_type")
        private String companyType;

        @SerializedName("social_networks")
        private String socialNetworks;

        @SerializedName("location_link")
        private String locationLink;

        @SerializedName("pdf_file")
        private String pdfFile;

        @SerializedName("company_cat")
        private String companyCat;

        @SerializedName("address")
        private String address;

        @SerializedName("company_id")
        private String companyId;

        @SerializedName("city")
        private String city;

        @SerializedName("mobile")
        private String mobile;

        @SerializedName("description")
        private String description;

        @SerializedName("banner")
        private String banner;

        @SerializedName("created_at")
        private String createdAt;

        @SerializedName("updated_at")
        private String updatedAt;

        @SerializedName("web")
        private String web;

        @SerializedName("working_hours")
        private String workingHours;

        @SerializedName("admin_id")
        private int adminId;

        @SerializedName("name")
        private String name;

        @SerializedName("logo")
        private String logo;

        @SerializedName("id")
        private int id;

        @SerializedName("state")
        private String state;

        @SerializedName("email")
        private String email;

        @SerializedName("introduction")
        private String introduction;

        @SerializedName("status")
        private int status;

        public void setCompanyType(String companyType){
            this.companyType = companyType;
        }

        public String getCompanyType(){
            return companyType;
        }

        public void setSocialNetworks(String socialNetworks){
            this.socialNetworks = socialNetworks;
        }

        public String getSocialNetworks(){
            return socialNetworks;
        }

        public void setLocationLink(String locationLink){
            this.locationLink = locationLink;
        }

        public String getLocationLink(){
            return locationLink;
        }

        public void setPdfFile(String pdfFile){
            this.pdfFile = pdfFile;
        }

        public String getPdfFile(){
            return pdfFile;
        }

        public void setCompanyCat(String companyCat){
            this.companyCat = companyCat;
        }

        public String getCompanyCat(){
            return companyCat;
        }

        public void setAddress(String address){
            this.address = address;
        }

        public String getAddress(){
            return address;
        }

        public void setCompanyId(String companyId){
            this.companyId = companyId;
        }

        public String getCompanyId(){
            return companyId;
        }

        public void setCity(String city){
            this.city = city;
        }

        public String getCity(){
            return city;
        }

        public void setMobile(String mobile){
            this.mobile = mobile;
        }

        public String getMobile(){
            return mobile;
        }

        public void setDescription(String description){
            this.description = description;
        }

        public String getDescription(){
            return description;
        }

        public void setBanner(String banner){
            this.banner = banner;
        }

        public String getBanner(){
            return banner;
        }

        public void setCreatedAt(String createdAt){
            this.createdAt = createdAt;
        }

        public String getCreatedAt(){
            return createdAt;
        }

        public void setUpdatedAt(String updatedAt){
            this.updatedAt = updatedAt;
        }

        public String getUpdatedAt(){
            return updatedAt;
        }

        public void setWeb(String web){
            this.web = web;
        }

        public String getWeb(){
            return web;
        }

        public void setWorkingHours(String workingHours){
            this.workingHours = workingHours;
        }

        public String getWorkingHours(){
            return workingHours;
        }

        public void setAdminId(int adminId){
            this.adminId = adminId;
        }

        public int getAdminId(){
            return adminId;
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

        public void setIntroduction(String introduction){
            this.introduction = introduction;
        }

        public String getIntroduction(){
            return introduction;
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
                    "CartItem{" +
                            "company_type = '" + companyType + '\'' +
                            ",social_networks = '" + socialNetworks + '\'' +
                            ",location_link = '" + locationLink + '\'' +
                            ",pdf_file = '" + pdfFile + '\'' +
                            ",company_cat = '" + companyCat + '\'' +
                            ",address = '" + address + '\'' +
                            ",company_id = '" + companyId + '\'' +
                            ",city = '" + city + '\'' +
                            ",mobile = '" + mobile + '\'' +
                            ",description = '" + description + '\'' +
                            ",banner = '" + banner + '\'' +
                            ",created_at = '" + createdAt + '\'' +
                            ",updated_at = '" + updatedAt + '\'' +
                            ",web = '" + web + '\'' +
                            ",working_hours = '" + workingHours + '\'' +
                            ",admin_id = '" + adminId + '\'' +
                            ",name = '" + name + '\'' +
                            ",logo = '" + logo + '\'' +
                            ",id = '" + id + '\'' +
                            ",state = '" + state + '\'' +
                            ",email = '" + email + '\'' +
                            ",introduction = '" + introduction + '\'' +
                            ",status = '" + status + '\'' +
                            "}";
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
