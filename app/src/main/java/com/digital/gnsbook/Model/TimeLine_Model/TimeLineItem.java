package com.digital.gnsbook.Model.TimeLine_Model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TimeLineItem {


	@SerializedName("name")
	@Expose
	private String name;
	@SerializedName("logo")
	@Expose
	private String logo;
	@SerializedName("company_cat")
	@Expose
	private String companyCat;
	@SerializedName("id")
	@Expose
	private Integer id;
	@SerializedName("customer_id")
	@Expose
	private String customerId;
	@SerializedName("company_id")
	@Expose
	private String companyId;
	@SerializedName("title")
	@Expose
	private String title;
	@SerializedName("description")
	@Expose
	private String description;
	@SerializedName("image")
	@Expose
	private String image;
	@SerializedName("like_count")
	@Expose
	private Integer likeCount;
	@SerializedName("type")
	@Expose
	private Integer type;
	@SerializedName("comment_count")
	@Expose
	private Integer commentCount;
	@SerializedName("created_at")
	@Expose
	private String createdAt;
	@SerializedName("Self_Likes")
	@Expose
	private Integer selfLikes;
	@SerializedName("Likes")
	@Expose
	private List<LikesItem> likes = null;
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
	@SerializedName("sell_type")
	@Expose
	private Integer sellType;
	@SerializedName("updated_at")
	@Expose
	private String updatedAt;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getCompanyCat() {
		return companyCat;
	}

	public void setCompanyCat(String companyCat) {
		this.companyCat = companyCat;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Integer getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(Integer likeCount) {
		this.likeCount = likeCount;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public Integer getSelfLikes() {
		return selfLikes;
	}

	public void setSelfLikes(Integer selfLikes) {
		this.selfLikes = selfLikes;
	}

	public List<LikesItem> getLikes() {
		return likes;
	}

	public void setLikes(List<LikesItem> likes) {
		this.likes = likes;
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

	public Integer getSellType() {
		return sellType;
	}

	public void setSellType(Integer sellType) {
		this.sellType = sellType;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

}