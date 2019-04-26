package com.digital.gnsbook.Fragment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LikesItem{

	@SerializedName("id")
	@Expose
	private Integer id;
	@SerializedName("customer_id")
	@Expose
	private Integer customerId;
	@SerializedName("name")
	@Expose
	private String name;
	@SerializedName("d_pic")
	@Expose
	private String dPic;

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

	public String getDPic() {
		return dPic;
	}

	public void setDPic(String dPic) {
		this.dPic = dPic;
	}

}