package com.digital.gnsbook.Model;

import com.google.gson.annotations.SerializedName;

public class CartItem {

	public int prdamount;
	public int basicamt;
	public int prdminteger;

	@SerializedName("product_desc")
	private String productDesc;

	@SerializedName("images")
	private String images;

	@SerializedName("amount")
	private int amount;

	@SerializedName("quantity")
	private int quantity;

	@SerializedName("product_id")
	private int productId;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("product_price")
	private int productPrice;

	@SerializedName("customer_id")
	private String customerId;

	@SerializedName("product_name")
	private String productName;

	public void setProductDesc(String productDesc){
		this.productDesc = productDesc;
	}

	public String getProductDesc(){
		return productDesc;
	}

	public void setImages(String images){
		this.images = images;
	}

	public String getImages(){
		return images;
	}

	public void setAmount(int amount){
		this.amount = amount;
	}

	public int getAmount(){
		return amount;
	}

	public void setQuantity(int quantity){
		this.quantity = quantity;
	}

	public int getQuantity(){
		return quantity;
	}

	public void setProductId(int productId){
		this.productId = productId;
	}

	public int getProductId(){
		return productId;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setProductPrice(int productPrice){
		this.productPrice = productPrice;
	}

	public int getProductPrice(){
		return productPrice;
	}

	public void setCustomerId(String customerId){
		this.customerId = customerId;
	}

	public String getCustomerId(){
		return customerId;
	}

	public void setProductName(String productName){
		this.productName = productName;
	}

	public String getProductName(){
		return productName;
	}

	@Override
	public String toString(){
		return
				"FriendItem{" +
						"product_desc = '" + productDesc + '\'' +
						",images = '" + images + '\'' +
						",amount = '" + amount + '\'' +
						",quantity = '" + quantity + '\'' +
						",product_id = '" + productId + '\'' +
						",created_at = '" + createdAt + '\'' +
						",product_price = '" + productPrice + '\'' +
						",customer_id = '" + customerId + '\'' +
						",product_name = '" + productName + '\'' +
						"}";
	}
}