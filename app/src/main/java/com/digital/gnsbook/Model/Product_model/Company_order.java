package com.digital.gnsbook.Model.Product_model;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Company_order {

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


    public class Result implements Serializable {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("product_id")
        @Expose
        private Integer productId;
        @SerializedName("customer_id")
        @Expose
        private String customerId;
        @SerializedName("company_id")
        @Expose
        private String companyId;
        @SerializedName("quantity")
        @Expose
        private Integer quantity;
        @SerializedName("amount")
        @Expose
        private Integer amount;
        @SerializedName("address_id")
        @Expose
        private String addressId;
        @SerializedName("order_status")
        @Expose
        private Integer orderStatus;
        @SerializedName("order_id")
        @Expose
        private String orderId;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("product_name")
        @Expose
        private String productName;
        @SerializedName("product_cat")
        @Expose
        private String productCat;
        @SerializedName("product_price")
        @Expose
        private Integer productPrice;
        @SerializedName("product_desc")
        @Expose
        private String productDesc;
        @SerializedName("product_link")
        @Expose
        private String productLink;
        @SerializedName("images")
        @Expose
        private String images;
        @SerializedName("like_count")
        @Expose
        private Integer likeCount;
        @SerializedName("comment_count")
        @Expose
        private Integer commentCount;
        @SerializedName("type")
        @Expose
        private Integer type;
        @SerializedName("reward_points")
        @Expose
        private Integer rewardPoints;
        @SerializedName("sell_type")
        @Expose
        private Integer sellType;
        @SerializedName("customer_name")
        @Expose
        private String customerName;
        @SerializedName("phone_number")
        @Expose
        private String phoneNumber;
        @SerializedName("landmark")
        @Expose
        private String landmark;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("state")
        @Expose
        private String state;
        @SerializedName("country")
        @Expose
        private String country;
        @SerializedName("apartment_name")
        @Expose
        private String apartmentName;
        @SerializedName("zipcode")
        @Expose
        private Integer zipcode;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getProductId() {
            return productId;
        }

        public void setProductId(Integer productId) {
            this.productId = productId;
        }

        public String getCustomerId() {
            return customerId;
        }

        public void setCustomerId(String customerId) {
            this.customerId = customerId;
        }

        public String getCompanyId() {
            return companyId;
        }

        public void setCompanyId(String companyId) {
            this.companyId = companyId;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        public Integer getAmount() {
            return amount;
        }

        public void setAmount(Integer amount) {
            this.amount = amount;
        }

        public String getAddressId() {
            return addressId;
        }

        public void setAddressId(String addressId) {
            this.addressId = addressId;
        }

        public Integer getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(Integer orderStatus) {
            this.orderStatus = orderStatus;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
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

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductCat() {
            return productCat;
        }

        public void setProductCat(String productCat) {
            this.productCat = productCat;
        }

        public Integer getProductPrice() {
            return productPrice;
        }

        public void setProductPrice(Integer productPrice) {
            this.productPrice = productPrice;
        }

        public String getProductDesc() {
            return productDesc;
        }

        public void setProductDesc(String productDesc) {
            this.productDesc = productDesc;
        }

        public String getProductLink() {
            return productLink;
        }

        public void setProductLink(String productLink) {
            this.productLink = productLink;
        }

        public String getImages() {
            return images;
        }

        public void setImages(String images) {
            this.images = images;
        }

        public Integer getLikeCount() {
            return likeCount;
        }

        public void setLikeCount(Integer likeCount) {
            this.likeCount = likeCount;
        }

        public Integer getCommentCount() {
            return commentCount;
        }

        public void setCommentCount(Integer commentCount) {
            this.commentCount = commentCount;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public Integer getRewardPoints() {
            return rewardPoints;
        }

        public void setRewardPoints(Integer rewardPoints) {
            this.rewardPoints = rewardPoints;
        }

        public Integer getSellType() {
            return sellType;
        }

        public void setSellType(Integer sellType) {
            this.sellType = sellType;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getLandmark() {
            return landmark;
        }

        public void setLandmark(String landmark) {
            this.landmark = landmark;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getApartmentName() {
            return apartmentName;
        }

        public void setApartmentName(String apartmentName) {
            this.apartmentName = apartmentName;
        }

        public Integer getZipcode() {
            return zipcode;
        }

        public void setZipcode(Integer zipcode) {
            this.zipcode = zipcode;
        }

    }
}