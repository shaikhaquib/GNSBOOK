package com.digital.gnsbook.Model.TimeLine_Model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TimeLineResponse {

	@SerializedName("type")
	@Expose
	private Integer type;
	@SerializedName("result")
	@Expose
	private List<TimeLineItem> result = null;
	@SerializedName("sell_type")
	@Expose
	private Integer sellType;
	@SerializedName("product_name")
	@Expose
	private String productName;
	@SerializedName("product_cat")
	@Expose
	private String productCat;
	@SerializedName("product_price")
	@Expose
	private String productPrice;
	@SerializedName("product_desc")
	@Expose
	private String productDesc;
	@SerializedName("product_link")
	@Expose
	private String productLink;
	@SerializedName("images")
	@Expose
	private String images;
	@SerializedName("updated_at")
	@Expose
	private String updatedAt;
	@SerializedName("description")
	@Expose
	private String description;
	@SerializedName("image")
	@Expose
	private String image;
	@SerializedName("title")
	@Expose
	private String title;
	@SerializedName("customer_id")
	@Expose
	private String customerId;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public List<TimeLineItem> getResult() {
		return result;
	}

	public void setResult(List<TimeLineItem> result) {
		this.result = result;
	}

	public Integer getSellType() {
		return sellType;
	}

	public void setSellType(Integer sellType) {
		this.sellType = sellType;
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

	public String getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(String productPrice) {
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

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

}