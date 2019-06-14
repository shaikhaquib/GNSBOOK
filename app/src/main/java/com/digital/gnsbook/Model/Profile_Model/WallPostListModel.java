package com.digital.gnsbook.Model.Profile_Model;

import java.util.List;

import com.digital.gnsbook.Model.TimeLine_Model.TimeLineItem;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WallPostListModel {

@SerializedName("status")
@Expose
private Boolean status;
@SerializedName("result")
@Expose
private List<TimeLineItem> result = null;

public Boolean getStatus() {
return status;
}

public void setStatus(Boolean status) {
this.status = status;
}

public List<TimeLineItem> getResult() {
return result;
}

public void setResult(List<TimeLineItem> result) {
this.result = result;
}

}