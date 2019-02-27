package null;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class ResultItem{

	@SerializedName("Subscribe_status")
	private int subscribeStatus;

	@SerializedName("plan_type")
	private int planType;

	@SerializedName("amount")
	private int amount;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("offer_date")
	private int offerDate;

	@SerializedName("plan_name")
	private String planName;

	@SerializedName("offer")
	private int offer;

	@SerializedName("offer_validity")
	private int offerValidity;

	@SerializedName("details")
	private String details;

	@SerializedName("id")
	private int id;

	@SerializedName("validity")
	private int validity;

	@SerializedName("subscription_type")
	private int subscriptionType;

	@SerializedName("cid")
	private String cid;

	@SerializedName("status")
	private int status;

	public void setSubscribeStatus(int subscribeStatus){
		this.subscribeStatus = subscribeStatus;
	}

	public int getSubscribeStatus(){
		return subscribeStatus;
	}

	public void setPlanType(int planType){
		this.planType = planType;
	}

	public int getPlanType(){
		return planType;
	}

	public void setAmount(int amount){
		this.amount = amount;
	}

	public int getAmount(){
		return amount;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setOfferDate(int offerDate){
		this.offerDate = offerDate;
	}

	public int getOfferDate(){
		return offerDate;
	}

	public void setPlanName(String planName){
		this.planName = planName;
	}

	public String getPlanName(){
		return planName;
	}

	public void setOffer(int offer){
		this.offer = offer;
	}

	public int getOffer(){
		return offer;
	}

	public void setOfferValidity(int offerValidity){
		this.offerValidity = offerValidity;
	}

	public int getOfferValidity(){
		return offerValidity;
	}

	public void setDetails(String details){
		this.details = details;
	}

	public String getDetails(){
		return details;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setValidity(int validity){
		this.validity = validity;
	}

	public int getValidity(){
		return validity;
	}

	public void setSubscriptionType(int subscriptionType){
		this.subscriptionType = subscriptionType;
	}

	public int getSubscriptionType(){
		return subscriptionType;
	}

	public void setCid(String cid){
		this.cid = cid;
	}

	public String getCid(){
		return cid;
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
			"SubsResultItem{" +
			"subscribe_status = '" + subscribeStatus + '\'' + 
			",plan_type = '" + planType + '\'' + 
			",amount = '" + amount + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",offer_date = '" + offerDate + '\'' + 
			",plan_name = '" + planName + '\'' + 
			",offer = '" + offer + '\'' + 
			",offer_validity = '" + offerValidity + '\'' + 
			",details = '" + details + '\'' + 
			",id = '" + id + '\'' + 
			",validity = '" + validity + '\'' + 
			",subscription_type = '" + subscriptionType + '\'' + 
			",cid = '" + cid + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}