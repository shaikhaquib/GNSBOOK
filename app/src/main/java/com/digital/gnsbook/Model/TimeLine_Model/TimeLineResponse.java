package com.digital.gnsbook.Model.TimeLine_Model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TimeLineResponse {

	@SerializedName("type")
	@Expose
	private String type;
	@SerializedName("result")
	@Expose
	private List<TimeLineItem> result = null;
	@SerializedName("sell_type")
	@Expose
	private Integer sellType;
	@SerializedName("product_name")
	@Expose
	private Object productName;
	@SerializedName("product_cat")
	@Expose
	private Object productCat;
	@SerializedName("product_price")
	@Expose
	private Object productPrice;
	@SerializedName("product_desc")
	@Expose
	private Object productDesc;
	@SerializedName("product_link")
	@Expose
	private Object productLink;
	@SerializedName("images")
	@Expose
	private Object images;
	@SerializedName("updated_at")
	@Expose
	private Object updatedAt;
	@SerializedName("description")
	@Expose
	private Object description;
	@SerializedName("image")
	@Expose
	private Object image;
	@SerializedName("title")
	@Expose
	private Object title;
	@SerializedName("customer_id")
	@Expose
	private Object customerId;

	public String getType() {
		return type;
	}

	public void setType(String type) {
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

	public Object getProductName() {
		return productName;
	}

	public void setProductName(Object productName) {
		this.productName = productName;
	}

	public Object getProductCat() {
		return productCat;
	}

	public void setProductCat(Object productCat) {
		this.productCat = productCat;
	}

	public Object getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(Object productPrice) {
		this.productPrice = productPrice;
	}

	public Object getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(Object productDesc) {
		this.productDesc = productDesc;
	}

	public Object getProductLink() {
		return productLink;
	}

	public void setProductLink(Object productLink) {
		this.productLink = productLink;
	}

	public Object getImages() {
		return images;
	}

	public void setImages(Object images) {
		this.images = images;
	}

	public Object getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Object updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Object getDescription() {
		return description;
	}

	public void setDescription(Object description) {
		this.description = description;
	}

	public Object getImage() {
		return image;
	}

	public void setImage(Object image) {
		this.image = image;
	}

	public Object getTitle() {
		return title;
	}

	public void setTitle(Object title) {
		this.title = title;
	}

	public Object getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Object customerId) {
		this.customerId = customerId;
	}

}