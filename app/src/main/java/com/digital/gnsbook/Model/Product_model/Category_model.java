package com.digital.gnsbook.Model.Product_model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Category_model {

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

        @SerializedName("product_cat")
        @Expose
        private String productCat;

        public String getProductCat() {
            return productCat;
        }

        public void setProductCat(String productCat) {
            this.productCat = productCat;
        }

}



}